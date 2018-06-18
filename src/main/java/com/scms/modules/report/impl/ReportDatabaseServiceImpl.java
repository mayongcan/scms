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
import com.scms.modules.report.ReportDatabaseService;

@Service
public class ReportDatabaseServiceImpl extends BaseRepository implements ReportDatabaseService{

    //销售明细
    private static final String SQL_GET_SALE_PURCHASE_COMPARE_LIST_SELECT = "SELECT distinct(sog.GOODS_ID), "     
            + "sgc.CATEGORY_NAME as \"categoryName\", "
            + "sgi.ID as \"goodsId\", sgi.GOODS_NAME as \"goodsName\", sgi.GOODS_SERIAL_NUM as \"goodsSerialNum\", sgi.GOODS_YEAR as \"goodsYear\", sgi.GOODS_SEASON as \"goodsSeason\", "
            + "svi.VENDER_NAME as \"venderName\", "
            + "(select sum(tmpsgi.INVENTORY_NUM) from scms_goods_inventory tmpsgi where tmpsgi.GOODS_ID = sog.GOODS_ID ) as \"goodsInventoryNum\", ";
    private static final String SQL_GET_SALE_PURCHASE_COMPARE_LIST_FROM = "FROM scms_order_goods_detail tb left join scms_order_info soi on soi.ID = tb.ORDER_ID "
            + "left join scms_order_goods sog on sog.ID = tb.DETAIL_ID "
            + "left join scms_goods_info sgi on sgi.ID = sog.GOODS_ID "
            + "left join scms_goods_category sgc on sgc.ID = sgi.CATEGORY_ID "
            + "left join scms_vender_info svi on svi.ID = sgi.VENDER_ID "
            + "WHERE 1 = 1 ";

    private static final String SQL_GET_SALE_PURCHASE_COMPARE_SUM = "SELECT "
            + "sum(tb1.GOODS_ORDER_NUM) as \"num\" "
            + "FROM scms_order_goods_detail tb1 left join scms_order_info soi1 on soi1.ID = tb1.ORDER_ID "
            + "left join scms_order_goods sog1 on sog1.ID = tb1.DETAIL_ID "
            + "left join scms_goods_info sgi1 on sgi1.ID = sog1.GOODS_ID "
            + "left join scms_goods_category sgc1 on sgc1.ID = sgi1.CATEGORY_ID "
            + "left join scms_vender_info svi1 on svi1.ID = sgi1.VENDER_ID "
            + "WHERE sog1.GOODS_ID = sog.GOODS_ID ";

    private static final String SQL_GET_SALE_PURCHASE_COMPARE_LIST_COUNT = "SELECT count(distinct(sog.GOODS_ID)) as \"count\" "
            + "FROM scms_order_goods_detail tb left join scms_order_info soi on soi.ID = tb.ORDER_ID "
            + "left join scms_order_goods sog on sog.ID = tb.DETAIL_ID "
            + "left join scms_goods_info sgi on sgi.ID = sog.GOODS_ID "
            + "left join scms_goods_category sgc on sgc.ID = sgi.CATEGORY_ID "
            + "WHERE 1 = 1 ";

    //统计
    private static final String SQL_GET_SALE_PURCHASE_COMPARE_STATISTICS = "SELECT "
            + "sum(tb.GOODS_ORDER_NUM) as \"num\" "
            + "FROM scms_order_goods_detail tb left join scms_order_info soi on soi.ID = tb.ORDER_ID "
            + "left join scms_order_goods sog on sog.ID = tb.DETAIL_ID "
            + "left join scms_goods_info sgi on sgi.ID = sog.GOODS_ID "
            + "left join scms_goods_category sgc on sgc.ID = sgi.CATEGORY_ID "
            + "left join scms_vender_info svi on svi.ID = sgi.VENDER_ID "
            + "WHERE 1 = 1 ";
    

