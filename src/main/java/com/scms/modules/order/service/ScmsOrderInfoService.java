/*
 * Copyright(c) 2018 gimplatform(通用信息管理平台) All rights reserved.
 */
package com.scms.modules.order.service;

import java.util.Map;

import org.springframework.data.domain.Pageable;
import com.alibaba.fastjson.JSONObject;
import com.gimplatform.core.entity.UserInfo;

import com.scms.modules.order.entity.ScmsOrderInfo;

/**
 * 服务类接口
 * @version 1.0
 * @author 
 */
public interface ScmsOrderInfoService {
	
	/**
	 * 获取列表
	 * @param page
	 * @param scmsOrderInfo
	 * @return
	 */
	public JSONObject getList(Pageable page, ScmsOrderInfo scmsOrderInfo, Map<String, Object> params);
	
	/**
	 * 新增
	 * @param params
	 * @param userInfo
	 * @return
	 */
	public JSONObject addOrderInfo(Map<String, Object> params, UserInfo userInfo);
	
	/**
	 * 编辑
	 * @param params
	 * @param userInfo
	 * @return
	 */
	public JSONObject editOrderInfo(Map<String, Object> params, UserInfo userInfo);
	
	/**
	 * 删除
	 * @param idsList
	 * @param userInfo
	 * @return
	 */
	public JSONObject del(String idsList, UserInfo userInfo);
	
	/**
	 * 更新订单状态
	 * @param idsList
	 * @param userInfo
	 * @param status
	 * @return
	 */
    public JSONObject updateOrderStatus(String idsList, UserInfo userInfo, String status);

    /**
     * 获取进货单、返厂单列表
     * @param page
     * @param scmsOrderInfo
     * @param params
     * @return
     */
    public JSONObject getOrderJhdList(Pageable page, ScmsOrderInfo scmsOrderInfo, Map<String, Object> params);
    
    /**
     * 新增进货单、返厂单
     * @param params
     * @param userInfo
     * @return
     */
    public JSONObject addOrderJhd(Map<String, Object> params, UserInfo userInfo);
    
    /**
     * 编辑进货单、返厂单
     * @param params
     * @param userInfo
     * @return
     */
    public JSONObject editOrderJhd(Map<String, Object> params, UserInfo userInfo);

    /**
     * 获取收银单列表
     * @param page
     * @param scmsOrderInfo
     * @param params
     * @return
     */
    public JSONObject getOrderSydList(Pageable page, ScmsOrderInfo scmsOrderInfo, Map<String, Object> params);
    
    /**
     * 新增收银单
     * @param params
     * @param userInfo
     * @return
     */
    public JSONObject addOrderSyd(Map<String, Object> params, UserInfo userInfo);
    
    /**
     * 获取客户收款历史统计
     * @param params
     * @return
     */
    public JSONObject getReceiptHistoryStatistics(Map<String, Object> params);
    
}
