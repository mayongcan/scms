/*
 * Copyright(c) 2018 gimplatform(通用信息管理平台) All rights reserved.
 */
package com.scms.modules.order.service;

import java.util.Map;

import org.springframework.data.domain.Pageable;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.gimplatform.core.entity.UserInfo;
import com.scms.modules.order.entity.ScmsOrderInfo;
import com.scms.modules.order.entity.ScmsOrderPay;

/**
 * 服务类接口
 * @version 1.0
 * @author 
 */
public interface ScmsOrderPayService {
	
	/**
	 * 获取列表
	 * @param page
	 * @param scmsOrderPay
	 * @return
	 */
	public JSONObject getList(Pageable page, ScmsOrderPay scmsOrderPay, Map<String, Object> params);	

    /**
     * 编辑订单支付信息
     * @param params
     * @param userInfo
     * @return
     */
    public JSONObject editOrderPay(Map<String, Object> params, UserInfo userInfo);
    
    public Double getPayAmount(ScmsOrderInfo scmsOrderInfo);
    
    public void delOrderPay(ScmsOrderInfo scmsOrderInfo, String validReason);
    
    public void saveOrderPayList(JSONArray jsonArray, ScmsOrderInfo scmsOrderInfo, UserInfo userInfo);

}
