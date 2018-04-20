/*
 * Copyright(c) 2018 gimplatform(通用信息管理平台) All rights reserved.
 */
package com.scms.modules.goods.service;

import java.util.Map;

import org.springframework.data.domain.Pageable;
import com.alibaba.fastjson.JSONObject;

import com.scms.modules.goods.entity.ScmsGoodsExtraPrice;

/**
 * 服务类接口
 * @version 1.0
 * @author 
 */
public interface ScmsGoodsExtraPriceService {
	
	/**
	 * 获取列表
	 * @param page
	 * @param scmsGoodsExtraPrice
	 * @return
	 */
	public JSONObject getList(Pageable page, ScmsGoodsExtraPrice scmsGoodsExtraPrice, Map<String, Object> params);
}
