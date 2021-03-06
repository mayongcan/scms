/*
 * Copyright(c) 2018 gimplatform(通用信息管理平台) All rights reserved.
 */
package com.scms.modules.order.repository.impl;

import java.util.List;
import java.util.Map;

import org.apache.commons.collections4.MapUtils;

import com.gimplatform.core.common.SqlParams;
import com.gimplatform.core.repository.BaseRepository;
import com.gimplatform.core.utils.StringUtils;
import com.scms.modules.order.entity.ScmsOrderGoodsDetail;
import com.scms.modules.order.repository.custom.ScmsOrderGoodsDetailRepositoryCustom;

public class ScmsOrderGoodsDetailRepositoryImpl extends BaseRepository implements ScmsOrderGoodsDetailRepositoryCustom{

	private static final String SQL_GET_LIST = "SELECT tb.ID as \"id\", tb.ORDER_ID as \"orderId\", tb.DETAIL_ID as \"detailId\", tb.GOODS_BARCODE as \"goodsBarcode\", tb.GOODS_COLOR_ID as \"goodsColorId\", "
	        + "tb.GOODS_COLOR_NAME as \"goodsColorName\", tb.GOODS_SIZE_ID as \"goodsSizeId\", tb.GOODS_SIZE_NAME as \"goodsSizeName\", tb.GOODS_SALE_PRICE as \"goodsSalePrice\", tb.GOODS_PURCHASE_PRICE as \"goodsPurchasePrice\", "
	        + "tb.GOODS_ORDER_PRICE as \"goodsOrderPrice\", tb.GOODS_DISCOUNT as \"goodsDiscount\", tb.GOODS_ORDER_PROFIT as \"goodsOrderProfit\", tb.GOODS_ORDER_NUM as \"goodsOrderNum\", "
	        + "tb.SEND_STATUS as \"sendStatus\", tb.RECEIVE_STATUS as \"receiveStatus\", "
	        + "(select sgi.INVENTORY_NUM from scms_goods_inventory sgi where sgi.SHOP_ID = soi.SHOP_ID AND sgi.GOODS_ID = sog.GOODS_ID AND sgi.COLOR_ID = tb.GOODS_COLOR_ID AND sgi.INVENTORY_SIZE_ID = tb.GOODS_SIZE_ID) as \"inventoryNum\" "
			+ "FROM scms_order_goods_detail tb left join scms_order_info soi on soi.ID = tb.ORDER_ID "
			+ "left join scms_order_goods sog on sog.ID = tb.DETAIL_ID "
			+ "WHERE 1 = 1 ";

	private static final String SQL_GET_LIST_COUNT = "SELECT count(1) as \"count\" "
			+ "FROM scms_order_goods_detail tb "
			+ "WHERE 1 = 1 ";
	
	public List<Map<String, Object>> getList(ScmsOrderGoodsDetail scmsOrderGoodsDetail, Map<String, Object> params, int pageIndex, int pageSize) {
		//生成查询条件
		SqlParams sqlParams = genListWhere(SQL_GET_LIST, scmsOrderGoodsDetail, params);
		//添加分页和排序
		sqlParams = getPageableSql(sqlParams, pageIndex, pageSize, " tb.ID DESC ", " \"id\" DESC ");
		return getResultList(sqlParams);
	}

	public int getListCount(ScmsOrderGoodsDetail scmsOrderGoodsDetail, Map<String, Object> params) {
		//生成查询条件
		SqlParams sqlParams = genListWhere(SQL_GET_LIST_COUNT, scmsOrderGoodsDetail, params);
		return getResultListTotalCount(sqlParams);
	}

	/**
	 * 生成查询条件
	 * @param sql
	 * @param params
	 * @return
	 */
	private SqlParams genListWhere(String sql, ScmsOrderGoodsDetail scmsOrderGoodsDetail, Map<String, Object> params){
		SqlParams sqlParams = new SqlParams();
		sqlParams.querySql.append(sql);
		Long detailId = MapUtils.getLong(params, "detailId", null);
		String sendStatus = MapUtils.getString(params, "sendStatus");
		String receiveStatus = MapUtils.getString(params, "receiveStatus");
		if(detailId != null) {
            sqlParams.querySql.append(" AND tb.DETAIL_ID = :detailId ");
            sqlParams.paramsList.add("detailId");
            sqlParams.valueList.add(detailId);
		}
		if(!StringUtils.isBlank(sendStatus)) {
            sqlParams.querySql.append(" AND tb.SEND_STATUS = :sendStatus ");
            sqlParams.paramsList.add("sendStatus");
            sqlParams.valueList.add(sendStatus);
		}
        if(!StringUtils.isBlank(receiveStatus)) {
            sqlParams.querySql.append(" AND tb.RECEIVE_STATUS = :receiveStatus ");
            sqlParams.paramsList.add("receiveStatus");
            sqlParams.valueList.add(receiveStatus);
        }
        return sqlParams;
	}
}