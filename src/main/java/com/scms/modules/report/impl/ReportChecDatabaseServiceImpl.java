/*
 * Copyright(c) 2018 gimplatform(通用信息管理平台) All rights reserved.
 */
package com.scms.modules.report.impl;

import java.util.List;
import java.util.Map;

import org.apache.commons.collections4.MapUtils;
import org.springframework.stereotype.Service;

import com.gimplatform.core.common.SqlParams;
import com.gimplatform.core.repository.BaseRepository;
import com.gimplatform.core.utils.StringUtils;
import com.scms.modules.report.ReportCheckDatabaseService;

@Service
public class ReportChecDatabaseServiceImpl extends BaseRepository implements ReportCheckDatabaseService{

    //明细
    private static final String SQL_GET_CHECK_REPORT_DETAIL_LIST = "SELECT tb.ID as \"id\", tb.ORDER_NUM as \"orderNum\", tb.SHOP_NAME as \"shopName\", tb.CREATE_DATE as \"createDate\", tb.CREATE_BY_NAME as \"createByName\", 'pdd' as \"orderType\", "
            + "sicgd.GOODS_COLOR_ID as \"goodsColorId\", sicgd.GOODS_COLOR_NAME as \"goodsColorName\", sicgd.GOODS_SIZE_ID as \"goodsSizeId\", sicgd.GOODS_SIZE_NAME as \"goodsSizeName\", "
            + "sicgd.GOODS_TEXTURE_ID as \"goodsTextureId\", sicgd.GOODS_TEXTURE_NAME as \"goodsTextureName\", sicgd.GOODS_BEFORE_NUM as \"goodsBeforeNum\", sicgd.GOODS_AFTER_NUM as \"goodsAfterNum\", "
            + "sicgd.PROFIT_LOSS as \"profitLoss\", sicgd.GOODS_SALE_PRICE as \"goodsSalePrice\", sicgd.GOODS_PURCHASE_PRICE as \"goodsPurchasePrice\", "
            + "sgc.CATEGORY_NAME as \"categoryName\", "
            + "sgi.GOODS_NAME as \"goodsName\", sgi.GOODS_SERIAL_NUM as \"goodsSerialNum\", sgi.GOODS_PHOTO as \"goodsPhoto\", sgi.GOODS_YEAR as \"goodsYear\", sgi.GOODS_SEASON as \"goodsSeason\", "
            + "svi.VENDER_NAME as \"venderName\" "
            + "FROM scms_inventory_check tb left join scms_inventory_check_goods sicg on sicg.ORDER_ID = tb.ID "
            + "left join scms_inventory_check_goods_detail sicgd on sicgd.DETAIL_ID = sicg.ID "
            + "left join scms_goods_info sgi on sgi.ID = sicg.GOODS_ID "
            + "left join scms_goods_category sgc on sgc.ID = sgi.CATEGORY_ID "
            + "left join scms_vender_info svi on svi.ID = sgi.VENDER_ID "
            + "WHERE 1 = 1 ";

    private static final String SQL_GET_CHECK_REPORT_DETAIL_LIST_COUNT = "SELECT count(1) as \"count\" "
            + "FROM scms_inventory_check tb left join scms_inventory_check_goods sicg on sicg.ORDER_ID = tb.ID "
            + "left join scms_inventory_check_goods_detail sicgd on sicgd.DETAIL_ID = sicg.ID "
            + "left join scms_goods_info sgi on sgi.ID = sicg.GOODS_ID "
            + "left join scms_goods_category sgc on sgc.ID = sgi.CATEGORY_ID "
            + "left join scms_vender_info svi on svi.ID = sgi.VENDER_ID "
            + "WHERE 1 = 1 ";

