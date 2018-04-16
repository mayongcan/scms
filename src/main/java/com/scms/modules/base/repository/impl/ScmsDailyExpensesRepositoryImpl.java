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
import com.scms.modules.base.entity.ScmsDailyExpenses;
import com.scms.modules.base.repository.custom.ScmsDailyExpensesRepositoryCustom;

public class ScmsDailyExpensesRepositoryImpl extends BaseRepository implements ScmsDailyExpensesRepositoryCustom{

	private static final String SQL_GET_LIST = "SELECT tb.ID as \"id\", tb.EXPENSES_NAME as \"expensesName\", tb.EXPENSES_NUM as \"expensesNum\", tb.CREATE_BY as \"createBy\", "
	        + "tb.CREATE_DATE as \"createDate\", tb.IS_VALID as \"isValid\", user.USER_NAME as \"createByName\" "
			+ "FROM scms_daily_expenses tb left join sys_user_info user on user.USER_ID = tb.CREATE_BY "
			+ "WHERE 1 = 1 AND tb.IS_VALID = 'Y'";

	private static final String SQL_GET_LIST_COUNT = "SELECT count(1) as \"count\" "
            + "FROM scms_daily_expenses tb left join sys_user_info user on user.USER_ID = tb.CREATE_BY "
			+ "WHERE 1 = 1 AND tb.IS_VALID = 'Y'";
	
	public List<Map<String, Object>> getList(ScmsDailyExpenses scmsDailyExpenses, Map<String, Object> params, int pageIndex, int pageSize) {
		//生成查询条件
		SqlParams sqlParams = genListWhere(SQL_GET_LIST, scmsDailyExpenses, params);
		//添加分页和排序
		sqlParams = getPageableSql(sqlParams, pageIndex, pageSize, " tb.ID DESC ", " \"id\" DESC ");
		return getResultList(sqlParams);
	}

	public int getListCount(ScmsDailyExpenses scmsDailyExpenses, Map<String, Object> params) {
		//生成查询条件
		SqlParams sqlParams = genListWhere(SQL_GET_LIST_COUNT, scmsDailyExpenses, params);
		return getResultListTotalCount(sqlParams);
	}

	/**
	 * 生成查询条件
	 * @param sql
	 * @param params
	 * @return
	 */
	private SqlParams genListWhere(String sql, ScmsDailyExpenses scmsDailyExpenses, Map<String, Object> params){
		SqlParams sqlParams = new SqlParams();
		sqlParams.querySql.append(sql);
		String expensesName = MapUtils.getString(params, "expensesName");
        String beginTime = MapUtils.getString(params, "beginTime");
        String endTime = MapUtils.getString(params, "endTime");
		if(!StringUtils.isBlank(expensesName)) {
		    sqlParams.querySql.append(getLikeSql("tb.EXPENSES_NAME", ":expensesName"));
            sqlParams.paramsList.add("expensesName");
            sqlParams.valueList.add(expensesName);
		}
		if(!StringUtils.isBlank(beginTime) && !StringUtils.isBlank(endTime)) {
            sqlParams.querySql.append(" AND tb.CREATE_DATE between :beginTime and :endTime ");
            sqlParams.paramsList.add("beginTime");
            sqlParams.paramsList.add("endTime");
            sqlParams.valueList.add(beginTime);
            sqlParams.valueList.add(endTime);
		}
        return sqlParams;
	}
}