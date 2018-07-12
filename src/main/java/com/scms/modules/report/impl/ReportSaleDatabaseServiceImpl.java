/*
 * Copyright(c) 2018 gimplatform(通用信息管理平台) All rights reserved.
 */
package com.scms.modules.report.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections4.MapUtils;
import org.springframework.stereotype.Service;

import com.gimplatform.core.common.SqlParams;
import com.gimplatform.core.repository.BaseRepository;
import com.gimplatform.core.utils.StringUtils;
import com.scms.modules.report.ReportSaleDatabaseService;

@Service
public class ReportSaleDatabaseServiceImpl extends BaseRepository implements ReportSaleDatabaseService{

    //销售明细
    private static final String SQL_GET_SALE_REPORT_DETAIL_LIST = "SELECT tb.ID as \"id\", tb.ORDER_ID as \"orderId\", tb.DETAIL_ID as \"detailId\", tb.GOODS_BARCODE as \"goodsBarcode\", tb.GOODS_COLOR_ID as \"goodsColorId\", "
            + "tb.GOODS_COLOR_NAME as \"goodsColorName\", tb.GOODS_SIZE_ID as \"goodsSizeId\", tb.GOODS_SIZE_NAME as \"goodsSizeName\", tb.GOODS_SALE_PRICE as \"goodsSalePrice\", tb.GOODS_PURCHASE_PRICE as \"goodsPurchasePrice\", "
            + "tb.GOODS_ORDER_PRICE as \"goodsOrderPrice\", tb.GOODS_DISCOUNT as \"goodsDiscount\", tb.GOODS_ORDER_PROFIT as \"goodsOrderProfit\", tb.GOODS_ORDER_NUM as \"goodsOrderNum\", tb.SEND_STATUS as \"sendStatus\", tb.RECEIVE_STATUS as \"receiveStatus\","
            + "soi.ORDER_NUM as \"orderNum\", soi.SHOP_NAME as \"shopName\", soi.CUSTOMER_NAME as \"customerName\", soi.ORDER_TYPE as \"orderType\", soi.CREATE_DATE as \"createDate\", soi.CREATE_BY_NAME as \"createByName\", soi.SELLER_BY_NAME as \"sellerByName\", soi.PERFORMANCE_BY_NAME as \"performanceByName\", "
            + "sgc.CATEGORY_NAME as \"categoryName\", "
            + "sgi.GOODS_NAME as \"goodsName\", sgi.GOODS_SERIAL_NUM as \"goodsSerialNum\", sgi.GOODS_PHOTO as \"goodsPhoto\", sgi.GOODS_YEAR as \"goodsYear\", sgi.GOODS_SEASON as \"goodsSeason\", "
            + "svi.VENDER_NAME as \"venderName\" "
            + "FROM scms_order_goods_detail tb left join scms_order_info soi on soi.ID = tb.ORDER_ID "
            + "left join scms_order_goods sog on sog.ID = tb.DETAIL_ID "
            + "left join scms_goods_info sgi on sgi.ID = sog.GOODS_ID "
            + "left join scms_goods_category sgc on sgc.ID = sgi.CATEGORY_ID "
            + "left join scms_vender_info svi on svi.ID = sgi.VENDER_ID "
            + "WHERE 1 = 1 ";

    private static final String SQL_GET_SALE_REPORT_DETAIL_LIST_COUNT = "SELECT count(1) as \"count\" "
            + "FROM scms_order_goods_detail tb left join scms_order_info soi on soi.ID = tb.ORDER_ID "
            + "left join scms_order_goods sog on sog.ID = tb.DETAIL_ID "
            + "left join scms_goods_info sgi on sgi.ID = sog.GOODS_ID "
            + "left join scms_goods_category sgc on sgc.ID = sgi.CATEGORY_ID "
            + "WHERE 1 = 1 ";

