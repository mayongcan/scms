/*
 * Copyright(c) 2018 gimplatform(通用信息管理平台) All rights reserved.
 */
package com.scms.modules.goods.repository.impl;

import java.util.List;
import java.util.Map;

import org.apache.commons.collections4.MapUtils;

import com.gimplatform.core.common.SqlParams;
import com.gimplatform.core.repository.BaseRepository;
import com.scms.modules.goods.entity.ScmsGoodsExtraPrice;
import com.scms.modules.goods.repository.custom.ScmsGoodsExtraPriceRepositoryCustom;

public class ScmsGoodsExtraPriceRepositoryImpl extends BaseRepository implements ScmsGoodsExtraPriceRepositoryCustom{

	private static final String SQL_GET_LIST = "SELECT tb.ID as \"id\", tb.GOODS_ID as \"goodsId\", tb.EXTRA_NAME as \"extraName\", tb.EXTRA_PRICE as \"extraPrice\" "
			+ "FROM scms_goods_extra_price tb "
			+ "WHERE 1 = 1 ";

	private static final String SQL_GET_LIST_COUNT = "SELECT count(1) as \"count\" "
			+ "FROM scms_goods_extra_price tb "
			+ "WHERE 1 = 1 ";
	
	public List<Map<String, Object>> getList(ScmsGoodsExtraPrice scmsGoodsExtraPrice, Map<String, Object> params, int pageIndex, int pageSize) {
		//生成查询条件
		SqlParams sqlParams = genListWhere(SQL_GET_LIST, scmsGoodsExtraPrice, params);
		//添加分页和排序
		sqlParams = getPageableSql(sqlParams, pageIndex, pageSize, " tb.ID ASC ", " \"id\" ASC ");
		return getResultList(sqlParams);
	}

	public int getListCount(ScmsGoodsExtraPrice scmsGoodsExtraPrice, Map<String, Object> params) {
		//生成查询条件
		SqlParams sqlParams = genListWhere(SQL_GET_LIST_COUNT, scmsGoodsExtraPrice, params);
		return getResultListTotalCount(sqlParams);
	}

	/**
	 * 生成查询条件
	 * @param sql
	 * @param params
	 * @return
	 */
	private SqlParams genListWhere(String sql, ScmsGoodsExtraPrice scmsGoodsExtraPrice, Map<String, Object> params){
		SqlParams sqlParams = new SqlParams();
		sqlParams.querySql.append(sql);
        Long goodsId = MapUtils.getLong(params, "goodsId", null);
        if (goodsId != null) {
            sqlParams.querySql.append(" AND tb.GOODS_ID = :goodsId ");
            sqlParams.paramsList.add("goodsId");
            sqlParams.valueList.add(goodsId);
        }
        return sqlParams;
	}
}