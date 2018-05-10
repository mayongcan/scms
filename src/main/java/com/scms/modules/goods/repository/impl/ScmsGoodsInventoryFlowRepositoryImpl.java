/*
 * Copyright(c) 2018 gimplatform(通用信息管理平台) All rights reserved.
 */
package com.scms.modules.goods.repository.impl;

import java.util.List;
import java.util.Map;

import org.apache.commons.collections4.MapUtils;
import com.gimplatform.core.common.SqlParams;
import com.gimplatform.core.repository.BaseRepository;
import com.gimplatform.core.utils.StringUtils;

import com.scms.modules.goods.entity.ScmsGoodsInventoryFlow;
import com.scms.modules.goods.repository.custom.ScmsGoodsInventoryFlowRepositoryCustom;

public class ScmsGoodsInventoryFlowRepositoryImpl extends BaseRepository implements ScmsGoodsInventoryFlowRepositoryCustom{

	private static final String SQL_GET_LIST = "SELECT tb.ID as \"id\", tb.MERCHANTS_ID as \"merchantsId\", tb.SHOP_ID as \"shopId\", tb.GOODS_ID as \"goodsId\", tb.GOODS_BARCODE as \"goodsBarcode\", "
	        + "tb.COLOR_ID as \"colorId\", tb.COLOR_NAME as \"colorName\", tb.TEXTURE_ID as \"textureId\", tb.TEXTURE_NAME as \"textureName\", tb.SIZE_ID as \"sizeId\", "
	        + "tb.SIZE_NAME as \"sizeName\", tb.OLD_NUM as \"oldNum\", tb.NEW_NUM as \"newNum\", tb.OPERATE_NAME as \"operateName\", tb.ORDER_NUM as \"orderNum\", "
	        + "tb.ORDER_TYPE as \"orderType\", tb.CREATE_DATE as \"createDate\" "
			+ "FROM scms_goods_inventory_flow tb "
			+ "WHERE 1 = 1 ";

	private static final String SQL_GET_LIST_COUNT = "SELECT count(1) as \"count\" "
			+ "FROM scms_goods_inventory_flow tb "
			+ "WHERE 1 = 1 ";
	
	public List<Map<String, Object>> getList(ScmsGoodsInventoryFlow scmsGoodsInventoryFlow, Map<String, Object> params, int pageIndex, int pageSize) {
		//生成查询条件
		SqlParams sqlParams = genListWhere(SQL_GET_LIST, scmsGoodsInventoryFlow, params);
		//添加分页和排序
		sqlParams = getPageableSql(sqlParams, pageIndex, pageSize, " tb.ID DESC ", " \"id\" DESC ");
		return getResultList(sqlParams);
	}

	public int getListCount(ScmsGoodsInventoryFlow scmsGoodsInventoryFlow, Map<String, Object> params) {
		//生成查询条件
		SqlParams sqlParams = genListWhere(SQL_GET_LIST_COUNT, scmsGoodsInventoryFlow, params);
		return getResultListTotalCount(sqlParams);
	}

	/**
	 * 生成查询条件
	 * @param sql
	 * @param params
	 * @return
	 */
	private SqlParams genListWhere(String sql, ScmsGoodsInventoryFlow scmsGoodsInventoryFlow, Map<String, Object> params){
		SqlParams sqlParams = new SqlParams();
		sqlParams.querySql.append(sql);
        if (scmsGoodsInventoryFlow != null && scmsGoodsInventoryFlow.getMerchantsId() != null) {
            sqlParams.querySql.append(" AND tb.MERCHANTS_ID = :merchantsId ");
            sqlParams.paramsList.add("merchantsId");
            sqlParams.valueList.add(scmsGoodsInventoryFlow.getMerchantsId());
        }
		if (scmsGoodsInventoryFlow != null && scmsGoodsInventoryFlow.getShopId() != null) {
			sqlParams.querySql.append(" AND tb.SHOP_ID = :shopId ");
			sqlParams.paramsList.add("shopId");
			sqlParams.valueList.add(scmsGoodsInventoryFlow.getShopId());
		}
		if (scmsGoodsInventoryFlow != null && scmsGoodsInventoryFlow.getGoodsId() != null) {
			sqlParams.querySql.append(" AND tb.GOODS_ID = :goodsId ");
			sqlParams.paramsList.add("goodsId");
			sqlParams.valueList.add(scmsGoodsInventoryFlow.getGoodsId());
		}
		if (scmsGoodsInventoryFlow != null && scmsGoodsInventoryFlow.getColorId() != null) {
			sqlParams.querySql.append(" AND tb.COLOR_ID = :colorId ");
			sqlParams.paramsList.add("colorId");
			sqlParams.valueList.add(scmsGoodsInventoryFlow.getColorId());
		}
		if (scmsGoodsInventoryFlow != null && scmsGoodsInventoryFlow.getTextureId() != null) {
			sqlParams.querySql.append(" AND tb.TEXTURE_ID = :textureId ");
			sqlParams.paramsList.add("textureId");
			sqlParams.valueList.add(scmsGoodsInventoryFlow.getTextureId());
		}
		if (scmsGoodsInventoryFlow != null && scmsGoodsInventoryFlow.getSizeId() != null) {
			sqlParams.querySql.append(" AND tb.SIZE_ID = :sizeId ");
			sqlParams.paramsList.add("sizeId");
			sqlParams.valueList.add(scmsGoodsInventoryFlow.getSizeId());
		}
		if (!StringUtils.isBlank(MapUtils.getString(params, "createDateBegin")) && !StringUtils.isBlank(MapUtils.getString(params, "createDateEnd"))) {
			sqlParams.querySql.append(" AND tb.CREATE_DATE between :createDateBegin AND :createDateEnd ");
			sqlParams.paramsList.add("createDateBegin");
			sqlParams.paramsList.add("createDateEnd");
			sqlParams.valueList.add(MapUtils.getString(params, "createDateBegin"));
			sqlParams.valueList.add(MapUtils.getString(params, "createDateEnd"));
		}
        return sqlParams;
	}
}