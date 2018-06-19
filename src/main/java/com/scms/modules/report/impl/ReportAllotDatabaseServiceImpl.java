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
import com.scms.modules.report.ReportAllotDatabaseService;

@Service
public class ReportAllotDatabaseServiceImpl extends BaseRepository implements ReportAllotDatabaseService{

    //明细
    private static final String SQL_GET_ALLOT_REPORT_DETAIL_LIST = "SELECT tb.ID as \"id\", tb.ORDER_NUM as \"orderNum\", tb.CREATE_DATE as \"createDate\", "
            + "tb.CREATE_BY_NAME as \"createByName\", 'dhd' as \"orderType\", tb.SRC_SHOP_ID as \"srcShopId\", tb.SRC_SHOP_NAME as \"srcShopName\", tb.DEST_SHOP_ID as \"destShopId\", "
            + "tb.DEST_SHOP_NAME as \"destShopName\", tb.ORDER_STATUS as \"orderStatus\",  "
            + "sitgd.GOODS_COLOR_ID as \"goodsColorId\", sitgd.GOODS_COLOR_NAME as \"goodsColorName\", sitgd.GOODS_SIZE_ID as \"goodsSizeId\", sitgd.GOODS_SIZE_NAME as \"goodsSizeName\", "
            + "sitgd.GOODS_TEXTURE_ID as \"goodsTextureId\", sitgd.GOODS_TEXTURE_NAME as \"goodsTextureName\", sitgd.GOODS_ORDER_NUM as \"goodsOrderNum\", "
            + "sgc.CATEGORY_NAME as \"categoryName\", "
            + "sgi.GOODS_NAME as \"goodsName\", sgi.GOODS_SERIAL_NUM as \"goodsSerialNum\", sgi.GOODS_PHOTO as \"goodsPhoto\", sgi.GOODS_YEAR as \"goodsYear\", sgi.GOODS_SEASON as \"goodsSeason\", "
            + "svi.VENDER_NAME as \"venderName\" "
            + "FROM scms_inventory_transfer tb left join scms_inventory_transfer_goods sitg on sitg.ORDER_ID = tb.ID "
            + "left join scms_inventory_transfer_goods_detail sitgd on sitgd.DETAIL_ID = sitg.ID "
            + "left join scms_goods_info sgi on sgi.ID = sitg.GOODS_ID "
            + "left join scms_goods_category sgc on sgc.ID = sgi.CATEGORY_ID "
            + "left join scms_vender_info svi on svi.ID = sgi.VENDER_ID "
            + "WHERE 1 = 1 ";

    private static final String SQL_GET_ALLOT_REPORT_DETAIL_LIST_COUNT = "SELECT count(1) as \"count\" "
            + "FROM scms_inventory_transfer tb left join scms_inventory_transfer_goods sitg on sitg.ORDER_ID = tb.ID "
            + "left join scms_inventory_transfer_goods_detail sitgd on sitgd.DETAIL_ID = sitg.ID "
            + "left join scms_goods_info sgi on sgi.ID = sitg.GOODS_ID "
            + "left join scms_goods_category sgc on sgc.ID = sgi.CATEGORY_ID "
            + "left join scms_vender_info svi on svi.ID = sgi.VENDER_ID "
            + "WHERE 1 = 1 ";

    //统计
    private static final String SQL_GET_ALLOT_REPORT_DETAIL_STATISTICS = "SELECT "
            + "count(distinct(sitg.GOODS_ID)) as \"totalGoodsNum\", "
            + "count(distinct(tb.SRC_SHOP_ID)) as \"totalSrcShopNum\", "
            + "count(distinct(tb.DEST_SHOP_ID)) as \"totalDestShopNum\", "
            + "sum(sitgd.GOODS_ORDER_NUM) as \"totalNum\" "
            + "FROM scms_inventory_transfer tb left join scms_inventory_transfer_goods sitg on sitg.ORDER_ID = tb.ID "
            + "left join scms_inventory_transfer_goods_detail sitgd on sitgd.DETAIL_ID = sitg.ID "
            + "left join scms_goods_info sgi on sgi.ID = sitg.GOODS_ID "
            + "left join scms_goods_category sgc on sgc.ID = sgi.CATEGORY_ID "
            + "left join scms_vender_info svi on svi.ID = sgi.VENDER_ID "
            + "WHERE 1 = 1 ";
    
