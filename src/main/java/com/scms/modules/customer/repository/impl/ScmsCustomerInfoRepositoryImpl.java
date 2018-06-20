/*
 * Copyright(c) 2018 gimplatform(通用信息管理平台) All rights reserved.
 */
package com.scms.modules.customer.repository.impl;

import java.util.List;
import java.util.Map;

import org.apache.commons.collections4.MapUtils;

import com.gimplatform.core.common.SqlParams;
import com.gimplatform.core.repository.BaseRepository;
import com.gimplatform.core.utils.StringUtils;

import com.scms.modules.customer.entity.ScmsCustomerInfo;
import com.scms.modules.customer.repository.custom.ScmsCustomerInfoRepositoryCustom;

public class ScmsCustomerInfoRepositoryImpl extends BaseRepository implements ScmsCustomerInfoRepositoryCustom{

	private static final String SQL_GET_LIST = "SELECT tb.ID as \"id\", tb.MERCHANTS_ID as \"merchantsId\", tb.TYPE_ID as \"typeId\", tb.LEVEL_ID as \"levelId\", "
	        + "tb.CUSTOMER_NAME as \"customerName\", tb.CUSTOMER_PHONE as \"customerPhone\", tb.CUSTOMER_BALANCE as \"customerBalance\", tb.CUSTOMER_EMAIL as \"customerEmail\", "
	        + "tb.CUSTOMER_ZIP as \"customerZip\", tb.CUSTOMER_ADDR as \"customerAddr\", tb.CUSTOMER_PHOTO as \"customerPhoto\", tb.CUSTOMER_MEMO as \"customerMemo\", "
	        + "tb.AREA_CODE as \"areaCode\", tb.AREA_NAME as \"areaName\", tb.CREATE_BY as \"createBy\", tb.CREATE_DATE as \"createDate\", "
	        + "sct.TYPE_NAME as \"typeName\", scl.LEVEL_NAME as \"levelName\", user.USER_NAME as \"createByName\" "
			+ "FROM scms_customer_info tb left join scms_customer_type sct on sct.ID = tb.TYPE_ID "
			+ "left join scms_customer_level scl on scl.ID = tb.LEVEL_ID "
			+ "left join sys_user_info user on user.USER_ID = tb.CREATE_BY "
			+ "WHERE 1 = 1 AND tb.IS_VALID = 'Y'";

	private static final String SQL_GET_LIST_COUNT = "SELECT count(1) as \"count\" "
            + "FROM scms_customer_info tb left join scms_customer_type sct on sct.ID = tb.TYPE_ID "
            + "left join scms_customer_level scl on scl.ID = tb.LEVEL_ID "
			+ "WHERE 1 = 1 AND tb.IS_VALID = 'Y'";

    private static final String SQL_GET_CUSTOMER_STATISTICS = "SELECT " + 
            "sum(tb.TOTAL_NUM) as \"totalNum\", sum(tb.TOTAL_UN_PAY) as \"totalUnPay\", " + 
            "sum(tb.SMALL_CHANGE) as \"totalSmallChange\", sum(tb.TOTAL_AMOUNT) as \"totalAmount\" " + 
            "FROM scms_order_info tb " + 
            "WHERE tb.ORDER_CUSTOMER_TYPE = '1' ";

    private static final String SQL_GET_CUSTOMER_CHECK_BILL_STATISTICS = "SELECT " + 
            "(SELECT count(1) FROM scms_customer_info tmpsci WHERE tmpsci.CUSTOMER_BALANCE < 0 AND tmpsci.MERCHANTS_ID = :merchantsId) as \"totalDebtNum\", " + 
            "(SELECT sum(tmpsci.CUSTOMER_BALANCE) FROM scms_customer_info tmpsci WHERE tmpsci.CUSTOMER_BALANCE < 0 AND tmpsci.MERCHANTS_ID = :merchantsId) as \"totalDebtPrice\", " + 
            "(SELECT count(1) FROM scms_customer_info tmpsci WHERE tmpsci.CUSTOMER_BALANCE > 0 AND tmpsci.MERCHANTS_ID = :merchantsId) as \"totalPayNum\", " + 
            "(SELECT sum(tmpsci.CUSTOMER_BALANCE) FROM scms_customer_info tmpsci WHERE tmpsci.CUSTOMER_BALANCE > 0 AND tmpsci.MERCHANTS_ID = :merchantsId) as \"totalPayPrice\" ";
	
	public List<Map<String, Object>> getList(ScmsCustomerInfo scmsCustomerInfo, Map<String, Object> params, int pageIndex, int pageSize) {
		//生成查询条件
		SqlParams sqlParams = genListWhere(SQL_GET_LIST, scmsCustomerInfo, params);
		//添加分页和排序
		sqlParams = getPageableSql(sqlParams, pageIndex, pageSize, " tb.ID DESC ", " \"id\" DESC ");
		return getResultList(sqlParams);
	}

	public int getListCount(ScmsCustomerInfo scmsCustomerInfo, Map<String, Object> params) {
		//生成查询条件
		SqlParams sqlParams = genListWhere(SQL_GET_LIST_COUNT, scmsCustomerInfo, params);
		return getResultListTotalCount(sqlParams);
	}

