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

import com.scms.modules.order.service.ScmsOrderPayService;
import com.scms.modules.order.entity.ScmsOrderPay;
import com.scms.modules.order.repository.ScmsOrderPayRepository;

@Service
public class ScmsOrderPayServiceImpl implements ScmsOrderPayService {
	
    @Autowired
    private ScmsOrderPayRepository scmsOrderPayRepository;

	@Override
	public JSONObject getList(Pageable page, ScmsOrderPay scmsOrderPay, Map<String, Object> params) {
		List<Map<String, Object>> list = scmsOrderPayRepository.getList(scmsOrderPay, params, page.getPageNumber(), page.getPageSize());
		int count = scmsOrderPayRepository.getListCount(scmsOrderPay, params);
		return RestfulRetUtils.getRetSuccessWithPage(list, count);	
	}

	@Override
	public JSONObject add(Map<String, Object> params, UserInfo userInfo) {
	    ScmsOrderPay scmsOrderPay = (ScmsOrderPay) BeanUtils.mapToBean(params, ScmsOrderPay.class);
		scmsOrderPayRepository.save(scmsOrderPay);
		return RestfulRetUtils.getRetSuccess();
	}

	@Override
	public JSONObject edit(Map<String, Object> params, UserInfo userInfo) {
        ScmsOrderPay scmsOrderPay = (ScmsOrderPay) BeanUtils.mapToBean(params, ScmsOrderPay.class);
		ScmsOrderPay scmsOrderPayInDb = scmsOrderPayRepository.findOne(scmsOrderPay.getId());
		if(scmsOrderPayInDb == null){
			return RestfulRetUtils.getErrorMsg("51006","当前编辑的对象不存在");
		}
		//合并两个javabean
		BeanUtils.mergeBean(scmsOrderPay, scmsOrderPayInDb);
		scmsOrderPayRepository.save(scmsOrderPayInDb);
		return RestfulRetUtils.getRetSuccess();
	}

	@Override
	public JSONObject del(String idsList, UserInfo userInfo) {
		String[] ids = idsList.split(",");
		for (int i = 0; i < ids.length; i++) {
			scmsOrderPayRepository.delete(StringUtils.toLong(ids[i]));
		}
		return RestfulRetUtils.getRetSuccess();
	}

}
