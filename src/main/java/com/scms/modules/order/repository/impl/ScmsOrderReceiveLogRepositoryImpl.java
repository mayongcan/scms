/*
 * Copyright(c) 2018 gimplatform(通用信息管理平台) All rights reserved.
 */
package com.scms.modules.order.repository.impl;

import java.util.List;
import java.util.Map;
import com.gimplatform.core.common.SqlParams;
import com.gimplatform.core.repository.BaseRepository;
import com.scms.modules.order.entity.ScmsOrderReceiveLog;
import com.scms.modules.order.repository.custom.ScmsOrderReceiveLogRepositoryCustom;

public class ScmsOrderReceiveLogRepositoryImpl extends BaseRepository implements ScmsOrderReceiveLogRepositoryCustom{

	private static final String SQL_GET_LIST = "SELECT tb.ID as \"id\", tb.ORDER_ID as \"orderId\", tb.RECEIVE_BY as \"receiveBy\", tb.RECEIVE_BY_NAME as \"receiveByName\", tb.RECEIVE_MEMO as \"receiveMemo\", tb.RECEIVE_DATE as \"receiveDate\" "
			+ "FROM scms_order_receive_log tb "
			+ "WHERE 1 = 1 ";

	private static final String SQL_GET_LIST_COUNT = "SELECT count(1) as \"count\" "
			+ "FROM scms_order_receive_log tb "
			+ "WHERE 1 = 1 ";
	
	public List<Map<String, Object>> getList(ScmsOrderReceiveLog scmsOrderReceiveLog, Map<String, Object> params, int pageIndex, int pageSize) {
		//生成查询条件
		SqlParams sqlParams = genListWhere(SQL_GET_LIST, scmsOrderReceiveLog, params);
		//添加分页和排序
		sqlParams = getPageableSql(sqlParams, pageIndex, pageSize, " tb.ID ASC ", " \"id\" ASC ");
		return getResultList(sqlParams);
	}

	public int getListCount(ScmsOrderReceiveLog scmsOrderReceiveLog, Map<String, Object> params) {
		//生成查询条件
		SqlParams sqlParams = genListWhere(SQL_GET_LIST_COUNT, scmsOrderReceiveLog, params);
		return getResultListTotalCount(sqlParams);
	}

	/**
	 * 生成查询条件
	 * @param sql
	 * @param params
	 * @return
	 */
	private SqlParams genListWhere(String sql, ScmsOrderReceiveLog scmsOrderReceiveLog, Map<String, Object> params){
		SqlParams sqlParams = new SqlParams();
		sqlParams.querySql.append(sql);
		if (scmsOrderReceiveLog != null && scmsOrderReceiveLog.getOrderId() != null) {
			sqlParams.querySql.append(" AND tb.ORDER_ID = :orderId ");
			sqlParams.paramsList.add("orderId");
			sqlParams.valueList.add(scmsOrderReceiveLog.getOrderId());
		}
        return sqlParams;
	}
}