    @Override
    public List<Map<String, Object>> getSalePurchaseCompareStatistics(Map<String, Object> params) {
        SqlParams sqlParams = genSalePurchaseCompareListWhere(null, SQL_GET_SALE_PURCHASE_COMPARE_STATISTICS, params);
        return getResultList(sqlParams);
    }
    
    @Override
    public List<Map<String, Object>> getSalePurchaseCompareList(Map<String, Object> params, int pageIndex, int pageSize) {
        //生成查询条件
        SqlParams sqlParams = new SqlParams();
        sqlParams.querySql.append(SQL_GET_SALE_PURCHASE_COMPARE_LIST_SELECT);
        //统计进货量
        sqlParams.querySql.append(" ( ");
        sqlParams = genSalePurchaseCompareSumListWhere(sqlParams, SQL_GET_SALE_PURCHASE_COMPARE_SUM, params, "'jhd'");
        sqlParams.querySql.append(" ) as \"totalPurchase\", ");
        //统计返厂量
        sqlParams.querySql.append(" ( ");
        sqlParams = genSalePurchaseCompareSumListWhere(sqlParams, SQL_GET_SALE_PURCHASE_COMPARE_SUM, params, "'fcd'");
        sqlParams.querySql.append(" ) as \"totalReturn\", ");
        //统计销售量
        sqlParams.querySql.append(" ( ");
        sqlParams = genSalePurchaseCompareSumListWhere(sqlParams, SQL_GET_SALE_PURCHASE_COMPARE_SUM, params, "'lsd','pfd','ysd'");
        sqlParams.querySql.append(" ) as \"totalSale\", ");
        //统计退货量
        sqlParams.querySql.append(" ( ");
        sqlParams = genSalePurchaseCompareSumListWhere(sqlParams, SQL_GET_SALE_PURCHASE_COMPARE_SUM, params, "'thd'");
        sqlParams.querySql.append(" ) as \"totalSaleReturn\" ");

        //整合外围查询
        params.put("orderTypeList","lsd,pfd,ysd,thd,jhd,fcd");
        sqlParams = genSalePurchaseCompareListWhere(sqlParams, SQL_GET_SALE_PURCHASE_COMPARE_LIST_FROM, params);
        //添加分页和排序
        sqlParams = getPageableSql(sqlParams, pageIndex, pageSize, " tb.ID DESC ", " \"id\" DESC ");
        return getResultList(sqlParams);
    }

    @Override
    public int getSalePurchaseCompareListCount(Map<String, Object> params) {
        //生成查询条件
        SqlParams sqlParams = genSalePurchaseCompareListWhere(null, SQL_GET_SALE_PURCHASE_COMPARE_LIST_COUNT, params);
        return getResultListTotalCount(sqlParams);
    }

    /**
     * 生成查询条件
     * @param sql
     * @param params
     * @return
     */
    private SqlParams genSalePurchaseCompareListWhere(SqlParams sqlParams, String sql, Map<String, Object> params){
        if(sqlParams == null) sqlParams = new SqlParams();
        sqlParams.querySql.append(sql);
        Long merchantsId = MapUtils.getLong(params, "merchantsId", null);
        Long categoryId = MapUtils.getLong(params, "categoryId", null);
        String goodsName = MapUtils.getString(params, "goodsName");
        String goodsSerialNum = MapUtils.getString(params, "goodsSerialNum");
        Long venderId = MapUtils.getLong(params, "venderId", null);
        String goodsYear = MapUtils.getString(params, "goodsYear");
        String goodsSeason = MapUtils.getString(params, "goodsSeason");
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
        String orderType = MapUtils.getString(params, "orderType");
        if (!StringUtils.isBlank(orderType)) {
            sqlParams.querySql.append(" AND soi.ORDER_TYPE = :orderType ");
            sqlParams.paramsList.add("orderType");
            sqlParams.valueList.add(orderType);
        }
        return sqlParams;
    }
    
