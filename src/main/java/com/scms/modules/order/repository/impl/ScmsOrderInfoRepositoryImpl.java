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

import com.scms.modules.order.entity.ScmsOrderInfo;
import com.scms.modules.order.repository.custom.ScmsOrderInfoRepositoryCustom;

public class ScmsOrderInfoRepositoryImpl extends BaseRepository implements ScmsOrderInfoRepositoryCustom{

	private static final String SQL_GET_LIST = "SELECT tb.ID as \"id\", tb.MERCHANTS_ID as \"merchantsId\", tb.MERCHANTS_NAME as \"merchantsName\", tb.SHOP_ID as \"shopId\", tb.SHOP_NAME as \"shopName\", "
	        + "tb.ORDER_TYPE as \"orderType\", tb.ORDER_STATUS as \"orderStatus\", tb.ORDER_PAY_STATUS as \"orderPayStatus\", tb.ORDER_SEND_STATUS as \"orderSendStatus\", tb.ORDER_RECEIVE_STATUS as \"orderReceiveStatus\", "
	        + "tb.ORDER_NUM as \"orderNum\", tb.TOTAL_AMOUNT as \"totalAmount\", tb.TOTAL_UN_PAY as \"totalUnPay\", tb.TOTAL_PROFIT as \"totalProfit\", tb.TOTAL_NUM as \"totalNum\", "
	        + "tb.ORDER_CUSTOMER_TYPE as \"orderCustomerType\", tb.CUSTOMER_TYPE_ID as \"customerTypeId\",tb.CUSTOMER_TYPE_NAME as \"customerTypeName\", tb.CUSTOMER_LEVEL_ID as \"customerLevelId\","
	        + "tb.CUSTOMER_LEVEL_NAME as \"customerLevelName\", tb.CUSTOMER_ID as \"customerId\", tb.CUSTOMER_NAME as \"customerName\", tb.TRANSPORT_ID as \"transportId\", "
	        + "tb.TRANSPORT_NAME as \"transportName\", tb.SELLER_BY as \"sellerBy\", tb.SELLER_BY_NAME as \"sellerByName\", tb.PERFORMANCE_BY as \"performanceBy\", tb.PERFORMANCE_BY_NAME as \"performanceByName\", "
	        + "tb.ORDER_MEMO as \"orderMemo\", tb.CREATE_BY as \"createBy\", tb.CREATE_BY_NAME as \"createByName\", tb.CREATE_DATE as \"createDate\", "
	        + "(select count(1) from scms_order_goods tmpsog where tmpsog.ORDER_ID = tb.ID) as \"totalGoodsRows\" "
			+ "FROM scms_order_info tb "
			+ "WHERE tb.IS_VALID = 'Y' ";

	private static final String SQL_GET_LIST_COUNT = "SELECT count(1) as \"count\" "
			+ "FROM scms_order_info tb "
			+ "WHERE tb.IS_VALID = 'Y' ";

	//进货单列表
    private static final String SQL_GET_JHD_LIST = "SELECT tb.ID as \"id\", tb.MERCHANTS_ID as \"merchantsId\", tb.MERCHANTS_NAME as \"merchantsName\", tb.SHOP_ID as \"shopId\", tb.SHOP_NAME as \"shopName\", "
            + "tb.ORDER_TYPE as \"orderType\", tb.ORDER_STATUS as \"orderStatus\", tb.ORDER_PAY_STATUS as \"orderPayStatus\", tb.ORDER_SEND_STATUS as \"orderSendStatus\", tb.ORDER_RECEIVE_STATUS as \"orderReceiveStatus\", "
            + "tb.ORDER_NUM as \"orderNum\", tb.TOTAL_AMOUNT as \"totalAmount\", tb.TOTAL_UN_PAY as \"totalUnPay\", tb.TOTAL_PROFIT as \"totalProfit\", tb.TOTAL_NUM as \"totalNum\", "
            + "tb.ORDER_CUSTOMER_TYPE as \"orderCustomerType\", tb.CUSTOMER_TYPE_ID as \"customerTypeId\",tb.CUSTOMER_TYPE_NAME as \"customerTypeName\", tb.CUSTOMER_LEVEL_ID as \"customerLevelId\","
            + "tb.CUSTOMER_LEVEL_NAME as \"customerLevelName\", tb.CUSTOMER_ID as \"customerId\", tb.CUSTOMER_NAME as \"customerName\", tb.TRANSPORT_ID as \"transportId\", "
            + "tb.TRANSPORT_NAME as \"transportName\", tb.SELLER_BY as \"sellerBy\", tb.SELLER_BY_NAME as \"sellerByName\", tb.PERFORMANCE_BY as \"performanceBy\", tb.PERFORMANCE_BY_NAME as \"performanceByName\", "
            + "tb.ORDER_MEMO as \"orderMemo\", tb.CREATE_BY as \"createBy\", tb.CREATE_BY_NAME as \"createByName\", tb.CREATE_DATE as \"createDate\", "
            + "(select count(1) from scms_order_goods tmpsog where tmpsog.ORDER_ID = tb.ID) as \"totalGoodsRows\", "
            + "ssi.SUPPLIER_NAME as \"supplierName\", ssi.SUPPLIER_ADMIN as \"supplierAdmin\", ssi.SUPPLIER_PHONE as \"supplierPhone\" "
            + "FROM scms_order_info tb left join scms_supplier_info ssi on ssi.ID = tb.CUSTOMER_ID "
            + "WHERE tb.IS_VALID = 'Y' ";

