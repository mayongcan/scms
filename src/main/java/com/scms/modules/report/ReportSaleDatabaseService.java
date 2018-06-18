/*
 * Copyright(c) 2018 gimplatform(通用信息管理平台) All rights reserved.
 */
package com.scms.modules.report;

import java.util.List;
import java.util.Map;

public interface ReportSaleDatabaseService{
    
    public List<Map<String, Object>> getSaleReportDetailStatistics(Map<String, Object> params);
    
    public List<Map<String, Object>> getSaleReportDetailList(Map<String, Object> params, int pageIndex, int pageSize);
    
    public int getSaleReportDetailListCount(Map<String, Object> params);

    
    public List<Map<String, Object>> getSaleReportGoodsList(Map<String, Object> params, int pageIndex, int pageSize);
    
    public int getSaleReportGoodsListCount(Map<String, Object> params);

    
    public List<Map<String, Object>> getSaleReportOrderList(Map<String, Object> params, int pageIndex, int pageSize);
    
    public int getSaleReportOrderListCount(Map<String, Object> params);

    
    public List<Map<String, Object>> getSaleReportCustomerList(Map<String, Object> params, int pageIndex, int pageSize);
    
    public int getSaleReportCustomerListCount(Map<String, Object> params);

    
    public List<Map<String, Object>> getSaleReportCreateByList(Map<String, Object> params, int pageIndex, int pageSize);
    
    public int getSaleReportCreateByListCount(Map<String, Object> params);

    
    public List<Map<String, Object>> getSaleReportSellerByList(Map<String, Object> params, int pageIndex, int pageSize);
    
    public int getSaleReportSellerByListCount(Map<String, Object> params);

    
    public List<Map<String, Object>> getSaleReportPerformanceByList(Map<String, Object> params, int pageIndex, int pageSize);
    
    public int getSaleReportPerformanceByListCount(Map<String, Object> params);
    
    
    public List<Map<String, Object>> getSaleReportShopList(Map<String, Object> params, int pageIndex, int pageSize);
    
    public int getSaleReportShopListCount(Map<String, Object> params);
}