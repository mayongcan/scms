/*
 * Copyright(c) 2018 gimplatform(通用信息管理平台) All rights reserved.
 */
package com.scms.modules.finance.repository.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections4.MapUtils;

import com.gimplatform.core.common.SqlParams;
import com.gimplatform.core.repository.BaseRepository;
import com.gimplatform.core.utils.BeanUtils;
import com.gimplatform.core.utils.StringUtils;
import com.scms.modules.finance.entity.ScmsFinanceFlow;
import com.scms.modules.finance.repository.custom.ScmsFinanceFlowRepositoryCustom;

public class ScmsFinanceFlowRepositoryImpl extends BaseRepository implements ScmsFinanceFlowRepositoryCustom{

	private static final String SQL_GET_LIST = "SELECT tb.ID as \"id\", tb.MERCHANTS_ID as \"merchantsId\", tb.SHOP_ID as \"shopId\", "
	        + "tb.FLOW_TYPE as \"flowType\", tb.EXPENSES_ID as \"expensesId\", tb.ORDER_ID as \"orderId\", tb.ORDER_NUM as \"orderNum\", tb.ORDER_TYPE as \"orderType\", "
	        + "tb.PAY_TYPE_ID as \"payTypeId\", tb.PAY_TYPE_NAME as \"payTypeName\", tb.INCOME_TYPE as \"incomeType\", tb.PAY_AMOUNT as \"payAmount\", tb.PAY_MEMO as \"payMemo\", "
	        + "tb.CREATE_BY as \"createBy\", tb.CREATE_BY_NAME as \"createByName\", tb.CREATE_DATE as \"createDate\", tb.VALID_REASON as \"validReason\", tb.IS_VALID as \"isValid\","
	        + "si.SHOP_NAME as \"shopName\" "
			+ "FROM scms_finance_flow tb left join scms_shop_info si on si.ID = tb.SHOP_ID "
			+ "WHERE 1 = 1 ";

	private static final String SQL_GET_LIST_COUNT = "SELECT count(1) as \"count\" "
	        + "FROM scms_finance_flow tb left join scms_shop_info si on si.ID = tb.SHOP_ID "
			+ "WHERE 1 = 1 ";
	
	public List<Map<String, Object>> getList(ScmsFinanceFlow scmsFinanceFlow, Map<String, Object> params, int pageIndex, int pageSize) {
		//生成查询条件
		SqlParams sqlParams = genListWhere(SQL_GET_LIST, scmsFinanceFlow, params);
		//添加分页和排序
		sqlParams = getPageableSql(sqlParams, pageIndex, pageSize, " tb.ID DESC ", " \"id\" DESC ");
		return getResultList(sqlParams);
	}

	public int getListCount(ScmsFinanceFlow scmsFinanceFlow, Map<String, Object> params) {
		//生成查询条件
		SqlParams sqlParams = genListWhere(SQL_GET_LIST_COUNT, scmsFinanceFlow, params);
		return getResultListTotalCount(sqlParams);
	}

	/**
	 * 生成查询条件
	 * @param sql
	 * @param params
	 * @return
	 */
	private SqlParams genListWhere(String sql, ScmsFinanceFlow scmsFinanceFlow, Map<String, Object> params){
		SqlParams sqlParams = new SqlParams();
		sqlParams.querySql.append(sql);
		if (scmsFinanceFlow != null && scmsFinanceFlow.getMerchantsId() != null) {
			sqlParams.querySql.append(" AND tb.MERCHANTS_ID = :merchantsId ");
			sqlParams.paramsList.add("merchantsId");
			sqlParams.valueList.add(scmsFinanceFlow.getMerchantsId());
		}
		if (scmsFinanceFlow != null && scmsFinanceFlow.getShopId() != null) {
			sqlParams.querySql.append(" AND tb.SHOP_ID = :shopId ");
			sqlParams.paramsList.add("shopId");
			sqlParams.valueList.add(scmsFinanceFlow.getShopId());
		}
		String orderNum = MapUtils.getString(params, "orderNum");
		String createByName = MapUtils.getString(params, "createByName");
        String isValid = MapUtils.getString(params, "isValid");
		if(!StringUtils.isBlank(orderNum)) {
            sqlParams.querySql.append(getLikeSql("tb.ORDER_NUM", ":orderNum"));
            sqlParams.paramsList.add("orderNum");
            sqlParams.valueList.add(orderNum);
		}
		if(!StringUtils.isBlank(createByName)) {
            sqlParams.querySql.append(getLikeSql("tb.CREATE_BY_NAME", ":createByName"));
            sqlParams.paramsList.add("createByName");
            sqlParams.valueList.add(createByName);
        }
        if(!StringUtils.isBlank(isValid)) {
            sqlParams.querySql.append(" AND tb.IS_VALID = :isValid ");
            sqlParams.paramsList.add("isValid");
            sqlParams.valueList.add(isValid);
        }
        if (!StringUtils.isBlank(MapUtils.getString(params, "createDateBegin")) && !StringUtils.isBlank(MapUtils.getString(params, "createDateEnd"))) {
            sqlParams.querySql.append(" AND tb.CREATE_DATE between :createDateBegin AND :createDateEnd ");
            sqlParams.paramsList.add("createDateBegin");
            sqlParams.paramsList.add("createDateEnd");
            sqlParams.valueList.add(MapUtils.getString(params, "createDateBegin"));
            sqlParams.valueList.add(MapUtils.getString(params, "createDateEnd"));
        }
        return sqlParams;
	}

