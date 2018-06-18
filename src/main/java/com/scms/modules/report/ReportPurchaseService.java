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
public interface ReportPurchaseService {

    /**
     * 获取销售报表-销售明细(统计)
     * @param params
     * @return
     */
    public JSONObject getPurchaseReportDetailStatistics(Map<String, Object> params);
	
    /**
     * 获取销售报表-销售明细
     * @param page
     * @param params
     * @return
     */
	public JSONObject getPurchaseReportDetailList(Pageable page, Map<String, Object> params);
    
    /**
     * 获取销售报表-商品汇总
     * @param page
     * @param params
     * @return
     */
    public JSONObject getPurchaseReportGoodsList(Pageable page, Map<String, Object> params);
    
    /**
     * 获取销售报表-订单汇总
     * @param page
     * @param params
     * @return
     */
    public JSONObject getPurchaseReportOrderList(Pageable page, Map<String, Object> params);
    
    /**
     * 获取销售报表-供货商汇总
     * @param page
     * @param params
     * @return
     */
    public JSONObject getPurchaseReportSupplierList(Pageable page, Map<String, Object> params);
    
    /**
     * 获取销售报表-订单创建人
     * @param page
     * @param params
     * @return
     */
    public JSONObject getPurchaseReportCreateByList(Pageable page, Map<String, Object> params);
    
    /**
     * 获取销售报表-店铺汇总
     * @param page
     * @param params
     * @return
     */
    public JSONObject getPurchaseReportShopList(Pageable page, Map<String, Object> params);
}