    //统计
    private static final String SQL_GET_SALE_REPORT_DETAIL_STATISTICS = "SELECT sum(tb.GOODS_ORDER_NUM) as \"singleNum\", "
            + "sum(tb.GOODS_ORDER_NUM * tb.GOODS_PURCHASE_PRICE) as \"totalPurchase\", "
            + "sum(tb.GOODS_ORDER_NUM * tb.GOODS_ORDER_PROFIT) as \"totalProfit\" "
            + "FROM scms_order_goods_detail tb left join scms_order_info soi on soi.ID = tb.ORDER_ID "
            + "left join scms_order_goods sog on sog.ID = tb.DETAIL_ID "
            + "left join scms_goods_info sgi on sgi.ID = sog.GOODS_ID "
            + "left join scms_goods_category sgc on sgc.ID = sgi.CATEGORY_ID "
            + "left join scms_vender_info svi on svi.ID = sgi.VENDER_ID "
            + "WHERE 1 = 1 ";
    
    //商品汇总
    private static final String SQL_GET_SALE_REPORT_GOODS_LIST = "SELECT distinct(sog.GOODS_ID), "
            + "sum(tb.GOODS_ORDER_NUM) as \"goodsOrderNum\", "
            + "sum(tb.GOODS_ORDER_NUM * tb.GOODS_PURCHASE_PRICE) as \"goodsPurchasePrice\", "
            + "sum(tb.GOODS_ORDER_NUM * tb.GOODS_ORDER_PROFIT) as \"goodsOrderProfit\", "         
            + "sgc.CATEGORY_NAME as \"categoryName\", "
            + "sgi.ID as \"goodsId\", sgi.GOODS_NAME as \"goodsName\", sgi.GOODS_PHOTO as \"goodsPhoto\", sgi.GOODS_SERIAL_NUM as \"goodsSerialNum\", sgi.GOODS_YEAR as \"goodsYear\", sgi.GOODS_SEASON as \"goodsSeason\", "
            + "svi.VENDER_NAME as \"venderName\" "
            + "FROM scms_order_goods_detail tb left join scms_order_info soi on soi.ID = tb.ORDER_ID "
            + "left join scms_order_goods sog on sog.ID = tb.DETAIL_ID "
            + "left join scms_goods_info sgi on sgi.ID = sog.GOODS_ID "
            + "left join scms_goods_category sgc on sgc.ID = sgi.CATEGORY_ID "
            + "left join scms_vender_info svi on svi.ID = sgi.VENDER_ID "
            + "WHERE 1 = 1 ";

    private static final String SQL_GET_SALE_REPORT_GOODS_LIST_COUNT = "SELECT count(distinct(sog.GOODS_ID)) as \"count\" "
            + "FROM scms_order_goods_detail tb left join scms_order_info soi on soi.ID = tb.ORDER_ID "
            + "left join scms_order_goods sog on sog.ID = tb.DETAIL_ID "
            + "left join scms_goods_info sgi on sgi.ID = sog.GOODS_ID "
            + "left join scms_goods_category sgc on sgc.ID = sgi.CATEGORY_ID "
            + "WHERE 1 = 1 ";
    
    //订单汇总
    private static final String SQL_GET_SALE_REPORT_ORDER_LIST = "SELECT distinct(tb.ORDER_ID), "
            + "sum(tb.GOODS_ORDER_NUM) as \"goodsOrderNum\", "
            + "sum(tb.GOODS_ORDER_NUM * tb.GOODS_PURCHASE_PRICE) as \"goodsPurchasePrice\", "
            + "sum(tb.GOODS_ORDER_NUM * tb.GOODS_ORDER_PROFIT) as \"goodsOrderProfit\", " 
            + "soi.ID as \"orderId\", soi.ORDER_NUM as \"orderNum\", soi.SHOP_NAME as \"shopName\", soi.CUSTOMER_NAME as \"customerName\", soi.ORDER_TYPE as \"orderType\", soi.CREATE_DATE as \"createDate\", soi.CREATE_BY_NAME as \"createByName\", soi.SELLER_BY_NAME as \"sellerByName\", soi.PERFORMANCE_BY_NAME as \"performanceByName\" "
            + "FROM scms_order_goods_detail tb left join scms_order_info soi on soi.ID = tb.ORDER_ID "
            + "WHERE 1 = 1 ";

    private static final String SQL_GET_SALE_REPORT_ORDER_LIST_COUNT = "SELECT count(distinct(tb.ORDER_ID)) as \"count\" "
            + "FROM scms_order_goods_detail tb left join scms_order_info soi on soi.ID = tb.ORDER_ID "
            + "WHERE 1 = 1 ";
    
