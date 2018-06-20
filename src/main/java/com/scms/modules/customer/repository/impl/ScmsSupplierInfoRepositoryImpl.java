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

import com.scms.modules.customer.entity.ScmsSupplierInfo;
import com.scms.modules.customer.repository.custom.ScmsSupplierInfoRepositoryCustom;

public class ScmsSupplierInfoRepositoryImpl extends BaseRepository implements ScmsSupplierInfoRepositoryCustom{

	private static final String SQL_GET_LIST = "SELECT tb.ID as \"id\", tb.MERCHANTS_ID as \"merchantsId\", tb.SUPPLIER_NAME as \"supplierName\", tb.SUPPLIER_ADMIN as \"supplierAdmin\", "
	        + "tb.SUPPLIER_PHONE as \"supplierPhone\", tb.SUPPLIER_BALANCE as \"supplierBalance\", tb.SUPPLIER_EMAIL as \"supplierEmail\", tb.SUPPLIER_ZIP as \"supplierZip\", "
	        + "tb.SUPPLIER_ADDR as \"supplierAddr\", tb.SUPPLIER_PHOTO as \"supplierPhoto\", tb.SUPPLIER_MEMO as \"supplierMemo\", tb.AREA_CODE as \"areaCode\", tb.AREA_NAME as \"areaName\", "
	        + "tb.WEB_SITE as \"webSite\", tb.BANK_NAME_1 as \"bankName1\", tb.BANK_CARD_1 as \"bankCard1\", tb.BANK_NAME_2 as \"bankName2\", tb.BANK_CARD_2 as \"bankCard2\", "
	        + "tb.CREATE_BY as \"createBy\", tb.CREATE_DATE as \"createDate\", user.USER_NAME as \"createByName\" "
			+ "FROM scms_supplier_info tb left join sys_user_info user on user.USER_ID = tb.CREATE_BY "
			+ "WHERE 1 = 1 AND tb.IS_VALID = 'Y'";

	private static final String SQL_GET_LIST_COUNT = "SELECT count(1) as \"count\" "
			+ "FROM scms_supplier_info tb left join sys_user_info user on user.USER_ID = tb.CREATE_BY "
			+ "WHERE 1 = 1 AND tb.IS_VALID = 'Y'";

    private static final String SQL_GET_SUPPLIER_STATISTICS = "SELECT " + 
            "sum(tb.TOTAL_NUM) as \"totalNum\", sum(tb.TOTAL_UN_PAY) as \"totalUnPay\", " + 
            "sum(tb.SMALL_CHANGE) as \"totalSmallChange\", sum(tb.TOTAL_AMOUNT) as \"totalAmount\" " + 
            "FROM scms_order_info tb " + 
            "WHERE tb.ORDER_CUSTOMER_TYPE = '2' ";

    private static final String SQL_GET_SUPPLIER_CHECK_BILL_STATISTICS = "SELECT " + 
            "(SELECT count(1) FROM scms_supplier_info tmpsci WHERE tmpsci.SUPPLIER_BALANCE < 0 AND tmpsci.MERCHANTS_ID = :merchantsId) as \"totalDebtNum\", " + 
            "(SELECT sum(tmpsci.SUPPLIER_BALANCE) FROM scms_supplier_info tmpsci WHERE tmpsci.SUPPLIER_BALANCE < 0 AND tmpsci.MERCHANTS_ID = :merchantsId) as \"totalDebtPrice\", " + 
            "(SELECT count(1) FROM scms_supplier_info tmpsci WHERE tmpsci.SUPPLIER_BALANCE > 0 AND tmpsci.MERCHANTS_ID = :merchantsId) as \"totalPayNum\", " + 
            "(SELECT sum(tmpsci.SUPPLIER_BALANCE) FROM scms_supplier_info tmpsci WHERE tmpsci.SUPPLIER_BALANCE > 0 AND tmpsci.MERCHANTS_ID = :merchantsId) as \"totalPayPrice\" ";
	
	public List<Map<String, Object>> getList(ScmsSupplierInfo scmsSupplierInfo, Map<String, Object> params, int pageIndex, int pageSize) {
		//生成查询条件
		SqlParams sqlParams = genListWhere(SQL_GET_LIST, scmsSupplierInfo, params);
		//添加分页和排序
		sqlParams = getPageableSql(sqlParams, pageIndex, pageSize, " tb.ID DESC ", " \"id\" DESC ");
		return getResultList(sqlParams);
	}

