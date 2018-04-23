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
        Long merchantsId = MapUtils.getLong(params, "merchantsId", null);
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
        if (scmsSupplierInfo != null && !StringUtils.isBlank(scmsSupplierInfo.getSupplierPhone())) {
            sqlParams.querySql.append(getLikeSql("tb.SUPPLIER_PHONE", ":supplierPhone"));
            sqlParams.paramsList.add("supplierPhone");
            sqlParams.valueList.add(scmsSupplierInfo.getSupplierPhone());
        }
        return sqlParams;
	}
}