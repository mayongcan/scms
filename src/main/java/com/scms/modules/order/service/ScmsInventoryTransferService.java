/*
 * Copyright(c) 2018 gimplatform(通用信息管理平台) All rights reserved.
 */
package com.scms.modules.order.service;

import java.util.Map;

import org.springframework.data.domain.Pageable;
import com.alibaba.fastjson.JSONObject;
import com.gimplatform.core.entity.UserInfo;

import com.scms.modules.order.entity.ScmsInventoryTransfer;

/**
 * 服务类接口
 * @version 1.0
 * @author 
 */
public interface ScmsInventoryTransferService {
	
	/**
	 * 获取列表
	 * @param page
	 * @param scmsInventoryTransfer
	 * @return
	 */
	public JSONObject getList(Pageable page, ScmsInventoryTransfer scmsInventoryTransfer, Map<String, Object> params);
	
	/**
	 * 新增
	 * @param params
	 * @param userInfo
	 * @return
	 */
	public JSONObject add(Map<String, Object> params, UserInfo userInfo);
	
	/**
	 * 撤销
	 * @param idsList
	 * @param userInfo
	 * @return
	 */
	public JSONObject repeal(String idsList, UserInfo userInfo);
	

}
