/*
 * Copyright(c) 2018 gimplatform(通用信息管理平台) All rights reserved.
 */
package com.scms.modules.report;

import java.util.List;
import java.util.Map;

public interface ReportDatabaseService{
    
    public List<Map<String, Object>> getSalePurchaseCompareStatistics(Map<String, Object> params);
    
    public List<Map<String, Object>> getSalePurchaseCompareList(Map<String, Object> params, int pageIndex, int pageSize);
    
    public int getSalePurchaseCompareListCount(Map<String, Object> params);
}