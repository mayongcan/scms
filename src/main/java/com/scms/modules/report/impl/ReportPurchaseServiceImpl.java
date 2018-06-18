/*
 * Copyright(c) 2018 gimplatform(通用信息管理平台) All rights reserved.
 */
package com.scms.modules.report.impl;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;
import org.apache.commons.collections4.MapUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;

import com.alibaba.fastjson.JSONObject;
import com.gimplatform.core.utils.RestfulRetUtils;
import com.gimplatform.core.utils.StringUtils;
import com.scms.modules.report.ReportPurchaseDatabaseService;
import com.scms.modules.report.ReportPurchaseService;

@Service
public class ReportPurchaseServiceImpl implements ReportPurchaseService {
    
    @Autowired
    private ReportPurchaseDatabaseService reportPurchaseDatabaseService;

    @Override
    public JSONObject getPurchaseReportDetailStatistics(Map<String, Object> params) {
        List<Map<String, Object>> list = reportPurchaseDatabaseService.getPurchaseReportDetailStatistics(params);
        return RestfulRetUtils.getRetSuccessWithPage(list, list.size());  
    }

    @Override
    public JSONObject getPurchaseReportDetailList(Pageable page, Map<String, Object> params) {
        List<Map<String, Object>> list = reportPurchaseDatabaseService.getPurchaseReportDetailList(params, page.getPageNumber(), page.getPageSize());
        int count = reportPurchaseDatabaseService.getPurchaseReportDetailListCount(params);
        return RestfulRetUtils.getRetSuccessWithPage(list, count);  
    }

    @Override
    public JSONObject getPurchaseReportGoodsList(Pageable page, Map<String, Object> params) {
        int count = reportPurchaseDatabaseService.getPurchaseReportGoodsListCount(params);
        params.put("orderTypeList", "jhd");
        List<Map<String, Object>> listIn = reportPurchaseDatabaseService.getPurchaseReportGoodsList(params, page.getPageNumber(), page.getPageSize());
        params.put("orderTypeList", "fcd");
        List<Map<String, Object>> listOut = reportPurchaseDatabaseService.getPurchaseReportGoodsList(params, page.getPageNumber(), page.getPageSize());
        Iterator<Map<String, Object>> sListIterator = listOut.iterator();  
        while(sListIterator.hasNext()){  
            Map<String, Object> mapOut = sListIterator.next(); 
            String goodsId = MapUtils.getString(mapOut, "goodsId");
            if(!StringUtils.isEmpty(goodsId)) {
                boolean isExist = false;
                for(Map<String, Object> mapIn : listIn) {
                    if(goodsId.equals(MapUtils.getString(mapIn, "goodsId"))) {
                        isExist = true;
                        break;
                    }
                }
                //如果商品不存在退货单，则添加入列表
                if(!isExist) {
                    mapOut.put("goodsOrderNum", "-" + MapUtils.getString(mapOut, "goodsOrderNum"));
                    mapOut.put("totalOrderPrice", "-" + MapUtils.getString(mapOut, "totalOrderPrice"));
                    mapOut.put("totalPrice", "-" + MapUtils.getString(mapOut, "totalPrice"));
                    listIn.add(mapOut);
                    sListIterator.remove();  
                }
            }
        }  
        //两个列表相减
        for(Map<String, Object> mapIn : listIn) {
            String goodsId = MapUtils.getString(mapIn, "goodsId");
            if(!StringUtils.isEmpty(goodsId)) {
                for(Map<String, Object> mapOut : listOut) {
                    if(goodsId.equals(MapUtils.getString(mapOut, "goodsId"))) {
                        mapIn.put("goodsOrderNum", MapUtils.getIntValue(mapIn, "goodsOrderNum", 0) - MapUtils.getIntValue(mapOut, "goodsOrderNum", 0));
                        mapIn.put("totalOrderPrice", MapUtils.getFloatValue(mapIn, "totalOrderPrice", 0) - MapUtils.getFloatValue(mapOut, "totalOrderPrice", 0));
                        mapIn.put("totalPrice", MapUtils.getFloatValue(mapIn, "totalPrice", 0) - MapUtils.getFloatValue(mapOut, "totalPrice", 0));
                        break;
                    }
                }
            }
        }
        return RestfulRetUtils.getRetSuccessWithPage(listIn, count);  
    }

