/*
 * Copyright(c) 2018 gimplatform(通用信息管理平台) All rights reserved.
 */
package com.scms.modules.order.repository.impl;

import java.util.List;
import java.util.Map;
import com.gimplatform.core.common.SqlParams;
import com.gimplatform.core.repository.BaseRepository;

import com.scms.modules.order.entity.ScmsOrderGoods;
import com.scms.modules.order.repository.custom.ScmsOrderGoodsRepositoryCustom;

public class ScmsOrderGoodsRepositoryImpl extends BaseRepository implements ScmsOrderGoodsRepositoryCustom{

	private static final String SQL_GET_LIST = "SELECT tb.ID as \"id\", tb.ORDER_ID as \"orderId\", tb.GOODS_ID as \"goodsId\", tb.GOODS_NAME as \"goodsName\", "
	        + "tb.GOODS_SERIAL_NUM as \"goodsSerialNum\", tb.GOODS_BARCODE as \"goodsBarcode\", tb.GOODS_PHOTO as \"goodsPhoto\", tb.GOODS_COLOR_ID as \"goodsColorId\", "
	        + "tb.GOODS_COLOR_NAME as \"goodsColorName\", tb.GOODS_SIZE_ID as \"goodsSizeId\", tb.GOODS_SIZE_NAME as \"goodsSizeName\", tb.GOODS_TEXTURE_ID as \"goodsTextureId\", "
	        + "tb.GOODS_TEXTURE_NAME as \"goodsTextureName\", tb.GOODS_SALE_PRICE as \"goodsSalePrice\", tb.GOODS_PURCHASE_PRICE as \"goodsPurchasePrice\", "
	        + "tb.GOODS_ORDER_PRICE as \"goodsOrderPrice\", tb.GOODS_ORDER_PROFIT as \"goodsOrderProfit\", tb.GOODS_ORDER_NUM as \"goodsOrderNum\" "
			+ "FROM scms_order_goods tb "
			+ "WHERE 1 = 1 ";

	private static final String SQL_GET_LIST_COUNT = "SELECT count(1) as \"count\" "
			+ "FROM scms_order_goods tb "
			+ "WHERE 1 = 1 ";
	
	public List<Map<String, Object>> getList(ScmsOrderGoods scmsOrderGoods, Map<String, Object> params, int pageIndex, int pageSize) {
		//生成查询条件
		SqlParams sqlParams = genListWhere(SQL_GET_LIST, scmsOrderGoods, params);
		//添加分页和排序
		sqlParams = getPageableSql(sqlParams, pageIndex, pageSize, " tb.ID DESC ", " \"id\" DESC ");
		return getResultList(sqlParams);
	}

	public int getListCount(ScmsOrderGoods scmsOrderGoods, Map<String, Object> params) {
		//生成查询条件
		SqlParams sqlParams = genListWhere(SQL_GET_LIST_COUNT, scmsOrderGoods, params);
		return getResultListTotalCount(sqlParams);
	}

	/**
	 * 生成查询条件
	 * @param sql
	 * @param params
	 * @return
	 */
	private SqlParams genListWhere(String sql, ScmsOrderGoods scmsOrderGoods, Map<String, Object> params){
		SqlParams sqlParams = new SqlParams();
		sqlParams.querySql.append(sql);
        return sqlParams;
	}
}