	public int getListCount(ScmsSupplierInfo scmsSupplierInfo, Map<String, Object> params) {
		//生成查询条件
		SqlParams sqlParams = genListWhere(SQL_GET_LIST_COUNT, scmsSupplierInfo, params);
		return getResultListTotalCount(sqlParams);
	}

	/**
	 * 生成查询条件
	 * @param sql
	 * @param params
	 * @return
	 */
	private SqlParams genListWhere(String sql, ScmsSupplierInfo scmsSupplierInfo, Map<String, Object> params){
		SqlParams sqlParams = new SqlParams();
		sqlParams.querySql.append(sql);
        Long id = MapUtils.getLong(params, "id", null);
        Long merchantsId = MapUtils.getLong(params, "merchantsId", null);
        if (id != null) {
            sqlParams.querySql.append(" AND tb.ID = :id ");
            sqlParams.paramsList.add("id");
            sqlParams.valueList.add(id);
        }
        if (merchantsId != null) {
            sqlParams.querySql.append(" AND tb.MERCHANTS_ID = :merchantsId ");
            sqlParams.paramsList.add("merchantsId");
            sqlParams.valueList.add(merchantsId);
        }
        if (scmsSupplierInfo != null && !StringUtils.isBlank(scmsSupplierInfo.getSupplierName())) {
            sqlParams.querySql.append(getLikeSql("tb.SUPPLIER_NAME", ":supplierName"));
            sqlParams.paramsList.add("supplierName");
            sqlParams.valueList.add(scmsSupplierInfo.getSupplierName());
        }
        if (scmsSupplierInfo != null && !StringUtils.isBlank(scmsSupplierInfo.getSupplierAdmin())) {
            sqlParams.querySql.append(getLikeSql("tb.SUPPLIER_ADMIN", ":supplierAdmin"));
            sqlParams.paramsList.add("supplierAdmin");
            sqlParams.valueList.add(scmsSupplierInfo.getSupplierAdmin());
        }
        if (scmsSupplierInfo != null && !StringUtils.isBlank(scmsSupplierInfo.getSupplierAddr())) {
            sqlParams.querySql.append(getLikeSql("tb.SUPPLIER_ADDR", ":supplierAddr"));
            sqlParams.paramsList.add("supplierAddr");
            sqlParams.valueList.add(scmsSupplierInfo.getSupplierAddr());
        }
        if (scmsSupplierInfo != null && !StringUtils.isBlank(scmsSupplierInfo.getSupplierPhone())) {
            sqlParams.querySql.append(getLikeSql("tb.SUPPLIER_PHONE", ":supplierPhone"));
            sqlParams.paramsList.add("supplierPhone");
            sqlParams.valueList.add(scmsSupplierInfo.getSupplierPhone());
        }
        String nameAndPhone = MapUtils.getString(params, "nameAndPhone");
        if(!StringUtils.isBlank(nameAndPhone)) {
            sqlParams.querySql.append(" AND (tb.SUPPLIER_NAME like concat('%', :nameAndPhone , '%') OR tb.SUPPLIER_PHONE like concat('%', :nameAndPhone , '%')) ");
            sqlParams.paramsList.add("nameAndPhone");
            sqlParams.valueList.add(nameAndPhone);
        }
        return sqlParams;
	}

    @Override
    public List<Map<String, Object>> getSupplierStatistics(Map<String, Object> params) {
        SqlParams sqlParams = new SqlParams();
        sqlParams.querySql.append(SQL_GET_SUPPLIER_STATISTICS);
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
        Long supplierId = MapUtils.getLong(params, "supplierId", null);
        if(supplierId != null) {
            sqlParams.querySql.append(" AND tb.CUSTOMER_ID = :supplierId ");
            sqlParams.paramsList.add("supplierId");
            sqlParams.valueList.add(supplierId);
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
    public List<Map<String, Object>> getSupplierCheckBillStatistics(Map<String, Object> params) {
        SqlParams sqlParams = new SqlParams();
        sqlParams.querySql.append(SQL_GET_SUPPLIER_CHECK_BILL_STATISTICS);
        Long merchantsId = MapUtils.getLong(params, "merchantsId", null);
        if (merchantsId != null) {
            sqlParams.paramsList.add("merchantsId");
            sqlParams.valueList.add(merchantsId);
        }
        return getResultList(sqlParams);
    }
}