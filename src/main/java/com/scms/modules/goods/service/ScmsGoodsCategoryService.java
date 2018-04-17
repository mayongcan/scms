/*
 * Copyright(c) 2018 gimplatform(通用信息管理平台) All rights reserved.
 */
package com.scms.modules.goods.service;

import java.util.List;
import java.util.Map;

import org.springframework.data.domain.Pageable;
import com.alibaba.fastjson.JSONObject;
import com.gimplatform.core.entity.UserInfo;

import com.scms.modules.goods.entity.ScmsGoodsCategory;

/**
 * 服务类接口
 * @version 1.0
 * @author 
 */
public interface ScmsGoodsCategoryService {
	
	/**
	 * 获取列表
	 * @param page
	 * @param scmsGoodsCategory
	 * @return
	 */
	public JSONObject getList(Pageable page, ScmsGoodsCategory scmsGoodsCategory, Map<String, Object> params);
	
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
	 * 获取树内容
	 * @return
	 */
	public JSONObject getTreeList(Map<String, Object> params);

	/**
	 * 根据父标志，获取列表
	 * @param scmsGoodsCategory
	 * @return
	 */
	public List<ScmsGoodsCategory> getListByParentIds(ScmsGoodsCategory scmsGoodsCategory);

}
