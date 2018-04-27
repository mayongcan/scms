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

import com.scms.modules.order.service.ScmsInventoryCheckGoodsDetailService;
import com.scms.modules.order.entity.ScmsInventoryCheckGoodsDetail;
import com.scms.modules.order.repository.ScmsInventoryCheckGoodsDetailRepository;

@Service
public class ScmsInventoryCheckGoodsDetailServiceImpl implements ScmsInventoryCheckGoodsDetailService {
	
    @Autowired
    private ScmsInventoryCheckGoodsDetailRepository scmsInventoryCheckGoodsDetailRepository;

	@Override
	public JSONObject getList(Pageable page, ScmsInventoryCheckGoodsDetail scmsInventoryCheckGoodsDetail, Map<String, Object> params) {
		List<Map<String, Object>> list = scmsInventoryCheckGoodsDetailRepository.getList(scmsInventoryCheckGoodsDetail, params, page.getPageNumber(), page.getPageSize());
		int count = scmsInventoryCheckGoodsDetailRepository.getListCount(scmsInventoryCheckGoodsDetail, params);
		return RestfulRetUtils.getRetSuccessWithPage(list, count);	
	}

	@Override
	public JSONObject add(Map<String, Object> params, UserInfo userInfo) {
	    ScmsInventoryCheckGoodsDetail scmsInventoryCheckGoodsDetail = (ScmsInventoryCheckGoodsDetail) BeanUtils.mapToBean(params, ScmsInventoryCheckGoodsDetail.class);
		scmsInventoryCheckGoodsDetailRepository.save(scmsInventoryCheckGoodsDetail);
		return RestfulRetUtils.getRetSuccess();
	}

	@Override
	public JSONObject edit(Map<String, Object> params, UserInfo userInfo) {
        ScmsInventoryCheckGoodsDetail scmsInventoryCheckGoodsDetail = (ScmsInventoryCheckGoodsDetail) BeanUtils.mapToBean(params, ScmsInventoryCheckGoodsDetail.class);
		ScmsInventoryCheckGoodsDetail scmsInventoryCheckGoodsDetailInDb = scmsInventoryCheckGoodsDetailRepository.findOne(scmsInventoryCheckGoodsDetail.getId());
		if(scmsInventoryCheckGoodsDetailInDb == null){
			return RestfulRetUtils.getErrorMsg("51006","当前编辑的对象不存在");
		}
		//合并两个javabean
		BeanUtils.mergeBean(scmsInventoryCheckGoodsDetail, scmsInventoryCheckGoodsDetailInDb);
		scmsInventoryCheckGoodsDetailRepository.save(scmsInventoryCheckGoodsDetailInDb);
		return RestfulRetUtils.getRetSuccess();
	}

	@Override
	public JSONObject del(String idsList, UserInfo userInfo) {
		String[] ids = idsList.split(",");
		for (int i = 0; i < ids.length; i++) {
			scmsInventoryCheckGoodsDetailRepository.delete(StringUtils.toLong(ids[i]));
		}
		return RestfulRetUtils.getRetSuccess();
	}

}
