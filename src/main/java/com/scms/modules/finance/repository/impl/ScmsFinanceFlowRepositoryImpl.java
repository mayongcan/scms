/*
 * Copyright(c) 2018 gimplatform(通用信息管理平台) All rights reserved.
 */
package com.scms.modules.finance.repository.impl;

import java.util.List;
import java.util.Map;
import com.gimplatform.core.common.SqlParams;
import com.gimplatform.core.repository.BaseRepository;
import com.scms.modules.finance.entity.ScmsFinanceFlow;
import com.scms.modules.finance.repository.custom.ScmsFinanceFlowRepositoryCustom;

public class ScmsFinanceFlowRepositoryImpl extends BaseRepository implements ScmsFinanceFlowRepositoryCustom{

	private static final String SQL_GET_LIST = "SELECT tb.ID as \"id\", tb.MERCHANTS_ID as \"merchantsId\", tb.SHOP_ID as \"shopId\", "
	        + "tb.FLOW_TYPE as \"flowType\", tb.EXPENSES_ID as \"expensesId\", tb.ORDER_NUM as \"orderNum\", tb.ORDER_TYPE as \"orderType\", "
	        + "tb.PAY_TYPE_ID as \"payTypeId\", tb.PAY_TYPE_NAME as \"payTypeName\", tb.PAY_AMOUNT as \"payAmount\", tb.PAY_MEMO as \"payMemo\", "
	        + "tb.CREATE_BY as \"createBy\", tb.CREATE_BY_NAME as \"createByName\", tb.CREATE_DATE as \"createDate\", tb.VALID_REASON as \"validReason\", tb.IS_VALID as \"isValid\" "
			+ "FROM scms_finance_flow tb "
			+ "WHERE 1 = 1 ";

	private static final String SQL_GET_LIST_COUNT = "SELECT count(1) as \"count\" "
			+ "FROM scms_finance_flow tb "
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
        return sqlParams;
	}
}