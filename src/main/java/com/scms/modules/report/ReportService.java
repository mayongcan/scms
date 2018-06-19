/*
 * Copyright(c) 2018 gimplatform(通用信息管理平台) All rights reserved.
 */
package com.scms.modules.report;

import java.util.Map;
import org.springframework.data.domain.Pageable;
import com.alibaba.fastjson.JSONObject;

/**
 * 服务类接口
 * @version 1.0
 * @author 
 */
public interface ReportService {
    
    /**
     * 获取经营概况（统计）
     * @param params
     * @return
     */
    public JSONObject getSummary(Map<String, Object> params);
    
    /**
     * 统计表1
     * @param params
     * @return
     */
    public JSONObject getSummaryChart1(Map<String, Object> params);

    /**
     * 获取进销对比(统计)
     * @param params
     * @return
     */
    public JSONObject getSalePurchaseCompareStatistics(Map<String, Object> params);
	
    /**
     * 获取进销对比
     * @param page
     * @param params
     * @return
     */
	public JSONObject getSalePurchaseCompareList(Pageable page, Map<String, Object> params);
}