    //统计
    private static final String SQL_GET_CHECK_REPORT_DETAIL_STATISTICS = "SELECT "
            + "count(distinct(sicg.GOODS_ID)) as \"totalGoodsNum\", "
            + "count(distinct(tb.SHOP_ID)) as \"totalShopNum\", "
            + "sum(sicgd.GOODS_BEFORE_NUM) as \"goodsBeforeNum\", "
            + "sum(sicgd.GOODS_AFTER_NUM) as \"goodsAfterNum\", "
            + "sum(sicgd.PROFIT_LOSS) as \"totalProfitMoney\" "
            + "FROM scms_inventory_check tb left join scms_inventory_check_goods sicg on sicg.ORDER_ID = tb.ID "
            + "left join scms_inventory_check_goods_detail sicgd on sicgd.DETAIL_ID = sicg.ID "
            + "left join scms_goods_info sgi on sgi.ID = sicg.GOODS_ID "
            + "left join scms_goods_category sgc on sgc.ID = sgi.CATEGORY_ID "
            + "left join scms_vender_info svi on svi.ID = sgi.VENDER_ID "
            + "WHERE 1 = 1 ";
    
    //商品汇总
    private static final String SQL_GET_CHECK_REPORT_GOODS_LIST = "SELECT distinct(sicg.GOODS_ID), "
            + "sum(sicgd.GOODS_BEFORE_NUM) as \"goodsBeforeNum\", sum(sicgd.GOODS_AFTER_NUM) as \"goodsAfterNum\", sum(sicgd.PROFIT_LOSS) as \"profitLoss\", " 
            + "sgc.CATEGORY_NAME as \"categoryName\", "
            + "sgi.ID as \"goodsId\", sgi.GOODS_NAME as \"goodsName\", sgi.GOODS_SERIAL_NUM as \"goodsSerialNum\", sgi.GOODS_PHOTO as \"goodsPhoto\", sgi.GOODS_YEAR as \"goodsYear\", sgi.GOODS_SEASON as \"goodsSeason\", sgi.SALE_PRICE as \"goodsSalePrice\", sgi.PURCHASE_PRICE as \"goodsPurchasePrice\", "
            + "svi.VENDER_NAME as \"venderName\" "
            + "FROM scms_inventory_check tb left join scms_inventory_check_goods sicg on sicg.ORDER_ID = tb.ID "
            + "left join scms_inventory_check_goods_detail sicgd on sicgd.DETAIL_ID = sicg.ID "
            + "left join scms_goods_info sgi on sgi.ID = sicg.GOODS_ID "
            + "left join scms_goods_category sgc on sgc.ID = sgi.CATEGORY_ID "
            + "left join scms_vender_info svi on svi.ID = sgi.VENDER_ID "
            + "WHERE 1 = 1 ";

    private static final String SQL_GET_CHECK_REPORT_GOODS_LIST_COUNT = "SELECT count(distinct(sicg.GOODS_ID)) as \"count\" "
            + "FROM scms_inventory_check tb left join scms_inventory_check_goods sicg on sicg.ORDER_ID = tb.ID "
            + "left join scms_inventory_check_goods_detail sicgd on sicgd.DETAIL_ID = sicg.ID "
            + "left join scms_goods_info sgi on sgi.ID = sicg.GOODS_ID "
            + "left join scms_goods_category sgc on sgc.ID = sgi.CATEGORY_ID "
            + "left join scms_vender_info svi on svi.ID = sgi.VENDER_ID "
            + "WHERE 1 = 1 ";
    
    //订单汇总
    private static final String SQL_GET_CHECK_REPORT_ORDER_LIST = "SELECT distinct(tb.ID), "
            + "sum(sicgd.GOODS_BEFORE_NUM) as \"goodsBeforeNum\", sum(sicgd.GOODS_AFTER_NUM) as \"goodsAfterNum\", sum(sicgd.PROFIT_LOSS) as \"profitLoss\", " 
            + "tb.ID as \"orderId\", tb.ORDER_NUM as \"orderNum\", tb.SHOP_NAME as \"shopName\", 'pdd' as \"orderType\", tb.CREATE_DATE as \"createDate\", tb.CREATE_BY_NAME as \"createByName\" "
            + "FROM scms_inventory_check tb left join scms_inventory_check_goods sicg on sicg.ORDER_ID = tb.ID "
            + "left join scms_inventory_check_goods_detail sicgd on sicgd.DETAIL_ID = sicg.ID "
            + "WHERE 1 = 1 ";

    private static final String SQL_GET_CHECK_REPORT_ORDER_LIST_COUNT = "SELECT count(distinct(tb.ID)) as \"count\" "
            + "FROM scms_inventory_check tb left join scms_inventory_check_goods sicg on sicg.ORDER_ID = tb.ID "
            + "left join scms_inventory_check_goods_detail sicgd on sicgd.DETAIL_ID = sicg.ID "
            + "WHERE 1 = 1 ";
    