    //商品汇总
    private static final String SQL_GET_ALLOT_REPORT_GOODS_LIST = "SELECT distinct(sitg.GOODS_ID), "
            + "sum(sitgd.GOODS_ORDER_NUM) as \"totalNum\", "
            + "sgc.CATEGORY_NAME as \"categoryName\", "
            + "sgi.ID as \"goodsId\", sgi.GOODS_NAME as \"goodsName\", sgi.GOODS_SERIAL_NUM as \"goodsSerialNum\", sgi.GOODS_PHOTO as \"goodsPhoto\", sgi.GOODS_YEAR as \"goodsYear\", sgi.GOODS_SEASON as \"goodsSeason\", sgi.SALE_PRICE as \"goodsSalePrice\", sgi.PURCHASE_PRICE as \"goodsPurchasePrice\", "
            + "svi.VENDER_NAME as \"venderName\" "
            + "FROM scms_inventory_transfer tb left join scms_inventory_transfer_goods sitg on sitg.ORDER_ID = tb.ID "
            + "left join scms_inventory_transfer_goods_detail sitgd on sitgd.DETAIL_ID = sitg.ID "
            + "left join scms_goods_info sgi on sgi.ID = sitg.GOODS_ID "
            + "left join scms_goods_category sgc on sgc.ID = sgi.CATEGORY_ID "
            + "left join scms_vender_info svi on svi.ID = sgi.VENDER_ID "
            + "WHERE 1 = 1 ";

    private static final String SQL_GET_ALLOT_REPORT_GOODS_LIST_COUNT = "SELECT count(distinct(sitg.GOODS_ID)) as \"count\" "
            + "FROM scms_inventory_transfer tb left join scms_inventory_transfer_goods sitg on sitg.ORDER_ID = tb.ID "
            + "left join scms_inventory_transfer_goods_detail sitgd on sitgd.DETAIL_ID = sitg.ID "
            + "left join scms_goods_info sgi on sgi.ID = sitg.GOODS_ID "
            + "left join scms_goods_category sgc on sgc.ID = sgi.CATEGORY_ID "
            + "left join scms_vender_info svi on svi.ID = sgi.VENDER_ID "
            + "WHERE 1 = 1 ";
    
    //订单汇总
    private static final String SQL_GET_ALLOT_REPORT_ORDER_LIST = "SELECT distinct(tb.ID), "
            + "sum(sitgd.GOODS_ORDER_NUM) as \"totalNum\", "
            + "tb.ID as \"orderId\", tb.ORDER_NUM as \"orderNum\", tb.CREATE_DATE as \"createDate\", tb.CREATE_BY_NAME as \"createByName\", 'dhd' as \"orderType\", tb.SRC_SHOP_ID as \"srcShopId\", "
            + "tb.SRC_SHOP_NAME as \"srcShopName\", tb.DEST_SHOP_ID as \"destShopId\", tb.DEST_SHOP_NAME as \"destShopName\", tb.ORDER_STATUS as \"orderStatus\" "
            + "FROM scms_inventory_transfer tb left join scms_inventory_transfer_goods sitg on sitg.ORDER_ID = tb.ID "
            + "left join scms_inventory_transfer_goods_detail sitgd on sitgd.DETAIL_ID = sitg.ID "
            + "WHERE 1 = 1 ";

    private static final String SQL_GET_ALLOT_REPORT_ORDER_LIST_COUNT = "SELECT count(distinct(tb.ID)) as \"count\" "
            + "FROM scms_inventory_transfer tb left join scms_inventory_transfer_goods sitg on sitg.ORDER_ID = tb.ID "
            + "left join scms_inventory_transfer_goods_detail sitgd on sitgd.DETAIL_ID = sitg.ID "
            + "WHERE 1 = 1 ";
    
    //调出店铺汇总
    private static final String SQL_GET_ALLOT_REPORT_SRC_SHOP_LIST = "SELECT distinct(tb.SRC_SHOP_ID), "
            + "sum(sitgd.GOODS_ORDER_NUM) as \"totalNum\", "
            + "tb.SRC_SHOP_ID as \"srcShopId\", tb.SRC_SHOP_NAME as \"srcShopName\" "
            + "FROM scms_inventory_transfer tb left join scms_inventory_transfer_goods sitg on sitg.ORDER_ID = tb.ID "
            + "left join scms_inventory_transfer_goods_detail sitgd on sitgd.DETAIL_ID = sitg.ID "
            + "WHERE 1 = 1 ";

    private static final String SQL_GET_ALLOT_REPORT_SRC_SHOP_LIST_COUNT = "SELECT count(distinct(tb.SRC_SHOP_ID)) as \"count\" "
            + "FROM scms_inventory_transfer tb left join scms_inventory_transfer_goods sitg on sitg.ORDER_ID = tb.ID "
            + "left join scms_inventory_transfer_goods_detail sitgd on sitgd.DETAIL_ID = sitg.ID "
            + "WHERE 1 = 1 ";
    
