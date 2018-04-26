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

import com.scms.modules.order.service.ScmsInventoryTransferGoodsService;
import com.scms.modules.order.entity.ScmsInventoryTransferGoods;
import com.scms.modules.order.repository.ScmsInventoryTransferGoodsRepository;

@Service
public class ScmsInventoryTransferGoodsServiceImpl implements ScmsInventoryTransferGoodsService {
	
    @Autowired
    private ScmsInventoryTransferGoodsRepository scmsInventoryTransferGoodsRepository;

	@Override
	public JSONObject getList(Pageable page, ScmsInventoryTransferGoods scmsInventoryTransferGoods, Map<String, Object> params) {
		List<Map<String, Object>> list = scmsInventoryTransferGoodsRepository.getList(scmsInventoryTransferGoods, params, page.getPageNumber(), page.getPageSize());
		int count = scmsInventoryTransferGoodsRepository.getListCount(scmsInventoryTransferGoods, params);
		return RestfulRetUtils.getRetSuccessWithPage(list, count);	
	}

	@Override
	public JSONObject add(Map<String, Object> params, UserInfo userInfo) {
	    ScmsInventoryTransferGoods scmsInventoryTransferGoods = (ScmsInventoryTransferGoods) BeanUtils.mapToBean(params, ScmsInventoryTransferGoods.class);
		scmsInventoryTransferGoodsRepository.save(scmsInventoryTransferGoods);
		return RestfulRetUtils.getRetSuccess();
	}

	@Override
	public JSONObject edit(Map<String, Object> params, UserInfo userInfo) {
        ScmsInventoryTransferGoods scmsInventoryTransferGoods = (ScmsInventoryTransferGoods) BeanUtils.mapToBean(params, ScmsInventoryTransferGoods.class);
		ScmsInventoryTransferGoods scmsInventoryTransferGoodsInDb = scmsInventoryTransferGoodsRepository.findOne(scmsInventoryTransferGoods.getId());
		if(scmsInventoryTransferGoodsInDb == null){
			return RestfulRetUtils.getErrorMsg("51006","当前编辑的对象不存在");
		}
		//合并两个javabean
		BeanUtils.mergeBean(scmsInventoryTransferGoods, scmsInventoryTransferGoodsInDb);
		scmsInventoryTransferGoodsRepository.save(scmsInventoryTransferGoodsInDb);
		return RestfulRetUtils.getRetSuccess();
	}

	@Override
	public JSONObject del(String idsList, UserInfo userInfo) {
		String[] ids = idsList.split(",");
		for (int i = 0; i < ids.length; i++) {
			scmsInventoryTransferGoodsRepository.delete(StringUtils.toLong(ids[i]));
		}
		return RestfulRetUtils.getRetSuccess();
	}

}