    //客户汇总
    private static final String SQL_GET_SALE_REPORT_CUSTOMER_LIST = "SELECT distinct(soi.CUSTOMER_ID), "
            + "sum(tb.GOODS_ORDER_NUM) as \"goodsOrderNum\", "
            + "sum(tb.GOODS_ORDER_NUM * tb.GOODS_PURCHASE_PRICE) as \"goodsPurchasePrice\", "
            + "sum(tb.GOODS_ORDER_NUM * tb.GOODS_ORDER_PROFIT) as \"goodsOrderProfit\", " 
            + "soi.CUSTOMER_ID as \"customerId\", soi.CUSTOMER_NAME as \"customerName\" "
            + "FROM scms_order_goods_detail tb left join scms_order_info soi on soi.ID = tb.ORDER_ID "
            + "WHERE 1 = 1 ";

    private static final String SQL_GET_SALE_REPORT_CUSTOMER_LIST_COUNT = "SELECT count(distinct(soi.CUSTOMER_ID)) as \"count\" "
            + "FROM scms_order_goods_detail tb left join scms_order_info soi on soi.ID = tb.ORDER_ID "
            + "WHERE 1 = 1 ";
    
    //创建人汇总
    private static final String SQL_GET_SALE_REPORT_CREATEBY_LIST = "SELECT distinct(soi.CREATE_BY), "
            + "sum(tb.GOODS_ORDER_NUM) as \"goodsOrderNum\", "
            + "sum(tb.GOODS_ORDER_NUM * tb.GOODS_PURCHASE_PRICE) as \"goodsPurchasePrice\", "
            + "sum(tb.GOODS_ORDER_NUM * tb.GOODS_ORDER_PROFIT) as \"goodsOrderProfit\", " 
            + "soi.CREATE_BY as \"createBy\", soi.CREATE_BY_NAME as \"createByName\" "
            + "FROM scms_order_goods_detail tb left join scms_order_info soi on soi.ID = tb.ORDER_ID "
            + "WHERE 1 = 1 ";

    private static final String SQL_GET_SALE_REPORT_CREATEBY_LIST_COUNT = "SELECT count(distinct(soi.CREATE_BY)) as \"count\" "
            + "FROM scms_order_goods_detail tb left join scms_order_info soi on soi.ID = tb.ORDER_ID "
            + "WHERE 1 = 1 ";
    
    //销售人汇总
    private static final String SQL_GET_SALE_REPORT_SELLERBY_LIST = "SELECT distinct(soi.SELLER_BY), "
            + "sum(tb.GOODS_ORDER_NUM) as \"goodsOrderNum\", "
            + "sum(tb.GOODS_ORDER_NUM * tb.GOODS_PURCHASE_PRICE) as \"goodsPurchasePrice\", "
            + "sum(tb.GOODS_ORDER_NUM * tb.GOODS_ORDER_PROFIT) as \"goodsOrderProfit\", " 
            + "soi.SELLER_BY as \"sellerBy\", soi.SELLER_BY_NAME as \"sellerByName\" "
            + "FROM scms_order_goods_detail tb left join scms_order_info soi on soi.ID = tb.ORDER_ID "
            + "WHERE 1 = 1 ";

    private static final String SQL_GET_SALE_REPORT_SELLERBY_LIST_COUNT = "SELECT count(distinct(soi.SELLER_BY)) as \"count\" "
            + "FROM scms_order_goods_detail tb left join scms_order_info soi on soi.ID = tb.ORDER_ID "
            + "WHERE 1 = 1 ";
    
    //业绩归属人汇总
    private static final String SQL_GET_SALE_REPORT_PERFORMANCEBY_LIST = "SELECT distinct(soi.PERFORMANCE_BY), "
            + "sum(tb.GOODS_ORDER_NUM) as \"goodsOrderNum\", "
            + "sum(tb.GOODS_ORDER_NUM * tb.GOODS_PURCHASE_PRICE) as \"goodsPurchasePrice\", "
            + "sum(tb.GOODS_ORDER_NUM * tb.GOODS_ORDER_PROFIT) as \"goodsOrderProfit\", " 
            + "soi.PERFORMANCE_BY as \"performanceBy\", soi.PERFORMANCE_BY_NAME as \"performanceByName\" "
            + "FROM scms_order_goods_detail tb left join scms_order_info soi on soi.ID = tb.ORDER_ID "
            + "WHERE 1 = 1 ";