    @Override
    public JSONObject getPurchaseReportOrderList(Pageable page, Map<String, Object> params) {
        List<Map<String, Object>> list = reportPurchaseDatabaseService.getPurchaseReportOrderList(params, page.getPageNumber(), page.getPageSize());
        int count = reportPurchaseDatabaseService.getPurchaseReportOrderListCount(params);
        return RestfulRetUtils.getRetSuccessWithPage(list, count);  
    }

    @Override
    public JSONObject getPurchaseReportSupplierList(Pageable page, Map<String, Object> params) {
        int count = reportPurchaseDatabaseService.getPurchaseReportSupplierListCount(params);
        params.put("orderTypeList", "jhd");
        List<Map<String, Object>> listIn = reportPurchaseDatabaseService.getPurchaseReportSupplierList(params, page.getPageNumber(), page.getPageSize());
        params.put("orderTypeList", "fcd");
        List<Map<String, Object>> listOut = reportPurchaseDatabaseService.getPurchaseReportSupplierList(params, page.getPageNumber(), page.getPageSize());
        Iterator<Map<String, Object>> sListIterator = listOut.iterator();  
        while(sListIterator.hasNext()){  
            Map<String, Object> mapOut = sListIterator.next(); 
            String supplierId = MapUtils.getString(mapOut, "supplierId");
            if(!StringUtils.isEmpty(supplierId)) {
                boolean isExist = false;
                for(Map<String, Object> mapIn : listIn) {
                    if(supplierId.equals(MapUtils.getString(mapIn, "supplierId"))) {
                        isExist = true;
                        break;
                    }
                }
                //如果商品不存在退货单，则添加入列表
                if(!isExist) {
                    mapOut.put("goodsOrderNum", "-" + MapUtils.getString(mapOut, "goodsOrderNum"));
                    mapOut.put("totalOrderPrice", "-" + MapUtils.getString(mapOut, "totalOrderPrice"));
                    mapOut.put("totalPrice", "-" + MapUtils.getString(mapOut, "totalPrice"));
                    listIn.add(mapOut);
                    sListIterator.remove();  
                }
            }
        }  
        //两个列表相减
        for(Map<String, Object> mapIn : listIn) {
            String supplierId = MapUtils.getString(mapIn, "supplierId");
            if(!StringUtils.isEmpty(supplierId)) {
                for(Map<String, Object> mapOut : listOut) {
                    if(supplierId.equals(MapUtils.getString(mapOut, "supplierId"))) {
                        mapIn.put("goodsOrderNum", MapUtils.getIntValue(mapIn, "goodsOrderNum", 0) - MapUtils.getIntValue(mapOut, "goodsOrderNum", 0));
                        mapIn.put("totalOrderPrice", MapUtils.getFloatValue(mapIn, "totalOrderPrice", 0) - MapUtils.getFloatValue(mapOut, "totalOrderPrice", 0));
                        mapIn.put("totalPrice", MapUtils.getFloatValue(mapIn, "totalPrice", 0) - MapUtils.getFloatValue(mapOut, "totalPrice", 0));
                        break;
                    }
                }
            }
        }
        return RestfulRetUtils.getRetSuccessWithPage(listIn, count);
    }