    private static final String SQL_GET_JHD_LIST_COUNT = "SELECT count(1) as \"count\" "
            + "FROM scms_order_info tb left join scms_supplier_info ssi on ssi.ID = tb.CUSTOMER_ID "
            + "WHERE tb.IS_VALID = 'Y' ";
	
	public List<Map<String, Object>> getList(ScmsOrderInfo scmsOrderInfo, Map<String, Object> params, int pageIndex, int pageSize) {
		//生成查询条件
		SqlParams sqlParams = genBaseListWhere(SQL_GET_LIST, scmsOrderInfo, params);
		sqlParams = genListWhere(sqlParams, scmsOrderInfo, params);
		//添加分页和排序
		sqlParams = getPageableSql(sqlParams, pageIndex, pageSize, " tb.ID DESC ", " \"id\" DESC ");
		return getResultList(sqlParams);
	}

	public int getListCount(ScmsOrderInfo scmsOrderInfo, Map<String, Object> params) {
		//生成查询条件
		SqlParams sqlParams = genBaseListWhere(SQL_GET_LIST_COUNT, scmsOrderInfo, params);
        sqlParams = genListWhere(sqlParams, scmsOrderInfo, params);
		return getResultListTotalCount(sqlParams);
	}
	
	/**
	 * 公用查询条件
	 * @param sql
	 * @param scmsOrderInfo
	 * @param params
	 * @return
	 */
	private SqlParams genBaseListWhere(String sql, ScmsOrderInfo scmsOrderInfo, Map<String, Object> params){
        SqlParams sqlParams = new SqlParams();
        sqlParams.querySql.append(sql);
        if (scmsOrderInfo != null && scmsOrderInfo.getMerchantsId() != null) {
            sqlParams.querySql.append(" AND tb.MERCHANTS_ID = :merchantsId ");
            sqlParams.paramsList.add("merchantsId");
            sqlParams.valueList.add(scmsOrderInfo.getMerchantsId());
        }
        if (scmsOrderInfo != null && scmsOrderInfo.getShopId() != null) {
            sqlParams.querySql.append(" AND tb.SHOP_ID = :shopId ");
            sqlParams.paramsList.add("shopId");
            sqlParams.valueList.add(scmsOrderInfo.getShopId());
        }
        if (scmsOrderInfo != null && !StringUtils.isBlank(scmsOrderInfo.getOrderType())) {
            sqlParams.querySql.append(" AND tb.ORDER_TYPE = :orderType ");
            sqlParams.paramsList.add("orderType");
            sqlParams.valueList.add(scmsOrderInfo.getOrderType());
        }
        if (scmsOrderInfo != null && !StringUtils.isBlank(scmsOrderInfo.getOrderStatus())) {
            sqlParams.querySql.append(" AND tb.ORDER_STATUS = :orderStatus ");
            sqlParams.paramsList.add("orderStatus");
            sqlParams.valueList.add(scmsOrderInfo.getOrderStatus());
        }
        if (scmsOrderInfo != null && !StringUtils.isBlank(scmsOrderInfo.getOrderPayStatus())) {
            sqlParams.querySql.append(" AND tb.ORDER_PAY_STATUS = :orderPayStatus ");
            sqlParams.paramsList.add("orderPayStatus");
            sqlParams.valueList.add(scmsOrderInfo.getOrderPayStatus());
        }
        if (scmsOrderInfo != null && !StringUtils.isBlank(scmsOrderInfo.getOrderSendStatus())) {
            sqlParams.querySql.append(" AND tb.ORDER_SEND_STATUS = :orderSendStatus ");
            sqlParams.paramsList.add("orderSendStatus");
            sqlParams.valueList.add(scmsOrderInfo.getOrderSendStatus());
        }
        if (scmsOrderInfo != null && !StringUtils.isBlank(scmsOrderInfo.getOrderReceiveStatus())) {
            sqlParams.querySql.append(" AND tb.ORDER_RECEIVE_STATUS = :orderReceiveStatus ");
            sqlParams.paramsList.add("orderReceiveStatus");
            sqlParams.valueList.add(scmsOrderInfo.getOrderReceiveStatus());
        }
        return sqlParams;
    }