    private static final String SQL_GET_SALE_REPORT_PERFORMANCEBY_LIST_COUNT = "SELECT count(distinct(soi.PERFORMANCE_BY)) as \"count\" "
            + "FROM scms_order_goods_detail tb left join scms_order_info soi on soi.ID = tb.ORDER_ID "
            + "WHERE 1 = 1 ";
    
    //店铺汇总
    private static final String SQL_GET_SALE_REPORT_SHOP_LIST = "SELECT distinct(soi.SHOP_ID), "
            + "sum(tb.GOODS_ORDER_NUM) as \"goodsOrderNum\", "
            + "sum(tb.GOODS_ORDER_NUM * tb.GOODS_PURCHASE_PRICE) as \"goodsPurchasePrice\", "
            + "sum(tb.GOODS_ORDER_NUM * tb.GOODS_ORDER_PROFIT) as \"goodsOrderProfit\", " 
            + "soi.SHOP_ID as \"shopId\", soi.SHOP_NAME as \"shopName\" "
            + "FROM scms_order_goods_detail tb left join scms_order_info soi on soi.ID = tb.ORDER_ID "
            + "WHERE 1 = 1 ";

    private static final String SQL_GET_SALE_REPORT_SHOP_LIST_COUNT = "SELECT count(distinct(soi.SHOP_ID)) as \"count\" "
            + "FROM scms_order_goods_detail tb left join scms_order_info soi on soi.ID = tb.ORDER_ID "
            + "WHERE 1 = 1 ";

    @Override
    public List<Map<String, Object>> getSaleReportDetailStatistics(Map<String, Object> params) {
        //生成查询条件
        params.put("orderTypeList", "lsd,pfd,ysd");
        SqlParams sqlParams = genSaleReportDetailListWhere(SQL_GET_SALE_REPORT_DETAIL_STATISTICS, params);
        List<Map<String, Object>> listIn = getResultList(sqlParams);
        int singleNum = 0; float totalPurchase = 0f; float totalProfit = 0f;        
        if(listIn.size() > 0) {
            singleNum = MapUtils.getIntValue(listIn.get(0), "singleNum");
            totalPurchase = MapUtils.getFloatValue(listIn.get(0), "totalPurchase");
            totalProfit = MapUtils.getFloatValue(listIn.get(0), "totalProfit");
        }
        //单独统计退货单，退货单需要进行相减
        params.put("orderTypeList", "thd");
        sqlParams = genSaleReportDetailListWhere(SQL_GET_SALE_REPORT_DETAIL_STATISTICS, params);
        List<Map<String, Object>> listOut = getResultList(sqlParams);
        int tmpSingleNum = 0; float tmpTotalPurchase = 0f; float tmpTotalProfit = 0f;    
        if(listOut.size() > 0) {
            tmpSingleNum = MapUtils.getIntValue(listOut.get(0), "singleNum");
            tmpTotalPurchase = MapUtils.getFloatValue(listOut.get(0), "totalPurchase");
            tmpTotalProfit = MapUtils.getFloatValue(listOut.get(0), "totalProfit");
        }
        Map<String, Object> retMap = new HashMap<String, Object>();
        retMap.put("singleNum", singleNum - tmpSingleNum);
        retMap.put("totalSale", (totalPurchase - tmpTotalPurchase) + (totalProfit - tmpTotalProfit));
        retMap.put("totalPurchase", totalPurchase - tmpTotalPurchase);
        retMap.put("totalProfit", totalProfit - tmpTotalProfit);
        List<Map<String, Object>> retList = new ArrayList<Map<String, Object>>();
        retList.add(retMap);
        return retList;
    }
    
    @Override
    public List<Map<String, Object>> getSaleReportDetailList(Map<String, Object> params, int pageIndex, int pageSize) {
        //生成查询条件
        SqlParams sqlParams = genSaleReportDetailListWhere(SQL_GET_SALE_REPORT_DETAIL_LIST, params);
        //添加分页和排序
        sqlParams = getPageableSql(sqlParams, pageIndex, pageSize, " tb.ID DESC ", " \"id\" DESC ");
        return getResultList(sqlParams);
    }