    @Override
    public JSONObject getPurchaseReportCreateByList(Pageable page, Map<String, Object> params) {
        int count = reportPurchaseDatabaseService.getPurchaseReportCreateByListCount(params);
        params.put("orderTypeList", "jhd");
        List<Map<String, Object>> listIn = reportPurchaseDatabaseService.getPurchaseReportCreateByList(params, page.getPageNumber(), page.getPageSize());
        params.put("orderTypeList", "fcd");
        List<Map<String, Object>> listOut = reportPurchaseDatabaseService.getPurchaseReportCreateByList(params, page.getPageNumber(), page.getPageSize());
        Iterator<Map<String, Object>> sListIterator = listOut.iterator();  
        while(sListIterator.hasNext()){  
            Map<String, Object> mapOut = sListIterator.next(); 
            String createBy = MapUtils.getString(mapOut, "createBy");
            if(!StringUtils.isEmpty(createBy)) {
                boolean isExist = false;
                for(Map<String, Object> mapIn : listIn) {
                    if(createBy.equals(MapUtils.getString(mapIn, "createBy"))) {
                        isExist = true;
                        break;
                    }
                }
                //如果商品不存在退货单，则添加入列表
                if(!isExist) {
                    mapOut.put("goodsOrderNum", "-" + MapUtils.getString(mapOut, "goodsOrderNum"));
                    mapOut.put("totalOrderPrice", "-" + MapUtils.getString(mapOut, "totalOrderPrice"));
                    mapOut.put("totalPrice", "-" + MapUtils.getString(mapOut, "totalPrice"));
                    listIn.add(mapOut);
                    sListIterator.remove();  
                }
            }
        }  
        //两个列表相减
        for(Map<String, Object> mapIn : listIn) {
            String createBy = MapUtils.getString(mapIn, "createBy");
            if(!StringUtils.isEmpty(createBy)) {
                for(Map<String, Object> mapOut : listOut) {
                    if(createBy.equals(MapUtils.getString(mapOut, "createBy"))) {
                        mapIn.put("goodsOrderNum", MapUtils.getIntValue(mapIn, "goodsOrderNum", 0) - MapUtils.getIntValue(mapOut, "goodsOrderNum", 0));
                        mapIn.put("totalOrderPrice", MapUtils.getFloatValue(mapIn, "totalOrderPrice", 0) - MapUtils.getFloatValue(mapOut, "totalOrderPrice", 0));
                        mapIn.put("totalPrice", MapUtils.getFloatValue(mapIn, "totalPrice", 0) - MapUtils.getFloatValue(mapOut, "totalPrice", 0));
                        break;
                    }
                }
            }
        }
        return RestfulRetUtils.getRetSuccessWithPage(listIn, count);
    }

    @Override
    public JSONObject getPurchaseReportShopList(Pageable page, Map<String, Object> params) {
        int count = reportPurchaseDatabaseService.getPurchaseReportShopListCount(params);
        params.put("orderTypeList", "jhd");
        List<Map<String, Object>> listIn = reportPurchaseDatabaseService.getPurchaseReportShopList(params, page.getPageNumber(), page.getPageSize());
        params.put("orderTypeList", "fcd");
        List<Map<String, Object>> listOut = reportPurchaseDatabaseService.getPurchaseReportShopList(params, page.getPageNumber(), page.getPageSize());
        Iterator<Map<String, Object>> sListIterator = listOut.iterator();  
        while(sListIterator.hasNext()){  
            Map<String, Object> mapOut = sListIterator.next(); 
            String shopId = MapUtils.getString(mapOut, "shopId");
            if(!StringUtils.isEmpty(shopId)) {
                boolean isExist = false;
                for(Map<String, Object> mapIn : listIn) {
                    if(shopId.equals(MapUtils.getString(mapIn, "shopId"))) {
                        isExist = true;
                        break;
                    }
                }
                //如果商品不存在退货单，则添加入列表
                if(!isExist) {
                    mapOut.put("goodsOrderNum", "-" + MapUtils.getString(mapOut, "goodsOrderNum"));
                    mapOut.put("totalOrderPrice", "-" + MapUtils.getString(mapOut, "totalOrderPrice"));
                    mapOut.put("totalPrice", "-" + MapUtils.getString(mapOut, "totalPrice"));
                    listIn.add(mapOut);
                    sListIterator.remove();  
                }
            }
        }  
        //两个列表相减
        for(Map<String, Object> mapIn : listIn) {
            String shopId = MapUtils.getString(mapIn, "shopId");
            if(!StringUtils.isEmpty(shopId)) {
                for(Map<String, Object> mapOut : listOut) {
                    if(shopId.equals(MapUtils.getString(mapOut, "shopId"))) {
                        mapIn.put("goodsOrderNum", MapUtils.getIntValue(mapIn, "goodsOrderNum", 0) - MapUtils.getIntValue(mapOut, "goodsOrderNum", 0));
                        mapIn.put("totalOrderPrice", MapUtils.getFloatValue(mapIn, "totalOrderPrice", 0) - MapUtils.getFloatValue(mapOut, "totalOrderPrice", 0));
                        mapIn.put("totalPrice", MapUtils.getFloatValue(mapIn, "totalPrice", 0) - MapUtils.getFloatValue(mapOut, "totalPrice", 0));
                        break;
                    }
                }
            }
        }
        return RestfulRetUtils.getRetSuccessWithPage(listIn, count);
    }
}
