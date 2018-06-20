/*
 * Copyright(c) 2018 gimplatform(通用信息管理平台) All rights reserved.
 */
package com.scms.modules.goods.service;

import java.util.Map;

import org.springframework.data.domain.Pageable;
import com.alibaba.fastjson.JSONObject;
import com.gimplatform.core.entity.UserInfo;

import com.scms.modules.goods.entity.ScmsGoodsInfo;

/**
 * 服务类接口
 * @version 1.0
 * @author 
 */
public interface ScmsGoodsInfoService {
	
	/**
	 * 获取列表
	 * @param page
	 * @param scmsGoodsInfo
	 * @return
	 */
	public JSONObject getList(Pageable page, ScmsGoodsInfo scmsGoodsInfo, Map<String, Object> params);
	
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
	 * 获取所有商品的库存统计
	 * @param params
	 * @return
	 */
	public JSONObject getAllGoodsInventoryStatistics(Map<String, Object> params);

}