    @Override
    public int getSaleReportDetailListCount(Map<String, Object> params) {
        //生成查询条件
        SqlParams sqlParams = genSaleReportDetailListWhere(SQL_GET_SALE_REPORT_DETAIL_LIST_COUNT, params);
        return getResultListTotalCount(sqlParams);
    }

    @Override
    public List<Map<String, Object>> getSaleReportGoodsList(Map<String, Object> params, int pageIndex, int pageSize) {
        //生成查询条件
        SqlParams sqlParams = genSaleReportDetailListWhere(SQL_GET_SALE_REPORT_GOODS_LIST, params);
        //添加分页和排序
        sqlParams.querySql.insert(0, "SELECT * FROM ( ");
        sqlParams.querySql.append(" GROUP BY sog.GOODS_ID ) AS t ");
        sqlParams = getPageableSql(sqlParams, pageIndex, pageSize);
        return getResultList(sqlParams);
    }

    @Override
    public int getSaleReportGoodsListCount(Map<String, Object> params) {
        //生成查询条件
        SqlParams sqlParams = genSaleReportDetailListWhere(SQL_GET_SALE_REPORT_GOODS_LIST_COUNT, params);
        return getResultListTotalCount(sqlParams);
    }

    @Override
    public List<Map<String, Object>> getSaleReportOrderList(Map<String, Object> params, int pageIndex, int pageSize) {
        //生成查询条件
        SqlParams sqlParams = genSaleReportDetailListWhere(SQL_GET_SALE_REPORT_ORDER_LIST, params);
        //添加分页和排序
        sqlParams.querySql.insert(0, "SELECT * FROM ( ");
        sqlParams.querySql.append(" GROUP BY tb.ORDER_ID ) AS t ");
        sqlParams = getPageableSql(sqlParams, pageIndex, pageSize, " t.orderId DESC ", " \"orderId\" DESC ");
        return getResultList(sqlParams);
    }

    @Override
    public int getSaleReportOrderListCount(Map<String, Object> params) {
        //生成查询条件
        SqlParams sqlParams = genSaleReportDetailListWhere(SQL_GET_SALE_REPORT_ORDER_LIST_COUNT, params);
        return getResultListTotalCount(sqlParams);
    }

    @Override
    public List<Map<String, Object>> getSaleReportCustomerList(Map<String, Object> params, int pageIndex, int pageSize) {
        //生成查询条件
        SqlParams sqlParams = genSaleReportDetailListWhere(SQL_GET_SALE_REPORT_CUSTOMER_LIST, params);
        //添加分页和排序
        sqlParams.querySql.insert(0, "SELECT * FROM ( ");
        sqlParams.querySql.append(" GROUP BY soi.CUSTOMER_ID ) AS t ");
        sqlParams = getPageableSql(sqlParams, pageIndex, pageSize);
        return getResultList(sqlParams);
    }

    @Override
    public int getSaleReportCustomerListCount(Map<String, Object> params) {
        //生成查询条件
        SqlParams sqlParams = genSaleReportDetailListWhere(SQL_GET_SALE_REPORT_CUSTOMER_LIST_COUNT, params);
        return getResultListTotalCount(sqlParams);
    }

    @Override
    public List<Map<String, Object>> getSaleReportCreateByList(Map<String, Object> params, int pageIndex, int pageSize) {
        //生成查询条件
        SqlParams sqlParams = genSaleReportDetailListWhere(SQL_GET_SALE_REPORT_CREATEBY_LIST, params);
        //添加分页和排序
        sqlParams.querySql.insert(0, "SELECT * FROM ( ");
        sqlParams.querySql.append(" GROUP BY soi.CREATE_BY ) AS t ");
        sqlParams = getPageableSql(sqlParams, pageIndex, pageSize);
        return getResultList(sqlParams);
    }

    @Override
    public int getSaleReportCreateByListCount(Map<String, Object> params) {
        //生成查询条件
        SqlParams sqlParams = genSaleReportDetailListWhere(SQL_GET_SALE_REPORT_CREATEBY_LIST_COUNT, params);
        return getResultListTotalCount(sqlParams);
    }

