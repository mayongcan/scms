/*
 * Copyright(c) 2018 gimplatform(通用信息管理平台) All rights reserved.
 */
package com.scms.modules.user.repository.impl;

import java.util.List;
import java.util.Map;
import com.gimplatform.core.common.SqlParams;
import com.gimplatform.core.repository.BaseRepository;
import com.gimplatform.core.utils.StringUtils;

import com.scms.modules.user.entity.ScmsUserPoint;
import com.scms.modules.user.repository.custom.ScmsUserPointRepositoryCustom;

public class ScmsUserPointRepositoryImpl extends BaseRepository implements ScmsUserPointRepositoryCustom{

	private static final String SQL_GET_LIST = "SELECT tb.ID as \"id\", tb.USER_ID as \"userId\", tb.POINT_TYPE as \"pointType\", tb.POINT as \"point\", tb.CREATE_DATE as \"createDate\", "
	        + "tb.MEMO as \"memo\", tb.IS_VALID as \"isValid\", user.USER_NAME as \"userName\" "
			+ "FROM scms_user_point tb left join sys_user_info user on user.USER_ID = tb.USER_ID "
			+ "WHERE 1 = 1 AND tb.IS_VALID = 'Y'";

	private static final String SQL_GET_LIST_COUNT = "SELECT count(1) as \"count\" "
	        + "FROM scms_user_point tb left join sys_user_info user on user.USER_ID = tb.USER_ID "
			+ "WHERE 1 = 1 AND tb.IS_VALID = 'Y'";
	
	public List<Map<String, Object>> getList(ScmsUserPoint scmsUserPoint, Map<String, Object> params, int pageIndex, int pageSize) {
		//生成查询条件
		SqlParams sqlParams = genListWhere(SQL_GET_LIST, scmsUserPoint, params);
		//添加分页和排序
		sqlParams = getPageableSql(sqlParams, pageIndex, pageSize, " tb.ID DESC ", " \"id\" DESC ");
		return getResultList(sqlParams);
	}

	public int getListCount(ScmsUserPoint scmsUserPoint, Map<String, Object> params) {
		//生成查询条件
		SqlParams sqlParams = genListWhere(SQL_GET_LIST_COUNT, scmsUserPoint, params);
		return getResultListTotalCount(sqlParams);
	}

	/**
	 * 生成查询条件
	 * @param sql
	 * @param params
	 * @return
	 */
	private SqlParams genListWhere(String sql, ScmsUserPoint scmsUserPoint, Map<String, Object> params){
		SqlParams sqlParams = new SqlParams();
		sqlParams.querySql.append(sql);
		if (scmsUserPoint != null && scmsUserPoint.getUserId() != null) {
			sqlParams.querySql.append(" AND tb.USER_ID = :userId ");
			sqlParams.paramsList.add("userId");
			sqlParams.valueList.add(scmsUserPoint.getUserId());
		}
		if (scmsUserPoint != null && !StringUtils.isBlank(scmsUserPoint.getPointType())) {
			sqlParams.querySql.append(" AND tb.POINT_TYPE = :pointType ");
			sqlParams.paramsList.add("pointType");
			sqlParams.valueList.add(scmsUserPoint.getPointType());
		}
        return sqlParams;
	}
}