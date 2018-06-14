/*
 * Copyright(c) 2018 gimplatform(通用信息管理平台) All rights reserved.
 */
package com.scms.modules.goods.repository.impl;

import java.util.List;
import java.util.Map;

import org.apache.commons.collections4.MapUtils;

import com.gimplatform.core.common.SqlParams;
import com.gimplatform.core.repository.BaseRepository;
import com.scms.modules.goods.entity.ScmsGoodsInventory;
import com.scms.modules.goods.repository.custom.ScmsGoodsInventoryRepositoryCustom;

public class ScmsGoodsInventoryRepositoryImpl extends BaseRepository implements ScmsGoodsInventoryRepositoryCustom{

	private static final String SQL_GET_LIST = "SELECT tb.ID as \"id\", tb.MERCHANTS_ID as \"merchantsId\", tb.SHOP_ID as \"shopId\", tb.GOODS_ID as \"goodsId\", tb.GOODS_BARCODE as \"goodsBarcode\", "
	        + "tb.COLOR_ID as \"colorId\", tb.COLOR_NAME as \"colorName\", tb.INVENTORY_SIZE_ID as \"inventorySizeId\", tb.INVENTORY_SIZE as \"inventorySize\", tb.TEXTURE_ID as \"textureId\", "
	        + "tb.TEXTURE_NAME as \"textureName\", tb.INVENTORY_NUM as \"inventoryNum\", tb.INVENTORY_PHOTO as \"inventoryPhoto\", ssi.SHOP_NAME as \"shopName\" "
			+ "FROM scms_goods_inventory tb left join scms_shop_info ssi on ssi.ID = tb.SHOP_ID "
			+ "WHERE 1 = 1 ";

	private static final String SQL_GET_LIST_COUNT = "SELECT count(1) as \"count\" "
            + "FROM scms_goods_inventory tb left join scms_shop_info ssi on ssi.ID = tb.SHOP_ID "
			+ "WHERE 1 = 1 ";
	

    private static final String SQL_GET_STATISTICS_GOODS_INVENTORY = "SELECT DISTINCT(tb.SHOP_ID) as shopId, sum(tb.INVENTORY_NUM) as totalInventoryNum, si.SHOP_NAME as shopName, gi.PURCHASE_PRICE as purchasePrice "
            + "FROM scms_goods_inventory tb left join scms_shop_info si on si.ID = tb.SHOP_ID "
            + "left join scms_goods_info gi on gi.ID = tb.GOODS_ID "
            + "WHERE 1 = 1 ";
	
	public List<Map<String, Object>> getList(ScmsGoodsInventory scmsGoodsInventory, Map<String, Object> params, int pageIndex, int pageSize) {
		//生成查询条件
		SqlParams sqlParams = genListWhere(SQL_GET_LIST, scmsGoodsInventory, params);
		//添加分页和排序
		sqlParams = getPageableSql(sqlParams, pageIndex, pageSize, " tb.ID DESC ", " \"id\" DESC ");
		return getResultList(sqlParams);
	}

	public int getListCount(ScmsGoodsInventory scmsGoodsInventory, Map<String, Object> params) {
		//生成查询条件
		SqlParams sqlParams = genListWhere(SQL_GET_LIST_COUNT, scmsGoodsInventory, params);
		return getResultListTotalCount(sqlParams);
	}

	/**
	 * 生成查询条件
	 * @param sql
	 * @param params
	 * @return
	 */
	private SqlParams genListWhere(String sql, ScmsGoodsInventory scmsGoodsInventory, Map<String, Object> params){
		SqlParams sqlParams = new SqlParams();
		sqlParams.querySql.append(sql);
        Long goodsId = MapUtils.getLong(params, "goodsId", null);
        Long shopId = MapUtils.getLong(params, "shopId", null);
        if (goodsId != null) {
            sqlParams.querySql.append(" AND tb.GOODS_ID = :goodsId ");
            sqlParams.paramsList.add("goodsId");
            sqlParams.valueList.add(goodsId);
        }
        if (shopId != null) {
            sqlParams.querySql.append(" AND tb.SHOP_ID = :shopId ");
            sqlParams.paramsList.add("shopId");
            sqlParams.valueList.add(shopId);
        }
        return sqlParams;
	}

    @Override
    public List<Map<String, Object>> getStatisticsGoodsInventory(Map<String, Object> params) {
        SqlParams sqlParams = new SqlParams();
        sqlParams.querySql.append(SQL_GET_STATISTICS_GOODS_INVENTORY);
        Long goodsId = MapUtils.getLong(params, "goodsId", null);
        if (goodsId != null) {
            sqlParams.querySql.append(" AND tb.GOODS_ID = :goodsId ");
            sqlParams.paramsList.add("goodsId");
            sqlParams.valueList.add(goodsId);
        }
        sqlParams.querySql.append(" GROUP BY(tb.SHOP_ID) ");
        sqlParams.querySql.append(" ORDER BY tb.SHOP_ID ");
        return getResultList(sqlParams);
    }
}