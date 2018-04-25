/*
 * Copyright(c) 2018 gimplatform(通用信息管理平台) All rights reserved.
 */
package com.scms.modules.order.service;

import java.util.Map;

import org.springframework.data.domain.Pageable;
import com.alibaba.fastjson.JSONObject;
import com.gimplatform.core.entity.UserInfo;

import com.scms.modules.order.entity.ScmsOrderReceiveLog;

/**
 * 服务类接口
 * @version 1.0
 * @author 
 */
public interface ScmsOrderReceiveLogService {
	
	/**
	 * 获取列表
	 * @param page
	 * @param scmsOrderReceiveLog
	 * @return
	 */
	public JSONObject getList(Pageable page, ScmsOrderReceiveLog scmsOrderReceiveLog, Map<String, Object> params);
	
	/**
	 * 新增
	 * @param params
	 * @param userInfo
	 * @return
	 */
	public JSONObject addOrderReceiveLog(Map<String, Object> params, UserInfo userInfo);
	
}
