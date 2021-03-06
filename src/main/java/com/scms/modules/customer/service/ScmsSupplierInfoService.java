/*
 * Copyright(c) 2018 gimplatform(通用信息管理平台) All rights reserved.
 */
package com.scms.modules.customer.service;

import java.util.Map;

import org.springframework.data.domain.Pageable;
import com.alibaba.fastjson.JSONObject;
import com.gimplatform.core.entity.UserInfo;

import com.scms.modules.customer.entity.ScmsSupplierInfo;

/**
 * 服务类接口
 * @version 1.0
 * @author 
 */
public interface ScmsSupplierInfoService {
	
	/**
	 * 获取列表
	 * @param page
	 * @param scmsSupplierInfo
	 * @return
	 */
	public JSONObject getList(Pageable page, ScmsSupplierInfo scmsSupplierInfo, Map<String, Object> params);
	
	/**
	 * 新增
	 * @param params
	 * @param userInfo
	 * @return
	 */
	public JSONObject add(Map<String, Object> params, UserInfo userInfo);
	
	/**
	 * 编辑
	 * @param params
	 * @param userInfo
	 * @return
	 */
	public JSONObject edit(Map<String, Object> params, UserInfo userInfo);
	
	/**
	 * 删除
	 * @param idsList
	 * @param userInfo
	 * @return
	 */
	public JSONObject del(String idsList, UserInfo userInfo);
	
	/**
	 * 获取供货商统计
	 * @param params
	 * @return
	 */
    public JSONObject getSupplierStatistics(Map<String, Object> params);
	
	/**
	 * 供货商对账统计
	 * @param params
	 * @return
	 */
    public JSONObject getSupplierCheckBillStatistics(Map<String, Object> params);
}