	/**
	 * 生成查询条件
	 * @param sql
	 * @param params
	 * @return
	 */
	private SqlParams genListWhere(SqlParams sqlParams, ScmsOrderInfo scmsOrderInfo, Map<String, Object> params){
        if (scmsOrderInfo.getCustomerTypeId() != null) {
            sqlParams.querySql.append(" AND tb.CUSTOMER_TYPE_ID = :customerTypeId ");
            sqlParams.paramsList.add("customerTypeId");
            sqlParams.valueList.add(scmsOrderInfo.getCustomerTypeId());
        }
        if (scmsOrderInfo.getCustomerLevelId() != null) {
            sqlParams.querySql.append(" AND tb.CUSTOMER_LEVEL_ID = :customerLevelId ");
            sqlParams.paramsList.add("customerLevelId");
            sqlParams.valueList.add(scmsOrderInfo.getCustomerLevelId());
        }
        if (scmsOrderInfo != null && !StringUtils.isBlank(scmsOrderInfo.getCustomerName())) {
            sqlParams.querySql.append(getLikeSql("tb.CUSTOMER_NAME", ":customerName"));
            sqlParams.paramsList.add("customerName");
            sqlParams.valueList.add(scmsOrderInfo.getCustomerName());
        }
        return sqlParams;
	}

    @Override
    public List<Map<String, Object>> getOrderJhdList(ScmsOrderInfo scmsOrderInfo, Map<String, Object> params, int pageIndex, int pageSize) {
        //生成查询条件
        SqlParams sqlParams = genBaseListWhere(SQL_GET_JHD_LIST, scmsOrderInfo, params);
        sqlParams = genJhdListWhere(sqlParams, scmsOrderInfo, params);
        //添加分页和排序
        sqlParams = getPageableSql(sqlParams, pageIndex, pageSize, " tb.ID DESC ", " \"id\" DESC ");
        return getResultList(sqlParams);
    }

    @Override
    public int getOrderJhdListCount(ScmsOrderInfo scmsOrderInfo, Map<String, Object> params) {
        //生成查询条件
        SqlParams sqlParams = genBaseListWhere(SQL_GET_JHD_LIST_COUNT, scmsOrderInfo, params);
        sqlParams = genJhdListWhere(sqlParams, scmsOrderInfo, params);
        return getResultListTotalCount(sqlParams);
    }

    /**
     * 生成查询条件
     * @param sql
     * @param params
     * @return
     */
    private SqlParams genJhdListWhere(SqlParams sqlParams, ScmsOrderInfo scmsOrderInfo, Map<String, Object> params){
        String supplierName = MapUtils.getString(params, "supplierName");
        String supplierAdmin = MapUtils.getString(params, "supplierAdmin");
        String supplierPhone = MapUtils.getString(params, "supplierPhone");
        if (!StringUtils.isBlank(supplierName)) {
            sqlParams.querySql.append(getLikeSql("ssi.SUPPLIER_NAME", ":supplierName"));
            sqlParams.paramsList.add("supplierName");
            sqlParams.valueList.add(supplierName);
        }
        if (!StringUtils.isBlank(supplierAdmin)) {
            sqlParams.querySql.append(getLikeSql("ssi.SUPPLIER_ADMIN", ":supplierAdmin"));
            sqlParams.paramsList.add("supplierAdmin");
            sqlParams.valueList.add(supplierAdmin);
        }
        if (!StringUtils.isBlank(supplierPhone)) {
            sqlParams.querySql.append(getLikeSql("ssi.SUPPLIER_PHONE", ":supplierPhone"));
            sqlParams.paramsList.add("supplierPhone");
            sqlParams.valueList.add(supplierPhone);
        }
        return sqlParams;
    }
}