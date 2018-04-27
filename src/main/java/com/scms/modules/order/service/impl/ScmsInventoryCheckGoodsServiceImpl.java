/*
 * Copyright(c) 2018 gimplatform(通用信息管理平台) All rights reserved.
 */
package com.scms.modules.order.service.impl;

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

import com.scms.modules.order.service.ScmsInventoryCheckGoodsService;
import com.scms.modules.order.entity.ScmsInventoryCheckGoods;
import com.scms.modules.order.repository.ScmsInventoryCheckGoodsRepository;

@Service
public class ScmsInventoryCheckGoodsServiceImpl implements ScmsInventoryCheckGoodsService {
	
    @Autowired
    private ScmsInventoryCheckGoodsRepository scmsInventoryCheckGoodsRepository;

	@Override
	public JSONObject getList(Pageable page, ScmsInventoryCheckGoods scmsInventoryCheckGoods, Map<String, Object> params) {
		List<Map<String, Object>> list = scmsInventoryCheckGoodsRepository.getList(scmsInventoryCheckGoods, params, page.getPageNumber(), page.getPageSize());
		int count = scmsInventoryCheckGoodsRepository.getListCount(scmsInventoryCheckGoods, params);
		return RestfulRetUtils.getRetSuccessWithPage(list, count);	
	}

	@Override
	public JSONObject add(Map<String, Object> params, UserInfo userInfo) {
	    ScmsInventoryCheckGoods scmsInventoryCheckGoods = (ScmsInventoryCheckGoods) BeanUtils.mapToBean(params, ScmsInventoryCheckGoods.class);
		scmsInventoryCheckGoodsRepository.save(scmsInventoryCheckGoods);
		return RestfulRetUtils.getRetSuccess();
	}

	@Override
	public JSONObject edit(Map<String, Object> params, UserInfo userInfo) {
        ScmsInventoryCheckGoods scmsInventoryCheckGoods = (ScmsInventoryCheckGoods) BeanUtils.mapToBean(params, ScmsInventoryCheckGoods.class);
		ScmsInventoryCheckGoods scmsInventoryCheckGoodsInDb = scmsInventoryCheckGoodsRepository.findOne(scmsInventoryCheckGoods.getId());
		if(scmsInventoryCheckGoodsInDb == null){
			return RestfulRetUtils.getErrorMsg("51006","当前编辑的对象不存在");
		}
		//合并两个javabean
		BeanUtils.mergeBean(scmsInventoryCheckGoods, scmsInventoryCheckGoodsInDb);
		scmsInventoryCheckGoodsRepository.save(scmsInventoryCheckGoodsInDb);
		return RestfulRetUtils.getRetSuccess();
	}

	@Override
	public JSONObject del(String idsList, UserInfo userInfo) {
		String[] ids = idsList.split(",");
		for (int i = 0; i < ids.length; i++) {
			scmsInventoryCheckGoodsRepository.delete(StringUtils.toLong(ids[i]));
		}
		return RestfulRetUtils.getRetSuccess();
	}

}
