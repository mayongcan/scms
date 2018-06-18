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
import com.scms.modules.report.ReportSaleDatabaseService;
import com.scms.modules.report.ReportSaleService;

@Service
public class ReportSaleServiceImpl implements ReportSaleService {
    
    @Autowired
    private ReportSaleDatabaseService reportSaleDatabaseService;

    @Override
    public JSONObject getSaleReportDetailStatistics(Map<String, Object> params) {
        List<Map<String, Object>> list = reportSaleDatabaseService.getSaleReportDetailStatistics(params);
        return RestfulRetUtils.getRetSuccessWithPage(list, list.size());  
    }

    @Override
    public JSONObject getSaleReportDetailList(Pageable page, Map<String, Object> params) {
        List<Map<String, Object>> list = reportSaleDatabaseService.getSaleReportDetailList(params, page.getPageNumber(), page.getPageSize());
        int count = reportSaleDatabaseService.getSaleReportDetailListCount(params);
        return RestfulRetUtils.getRetSuccessWithPage(list, count);  
    }

    @Override
    public JSONObject getSaleReportGoodsList(Pageable page, Map<String, Object> params) {
        int count = reportSaleDatabaseService.getSaleReportGoodsListCount(params);
        params.put("orderTypeList", "lsd,pfd,ysd");
        List<Map<String, Object>> listIn = reportSaleDatabaseService.getSaleReportGoodsList(params, page.getPageNumber(), page.getPageSize());
        params.put("orderTypeList", "thd");
        List<Map<String, Object>> listOut = reportSaleDatabaseService.getSaleReportGoodsList(params, page.getPageNumber(), page.getPageSize());
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
                    mapOut.put("goodsPurchasePrice", "-" + MapUtils.getString(mapOut, "goodsPurchasePrice"));
                    mapOut.put("goodsOrderProfit", "-" + MapUtils.getString(mapOut, "goodsOrderProfit"));
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
                        mapIn.put("goodsPurchasePrice", MapUtils.getFloatValue(mapIn, "goodsPurchasePrice", 0) - MapUtils.getFloatValue(mapOut, "goodsPurchasePrice", 0));
                        mapIn.put("goodsOrderProfit", MapUtils.getFloatValue(mapIn, "goodsOrderProfit", 0) - MapUtils.getFloatValue(mapOut, "goodsOrderProfit", 0));
                        break;
                    }
                }
            }
        }
        return RestfulRetUtils.getRetSuccessWithPage(listIn, count);  
    }

    @Override
    public JSONObject getSaleReportOrderList(Pageable page, Map<String, Object> params) {
        List<Map<String, Object>> list = reportSaleDatabaseService.getSaleReportOrderList(params, page.getPageNumber(), page.getPageSize());
        int count = reportSaleDatabaseService.getSaleReportOrderListCount(params);
        return RestfulRetUtils.getRetSuccessWithPage(list, count);  
    }

    @Override
    public JSONObject getSaleReportCustomerList(Pageable page, Map<String, Object> params) {
        int count = reportSaleDatabaseService.getSaleReportCustomerListCount(params);
        params.put("orderTypeList", "lsd,pfd,ysd");
        List<Map<String, Object>> listIn = reportSaleDatabaseService.getSaleReportCustomerList(params, page.getPageNumber(), page.getPageSize());
        params.put("orderTypeList", "thd");
        List<Map<String, Object>> listOut = reportSaleDatabaseService.getSaleReportCustomerList(params, page.getPageNumber(), page.getPageSize());
        Iterator<Map<String, Object>> sListIterator = listOut.iterator();  
        while(sListIterator.hasNext()){  
            Map<String, Object> mapOut = sListIterator.next(); 
            String customerId = MapUtils.getString(mapOut, "customerId");
            if(!StringUtils.isEmpty(customerId)) {
                boolean isExist = false;
                for(Map<String, Object> mapIn : listIn) {
                    if(customerId.equals(MapUtils.getString(mapIn, "customerId"))) {
                        isExist = true;
                        break;
                    }
                }
                //如果商品不存在退货单，则添加入列表
                if(!isExist) {
                    mapOut.put("goodsOrderNum", "-" + MapUtils.getString(mapOut, "goodsOrderNum"));
                    mapOut.put("goodsPurchasePrice", "-" + MapUtils.getString(mapOut, "goodsPurchasePrice"));
                    mapOut.put("goodsOrderProfit", "-" + MapUtils.getString(mapOut, "goodsOrderProfit"));
                    listIn.add(mapOut);
                    sListIterator.remove();  
                }
            }
        }  
        //两个列表相减
        for(Map<String, Object> mapIn : listIn) {
            String customerId = MapUtils.getString(mapIn, "customerId");
            if(!StringUtils.isEmpty(customerId)) {
                for(Map<String, Object> mapOut : listOut) {
                    if(customerId.equals(MapUtils.getString(mapOut, "customerId"))) {
                        mapIn.put("goodsOrderNum", MapUtils.getIntValue(mapIn, "goodsOrderNum", 0) - MapUtils.getIntValue(mapOut, "goodsOrderNum", 0));
                        mapIn.put("goodsPurchasePrice", MapUtils.getFloatValue(mapIn, "goodsPurchasePrice", 0) - MapUtils.getFloatValue(mapOut, "goodsPurchasePrice", 0));
                        mapIn.put("goodsOrderProfit", MapUtils.getFloatValue(mapIn, "goodsOrderProfit", 0) - MapUtils.getFloatValue(mapOut, "goodsOrderProfit", 0));
                        break;
                    }
                }
            }
        }
        return RestfulRetUtils.getRetSuccessWithPage(listIn, count);
    }

    @Override
    public JSONObject getSaleReportCreateByList(Pageable page, Map<String, Object> params) {
        int count = reportSaleDatabaseService.getSaleReportCreateByListCount(params);
        params.put("orderTypeList", "lsd,pfd,ysd");
        List<Map<String, Object>> listIn = reportSaleDatabaseService.getSaleReportCreateByList(params, page.getPageNumber(), page.getPageSize());
        params.put("orderTypeList", "thd");
        List<Map<String, Object>> listOut = reportSaleDatabaseService.getSaleReportCreateByList(params, page.getPageNumber(), page.getPageSize());
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
                    mapOut.put("goodsPurchasePrice", "-" + MapUtils.getString(mapOut, "goodsPurchasePrice"));
                    mapOut.put("goodsOrderProfit", "-" + MapUtils.getString(mapOut, "goodsOrderProfit"));
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
                        mapIn.put("goodsPurchasePrice", MapUtils.getFloatValue(mapIn, "goodsPurchasePrice", 0) - MapUtils.getFloatValue(mapOut, "goodsPurchasePrice", 0));
                        mapIn.put("goodsOrderProfit", MapUtils.getFloatValue(mapIn, "goodsOrderProfit", 0) - MapUtils.getFloatValue(mapOut, "goodsOrderProfit", 0));
                        break;
                    }
                }
            }
        }
        return RestfulRetUtils.getRetSuccessWithPage(listIn, count);
    }

    @Override
    public JSONObject getSaleReportSellerByList(Pageable page, Map<String, Object> params) {
        int count = reportSaleDatabaseService.getSaleReportSellerByListCount(params);
        params.put("orderTypeList", "lsd,pfd,ysd");
        List<Map<String, Object>> listIn = reportSaleDatabaseService.getSaleReportSellerByList(params, page.getPageNumber(), page.getPageSize());
        params.put("orderTypeList", "thd");
        List<Map<String, Object>> listOut = reportSaleDatabaseService.getSaleReportSellerByList(params, page.getPageNumber(), page.getPageSize());
        Iterator<Map<String, Object>> sListIterator = listOut.iterator();  
        while(sListIterator.hasNext()){  
            Map<String, Object> mapOut = sListIterator.next(); 
            String sellerBy = MapUtils.getString(mapOut, "sellerBy");
            if(!StringUtils.isEmpty(sellerBy)) {
                boolean isExist = false;
                for(Map<String, Object> mapIn : listIn) {
                    if(sellerBy.equals(MapUtils.getString(mapIn, "sellerBy"))) {
                        isExist = true;
                        break;
                    }
                }
                //如果商品不存在退货单，则添加入列表
                if(!isExist) {
                    mapOut.put("goodsOrderNum", "-" + MapUtils.getString(mapOut, "goodsOrderNum"));
                    mapOut.put("goodsPurchasePrice", "-" + MapUtils.getString(mapOut, "goodsPurchasePrice"));
                    mapOut.put("goodsOrderProfit", "-" + MapUtils.getString(mapOut, "goodsOrderProfit"));
                    listIn.add(mapOut);
                    sListIterator.remove();  
                }
            }
        }  
        //两个列表相减
        for(Map<String, Object> mapIn : listIn) {
            String sellerBy = MapUtils.getString(mapIn, "sellerBy");
            if(!StringUtils.isEmpty(sellerBy)) {
                for(Map<String, Object> mapOut : listOut) {
                    if(sellerBy.equals(MapUtils.getString(mapOut, "sellerBy"))) {
                        mapIn.put("goodsOrderNum", MapUtils.getIntValue(mapIn, "goodsOrderNum", 0) - MapUtils.getIntValue(mapOut, "goodsOrderNum", 0));
                        mapIn.put("goodsPurchasePrice", MapUtils.getFloatValue(mapIn, "goodsPurchasePrice", 0) - MapUtils.getFloatValue(mapOut, "goodsPurchasePrice", 0));
                        mapIn.put("goodsOrderProfit", MapUtils.getFloatValue(mapIn, "goodsOrderProfit", 0) - MapUtils.getFloatValue(mapOut, "goodsOrderProfit", 0));
                        break;
                    }
                }
            }
        }
        return RestfulRetUtils.getRetSuccessWithPage(listIn, count);
    }

    @Override
    public JSONObject getSaleReportPerformanceByList(Pageable page, Map<String, Object> params) {
        int count = reportSaleDatabaseService.getSaleReportPerformanceByListCount(params);
        params.put("orderTypeList", "lsd,pfd,ysd");
        List<Map<String, Object>> listIn = reportSaleDatabaseService.getSaleReportPerformanceByList(params, page.getPageNumber(), page.getPageSize());
        params.put("orderTypeList", "thd");
        List<Map<String, Object>> listOut = reportSaleDatabaseService.getSaleReportPerformanceByList(params, page.getPageNumber(), page.getPageSize());
        Iterator<Map<String, Object>> sListIterator = listOut.iterator();  
        while(sListIterator.hasNext()){  
            Map<String, Object> mapOut = sListIterator.next(); 
            String performanceBy = MapUtils.getString(mapOut, "performanceBy");
            if(!StringUtils.isEmpty(performanceBy)) {
                boolean isExist = false;
                for(Map<String, Object> mapIn : listIn) {
                    if(performanceBy.equals(MapUtils.getString(mapIn, "performanceBy"))) {
                        isExist = true;
                        break;
                    }
                }
                //如果商品不存在退货单，则添加入列表
                if(!isExist) {
                    mapOut.put("goodsOrderNum", "-" + MapUtils.getString(mapOut, "goodsOrderNum"));
                    mapOut.put("goodsPurchasePrice", "-" + MapUtils.getString(mapOut, "goodsPurchasePrice"));
                    mapOut.put("goodsOrderProfit", "-" + MapUtils.getString(mapOut, "goodsOrderProfit"));
                    listIn.add(mapOut);
                    sListIterator.remove();  
                }
            }
        }  
        //两个列表相减
        for(Map<String, Object> mapIn : listIn) {
            String performanceBy = MapUtils.getString(mapIn, "performanceBy");
            if(!StringUtils.isEmpty(performanceBy)) {
                for(Map<String, Object> mapOut : listOut) {
                    if(performanceBy.equals(MapUtils.getString(mapOut, "performanceBy"))) {
                        mapIn.put("goodsOrderNum", MapUtils.getIntValue(mapIn, "goodsOrderNum", 0) - MapUtils.getIntValue(mapOut, "goodsOrderNum", 0));
                        mapIn.put("goodsPurchasePrice", MapUtils.getFloatValue(mapIn, "goodsPurchasePrice", 0) - MapUtils.getFloatValue(mapOut, "goodsPurchasePrice", 0));
                        mapIn.put("goodsOrderProfit", MapUtils.getFloatValue(mapIn, "goodsOrderProfit", 0) - MapUtils.getFloatValue(mapOut, "goodsOrderProfit", 0));
                        break;
                    }
                }
            }
        }
        return RestfulRetUtils.getRetSuccessWithPage(listIn, count);
    }

    @Override
    public JSONObject getSaleReportShopList(Pageable page, Map<String, Object> params) {
        int count = reportSaleDatabaseService.getSaleReportShopListCount(params);
        params.put("orderTypeList", "lsd,pfd,ysd");
        List<Map<String, Object>> listIn = reportSaleDatabaseService.getSaleReportShopList(params, page.getPageNumber(), page.getPageSize());
        params.put("orderTypeList", "thd");
        List<Map<String, Object>> listOut = reportSaleDatabaseService.getSaleReportShopList(params, page.getPageNumber(), page.getPageSize());
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
                    mapOut.put("goodsPurchasePrice", "-" + MapUtils.getString(mapOut, "goodsPurchasePrice"));
                    mapOut.put("goodsOrderProfit", "-" + MapUtils.getString(mapOut, "goodsOrderProfit"));
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
                        mapIn.put("goodsPurchasePrice", MapUtils.getFloatValue(mapIn, "goodsPurchasePrice", 0) - MapUtils.getFloatValue(mapOut, "goodsPurchasePrice", 0));
                        mapIn.put("goodsOrderProfit", MapUtils.getFloatValue(mapIn, "goodsOrderProfit", 0) - MapUtils.getFloatValue(mapOut, "goodsOrderProfit", 0));
                        break;
                    }
                }
            }
        }
        return RestfulRetUtils.getRetSuccessWithPage(listIn, count);
    }
}
