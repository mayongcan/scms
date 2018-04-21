/*
 * Copyright(c) 2018 gimplatform(通用信息管理平台) All rights reserved.
 */
package com.scms.modules.order.repository.impl;

import java.util.List;
import java.util.Map;
import com.gimplatform.core.common.SqlParams;
import com.gimplatform.core.repository.BaseRepository;
import com.scms.modules.order.entity.ScmsOrderModifyLog;
import com.scms.modules.order.repository.custom.ScmsOrderModifyLogRepositoryCustom;

public class ScmsOrderModifyLogRepositoryImpl extends BaseRepository implements ScmsOrderModifyLogRepositoryCustom{

	private static final String SQL_GET_LIST = "SELECT tb.ID as \"id\", tb.ORDER_ID as \"orderId\", tb.MODIFY_BY as \"modifyBy\", tb.MODIFY_BY_NAME as \"modifyByName\", tb.MODIFY_MEMO as \"modifyMemo\", tb.MODIFY_DATE as \"modifyDate\" "
			+ "FROM scms_order_modify_log tb "
			+ "WHERE 1 = 1 ";

	private static final String SQL_GET_LIST_COUNT = "SELECT count(1) as \"count\" "
			+ "FROM scms_order_modify_log tb "
			+ "WHERE 1 = 1 ";
	
	public List<Map<String, Object>> getList(ScmsOrderModifyLog scmsOrderModifyLog, Map<String, Object> params, int pageIndex, int pageSize) {
		//生成查询条件
		SqlParams sqlParams = genListWhere(SQL_GET_LIST, scmsOrderModifyLog, params);
		//添加分页和排序
		sqlParams = getPageableSql(sqlParams, pageIndex, pageSize, " tb.ID DESC ", " \"id\" DESC ");
		return getResultList(sqlParams);
	}

	public int getListCount(ScmsOrderModifyLog scmsOrderModifyLog, Map<String, Object> params) {
		//生成查询条件
		SqlParams sqlParams = genListWhere(SQL_GET_LIST_COUNT, scmsOrderModifyLog, params);
		return getResultListTotalCount(sqlParams);
	}

	/**
	 * 生成查询条件
	 * @param sql
	 * @param params
	 * @return
	 */
	private SqlParams genListWhere(String sql, ScmsOrderModifyLog scmsOrderModifyLog, Map<String, Object> params){
		SqlParams sqlParams = new SqlParams();
		sqlParams.querySql.append(sql);
        return sqlParams;
	}
}