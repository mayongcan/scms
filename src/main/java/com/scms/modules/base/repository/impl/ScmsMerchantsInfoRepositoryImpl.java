/*
 * Copyright(c) 2018 gimplatform(通用信息管理平台) All rights reserved.
 */
package com.scms.modules.base.repository.impl;

import java.util.List;
import java.util.Map;

import org.apache.commons.collections4.MapUtils;

import com.gimplatform.core.common.SqlParams;
import com.gimplatform.core.repository.BaseRepository;
import com.gimplatform.core.utils.StringUtils;

import com.scms.modules.base.entity.ScmsMerchantsInfo;
import com.scms.modules.base.repository.custom.ScmsMerchantsInfoRepositoryCustom;

public class ScmsMerchantsInfoRepositoryImpl extends BaseRepository implements ScmsMerchantsInfoRepositoryCustom{

	private static final String SQL_GET_LIST = "SELECT tb.ID as \"id\", tb.USER_ID as \"userId\", tb.MERCHANTS_NAME as \"merchantsName\", tb.MERCHANTS_MEMO as \"merchantsMemo\", "
	        + "tb.BUSS_SCOPE as \"bussScope\", tb.START_DATE as \"startDate\", tb.PHONE as \"phone\", tb.ADDR as \"addr\", tb.AREA_CODE as \"areaCode\", tb.AREA_NAME as \"areaName\", "
	        + "tb.CREATE_BY as \"createBy\", tb.CREATE_DATE as \"createDate\", "
	        + "user.USER_NAME as \"userName\" "
			+ "FROM scms_merchants_info tb left join sys_user_info user on user.USER_ID = tb.USER_ID "
			+ "WHERE 1 = 1 AND tb.IS_VALID = 'Y'";

	private static final String SQL_GET_LIST_COUNT = "SELECT count(1) as \"count\" "
            + "FROM scms_merchants_info tb left join sys_user_info user on user.USER_ID = tb.USER_ID "
			+ "WHERE 1 = 1 AND tb.IS_VALID = 'Y'";
	
	public List<Map<String, Object>> getList(ScmsMerchantsInfo scmsMerchantsInfo, Map<String, Object> params, int pageIndex, int pageSize) {
		//生成查询条件
		SqlParams sqlParams = genListWhere(SQL_GET_LIST, scmsMerchantsInfo, params);
		//添加分页和排序
		sqlParams = getPageableSql(sqlParams, pageIndex, pageSize, " tb.ID DESC ", " \"id\" DESC ");
		return getResultList(sqlParams);
	}

	public int getListCount(ScmsMerchantsInfo scmsMerchantsInfo, Map<String, Object> params) {
		//生成查询条件
		SqlParams sqlParams = genListWhere(SQL_GET_LIST_COUNT, scmsMerchantsInfo, params);
		return getResultListTotalCount(sqlParams);
	}

	/**
	 * 生成查询条件
	 * @param sql
	 * @param params
	 * @return
	 */
	private SqlParams genListWhere(String sql, ScmsMerchantsInfo scmsMerchantsInfo, Map<String, Object> params){
		SqlParams sqlParams = new SqlParams();
		sqlParams.querySql.append(sql);
		String userName = MapUtils.getString(params, "userName");
        if (scmsMerchantsInfo != null && scmsMerchantsInfo.getId() != null) {
            sqlParams.querySql.append(" AND tb.ID = :id ");
            sqlParams.paramsList.add("id");
            sqlParams.valueList.add(scmsMerchantsInfo.getId());
        }
		if (scmsMerchantsInfo != null && scmsMerchantsInfo.getUserId() != null) {
			sqlParams.querySql.append(" AND tb.USER_ID = :userId ");
			sqlParams.paramsList.add("userId");
			sqlParams.valueList.add(scmsMerchantsInfo.getUserId());
		}
		if (scmsMerchantsInfo != null && !StringUtils.isBlank(scmsMerchantsInfo.getMerchantsName())) {
            sqlParams.querySql.append(getLikeSql("tb.MERCHANTS_NAME", ":merchantsName"));
			sqlParams.paramsList.add("merchantsName");
			sqlParams.valueList.add(scmsMerchantsInfo.getMerchantsName());
		}
		if(!StringUtils.isBlank(userName)) {
		    sqlParams.querySql.append(this.getLikeSql("user.USER_NAME", ":userName"));
            sqlParams.paramsList.add("userName");
            sqlParams.valueList.add(userName);
		}
        return sqlParams;
	}
}