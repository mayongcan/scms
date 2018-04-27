/*
 * Copyright(c) 2018 gimplatform(通用信息管理平台) All rights reserved.
 */
package com.scms.modules.order.repository.impl;

import java.util.List;
import java.util.Map;

import org.apache.commons.collections4.MapUtils;

import com.gimplatform.core.common.SqlParams;
import com.gimplatform.core.repository.BaseRepository;
import com.scms.modules.order.entity.ScmsInventoryCheckGoodsDetail;
import com.scms.modules.order.repository.custom.ScmsInventoryCheckGoodsDetailRepositoryCustom;

public class ScmsInventoryCheckGoodsDetailRepositoryImpl extends BaseRepository implements ScmsInventoryCheckGoodsDetailRepositoryCustom{

	private static final String SQL_GET_LIST = "SELECT tb.ID as \"id\", tb.ORDER_ID as \"orderId\", tb.DETAIL_ID as \"detailId\", tb.GOODS_BARCODE as \"goodsBarcode\", tb.GOODS_COLOR_ID as \"goodsColorId\", tb.GOODS_COLOR_NAME as \"goodsColorName\", tb.GOODS_TEXTURE_ID as \"goodsTextureId\", tb.GOODS_TEXTURE_NAME as \"goodsTextureName\", tb.GOODS_SIZE_ID as \"goodsSizeId\", tb.GOODS_SIZE_NAME as \"goodsSizeName\", tb.GOODS_SALE_PRICE as \"goodsSalePrice\", tb.GOODS_PURCHASE_PRICE as \"goodsPurchasePrice\", tb.GOODS_BEFORE_NUM as \"goodsBeforeNum\", tb.GOODS_AFTER_NUM as \"goodsAfterNum\", tb.PROFIT_LOSS as \"profitLoss\" "
			+ "FROM scms_inventory_check_goods_detail tb "
			+ "WHERE 1 = 1 ";

	private static final String SQL_GET_LIST_COUNT = "SELECT count(1) as \"count\" "
			+ "FROM scms_inventory_check_goods_detail tb "
			+ "WHERE 1 = 1 ";
	
	public List<Map<String, Object>> getList(ScmsInventoryCheckGoodsDetail scmsInventoryCheckGoodsDetail, Map<String, Object> params, int pageIndex, int pageSize) {
		//生成查询条件
		SqlParams sqlParams = genListWhere(SQL_GET_LIST, scmsInventoryCheckGoodsDetail, params);
		//添加分页和排序
		sqlParams = getPageableSql(sqlParams, pageIndex, pageSize, " tb.ID DESC ", " \"id\" DESC ");
		return getResultList(sqlParams);
	}

	public int getListCount(ScmsInventoryCheckGoodsDetail scmsInventoryCheckGoodsDetail, Map<String, Object> params) {
		//生成查询条件
		SqlParams sqlParams = genListWhere(SQL_GET_LIST_COUNT, scmsInventoryCheckGoodsDetail, params);
		return getResultListTotalCount(sqlParams);
	}

	/**
	 * 生成查询条件
	 * @param sql
	 * @param params
	 * @return
	 */
	private SqlParams genListWhere(String sql, ScmsInventoryCheckGoodsDetail scmsInventoryCheckGoodsDetail, Map<String, Object> params){
		SqlParams sqlParams = new SqlParams();
		sqlParams.querySql.append(sql);
        Long detailId = MapUtils.getLong(params, "detailId", null);
        if(detailId != null) {
            sqlParams.querySql.append(" AND tb.DETAIL_ID = :detailId ");
            sqlParams.paramsList.add("detailId");
            sqlParams.valueList.add(detailId);
        }
        return sqlParams;
	}
}