	/**
	 * 生成查询条件
	 * @param sql
	 * @param params
	 * @return
	 */
	private SqlParams genListWhere(String sql, ScmsCustomerInfo scmsCustomerInfo, Map<String, Object> params){
		SqlParams sqlParams = new SqlParams();
		sqlParams.querySql.append(sql);
        Long id = MapUtils.getLong(params, "id", null);
        if (id != null) {
            sqlParams.querySql.append(" AND tb.ID = :id ");
            sqlParams.paramsList.add("id");
            sqlParams.valueList.add(id);
        }
        Long merchantsId = MapUtils.getLong(params, "merchantsId", null);
        if (merchantsId != null) {
            sqlParams.querySql.append(" AND tb.MERCHANTS_ID = :merchantsId ");
            sqlParams.paramsList.add("merchantsId");
            sqlParams.valueList.add(merchantsId);
        }
		if (scmsCustomerInfo != null && !StringUtils.isBlank(scmsCustomerInfo.getCustomerName())) {
            sqlParams.querySql.append(getLikeSql("tb.CUSTOMER_NAME", ":customerName"));
			sqlParams.paramsList.add("customerName");
			sqlParams.valueList.add(scmsCustomerInfo.getCustomerName());
		}
		if (scmsCustomerInfo != null && !StringUtils.isBlank(scmsCustomerInfo.getCustomerPhone())) {
            sqlParams.querySql.append(getLikeSql("tb.CUSTOMER_PHONE", ":customerPhone"));
			sqlParams.paramsList.add("customerPhone");
			sqlParams.valueList.add(scmsCustomerInfo.getCustomerPhone());
		}
        if (scmsCustomerInfo != null && !StringUtils.isBlank(scmsCustomerInfo.getCustomerAddr())) {
            sqlParams.querySql.append(getLikeSql("tb.CUSTOMER_ADDR", ":customerAddr"));
            sqlParams.paramsList.add("customerAddr");
            sqlParams.valueList.add(scmsCustomerInfo.getCustomerAddr());
        }
		String customerType = MapUtils.getString(params, "customerType");
        String customerLevel = MapUtils.getString(params, "customerLevel");
		if(!StringUtils.isBlank(customerType)) {
            sqlParams.querySql.append(" AND tb.TYPE_ID = :customerType ");
            sqlParams.paramsList.add("customerType");
            sqlParams.valueList.add(customerType);
		}
        if(!StringUtils.isBlank(customerLevel)) {
            sqlParams.querySql.append(" AND tb.LEVEL_ID = :customerLevel ");
            sqlParams.paramsList.add("customerLevel");
            sqlParams.valueList.add(customerLevel);
        }
        String nameAndPhone = MapUtils.getString(params, "nameAndPhone");
        if(!StringUtils.isBlank(nameAndPhone)) {
            sqlParams.querySql.append(" AND (tb.CUSTOMER_NAME like concat('%', :nameAndPhone , '%') OR tb.CUSTOMER_PHONE like concat('%', :nameAndPhone , '%')) ");
            sqlParams.paramsList.add("nameAndPhone");
            sqlParams.valueList.add(nameAndPhone);
        }
        return sqlParams;
	}

    @Override
    public List<Map<String, Object>> getCustomerStatistics(Map<String, Object> params) {
        SqlParams sqlParams = new SqlParams();
        sqlParams.querySql.append(SQL_GET_CUSTOMER_STATISTICS);
        Long merchantsId = MapUtils.getLong(params, "merchantsId", null);
        if (merchantsId != null) {
            sqlParams.querySql.append(" AND tb.MERCHANTS_ID = :merchantsId ");
            sqlParams.paramsList.add("merchantsId");
            sqlParams.valueList.add(merchantsId);
        }
        String orderTypeList = MapUtils.getString(params, "orderTypeList");
        if (!StringUtils.isBlank(orderTypeList)) {
            List<String> orderList = StringUtils.splitToList(orderTypeList, ",");
            sqlParams.querySql.append(" AND tb.ORDER_TYPE IN (:orderList) ");
            sqlParams.paramsList.add("orderList");
            sqlParams.valueList.add(orderList);
        }
        Long customerId = MapUtils.getLong(params, "customerId", null);
        if(customerId != null) {
            sqlParams.querySql.append(" AND tb.CUSTOMER_ID = :customerId ");
            sqlParams.paramsList.add("customerId");
            sqlParams.valueList.add(customerId);
        }
        if (!StringUtils.isBlank(MapUtils.getString(params, "createDateBegin")) && !StringUtils.isBlank(MapUtils.getString(params, "createDateEnd"))) {
            sqlParams.querySql.append(" AND tb.CREATE_DATE between :createDateBegin AND :createDateEnd ");
            sqlParams.paramsList.add("createDateBegin");
            sqlParams.paramsList.add("createDateEnd");
            sqlParams.valueList.add(MapUtils.getString(params, "createDateBegin"));
            sqlParams.valueList.add(MapUtils.getString(params, "createDateEnd"));
        }
        return getResultList(sqlParams);
    }

    @Override
    public List<Map<String, Object>> getCustomerCheckBillStatistics(Map<String, Object> params) {
        SqlParams sqlParams = new SqlParams();
        sqlParams.querySql.append(SQL_GET_CUSTOMER_CHECK_BILL_STATISTICS);
        Long merchantsId = MapUtils.getLong(params, "merchantsId", null);
        if (merchantsId != null) {
            sqlParams.paramsList.add("merchantsId");
            sqlParams.valueList.add(merchantsId);
        }
        return getResultList(sqlParams);
    }
}