    //调入店铺汇总
    private static final String SQL_GET_ALLOT_REPORT_DEST_SHOP_LIST = "SELECT distinct(tb.DEST_SHOP_ID), "
            + "sum(sitgd.GOODS_ORDER_NUM) as \"totalNum\", "
            + "tb.DEST_SHOP_ID as \"destShopId\", tb.DEST_SHOP_NAME as \"destShopName\" "
            + "FROM scms_inventory_transfer tb left join scms_inventory_transfer_goods sitg on sitg.ORDER_ID = tb.ID "
            + "left join scms_inventory_transfer_goods_detail sitgd on sitgd.DETAIL_ID = sitg.ID "
            + "WHERE 1 = 1 ";

    private static final String SQL_GET_ALLOT_REPORT_DEST_SHOP_LIST_COUNT = "SELECT count(distinct(tb.DEST_SHOP_ID)) as \"count\" "
            + "FROM scms_inventory_transfer tb left join scms_inventory_transfer_goods sitg on sitg.ORDER_ID = tb.ID "
            + "left join scms_inventory_transfer_goods_detail sitgd on sitgd.DETAIL_ID = sitg.ID "
            + "WHERE 1 = 1 ";

    @Override
    public List<Map<String, Object>> getAllotReportDetailStatistics(Map<String, Object> params) {
      //生成查询条件
        SqlParams sqlParams = genAllotReportDetailListWhere(SQL_GET_ALLOT_REPORT_DETAIL_STATISTICS, params);
        return getResultList(sqlParams);
    }
    
    @Override
    public List<Map<String, Object>> getAllotReportDetailList(Map<String, Object> params, int pageIndex, int pageSize) {
        //生成查询条件
        SqlParams sqlParams = genAllotReportDetailListWhere(SQL_GET_ALLOT_REPORT_DETAIL_LIST, params);
        //添加分页和排序
        sqlParams = getPageableSql(sqlParams, pageIndex, pageSize, " tb.ID DESC ", " \"id\" DESC ");
        return getResultList(sqlParams);
    }

    @Override
    public int getAllotReportDetailListCount(Map<String, Object> params) {
        //生成查询条件
        SqlParams sqlParams = genAllotReportDetailListWhere(SQL_GET_ALLOT_REPORT_DETAIL_LIST_COUNT, params);
        return getResultListTotalCount(sqlParams);
    }

    @Override
    public List<Map<String, Object>> getAllotReportGoodsList(Map<String, Object> params, int pageIndex, int pageSize) {
        //生成查询条件
        SqlParams sqlParams = genAllotReportDetailListWhere(SQL_GET_ALLOT_REPORT_GOODS_LIST, params);
        //添加分页和排序
        sqlParams.querySql.insert(0, "SELECT * FROM ( ");
        sqlParams.querySql.append(" GROUP BY sitg.GOODS_ID ) AS t ");
        sqlParams = getPageableSql(sqlParams, pageIndex, pageSize);
        return getResultList(sqlParams);
    }

    @Override
    public int getAllotReportGoodsListCount(Map<String, Object> params) {
        //生成查询条件
        SqlParams sqlParams = genAllotReportDetailListWhere(SQL_GET_ALLOT_REPORT_GOODS_LIST_COUNT, params);
        return getResultListTotalCount(sqlParams);
    }

    @Override
    public List<Map<String, Object>> getAllotReportOrderList(Map<String, Object> params, int pageIndex, int pageSize) {
        //生成查询条件
        SqlParams sqlParams = genAllotReportDetailListWhere(SQL_GET_ALLOT_REPORT_ORDER_LIST, params);
        //添加分页和排序
        sqlParams.querySql.insert(0, "SELECT * FROM ( ");
        sqlParams.querySql.append(" GROUP BY tb.ID ) AS t ");
        sqlParams = getPageableSql(sqlParams, pageIndex, pageSize, " t.orderId DESC ", " \"orderId\" DESC ");
        return getResultList(sqlParams);
    }

    @Override
    public int getAllotReportOrderListCount(Map<String, Object> params) {
        //生成查询条件
        SqlParams sqlParams = genAllotReportDetailListWhere(SQL_GET_ALLOT_REPORT_ORDER_LIST_COUNT, params);
        return getResultListTotalCount(sqlParams);
    }

    @Override
    public List<Map<String, Object>> getAllotReportSrcShopList(Map<String, Object> params, int pageIndex, int pageSize) {
        //生成查询条件
        SqlParams sqlParams = genAllotReportDetailListWhere(SQL_GET_ALLOT_REPORT_SRC_SHOP_LIST, params);
        //添加分页和排序
        sqlParams.querySql.insert(0, "SELECT * FROM ( ");
        sqlParams.querySql.append(" GROUP BY tb.SRC_SHOP_ID ) AS t ");
        sqlParams = getPageableSql(sqlParams, pageIndex, pageSize);
        return getResultList(sqlParams);
    }

