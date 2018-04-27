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

import com.scms.modules.order.service.ScmsOrderGoodsDetailService;
import com.scms.modules.order.entity.ScmsOrderGoodsDetail;
import com.scms.modules.order.repository.ScmsOrderGoodsDetailRepository;

@Service
public class ScmsOrderGoodsDetailServiceImpl implements ScmsOrderGoodsDetailService {
	
    @Autowired
    private ScmsOrderGoodsDetailRepository scmsOrderGoodsDetailRepository;

	@Override
	public JSONObject getList(Pageable page, ScmsOrderGoodsDetail scmsOrderGoodsDetail, Map<String, Object> params) {
		List<Map<String, Object>> list = scmsOrderGoodsDetailRepository.getList(scmsOrderGoodsDetail, params, page.getPageNumber(), page.getPageSize());
		int count = scmsOrderGoodsDetailRepository.getListCount(scmsOrderGoodsDetail, params);
		return RestfulRetUtils.getRetSuccessWithPage(list, count);	
	}

	@Override
	public JSONObject add(Map<String, Object> params, UserInfo userInfo) {
	    ScmsOrderGoodsDetail scmsOrderGoodsDetail = (ScmsOrderGoodsDetail) BeanUtils.mapToBean(params, ScmsOrderGoodsDetail.class);
	    scmsOrderGoodsDetail.setSendStatus("0");
	    scmsOrderGoodsDetail.setReceiveStatus("0");
	    scmsOrderGoodsDetailRepository.save(scmsOrderGoodsDetail);
		return RestfulRetUtils.getRetSuccess();
	}

	@Override
	public JSONObject edit(Map<String, Object> params, UserInfo userInfo) {
        ScmsOrderGoodsDetail scmsOrderGoodsDetail = (ScmsOrderGoodsDetail) BeanUtils.mapToBean(params, ScmsOrderGoodsDetail.class);
		ScmsOrderGoodsDetail scmsOrderGoodsDetailInDb = scmsOrderGoodsDetailRepository.findOne(scmsOrderGoodsDetail.getId());
		if(scmsOrderGoodsDetailInDb == null){
			return RestfulRetUtils.getErrorMsg("51006","当前编辑的对象不存在");
		}
		//合并两个javabean
		BeanUtils.mergeBean(scmsOrderGoodsDetail, scmsOrderGoodsDetailInDb);
		scmsOrderGoodsDetailRepository.save(scmsOrderGoodsDetailInDb);
		return RestfulRetUtils.getRetSuccess();
	}

	@Override
	public JSONObject del(String idsList, UserInfo userInfo) {
		String[] ids = idsList.split(",");
		for (int i = 0; i < ids.length; i++) {
		    scmsOrderGoodsDetailRepository.delete(StringUtils.toLong(ids[i]));
		}
		return RestfulRetUtils.getRetSuccess();
	}

}
