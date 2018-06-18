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
public interface ReportCheckService {

    /**
     * 获取盘点报表-销售明细(统计)
     * @param params
     * @return
     */
    public JSONObject getCheckReportDetailStatistics(Map<String, Object> params);
	
    /**
     * 获取盘点报表-销售明细
     * @param page
     * @param params
     * @return
     */
	public JSONObject getCheckReportDetailList(Pageable page, Map<String, Object> params);
    
    /**
     * 获取盘点报表-商品汇总
     * @param page
     * @param params
     * @return
     */
    public JSONObject getCheckReportGoodsList(Pageable page, Map<String, Object> params);
    
    /**
     * 获取盘点报表-订单汇总
     * @param page
     * @param params
     * @return
     */
    public JSONObject getCheckReportOrderList(Pageable page, Map<String, Object> params);
    
    /**
     * 获取盘点报表-店铺汇总
     * @param page
     * @param params
     * @return
     */
    public JSONObject getCheckReportShopList(Pageable page, Map<String, Object> params);
}