    @Override
    public int getAllotReportSrcShopListCount(Map<String, Object> params) {
        //生成查询条件
        SqlParams sqlParams = genAllotReportDetailListWhere(SQL_GET_ALLOT_REPORT_SRC_SHOP_LIST_COUNT, params);
        return getResultListTotalCount(sqlParams);
    }

    @Override
    public List<Map<String, Object>> getAllotReportDestShopList(Map<String, Object> params, int pageIndex, int pageSize) {
        //生成查询条件
        SqlParams sqlParams = genAllotReportDetailListWhere(SQL_GET_ALLOT_REPORT_DEST_SHOP_LIST, params);
        //添加分页和排序
        sqlParams.querySql.insert(0, "SELECT * FROM ( ");
        sqlParams.querySql.append(" GROUP BY tb.DEST_SHOP_ID ) AS t ");
        sqlParams = getPageableSql(sqlParams, pageIndex, pageSize);
        return getResultList(sqlParams);
    }

    @Override
    public int getAllotReportDestShopListCount(Map<String, Object> params) {
        //生成查询条件
        SqlParams sqlParams = genAllotReportDetailListWhere(SQL_GET_ALLOT_REPORT_DEST_SHOP_LIST_COUNT, params);
        return getResultListTotalCount(sqlParams);
    }

    /**
     * 生成查询条件
     * @param sql
     * @param params
     * @return
     */
    private SqlParams genAllotReportDetailListWhere(String sql, Map<String, Object> params){
        SqlParams sqlParams = new SqlParams();
        sqlParams.querySql.append(sql);
        Long merchantsId = MapUtils.getLong(params, "merchantsId", null);
        Long categoryId = MapUtils.getLong(params, "categoryId", null);
        String goodsName = MapUtils.getString(params, "goodsName");
        String goodsSerialNum = MapUtils.getString(params, "goodsSerialNum");
        Long venderId = MapUtils.getLong(params, "venderId", null);
        String goodsYear = MapUtils.getString(params, "goodsYear");
        String goodsSeason = MapUtils.getString(params, "goodsSeason");
        Long srcShopId = MapUtils.getLong(params, "srcShopId", null);
        Long destShopId = MapUtils.getLong(params, "destShopId", null);
        String orderNum = MapUtils.getString(params, "orderNum");
        String goodsColor = MapUtils.getString(params, "goodsColor");
        String goodsTexture = MapUtils.getString(params, "goodsTexture");
        String goodsSize = MapUtils.getString(params, "goodsSize");
        String orderStatus = MapUtils.getString(params, "orderStatus");
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
        if(srcShopId != null) {
            sqlParams.querySql.append(" AND tb.SRC_SHOP_ID = :srcShopId ");
            sqlParams.paramsList.add("srcShopId");
            sqlParams.valueList.add(srcShopId);
        }
        if(destShopId != null) {
            sqlParams.querySql.append(" AND tb.DEST_SHOP_ID = :destShopId ");
            sqlParams.paramsList.add("destShopId");
            sqlParams.valueList.add(destShopId);
        }
        if(!StringUtils.isBlank(orderNum)) {
            sqlParams.querySql.append(getLikeSql("tb.ORDER_NUM", ":orderNum"));
            sqlParams.paramsList.add("orderNum");
            sqlParams.valueList.add(orderNum);
        }
        if(!StringUtils.isBlank(goodsColor)) {
            sqlParams.querySql.append(" AND sitgd.GOODS_COLOR_ID = :goodsColor ");
            sqlParams.paramsList.add("goodsColor");
            sqlParams.valueList.add(goodsColor);
        }
        if(!StringUtils.isBlank(goodsTexture)) {
            sqlParams.querySql.append(" AND sitgd.GOODS_TEXTURE_ID = :goodsTexture ");
            sqlParams.paramsList.add("goodsTexture");
            sqlParams.valueList.add(goodsTexture);
        }
        if(!StringUtils.isBlank(goodsSize)) {
            sqlParams.querySql.append(" AND sitgd.GOODS_SIZE_ID = :goodsSize ");
            sqlParams.paramsList.add("goodsSize");
            sqlParams.valueList.add(goodsSize);
        }
        if(!StringUtils.isBlank(orderStatus)) {
            sqlParams.querySql.append(" AND tb.ORDER_STATUS = :orderStatus ");
            sqlParams.paramsList.add("orderStatus");
            sqlParams.valueList.add(orderStatus);
        }
        return sqlParams;
    }
}