/*
 * Copyright(c) 2018 gimplatform(通用信息管理平台) All rights reserved.
 */
package com.scms.modules.order.repository.impl;

import java.util.List;
import java.util.Map;
import com.gimplatform.core.common.SqlParams;
import com.gimplatform.core.repository.BaseRepository;
import com.scms.modules.order.entity.ScmsInventoryCheck;
import com.scms.modules.order.repository.custom.ScmsInventoryCheckRepositoryCustom;

public class ScmsInventoryCheckRepositoryImpl extends BaseRepository implements ScmsInventoryCheckRepositoryCustom{

	private static final String SQL_GET_LIST = "SELECT tb.ID as \"id\", tb.MERCHANTS_ID as \"merchantsId\", tb.MERCHANTS_NAME as \"merchantsName\", tb.SHOP_ID as \"shopId\", "
	        + "tb.SHOP_NAME as \"shopName\", tb.ORDER_NUM as \"orderNum\", tb.TOTAL_NUM as \"totalNum\", tb.MEMO as \"memo\", tb.CREATE_BY as \"createBy\", "
	        + "tb.CREATE_BY_NAME as \"createByName\", tb.CREATE_DATE as \"createDate\", "
	        + "(select count(1) from scms_inventory_check_goods tmpsog where tmpsog.ORDER_ID = tb.ID) as \"totalGoodsRows\" "
			+ "FROM scms_inventory_check tb "
			+ "WHERE tb.IS_VALID = 'Y'";

	private static final String SQL_GET_LIST_COUNT = "SELECT count(1) as \"count\" "
			+ "FROM scms_inventory_check tb "
			+ "WHERE tb.IS_VALID = 'Y'";
	
	public List<Map<String, Object>> getList(ScmsInventoryCheck scmsInventoryCheck, Map<String, Object> params, int pageIndex, int pageSize) {
		//生成查询条件
		SqlParams sqlParams = genListWhere(SQL_GET_LIST, scmsInventoryCheck, params);
		//添加分页和排序
		sqlParams = getPageableSql(sqlParams, pageIndex, pageSize, " tb.ID DESC ", " \"id\" DESC ");
		return getResultList(sqlParams);
	}

	public int getListCount(ScmsInventoryCheck scmsInventoryCheck, Map<String, Object> params) {
		//生成查询条件
		SqlParams sqlParams = genListWhere(SQL_GET_LIST_COUNT, scmsInventoryCheck, params);
		return getResultListTotalCount(sqlParams);
	}

	/**
	 * 生成查询条件
	 * @param sql
	 * @param params
	 * @return
	 */
	private SqlParams genListWhere(String sql, ScmsInventoryCheck scmsInventoryCheck, Map<String, Object> params){
		SqlParams sqlParams = new SqlParams();
		sqlParams.querySql.append(sql);
        if (scmsInventoryCheck != null && scmsInventoryCheck.getMerchantsId() != null) {
            sqlParams.querySql.append(" AND tb.MERCHANTS_ID = :merchantsId ");
            sqlParams.paramsList.add("merchantsId");
            sqlParams.valueList.add(scmsInventoryCheck.getMerchantsId());
        }
        if (scmsInventoryCheck != null && scmsInventoryCheck.getShopId() != null) {
            sqlParams.querySql.append(" AND tb.SHOP_ID = :shopId ");
            sqlParams.paramsList.add("shopId");
            sqlParams.valueList.add(scmsInventoryCheck.getShopId());
        }
        return sqlParams;
	}
}