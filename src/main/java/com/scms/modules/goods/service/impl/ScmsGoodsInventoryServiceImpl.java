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
import com.scms.modules.goods.service.ScmsGoodsInventoryService;
import com.scms.modules.goods.entity.ScmsGoodsInventory;
import com.scms.modules.goods.repository.ScmsGoodsInventoryRepository;

@Service
public class ScmsGoodsInventoryServiceImpl implements ScmsGoodsInventoryService {
	
    @Autowired
    private ScmsGoodsInventoryRepository scmsGoodsInventoryRepository;

	@Override
	public JSONObject getList(Pageable page, ScmsGoodsInventory scmsGoodsInventory, Map<String, Object> params) {
		List<Map<String, Object>> list = scmsGoodsInventoryRepository.getList(scmsGoodsInventory, params, page.getPageNumber(), page.getPageSize());
		int count = scmsGoodsInventoryRepository.getListCount(scmsGoodsInventory, params);
		return RestfulRetUtils.getRetSuccessWithPage(list, count);	
	}
}
