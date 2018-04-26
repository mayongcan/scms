/*
 * Copyright(c) 2018 gimplatform(通用信息管理平台) All rights reserved.
 */
package com.scms.modules.order.repository.impl;

import java.util.List;
import java.util.Map;

import org.apache.commons.collections4.MapUtils;

import com.gimplatform.core.common.SqlParams;
import com.gimplatform.core.repository.BaseRepository;

import com.scms.modules.order.entity.ScmsInventoryTransferGoods;
import com.scms.modules.order.repository.custom.ScmsInventoryTransferGoodsRepositoryCustom;

public class ScmsInventoryTransferGoodsRepositoryImpl extends BaseRepository implements ScmsInventoryTransferGoodsRepositoryCustom{

	private static final String SQL_GET_LIST = "SELECT tb.ID as \"id\", tb.ORDER_ID as \"orderId\", tb.GOODS_ID as \"goodsId\", tb.GOODS_NAME as \"goodsName\", "
	        + "tb.GOODS_SERIAL_NUM as \"goodsSerialNum\", tb.GOODS_PHOTO as \"goodsPhoto\" "
			+ "FROM scms_inventory_transfer_goods tb "
			+ "WHERE 1 = 1 ";

	private static final String SQL_GET_LIST_COUNT = "SELECT count(1) as \"count\" "
			+ "FROM scms_inventory_transfer_goods tb "
			+ "WHERE 1 = 1 ";
	
	public List<Map<String, Object>> getList(ScmsInventoryTransferGoods scmsInventoryTransferGoods, Map<String, Object> params, int pageIndex, int pageSize) {
		//生成查询条件
		SqlParams sqlParams = genListWhere(SQL_GET_LIST, scmsInventoryTransferGoods, params);
		//添加分页和排序
		sqlParams = getPageableSql(sqlParams, pageIndex, pageSize, " tb.ID DESC ", " \"id\" DESC ");
		return getResultList(sqlParams);
	}

	public int getListCount(ScmsInventoryTransferGoods scmsInventoryTransferGoods, Map<String, Object> params) {
		//生成查询条件
		SqlParams sqlParams = genListWhere(SQL_GET_LIST_COUNT, scmsInventoryTransferGoods, params);
		return getResultListTotalCount(sqlParams);
	}

	/**
	 * 生成查询条件
	 * @param sql
	 * @param params
	 * @return
	 */
	private SqlParams genListWhere(String sql, ScmsInventoryTransferGoods scmsInventoryTransferGoods, Map<String, Object> params){
		SqlParams sqlParams = new SqlParams();
		sqlParams.querySql.append(sql);
        Long orderId = MapUtils.getLong(params, "orderId", null);
        if(orderId != null) {
            sqlParams.querySql.append(" AND tb.ORDER_ID = :orderId ");
            sqlParams.paramsList.add("orderId");
            sqlParams.valueList.add(orderId);
        }
        return sqlParams;
	}
}