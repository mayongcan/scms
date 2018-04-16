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

import com.scms.modules.base.entity.ScmsFeedbackInfo;
import com.scms.modules.base.repository.custom.ScmsFeedbackInfoRepositoryCustom;

public class ScmsFeedbackInfoRepositoryImpl extends BaseRepository implements ScmsFeedbackInfoRepositoryCustom{

	private static final String SQL_GET_LIST = "SELECT tb.ID as \"id\", tb.MERCHANTS_ID as \"merchantsId\", tb.USER_ID as \"userId\", tb.TITLE as \"title\", tb.CONTENT as \"content\", "
	        + "tb.IS_READ as \"isRead\", tb.IS_HANDLE as \"isHandle\", tb.CREATE_DATE as \"createDate\", tb.READ_DATE as \"readDate\", tb.HANDLE_DATE as \"handleDate\", "
	        + "mi.MERCHANTS_NAME as \"merchantsName\", user.USER_NAME as \"userName\" "
			+ "FROM scms_feedback_info tb left join scms_merchants_info mi on mi.ID = tb.MERCHANTS_ID "
			+ "left join sys_user_info user on user.USER_ID = tb.USER_ID "
			+ "WHERE 1 = 1 ";

	private static final String SQL_GET_LIST_COUNT = "SELECT count(1) as \"count\" "
            + "FROM scms_feedback_info tb left join scms_merchants_info mi on mi.ID = tb.MERCHANTS_ID "
            + "left join sys_user_info user on user.USER_ID = tb.USER_ID "
			+ "WHERE 1 = 1 ";
	
	public List<Map<String, Object>> getList(ScmsFeedbackInfo scmsFeedbackInfo, Map<String, Object> params, int pageIndex, int pageSize) {
		//生成查询条件
		SqlParams sqlParams = genListWhere(SQL_GET_LIST, scmsFeedbackInfo, params);
		//添加分页和排序
		sqlParams = getPageableSql(sqlParams, pageIndex, pageSize, " tb.ID DESC ", " \"id\" DESC ");
		return getResultList(sqlParams);
	}

	public int getListCount(ScmsFeedbackInfo scmsFeedbackInfo, Map<String, Object> params) {
		//生成查询条件
		SqlParams sqlParams = genListWhere(SQL_GET_LIST_COUNT, scmsFeedbackInfo, params);
		return getResultListTotalCount(sqlParams);
	}

	/**
	 * 生成查询条件
	 * @param sql
	 * @param params
	 * @return
	 */
	private SqlParams genListWhere(String sql, ScmsFeedbackInfo scmsFeedbackInfo, Map<String, Object> params){
		SqlParams sqlParams = new SqlParams();
		sqlParams.querySql.append(sql);
		if (scmsFeedbackInfo != null && scmsFeedbackInfo.getMerchantsId() != null) {
			sqlParams.querySql.append(" AND tb.MERCHANTS_ID = :merchantsId ");
			sqlParams.paramsList.add("merchantsId");
			sqlParams.valueList.add(scmsFeedbackInfo.getMerchantsId());
		}
		if (scmsFeedbackInfo != null && scmsFeedbackInfo.getUserId() != null) {
			sqlParams.querySql.append(" AND tb.USER_ID = :userId ");
			sqlParams.paramsList.add("userId");
			sqlParams.valueList.add(scmsFeedbackInfo.getUserId());
		}
		String title = MapUtils.getString(params, "title");
		String merchantsName = MapUtils.getString(params, "merchantsName");
		String userName = MapUtils.getString(params, "userName");
		if(!StringUtils.isBlank(title)) {
		    sqlParams.querySql.append(getLikeSql("tb.TITLE", ":title"));
            sqlParams.paramsList.add("title");
            sqlParams.valueList.add(title);
		}
        if(!StringUtils.isBlank(merchantsName)) {
            sqlParams.querySql.append(getLikeSql("mi.MERCHANTS_NAME", ":merchantsName"));
            sqlParams.paramsList.add("merchantsName");
            sqlParams.valueList.add(merchantsName);
        }
        if(!StringUtils.isBlank(userName)) {
            sqlParams.querySql.append(getLikeSql("user.USER_NAME", ":userName"));
            sqlParams.paramsList.add("userName");
            sqlParams.valueList.add(userName);
        }
        return sqlParams;
	}
}