    /**
     * 子项统计数据
     * @param sqlParams
     * @param sql
     * @param params
     * @return
     */
    private SqlParams genSalePurchaseCompareSumListWhere(SqlParams sqlParams, String sql, Map<String, Object> params, String orderTypeList){
        if(sqlParams == null) sqlParams = new SqlParams();
        sqlParams.querySql.append(sql);
        Long merchantsId = MapUtils.getLong(params, "merchantsId", null);
        Long categoryId = MapUtils.getLong(params, "categoryId", null);
        String goodsName = MapUtils.getString(params, "goodsName");
        String goodsSerialNum = MapUtils.getString(params, "goodsSerialNum");
        Long venderId = MapUtils.getLong(params, "venderId", null);
        String goodsYear = MapUtils.getString(params, "goodsYear");
        String goodsSeason = MapUtils.getString(params, "goodsSeason");
        sqlParams.querySql.append(" AND soi1.ORDER_TYPE IN ( " + orderTypeList + " ) ");
        if (merchantsId != null) {
            sqlParams.querySql.append(" AND soi1.MERCHANTS_ID = :merchantsId ");
            sqlParams.paramsList.add("merchantsId");
            sqlParams.valueList.add(merchantsId);
        }
        if (!StringUtils.isBlank(MapUtils.getString(params, "createDateBegin")) && !StringUtils.isBlank(MapUtils.getString(params, "createDateEnd"))) {
            sqlParams.querySql.append(" AND soi1.CREATE_DATE between :createDateBegin AND :createDateEnd ");
            sqlParams.paramsList.add("createDateBegin");
            sqlParams.paramsList.add("createDateEnd");
            sqlParams.valueList.add(MapUtils.getString(params, "createDateBegin") + " 00:00:00");
            sqlParams.valueList.add(MapUtils.getString(params, "createDateEnd") + " 23:59:59");
        }
        if(!StringUtils.isBlank(goodsName)) {
            sqlParams.querySql.append(getLikeSql("sgi1.GOODS_NAME", ":goodsName"));
            sqlParams.paramsList.add("goodsName");
            sqlParams.valueList.add(goodsName);
        }
        if(!StringUtils.isBlank(goodsSerialNum)) {
            sqlParams.querySql.append(getLikeSql("sgi1.GOODS_SERIAL_NUM", ":goodsSerialNum"));
            sqlParams.paramsList.add("goodsSerialNum");
            sqlParams.valueList.add(goodsSerialNum);
        }
        if (categoryId != null) {
            sqlParams.querySql.append(" AND sgi1.CATEGORY_ID in(select distinct(CATEGORY_CHILD_ID) from scms_goods_category_recur where CATEGORY_ID = :categoryId ) ");
            sqlParams.paramsList.add("categoryId");
            sqlParams.valueList.add(categoryId);
        }
        if(venderId != null) {
            sqlParams.querySql.append(" AND sgi1.VENDER_ID = :venderId ");
            sqlParams.paramsList.add("venderId");
            sqlParams.valueList.add(venderId);
        }
        if(!StringUtils.isBlank(goodsYear)) {
            sqlParams.querySql.append(" AND sgi1.GOODS_YEAR = :goodsYear ");
            sqlParams.paramsList.add("goodsYear");
            sqlParams.valueList.add(goodsYear);
        }
        if(!StringUtils.isBlank(goodsSeason)) {
            sqlParams.querySql.append(" AND sgi1.GOODS_SEASON = :goodsSeason ");
            sqlParams.paramsList.add("goodsSeason");
            sqlParams.valueList.add(goodsSeason);
        }
        String orderType = MapUtils.getString(params, "orderType");
        if (!StringUtils.isBlank(orderType)) {
            sqlParams.querySql.append(" AND soi1.ORDER_TYPE = :orderType ");
            sqlParams.paramsList.add("orderType");
            sqlParams.valueList.add(orderType);
        }
        return sqlParams;
    }
}