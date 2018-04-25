/*
 * Copyright(c) 2018 gimplatform(通用信息管理平台) All rights reserved.
 */
package com.scms.modules.order.repository.impl;

import java.util.List;
import java.util.Map;
import com.gimplatform.core.common.SqlParams;
import com.gimplatform.core.repository.BaseRepository;
import com.scms.modules.order.entity.ScmsOrderSendLog;
import com.scms.modules.order.repository.custom.ScmsOrderSendLogRepositoryCustom;

public class ScmsOrderSendLogRepositoryImpl extends BaseRepository implements ScmsOrderSendLogRepositoryCustom{

	private static final String SQL_GET_LIST = "SELECT tb.ID as \"id\", tb.ORDER_ID as \"orderId\", tb.SEND_BY as \"sendBy\", tb.SEND_BY_NAME as \"sendByName\", tb.SEND_MEMO as \"sendMemo\", tb.SEND_DATE as \"sendDate\" "
			+ "FROM scms_order_send_log tb "
			+ "WHERE 1 = 1 ";

	private static final String SQL_GET_LIST_COUNT = "SELECT count(1) as \"count\" "
			+ "FROM scms_order_send_log tb "
			+ "WHERE 1 = 1 ";
	
	public List<Map<String, Object>> getList(ScmsOrderSendLog scmsOrderSendLog, Map<String, Object> params, int pageIndex, int pageSize) {
		//生成查询条件
		SqlParams sqlParams = genListWhere(SQL_GET_LIST, scmsOrderSendLog, params);
		//添加分页和排序
		sqlParams = getPageableSql(sqlParams, pageIndex, pageSize, " tb.ID ASC ", " \"id\" ASC ");
		return getResultList(sqlParams);
	}

	public int getListCount(ScmsOrderSendLog scmsOrderSendLog, Map<String, Object> params) {
		//生成查询条件
		SqlParams sqlParams = genListWhere(SQL_GET_LIST_COUNT, scmsOrderSendLog, params);
		return getResultListTotalCount(sqlParams);
	}

	/**
	 * 生成查询条件
	 * @param sql
	 * @param params
	 * @return
	 */
	private SqlParams genListWhere(String sql, ScmsOrderSendLog scmsOrderSendLog, Map<String, Object> params){
		SqlParams sqlParams = new SqlParams();
		sqlParams.querySql.append(sql);
        if (scmsOrderSendLog != null && scmsOrderSendLog.getOrderId() != null) {
            sqlParams.querySql.append(" AND tb.ORDER_ID = :orderId ");
            sqlParams.paramsList.add("orderId");
            sqlParams.valueList.add(scmsOrderSendLog.getOrderId());
        }
        return sqlParams;
	}
}