    @Override
    public List<Map<String, Object>> getSaleReportSellerByList(Map<String, Object> params, int pageIndex, int pageSize) {
        //生成查询条件
        SqlParams sqlParams = genSaleReportDetailListWhere(SQL_GET_SALE_REPORT_SELLERBY_LIST, params);
        //添加分页和排序
        sqlParams.querySql.insert(0, "SELECT * FROM ( ");
        sqlParams.querySql.append(" GROUP BY soi.SELLER_BY ) AS t ");
        sqlParams = getPageableSql(sqlParams, pageIndex, pageSize);
        return getResultList(sqlParams);
    }

    @Override
    public int getSaleReportSellerByListCount(Map<String, Object> params) {
        //生成查询条件
        SqlParams sqlParams = genSaleReportDetailListWhere(SQL_GET_SALE_REPORT_SELLERBY_LIST_COUNT, params);
        return getResultListTotalCount(sqlParams);
    }

    @Override
    public List<Map<String, Object>> getSaleReportPerformanceByList(Map<String, Object> params, int pageIndex, int pageSize) {
        //生成查询条件
        SqlParams sqlParams = genSaleReportDetailListWhere(SQL_GET_SALE_REPORT_PERFORMANCEBY_LIST, params);
        //添加分页和排序
        sqlParams.querySql.insert(0, "SELECT * FROM ( ");
        sqlParams.querySql.append(" GROUP BY soi.PERFORMANCE_BY ) AS t ");
        sqlParams = getPageableSql(sqlParams, pageIndex, pageSize);
        return getResultList(sqlParams);
    }

    @Override
    public int getSaleReportPerformanceByListCount(Map<String, Object> params) {
        //生成查询条件
        SqlParams sqlParams = genSaleReportDetailListWhere(SQL_GET_SALE_REPORT_PERFORMANCEBY_LIST_COUNT, params);
        return getResultListTotalCount(sqlParams);
    }

    @Override
    public List<Map<String, Object>> getSaleReportShopList(Map<String, Object> params, int pageIndex, int pageSize) {
        //生成查询条件
        SqlParams sqlParams = genSaleReportDetailListWhere(SQL_GET_SALE_REPORT_SHOP_LIST, params);
        //添加分页和排序
        sqlParams.querySql.insert(0, "SELECT * FROM ( ");
        sqlParams.querySql.append(" GROUP BY soi.SHOP_ID ) AS t ");
        sqlParams = getPageableSql(sqlParams, pageIndex, pageSize);
        return getResultList(sqlParams);
    }

    @Override
    public int getSaleReportShopListCount(Map<String, Object> params) {
        //生成查询条件
        SqlParams sqlParams = genSaleReportDetailListWhere(SQL_GET_SALE_REPORT_SHOP_LIST_COUNT, params);
        return getResultListTotalCount(sqlParams);
    }

