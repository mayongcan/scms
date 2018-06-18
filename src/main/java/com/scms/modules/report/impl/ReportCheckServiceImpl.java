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
import com.scms.modules.report.ReportCheckDatabaseService;
import com.scms.modules.report.ReportCheckService;

@Service
public class ReportCheckServiceImpl implements ReportCheckService {
    
    @Autowired
    private ReportCheckDatabaseService reportCheckDatabaseService;

    @Override
    public JSONObject getCheckReportDetailStatistics(Map<String, Object> params) {
        List<Map<String, Object>> list = reportCheckDatabaseService.getCheckReportDetailStatistics(params);
        return RestfulRetUtils.getRetSuccessWithPage(list, list.size());  
    }

    @Override
    public JSONObject getCheckReportDetailList(Pageable page, Map<String, Object> params) {
        List<Map<String, Object>> list = reportCheckDatabaseService.getCheckReportDetailList(params, page.getPageNumber(), page.getPageSize());
        int count = reportCheckDatabaseService.getCheckReportDetailListCount(params);
        return RestfulRetUtils.getRetSuccessWithPage(list, count);  
    }

    @Override
    public JSONObject getCheckReportGoodsList(Pageable page, Map<String, Object> params) {
        List<Map<String, Object>> list = reportCheckDatabaseService.getCheckReportGoodsList(params, page.getPageNumber(), page.getPageSize());
        int count = reportCheckDatabaseService.getCheckReportGoodsListCount(params);
        return RestfulRetUtils.getRetSuccessWithPage(list, count);  
    }

    @Override
    public JSONObject getCheckReportOrderList(Pageable page, Map<String, Object> params) {
        List<Map<String, Object>> list = reportCheckDatabaseService.getCheckReportOrderList(params, page.getPageNumber(), page.getPageSize());
        int count = reportCheckDatabaseService.getCheckReportOrderListCount(params);
        return RestfulRetUtils.getRetSuccessWithPage(list, count);  
    }

    @Override
    public JSONObject getCheckReportShopList(Pageable page, Map<String, Object> params) {
        List<Map<String, Object>> list = reportCheckDatabaseService.getCheckReportShopList(params, page.getPageNumber(), page.getPageSize());
        int count = reportCheckDatabaseService.getCheckReportShopListCount(params);
        return RestfulRetUtils.getRetSuccessWithPage(list, count);  
    }
}
