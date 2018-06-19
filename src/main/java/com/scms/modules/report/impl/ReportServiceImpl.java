/*
 * Copyright(c) 2018 gimplatform(通用信息管理平台) All rights reserved.
 */
package com.scms.modules.report.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.stereotype.Service;
import org.apache.commons.collections4.MapUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import com.alibaba.fastjson.JSONObject;
import com.gimplatform.core.utils.DateUtils;
import com.gimplatform.core.utils.RestfulRetUtils;
import com.gimplatform.core.utils.StringUtils;
import com.scms.modules.report.ReportDatabaseService;
import com.scms.modules.report.ReportService;

@Service
public class ReportServiceImpl implements ReportService {
    
    @Autowired
    private ReportDatabaseService reportDatabaseService;

    @Override
    public JSONObject getSummary(Map<String, Object> params) {
        List<Map<String, Object>> list = reportDatabaseService.getSummary(params);
        return RestfulRetUtils.getRetSuccessWithPage(list, list.size()); 
    }

    public JSONObject getSummaryChart1(Map<String, Object> params) {
        //进行补零
        String totalTitle = "", totalAmountList = "", totalProfitList = "";
        String searchType = MapUtils.getString(params, "searchType");
        if("2".equals(searchType)) {
            params.put("createDateEnd", DateUtils.formatDate(new Date(), "yyyy-MM-dd"));
            params.put("createDateBegin", DateUtils.formatDate(DateUtils.addYears(new Date(), -1), "yyyy-MM-dd"));
            List<Map<String, Object>> list = reportDatabaseService.getSummaryChart1(params);
            //最近一年
            for(int i = 11;i >= 0; i--) {
                String month = DateUtils.formatDate(DateUtils.addMonths(new Date(), -i), "M");
                String title = DateUtils.formatDate(DateUtils.addMonths(new Date(), -i), "yyyy年MM月");
                String totalAmount = "", totalProfit = "";
                for(Map<String, Object> map : list) {
                    if(month.equals(MapUtils.getString(map, "month"))) {
                        totalAmount = MapUtils.getString(map, "totalAmount");
                        totalProfit = MapUtils.getString(map, "totalProfit");
                        break;
                    }
                }
                totalTitle += title + ",";
                if(StringUtils.isBlank(totalAmount)) totalAmount = "0";
                totalAmountList += totalAmount + ",";
                if(StringUtils.isBlank(totalProfit)) totalProfit = "0";
                totalProfitList += totalProfit + ",";
            }
        }else {
            params.put("createDateEnd", DateUtils.formatDate(new Date(), "yyyy-MM-dd"));
            params.put("createDateBegin", DateUtils.formatDate(DateUtils.addDays(new Date(), -6), "yyyy-MM-dd"));
            List<Map<String, Object>> list = reportDatabaseService.getSummaryChart1(params);
            //最近7日
            for(int i = 6;i >= 0; i--) {
                String day = DateUtils.formatDate(DateUtils.addDays(new Date(), -i), "dd");
                String title = DateUtils.formatDate(DateUtils.addDays(new Date(), -i), "yyyy年MM月dd日");
                String totalAmount = "", totalProfit = "";
                for(Map<String, Object> map : list) {
                    if(day.equals(MapUtils.getString(map, "day"))) {
                        totalAmount = MapUtils.getString(map, "totalAmount");
                        totalProfit = MapUtils.getString(map, "totalProfit");
                        break;
                    }
                }
                totalTitle += title + ",";
                if(StringUtils.isBlank(totalAmount)) totalAmount = "0";
                totalAmountList += totalAmount + ",";
                if(StringUtils.isBlank(totalProfit)) totalProfit = "0";
                totalProfitList += totalProfit + ",";
            }
        }
        if(!StringUtils.isBlank(totalTitle)) totalTitle = totalTitle.substring(0, totalTitle.length() - 1);
        if(!StringUtils.isBlank(totalAmountList)) totalAmountList = totalAmountList.substring(0, totalAmountList.length() - 1);
        if(!StringUtils.isBlank(totalProfitList)) totalProfitList = totalProfitList.substring(0, totalProfitList.length() - 1);
        List<Map<String, Object>> retList = new ArrayList<Map<String, Object>>();
        Map<String, Object> retMap = new HashMap<String, Object>();
        retMap.put("totalTitle", totalTitle);
        retMap.put("totalAmount", totalAmountList);
        retMap.put("totalProfit", totalProfitList);
        retList.add(retMap);
        return RestfulRetUtils.getRetSuccessWithPage(retList, retList.size()); 
    }
    
    @Override
    public JSONObject getSalePurchaseCompareStatistics(Map<String, Object> params) {
        params.put("orderTypeList","jhd");
        List<Map<String, Object>> list1 = reportDatabaseService.getSalePurchaseCompareStatistics(params);
        params.put("orderTypeList","fcd");
        List<Map<String, Object>> list2 = reportDatabaseService.getSalePurchaseCompareStatistics(params);
        params.put("orderTypeList","lsd,pfd,ysd");
        List<Map<String, Object>> list3 = reportDatabaseService.getSalePurchaseCompareStatistics(params);
        params.put("orderTypeList","thd");
        List<Map<String, Object>> list4 = reportDatabaseService.getSalePurchaseCompareStatistics(params);

        Map<String, Object> retMap = new HashMap<String, Object>();
        retMap.put("totalPurchase", MapUtils.getString(list1.get(0), "num", "0"));
        retMap.put("totalReturn", MapUtils.getString(list2.get(0), "num", "0"));
        retMap.put("totalSale", MapUtils.getString(list3.get(0), "num", "0"));
        retMap.put("totalSaleReturn", MapUtils.getString(list4.get(0), "num", "0"));
        List<Map<String, Object>> retList = new ArrayList<Map<String, Object>>();
        retList.add(retMap);

        return RestfulRetUtils.getRetSuccessWithPage(retList, retList.size());  
    }

    @Override
    public JSONObject getSalePurchaseCompareList(Pageable page, Map<String, Object> params) {
        List<Map<String, Object>> list = reportDatabaseService.getSalePurchaseCompareList(params, page.getPageNumber(), page.getPageSize());
        int count = reportDatabaseService.getSalePurchaseCompareListCount(params);
        return RestfulRetUtils.getRetSuccessWithPage(list, count);  
    }
}
