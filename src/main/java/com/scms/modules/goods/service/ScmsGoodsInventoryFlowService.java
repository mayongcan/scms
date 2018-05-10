/*
 * Copyright(c) 2018 gimplatform(通用信息管理平台) All rights reserved.
 */
package com.scms.modules.goods.service;

import java.util.Map;
import org.springframework.data.domain.Pageable;
import com.alibaba.fastjson.JSONObject;
import com.scms.modules.goods.entity.ScmsGoodsInventoryFlow;

/**
 * 服务类接口
 * @version 1.0
 * @author 
 */
public interface ScmsGoodsInventoryFlowService {
	
	/**
	 * 获取列表
	 * @param page
	 * @param scmsGoodsInventoryFlow
	 * @return
	 */
	public JSONObject getList(Pageable page, ScmsGoodsInventoryFlow scmsGoodsInventoryFlow, Map<String, Object> params);
	
	/**
	 * 新增
	 * @param params
	 * @param userInfo
	 */
    public void add(ScmsGoodsInventoryFlow scmsGoodsInventoryFlow);
}
