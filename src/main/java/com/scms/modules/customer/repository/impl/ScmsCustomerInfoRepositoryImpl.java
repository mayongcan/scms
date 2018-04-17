/*
 * Copyright(c) 2018 gimplatform(通用信息管理平台) All rights reserved.
 */
package com.scms.modules.customer.repository.impl;

import java.util.List;
import java.util.Map;
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
        return sqlParams;
	}
}