    @Override
    public List<Map<String, Object>> getFinanceFlowStatistics(Map<String, Object> params) {
        ScmsFinanceFlow scmsFinanceFlow = (ScmsFinanceFlow) BeanUtils.mapToBean(params, ScmsFinanceFlow.class);
        SqlParams sqlIncome = genStatisticsListWhere("SELECT sum(tb.PAY_AMOUNT) as \"count\" FROM scms_finance_flow tb WHERE tb.IS_VALID = 'Y' AND tb.INCOME_TYPE = '1' ", scmsFinanceFlow, params);
        SqlParams sqlExpend = genStatisticsListWhere("SELECT sum(tb.PAY_AMOUNT) as \"count\" FROM scms_finance_flow tb WHERE tb.IS_VALID = 'Y' AND tb.INCOME_TYPE = '2' ", scmsFinanceFlow, params);
        int income = getResultListTotalCount(sqlIncome);
        int expend = getResultListTotalCount(sqlExpend);
        List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("income", income);
        map.put("expend", expend);
        map.put("all", income - expend);
        list.add(map);
        return list;
    }
    
    private SqlParams genStatisticsListWhere(String sql, ScmsFinanceFlow scmsFinanceFlow, Map<String, Object> params){
        SqlParams sqlParams = new SqlParams();
        sqlParams.querySql.append(sql);
        if (scmsFinanceFlow != null && scmsFinanceFlow.getMerchantsId() != null) {
            sqlParams.querySql.append(" AND tb.MERCHANTS_ID = :merchantsId ");
            sqlParams.paramsList.add("merchantsId");
            sqlParams.valueList.add(scmsFinanceFlow.getMerchantsId());
        }
        if (scmsFinanceFlow != null && scmsFinanceFlow.getShopId() != null) {
            sqlParams.querySql.append(" AND tb.SHOP_ID = :shopId ");
            sqlParams.paramsList.add("shopId");
            sqlParams.valueList.add(scmsFinanceFlow.getShopId());
        }
        String orderNum = MapUtils.getString(params, "orderNum");
        String createByName = MapUtils.getString(params, "createByName");
        if(!StringUtils.isBlank(orderNum)) {
            sqlParams.querySql.append(getLikeSql("tb.ORDER_NUM", ":orderNum"));
            sqlParams.paramsList.add("orderNum");
            sqlParams.valueList.add(orderNum);
        }
        if(!StringUtils.isBlank(createByName)) {
            sqlParams.querySql.append(getLikeSql("tb.CREATE_BY_NAME", ":createByName"));
            sqlParams.paramsList.add("createByName");
            sqlParams.valueList.add(createByName);
        }
        if (!StringUtils.isBlank(MapUtils.getString(params, "createDateBegin")) && !StringUtils.isBlank(MapUtils.getString(params, "createDateEnd"))) {
            sqlParams.querySql.append(" AND tb.CREATE_DATE between :createDateBegin AND :createDateEnd ");
            sqlParams.paramsList.add("createDateBegin");
            sqlParams.paramsList.add("createDateEnd");
            sqlParams.valueList.add(MapUtils.getString(params, "createDateBegin"));
            sqlParams.valueList.add(MapUtils.getString(params, "createDateEnd"));
        }
        return sqlParams;
    }
}