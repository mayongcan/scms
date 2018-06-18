/*
 * Copyright(c) 2018 gimplatform(通用信息管理平台) All rights reserved.
 */
package com.scms.modules.report;

import java.util.List;
import java.util.Map;

public interface ReportAllotDatabaseService{
    
    public List<Map<String, Object>> getAllotReportDetailStatistics(Map<String, Object> params);
    
    public List<Map<String, Object>> getAllotReportDetailList(Map<String, Object> params, int pageIndex, int pageSize);
    
    public int getAllotReportDetailListCount(Map<String, Object> params);

    
    public List<Map<String, Object>> getAllotReportGoodsList(Map<String, Object> params, int pageIndex, int pageSize);
    
    public int getAllotReportGoodsListCount(Map<String, Object> params);

    
    public List<Map<String, Object>> getAllotReportOrderList(Map<String, Object> params, int pageIndex, int pageSize);
    
    public int getAllotReportOrderListCount(Map<String, Object> params);
    
    
    public List<Map<String, Object>> getAllotReportSrcShopList(Map<String, Object> params, int pageIndex, int pageSize);
    
    public int getAllotReportSrcShopListCount(Map<String, Object> params);
    
    
    public List<Map<String, Object>> getAllotReportDestShopList(Map<String, Object> params, int pageIndex, int pageSize);
    
    public int getAllotReportDestShopListCount(Map<String, Object> params);
}