/*
 * Copyright(c) 2018 gimplatform(通用信息管理平台) All rights reserved.
 */
package com.scms.modules.goods.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import com.alibaba.fastjson.JSONObject;
import com.gimplatform.core.utils.RestfulRetUtils;

import com.scms.modules.goods.service.ScmsGoodsInventoryFlowService;
import com.scms.modules.goods.entity.ScmsGoodsInventoryFlow;
import com.scms.modules.goods.repository.ScmsGoodsInventoryFlowRepository;

@Service
public class ScmsGoodsInventoryFlowServiceImpl implements ScmsGoodsInventoryFlowService {
	
    @Autowired
    private ScmsGoodsInventoryFlowRepository scmsGoodsInventoryFlowRepository;

	@Override
	public JSONObject getList(Pageable page, ScmsGoodsInventoryFlow scmsGoodsInventoryFlow, Map<String, Object> params) {
		List<Map<String, Object>> list = scmsGoodsInventoryFlowRepository.getList(scmsGoodsInventoryFlow, params, page.getPageNumber(), page.getPageSize());
		int count = scmsGoodsInventoryFlowRepository.getListCount(scmsGoodsInventoryFlow, params);
		return RestfulRetUtils.getRetSuccessWithPage(list, count);	
	}

	@Override
	public void add(ScmsGoodsInventoryFlow scmsGoodsInventoryFlow) {
		scmsGoodsInventoryFlowRepository.save(scmsGoodsInventoryFlow);
	}
}
