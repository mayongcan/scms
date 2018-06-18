/*
 * Copyright(c) 2018 gimplatform(通用信息管理平台) All rights reserved.
 */
package com.scms.modules.report;

import java.util.List;
import java.util.Map;

public interface ReportCheckDatabaseService{
    
    public List<Map<String, Object>> getCheckReportDetailStatistics(Map<String, Object> params);
    
    public List<Map<String, Object>> getCheckReportDetailList(Map<String, Object> params, int pageIndex, int pageSize);
    
    public int getCheckReportDetailListCount(Map<String, Object> params);

    
    public List<Map<String, Object>> getCheckReportGoodsList(Map<String, Object> params, int pageIndex, int pageSize);
    
    public int getCheckReportGoodsListCount(Map<String, Object> params);

    
    public List<Map<String, Object>> getCheckReportOrderList(Map<String, Object> params, int pageIndex, int pageSize);
    
    public int getCheckReportOrderListCount(Map<String, Object> params);
    
    
    public List<Map<String, Object>> getCheckReportShopList(Map<String, Object> params, int pageIndex, int pageSize);
    
    public int getCheckReportShopListCount(Map<String, Object> params);
}