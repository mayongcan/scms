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

import com.scms.modules.order.service.ScmsInventoryTransferGoodsDetailService;
import com.scms.modules.order.entity.ScmsInventoryTransferGoodsDetail;
import com.scms.modules.order.repository.ScmsInventoryTransferGoodsDetailRepository;

@Service
public class ScmsInventoryTransferGoodsDetailServiceImpl implements ScmsInventoryTransferGoodsDetailService {
	
    @Autowired
    private ScmsInventoryTransferGoodsDetailRepository scmsInventoryTransferGoodsDetailRepository;

	@Override
	public JSONObject getList(Pageable page, ScmsInventoryTransferGoodsDetail scmsInventoryTransferGoodsDetail, Map<String, Object> params) {
		List<Map<String, Object>> list = scmsInventoryTransferGoodsDetailRepository.getList(scmsInventoryTransferGoodsDetail, params, page.getPageNumber(), page.getPageSize());
		int count = scmsInventoryTransferGoodsDetailRepository.getListCount(scmsInventoryTransferGoodsDetail, params);
		return RestfulRetUtils.getRetSuccessWithPage(list, count);	
	}

	@Override
	public JSONObject add(Map<String, Object> params, UserInfo userInfo) {
	    ScmsInventoryTransferGoodsDetail scmsInventoryTransferGoodsDetail = (ScmsInventoryTransferGoodsDetail) BeanUtils.mapToBean(params, ScmsInventoryTransferGoodsDetail.class);
	    scmsInventoryTransferGoodsDetailRepository.save(scmsInventoryTransferGoodsDetail);
		return RestfulRetUtils.getRetSuccess();
	}

	@Override
	public JSONObject edit(Map<String, Object> params, UserInfo userInfo) {
        ScmsInventoryTransferGoodsDetail scmsInventoryTransferGoodsDetail = (ScmsInventoryTransferGoodsDetail) BeanUtils.mapToBean(params, ScmsInventoryTransferGoodsDetail.class);
		ScmsInventoryTransferGoodsDetail scmsInventoryTransferGoodsDetailInDb = scmsInventoryTransferGoodsDetailRepository.findOne(scmsInventoryTransferGoodsDetail.getId());
		if(scmsInventoryTransferGoodsDetailInDb == null){
			return RestfulRetUtils.getErrorMsg("51006","当前编辑的对象不存在");
		}
		//合并两个javabean
		BeanUtils.mergeBean(scmsInventoryTransferGoodsDetail, scmsInventoryTransferGoodsDetailInDb);
		scmsInventoryTransferGoodsDetailRepository.save(scmsInventoryTransferGoodsDetailInDb);
		return RestfulRetUtils.getRetSuccess();
	}

	@Override
	public JSONObject del(String idsList, UserInfo userInfo) {
		String[] ids = idsList.split(",");
		for (int i = 0; i < ids.length; i++) {
		    scmsInventoryTransferGoodsDetailRepository.delete(StringUtils.toLong(ids[i]));
		}
		return RestfulRetUtils.getRetSuccess();
	}

}
