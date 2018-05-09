/*
 * Copyright(c) 2018 gimplatform(通用信息管理平台) All rights reserved.
 */
package com.scms.modules.goods.service.impl;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import com.alibaba.fastjson.JSONObject;
import com.gimplatform.core.entity.UserInfo;
import com.gimplatform.core.utils.BeanUtils;
import com.gimplatform.core.utils.RestfulRetUtils;
import com.gimplatform.core.utils.StringUtils;

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
	public JSONObject add(Map<String, Object> params, UserInfo userInfo) {
	    ScmsGoodsInventoryFlow scmsGoodsInventoryFlow = (ScmsGoodsInventoryFlow) BeanUtils.mapToBean(params, ScmsGoodsInventoryFlow.class);
		scmsGoodsInventoryFlow.setCreateDate(new Date());
		scmsGoodsInventoryFlowRepository.save(scmsGoodsInventoryFlow);
		return RestfulRetUtils.getRetSuccess();
	}

	@Override
	public JSONObject edit(Map<String, Object> params, UserInfo userInfo) {
        ScmsGoodsInventoryFlow scmsGoodsInventoryFlow = (ScmsGoodsInventoryFlow) BeanUtils.mapToBean(params, ScmsGoodsInventoryFlow.class);
		ScmsGoodsInventoryFlow scmsGoodsInventoryFlowInDb = scmsGoodsInventoryFlowRepository.findOne(scmsGoodsInventoryFlow.getId());
		if(scmsGoodsInventoryFlowInDb == null){
			return RestfulRetUtils.getErrorMsg("51006","当前编辑的对象不存在");
		}
		//合并两个javabean
		BeanUtils.mergeBean(scmsGoodsInventoryFlow, scmsGoodsInventoryFlowInDb);
		scmsGoodsInventoryFlowRepository.save(scmsGoodsInventoryFlowInDb);
		return RestfulRetUtils.getRetSuccess();
	}

	@Override
	public JSONObject del(String idsList, UserInfo userInfo) {
		String[] ids = idsList.split(",");
		for (int i = 0; i < ids.length; i++) {
			scmsGoodsInventoryFlowRepository.delete(StringUtils.toLong(ids[i]));
		}
		return RestfulRetUtils.getRetSuccess();
	}

}
