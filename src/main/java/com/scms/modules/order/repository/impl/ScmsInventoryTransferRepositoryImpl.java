/*
 * Copyright(c) 2018 gimplatform(通用信息管理平台) All rights reserved.
 */
package com.scms.modules.order.repository.impl;

import java.util.List;
import java.util.Map;
import com.gimplatform.core.common.SqlParams;
import com.gimplatform.core.repository.BaseRepository;
import com.gimplatform.core.utils.StringUtils;

import com.scms.modules.order.entity.ScmsInventoryTransfer;
import com.scms.modules.order.repository.custom.ScmsInventoryTransferRepositoryCustom;

public class ScmsInventoryTransferRepositoryImpl extends BaseRepository implements ScmsInventoryTransferRepositoryCustom{

	private static final String SQL_GET_LIST = "SELECT tb.ID as \"id\", tb.MERCHANTS_ID as \"merchantsId\", tb.MERCHANTS_NAME as \"merchantsName\", tb.SRC_SHOP_ID as \"srcShopId\", "
	        + "tb.SRC_SHOP_NAME as \"srcShopName\", tb.DEST_SHOP_ID as \"destShopId\", tb.DEST_SHOP_NAME as \"destShopName\", tb.ORDER_NUM as \"orderNum\", tb.ORDER_STATUS as \"orderStatus\", "
	        + "tb.TOTAL_NUM as \"totalNum\", tb.MEMO as \"memo\", tb.CREATE_BY as \"createBy\", tb.CREATE_BY_NAME as \"createByName\", tb.CREATE_DATE as \"createDate\", "
	        + "(select count(1) from scms_inventory_transfer_goods tmpsog where tmpsog.ORDER_ID = tb.ID) as \"totalGoodsRows\" "
			+ "FROM scms_inventory_transfer tb "
			+ "WHERE tb.IS_VALID = 'Y'";

	private static final String SQL_GET_LIST_COUNT = "SELECT count(1) as \"count\" "
			+ "FROM scms_inventory_transfer tb "
			+ "WHERE tb.IS_VALID = 'Y'";
	
	public List<Map<String, Object>> getList(ScmsInventoryTransfer scmsInventoryTransfer, Map<String, Object> params, int pageIndex, int pageSize) {
		//生成查询条件
		SqlParams sqlParams = genListWhere(SQL_GET_LIST, scmsInventoryTransfer, params);
		//添加分页和排序
		sqlParams = getPageableSql(sqlParams, pageIndex, pageSize, " tb.ID DESC ", " \"id\" DESC ");
		return getResultList(sqlParams);
	}

	public int getListCount(ScmsInventoryTransfer scmsInventoryTransfer, Map<String, Object> params) {
		//生成查询条件
		SqlParams sqlParams = genListWhere(SQL_GET_LIST_COUNT, scmsInventoryTransfer, params);
		return getResultListTotalCount(sqlParams);
	}

	/**
	 * 生成查询条件
	 * @param sql
	 * @param params
	 * @return
	 */
	private SqlParams genListWhere(String sql, ScmsInventoryTransfer scmsInventoryTransfer, Map<String, Object> params){
		SqlParams sqlParams = new SqlParams();
		sqlParams.querySql.append(sql);
        if (scmsInventoryTransfer != null && scmsInventoryTransfer.getMerchantsId() != null) {
            sqlParams.querySql.append(" AND tb.MERCHANTS_ID = :merchantsId ");
            sqlParams.paramsList.add("merchantsId");
            sqlParams.valueList.add(scmsInventoryTransfer.getMerchantsId());
        }
        if (scmsInventoryTransfer != null && !StringUtils.isBlank(scmsInventoryTransfer.getOrderStatus())) {
            sqlParams.querySql.append(" AND tb.ORDER_STATUS = :orderStatus ");
            sqlParams.paramsList.add("orderStatus");
            sqlParams.valueList.add(scmsInventoryTransfer.getOrderStatus());
        }
        if (scmsInventoryTransfer != null && scmsInventoryTransfer.getSrcShopId() != null) {
            sqlParams.querySql.append(" AND tb.SRC_SHOP_ID = :srcShopId ");
            sqlParams.paramsList.add("srcShopId");
            sqlParams.valueList.add(scmsInventoryTransfer.getSrcShopId());
        }
        if (scmsInventoryTransfer != null && scmsInventoryTransfer.getDestShopId() != null) {
            sqlParams.querySql.append(" AND tb.DEST_SHOP_ID = :destShopId ");
            sqlParams.paramsList.add("destShopId");
            sqlParams.valueList.add(scmsInventoryTransfer.getDestShopId());
        }
        return sqlParams;
	}
}