    //店铺汇总
    private static final String SQL_GET_CHECK_REPORT_SHOP_LIST = "SELECT distinct(tb.SHOP_ID), "
            + "sum(sicgd.GOODS_BEFORE_NUM) as \"goodsBeforeNum\", sum(sicgd.GOODS_AFTER_NUM) as \"goodsAfterNum\", sum(sicgd.PROFIT_LOSS) as \"profitLoss\", "
            + "tb.SHOP_ID as \"shopId\", tb.SHOP_NAME as \"shopName\" "
            + "FROM scms_inventory_check tb left join scms_inventory_check_goods sicg on sicg.ORDER_ID = tb.ID "
            + "left join scms_inventory_check_goods_detail sicgd on sicgd.DETAIL_ID = sicg.ID "
            + "WHERE 1 = 1 ";

    private static final String SQL_GET_CHECK_REPORT_SHOP_LIST_COUNT = "SELECT count(distinct(tb.SHOP_ID)) as \"count\" "
            + "FROM scms_inventory_check tb left join scms_inventory_check_goods sicg on sicg.ORDER_ID = tb.ID "
            + "left join scms_inventory_check_goods_detail sicgd on sicgd.DETAIL_ID = sicg.ID "
            + "WHERE 1 = 1 ";

    @Override
    public List<Map<String, Object>> getCheckReportDetailStatistics(Map<String, Object> params) {
        //生成查询条件
        SqlParams sqlParams = genCheckReportDetailListWhere(SQL_GET_CHECK_REPORT_DETAIL_STATISTICS, params);
        return getResultList(sqlParams);
    }
    
    @Override
    public List<Map<String, Object>> getCheckReportDetailList(Map<String, Object> params, int pageIndex, int pageSize) {
        //生成查询条件
        SqlParams sqlParams = genCheckReportDetailListWhere(SQL_GET_CHECK_REPORT_DETAIL_LIST, params);
        //添加分页和排序
        sqlParams = getPageableSql(sqlParams, pageIndex, pageSize, " tb.ID DESC ", " \"id\" DESC ");
        return getResultList(sqlParams);
    }

    @Override
    public int getCheckReportDetailListCount(Map<String, Object> params) {
        //生成查询条件
        SqlParams sqlParams = genCheckReportDetailListWhere(SQL_GET_CHECK_REPORT_DETAIL_LIST_COUNT, params);
        return getResultListTotalCount(sqlParams);
    }

    @Override
    public List<Map<String, Object>> getCheckReportGoodsList(Map<String, Object> params, int pageIndex, int pageSize) {
        //生成查询条件
        SqlParams sqlParams = genCheckReportDetailListWhere(SQL_GET_CHECK_REPORT_GOODS_LIST, params);
        //添加分页和排序
        sqlParams.querySql.insert(0, "SELECT * FROM ( ");
        sqlParams.querySql.append(" GROUP BY sicg.GOODS_ID ) AS t ");
        sqlParams = getPageableSql(sqlParams, pageIndex, pageSize);
        return getResultList(sqlParams);
    }

    @Override
    public int getCheckReportGoodsListCount(Map<String, Object> params) {
        //生成查询条件
        SqlParams sqlParams = genCheckReportDetailListWhere(SQL_GET_CHECK_REPORT_GOODS_LIST_COUNT, params);
        return getResultListTotalCount(sqlParams);
    }

    @Override
    public List<Map<String, Object>> getCheckReportOrderList(Map<String, Object> params, int pageIndex, int pageSize) {
        //生成查询条件
        SqlParams sqlParams = genCheckReportDetailListWhere(SQL_GET_CHECK_REPORT_ORDER_LIST, params);
        //添加分页和排序
        sqlParams.querySql.insert(0, "SELECT * FROM ( ");
        sqlParams.querySql.append(" GROUP BY tb.ID ) AS t ");
        sqlParams = getPageableSql(sqlParams, pageIndex, pageSize, " t.orderId DESC ", " \"orderId\" DESC ");
        return getResultList(sqlParams);
    }

