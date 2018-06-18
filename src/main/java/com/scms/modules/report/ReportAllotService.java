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
public interface ReportAllotService {

    /**
     * 获取调货报表-销售明细(统计)
     * @param params
     * @return
     */
    public JSONObject getAllotReportDetailStatistics(Map<String, Object> params);
	
    /**
     * 获取调货报表-销售明细
     * @param page
     * @param params
     * @return
     */
	public JSONObject getAllotReportDetailList(Pageable page, Map<String, Object> params);
    
    /**
     * 获取调货报表-商品汇总
     * @param page
     * @param params
     * @return
     */
    public JSONObject getAllotReportGoodsList(Pageable page, Map<String, Object> params);
    
    /**
     * 获取调货报表-订单汇总
     * @param page
     * @param params
     * @return
     */
    public JSONObject getAllotReportOrderList(Pageable page, Map<String, Object> params);
    
    /**
     * 获取调货报表-调出店铺汇总
     * @param page
     * @param params
     * @return
     */
    public JSONObject getAllotReportSrcShopList(Pageable page, Map<String, Object> params);
    
    /**
     * 获取调货报表-调入店铺汇总
     * @param page
     * @param params
     * @return
     */
    public JSONObject getAllotReportDestShopList(Pageable page, Map<String, Object> params);
}
