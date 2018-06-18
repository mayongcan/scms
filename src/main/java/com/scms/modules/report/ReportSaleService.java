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
public interface ReportSaleService {

    /**
     * 获取销售报表-销售明细(统计)
     * @param params
     * @return
     */
    public JSONObject getSaleReportDetailStatistics(Map<String, Object> params);
	
    /**
     * 获取销售报表-销售明细
     * @param page
     * @param params
     * @return
     */
	public JSONObject getSaleReportDetailList(Pageable page, Map<String, Object> params);
    
    /**
     * 获取销售报表-商品汇总
     * @param page
     * @param params
     * @return
     */
    public JSONObject getSaleReportGoodsList(Pageable page, Map<String, Object> params);
    
    /**
     * 获取销售报表-订单汇总
     * @param page
     * @param params
     * @return
     */
    public JSONObject getSaleReportOrderList(Pageable page, Map<String, Object> params);
    
    /**
     * 获取销售报表-客户汇总
     * @param page
     * @param params
     * @return
     */
    public JSONObject getSaleReportCustomerList(Pageable page, Map<String, Object> params);
    
    /**
     * 获取销售报表-订单创建人
     * @param page
     * @param params
     * @return
     */
    public JSONObject getSaleReportCreateByList(Pageable page, Map<String, Object> params);
    
    /**
     * 获取销售报表-订单销售人
     * @param page
     * @param params
     * @return
     */
    public JSONObject getSaleReportSellerByList(Pageable page, Map<String, Object> params);
    
    /**
     * 获取销售报表-业绩归属人
     * @param page
     * @param params
     * @return
     */
    public JSONObject getSaleReportPerformanceByList(Pageable page, Map<String, Object> params);
    
    /**
     * 获取销售报表-店铺汇总
     * @param page
     * @param params
     * @return
     */
    public JSONObject getSaleReportShopList(Pageable page, Map<String, Object> params);
}