    @Override
    public int getCheckReportOrderListCount(Map<String, Object> params) {
        //生成查询条件
        SqlParams sqlParams = genCheckReportDetailListWhere(SQL_GET_CHECK_REPORT_ORDER_LIST_COUNT, params);
        return getResultListTotalCount(sqlParams);
    }

    @Override
    public List<Map<String, Object>> getCheckReportShopList(Map<String, Object> params, int pageIndex, int pageSize) {
        //生成查询条件
        SqlParams sqlParams = genCheckReportDetailListWhere(SQL_GET_CHECK_REPORT_SHOP_LIST, params);
        //添加分页和排序
        sqlParams.querySql.insert(0, "SELECT * FROM ( ");
        sqlParams.querySql.append(" GROUP BY tb.SHOP_ID ) AS t ");
        sqlParams = getPageableSql(sqlParams, pageIndex, pageSize);
        return getResultList(sqlParams);
    }

    @Override
    public int getCheckReportShopListCount(Map<String, Object> params) {
        //生成查询条件
        SqlParams sqlParams = genCheckReportDetailListWhere(SQL_GET_CHECK_REPORT_SHOP_LIST_COUNT, params);
        return getResultListTotalCount(sqlParams);
    }

    /**
     * 生成查询条件
     * @param sql
     * @param params
     * @return
     */
    private SqlParams genCheckReportDetailListWhere(String sql, Map<String, Object> params){
        SqlParams sqlParams = new SqlParams();
        sqlParams.querySql.append(sql);
        Long merchantsId = MapUtils.getLong(params, "merchantsId", null);
        Long categoryId = MapUtils.getLong(params, "categoryId", null);
        String goodsName = MapUtils.getString(params, "goodsName");
        String goodsSerialNum = MapUtils.getString(params, "goodsSerialNum");
        Long venderId = MapUtils.getLong(params, "venderId", null);
        String goodsYear = MapUtils.getString(params, "goodsYear");
        String goodsSeason = MapUtils.getString(params, "goodsSeason");
        Long shopId = MapUtils.getLong(params, "shopId", null);
        String orderNum = MapUtils.getString(params, "orderNum");
        String goodsColor = MapUtils.getString(params, "goodsColor");
        String goodsTexture = MapUtils.getString(params, "goodsTexture");
        String goodsSize = MapUtils.getString(params, "goodsSize");
        if (merchantsId != null) {
            sqlParams.querySql.append(" AND tb.MERCHANTS_ID = :merchantsId ");
            sqlParams.paramsList.add("merchantsId");
            sqlParams.valueList.add(merchantsId);
        }
        if (!StringUtils.isBlank(MapUtils.getString(params, "createDateBegin")) && !StringUtils.isBlank(MapUtils.getString(params, "createDateEnd"))) {
            sqlParams.querySql.append(" AND tb.CREATE_DATE between :createDateBegin AND :createDateEnd ");
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
            sqlParams.querySql.append(" AND tb.SHOP_ID = :shopId ");
            sqlParams.paramsList.add("shopId");
            sqlParams.valueList.add(shopId);
        }
        if(!StringUtils.isBlank(orderNum)) {
            sqlParams.querySql.append(getLikeSql("tb.ORDER_NUM", ":orderNum"));
            sqlParams.paramsList.add("orderNum");
            sqlParams.valueList.add(orderNum);
        }
        if(!StringUtils.isBlank(goodsColor)) {
            sqlParams.querySql.append(" AND sicgd.GOODS_COLOR_ID = :goodsColor ");
            sqlParams.paramsList.add("goodsColor");
            sqlParams.valueList.add(goodsColor);
        }
        if(!StringUtils.isBlank(goodsTexture)) {
            sqlParams.querySql.append(" AND sicgd.GOODS_TEXTURE_ID = :goodsTexture ");
            sqlParams.paramsList.add("goodsTexture");
            sqlParams.valueList.add(goodsTexture);
        }
        if(!StringUtils.isBlank(goodsSize)) {
            sqlParams.querySql.append(" AND sicgd.GOODS_SIZE_ID = :goodsSize ");
            sqlParams.paramsList.add("goodsSize");
            sqlParams.valueList.add(goodsSize);
        }
        return sqlParams;
    }
}