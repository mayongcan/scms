/*
 * Copyright(c) 2018 gimplatform(通用信息管理平台) All rights reserved.
 */
package com.scms.modules.goods.service;

import java.util.Map;

import org.springframework.data.domain.Pageable;
import com.alibaba.fastjson.JSONObject;

import com.scms.modules.goods.entity.ScmsGoodsExtraDiscount;

/**
 * 服务类接口
 * @version 1.0
 * @author 
 */
public interface ScmsGoodsExtraDiscountService {
	
	/**
	 * 获取列表
	 * @param page
	 * @param scmsGoodsExtraDiscount
	 * @return
	 */
	public JSONObject getList(Pageable page, ScmsGoodsExtraDiscount scmsGoodsExtraDiscount, Map<String, Object> params);
}