    /**
     * 生成查询条件
     * @param sql
     * @param params
     * @return
     */
    private SqlParams genSaleReportDetailListWhere(String sql, Map<String, Object> params){
        SqlParams sqlParams = new SqlParams();
        sqlParams.querySql.append(sql);
        Long merchantsId = MapUtils.getLong(params, "merchantsId", null);
        Long goodsId = MapUtils.getLong(params, "goodsId", null);
        Long categoryId = MapUtils.getLong(params, "categoryId", null);
        String goodsName = MapUtils.getString(params, "goodsName");
        String goodsSerialNum = MapUtils.getString(params, "goodsSerialNum");
        String customerName = MapUtils.getString(params, "customerName");
        Long venderId = MapUtils.getLong(params, "venderId", null);
        String goodsYear = MapUtils.getString(params, "goodsYear");
        String goodsSeason = MapUtils.getString(params, "goodsSeason");
        Long shopId = MapUtils.getLong(params, "shopId", null);
        String orderNum = MapUtils.getString(params, "orderNum");
        String goodsColor = MapUtils.getString(params, "goodsColor");
        String goodsSize = MapUtils.getString(params, "goodsSize");
        String orderTypeList = MapUtils.getString(params, "orderTypeList");
        if (!StringUtils.isBlank(orderTypeList)) {
            List<String> orderList = StringUtils.splitToList(orderTypeList, ",");
            sqlParams.querySql.append(" AND soi.ORDER_TYPE IN (:orderList) ");
            sqlParams.paramsList.add("orderList");
            sqlParams.valueList.add(orderList);
        }
        if (merchantsId != null) {
            sqlParams.querySql.append(" AND soi.MERCHANTS_ID = :merchantsId ");
            sqlParams.paramsList.add("merchantsId");
            sqlParams.valueList.add(merchantsId);
        }
        if (goodsId != null) {
            sqlParams.querySql.append(" AND sgi.ID = :goodsId ");
            sqlParams.paramsList.add("goodsId");
            sqlParams.valueList.add(goodsId);
        }
        if (!StringUtils.isBlank(MapUtils.getString(params, "createDateBegin")) && !StringUtils.isBlank(MapUtils.getString(params, "createDateEnd"))) {
            sqlParams.querySql.append(" AND soi.CREATE_DATE between :createDateBegin AND :createDateEnd ");
            sqlParams.paramsList.add("createDateBegin");
            sqlParams.paramsList.add("createDateEnd");
            sqlParams.valueList.add(MapUtils.getString(params, "createDateBegin") + " 00:00:00");
            sqlParams.valueList.add(MapUtils.getString(params, "createDateEnd") + " 23:59:59");
        }
        if(!StringUtils.isBlank(goodsName)) {
            sqlParams.querySql.append(getLikeSql("sgi.GOODS_NAME", ":goodsName"));
            sqlParams.paramsList.add("goodsName");
            sqlParams.valueList.add(goodsName);
        }
        if(!StringUtils.isBlank(goodsSerialNum)) {
            sqlParams.querySql.append(getLikeSql("sgi.GOODS_SERIAL_NUM", ":goodsSerialNum"));
            sqlParams.paramsList.add("goodsSerialNum");
            sqlParams.valueList.add(goodsSerialNum);
        }
        if(!StringUtils.isBlank(customerName)) {
            sqlParams.querySql.append(getLikeSql("soi.CUSTOMER_NAME", ":customerName"));
            sqlParams.paramsList.add("customerName");
            sqlParams.valueList.add(customerName);
        }
        if (categoryId != null) {
            sqlParams.querySql.append(" AND sgi.CATEGORY_ID in(select distinct(CATEGORY_CHILD_ID) from scms_goods_category_recur where CATEGORY_ID = :categoryId ) ");
            sqlParams.paramsList.add("categoryId");
            sqlParams.valueList.add(categoryId);
        }
        if(venderId != null) {
            sqlParams.querySql.append(" AND sgi.VENDER_ID = :venderId ");
            sqlParams.paramsList.add("venderId");
            sqlParams.valueList.add(venderId);
        }
        if(!StringUtils.isBlank(goodsYear)) {
            sqlParams.querySql.append(" AND sgi.GOODS_YEAR = :goodsYear ");
            sqlParams.paramsList.add("goodsYear");
            sqlParams.valueList.add(goodsYear);
        }
        if(!StringUtils.isBlank(goodsSeason)) {
            sqlParams.querySql.append(" AND sgi.GOODS_SEASON = :goodsSeason ");
            sqlParams.paramsList.add("goodsSeason");
            sqlParams.valueList.add(goodsSeason);
        }
        if(shopId != null) {
            sqlParams.querySql.append(" AND soi.SHOP_ID = :shopId ");
            sqlParams.paramsList.add("shopId");
            sqlParams.valueList.add(shopId);
        }
        if(!StringUtils.isBlank(orderNum)) {
            sqlParams.querySql.append(getLikeSql("soi.ORDER_NUM", ":orderNum"));
            sqlParams.paramsList.add("orderNum");
            sqlParams.valueList.add(orderNum);
        }
        if(!StringUtils.isBlank(goodsColor)) {
            sqlParams.querySql.append(" AND tb.GOODS_COLOR_ID = :goodsColor ");
            sqlParams.paramsList.add("goodsColor");
            sqlParams.valueList.add(goodsColor);
        }
        if(!StringUtils.isBlank(goodsSize)) {
            sqlParams.querySql.append(" AND tb.GOODS_SIZE_ID = :goodsSize ");
            sqlParams.paramsList.add("goodsSize");
            sqlParams.valueList.add(goodsSize);
        }
        String orderType = MapUtils.getString(params, "orderType");
        if (!StringUtils.isBlank(orderType)) {
            sqlParams.querySql.append(" AND soi.ORDER_TYPE = :orderType ");
            sqlParams.paramsList.add("orderType");
            sqlParams.valueList.add(orderType);
        }
        return sqlParams;
    }
}