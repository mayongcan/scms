/*
 * Copyright(c) 2018 gimplatform(通用信息管理平台) All rights reserved.
 */
package com.scms.modules.report.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.stereotype.Service;
import org.apache.commons.collections4.MapUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import com.alibaba.fastjson.JSONObject;
import com.gimplatform.core.utils.RestfulRetUtils;
import com.scms.modules.report.ReportDatabaseService;
import com.scms.modules.report.ReportService;

@Service
public class ReportServiceImpl implements ReportService {
    
    @Autowired
    private ReportDatabaseService reportDatabaseService;

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
