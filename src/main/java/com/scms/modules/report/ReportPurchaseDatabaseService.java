/*
 * Copyright(c) 2018 gimplatform(通用信息管理平台) All rights reserved.
 */
package com.scms.modules.report;

import java.util.List;
import java.util.Map;

public interface ReportPurchaseDatabaseService{
    
    public List<Map<String, Object>> getPurchaseReportDetailStatistics(Map<String, Object> params);
    
    public List<Map<String, Object>> getPurchaseReportDetailList(Map<String, Object> params, int pageIndex, int pageSize);
    
    public int getPurchaseReportDetailListCount(Map<String, Object> params);

    
    public List<Map<String, Object>> getPurchaseReportGoodsList(Map<String, Object> params, int pageIndex, int pageSize);
    
    public int getPurchaseReportGoodsListCount(Map<String, Object> params);

    
    public List<Map<String, Object>> getPurchaseReportOrderList(Map<String, Object> params, int pageIndex, int pageSize);
    
    public int getPurchaseReportOrderListCount(Map<String, Object> params);

    
    public List<Map<String, Object>> getPurchaseReportSupplierList(Map<String, Object> params, int pageIndex, int pageSize);
    
    public int getPurchaseReportSupplierListCount(Map<String, Object> params);

    
    public List<Map<String, Object>> getPurchaseReportCreateByList(Map<String, Object> params, int pageIndex, int pageSize);
    
    public int getPurchaseReportCreateByListCount(Map<String, Object> params);

    
    public List<Map<String, Object>> getPurchaseReportShopList(Map<String, Object> params, int pageIndex, int pageSize);
    
    public int getPurchaseReportShopListCount(Map<String, Object> params);
}