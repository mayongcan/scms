/*
 * Copyright(c) 2018 gimplatform(通用信息管理平台) All rights reserved.
 */
package com.scms.modules.report.impl;

import java.util.List;
import java.util.Map;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import com.alibaba.fastjson.JSONObject;
import com.gimplatform.core.utils.RestfulRetUtils;
import com.scms.modules.report.ReportAllotDatabaseService;
import com.scms.modules.report.ReportAllotService;

@Service
public class ReportAllotServiceImpl implements ReportAllotService {
    
    @Autowired
    private ReportAllotDatabaseService reportAllotDatabaseService;

    @Override
    public JSONObject getAllotReportDetailStatistics(Map<String, Object> params) {
        List<Map<String, Object>> list = reportAllotDatabaseService.getAllotReportDetailStatistics(params);
        return RestfulRetUtils.getRetSuccessWithPage(list, list.size());  
    }

    @Override
    public JSONObject getAllotReportDetailList(Pageable page, Map<String, Object> params) {
        List<Map<String, Object>> list = reportAllotDatabaseService.getAllotReportDetailList(params, page.getPageNumber(), page.getPageSize());
        int count = reportAllotDatabaseService.getAllotReportDetailListCount(params);
        return RestfulRetUtils.getRetSuccessWithPage(list, count);  
    }

    @Override
    public JSONObject getAllotReportGoodsList(Pageable page, Map<String, Object> params) {
        List<Map<String, Object>> list = reportAllotDatabaseService.getAllotReportGoodsList(params, page.getPageNumber(), page.getPageSize());
        int count = reportAllotDatabaseService.getAllotReportGoodsListCount(params);
        return RestfulRetUtils.getRetSuccessWithPage(list, count);  
    }

    @Override
    public JSONObject getAllotReportOrderList(Pageable page, Map<String, Object> params) {
        List<Map<String, Object>> list = reportAllotDatabaseService.getAllotReportOrderList(params, page.getPageNumber(), page.getPageSize());
        int count = reportAllotDatabaseService.getAllotReportOrderListCount(params);
        return RestfulRetUtils.getRetSuccessWithPage(list, count);  
    }

    @Override
    public JSONObject getAllotReportSrcShopList(Pageable page, Map<String, Object> params) {
        List<Map<String, Object>> list = reportAllotDatabaseService.getAllotReportSrcShopList(params, page.getPageNumber(), page.getPageSize());
        int count = reportAllotDatabaseService.getAllotReportSrcShopListCount(params);
        return RestfulRetUtils.getRetSuccessWithPage(list, count);  
    }

    @Override
    public JSONObject getAllotReportDestShopList(Pageable page, Map<String, Object> params) {
        List<Map<String, Object>> list = reportAllotDatabaseService.getAllotReportDestShopList(params, page.getPageNumber(), page.getPageSize());
        int count = reportAllotDatabaseService.getAllotReportDestShopListCount(params);
        return RestfulRetUtils.getRetSuccessWithPage(list, count);  
    }
}
