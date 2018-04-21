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

import com.scms.modules.order.service.ScmsOrderGoodsService;
import com.scms.modules.order.entity.ScmsOrderGoods;
import com.scms.modules.order.repository.ScmsOrderGoodsRepository;

@Service
public class ScmsOrderGoodsServiceImpl implements ScmsOrderGoodsService {
	
    @Autowired
    private ScmsOrderGoodsRepository scmsOrderGoodsRepository;

	@Override
	public JSONObject getList(Pageable page, ScmsOrderGoods scmsOrderGoods, Map<String, Object> params) {
		List<Map<String, Object>> list = scmsOrderGoodsRepository.getList(scmsOrderGoods, params, page.getPageNumber(), page.getPageSize());
		int count = scmsOrderGoodsRepository.getListCount(scmsOrderGoods, params);
		return RestfulRetUtils.getRetSuccessWithPage(list, count);	
	}

	@Override
	public JSONObject add(Map<String, Object> params, UserInfo userInfo) {
	    ScmsOrderGoods scmsOrderGoods = (ScmsOrderGoods) BeanUtils.mapToBean(params, ScmsOrderGoods.class);
		scmsOrderGoodsRepository.save(scmsOrderGoods);
		return RestfulRetUtils.getRetSuccess();
	}

	@Override
	public JSONObject edit(Map<String, Object> params, UserInfo userInfo) {
        ScmsOrderGoods scmsOrderGoods = (ScmsOrderGoods) BeanUtils.mapToBean(params, ScmsOrderGoods.class);
		ScmsOrderGoods scmsOrderGoodsInDb = scmsOrderGoodsRepository.findOne(scmsOrderGoods.getId());
		if(scmsOrderGoodsInDb == null){
			return RestfulRetUtils.getErrorMsg("51006","当前编辑的对象不存在");
		}
		//合并两个javabean
		BeanUtils.mergeBean(scmsOrderGoods, scmsOrderGoodsInDb);
		scmsOrderGoodsRepository.save(scmsOrderGoodsInDb);
		return RestfulRetUtils.getRetSuccess();
	}

	@Override
	public JSONObject del(String idsList, UserInfo userInfo) {
		String[] ids = idsList.split(",");
		for (int i = 0; i < ids.length; i++) {
			scmsOrderGoodsRepository.delete(StringUtils.toLong(ids[i]));
		}
		return RestfulRetUtils.getRetSuccess();
	}

}
