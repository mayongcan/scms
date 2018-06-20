/*
 * Copyright(c) 2018 gimplatform(通用信息管理平台) All rights reserved.
 */
package com.scms.modules.customer.service;

import java.util.Map;

import org.springframework.data.domain.Pageable;
import com.alibaba.fastjson.JSONObject;
import com.gimplatform.core.entity.UserInfo;

import com.scms.modules.customer.entity.ScmsCustomerInfo;

/**
 * 服务类接口
 * @version 1.0
 * @author 
 */
public interface ScmsCustomerInfoService {
	
	/**
	 * 获取列表
	 * @param page
	 * @param scmsCustomerInfo
	 * @return
	 */
	public JSONObject getList(Pageable page, ScmsCustomerInfo scmsCustomerInfo, Map<String, Object> params);
	
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
	 * 客户统计
	 * @param params
	 * @return
	 */
    public JSONObject getCustomerStatistics(Map<String, Object> params);

    /**
     * 客户对账统计
     * @param params
     * @return
     */
    public JSONObject getCustomerCheckBillStatistics(Map<String, Object> params);

}
