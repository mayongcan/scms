/*
 * Copyright(c) 2018 gimplatform(通用信息管理平台) All rights reserved.
 */
package com.scms.modules.order.service.impl;

import java.util.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;

import com.alibaba.fastjson.JSONObject;
import com.gimplatform.core.common.Constants;
import com.gimplatform.core.entity.UserInfo;
import com.gimplatform.core.utils.BeanUtils;
import com.gimplatform.core.utils.RestfulRetUtils;
import com.gimplatform.core.utils.StringUtils;

import com.scms.modules.order.service.ScmsOrderInfoService;
import com.scms.modules.order.entity.ScmsOrderInfo;
import com.scms.modules.order.repository.ScmsOrderInfoRepository;

@Service
public class ScmsOrderInfoServiceImpl implements ScmsOrderInfoService {
	
    @Autowired
    private ScmsOrderInfoRepository scmsOrderInfoRepository;

	@Override
	public JSONObject getList(Pageable page, ScmsOrderInfo scmsOrderInfo, Map<String, Object> params) {
		List<Map<String, Object>> list = scmsOrderInfoRepository.getList(scmsOrderInfo, params, page.getPageNumber(), page.getPageSize());
		int count = scmsOrderInfoRepository.getListCount(scmsOrderInfo, params);
		return RestfulRetUtils.getRetSuccessWithPage(list, count);	
	}

	@Override
	public JSONObject add(Map<String, Object> params, UserInfo userInfo) {
	    ScmsOrderInfo scmsOrderInfo = (ScmsOrderInfo) BeanUtils.mapToBean(params, ScmsOrderInfo.class);
		scmsOrderInfo.setIsValid(Constants.IS_VALID_VALID);
		scmsOrderInfo.setCreateBy(userInfo.getUserId());
		scmsOrderInfo.setCreateDate(new Date());
		scmsOrderInfoRepository.save(scmsOrderInfo);
		return RestfulRetUtils.getRetSuccess();
	}

	@Override
	public JSONObject edit(Map<String, Object> params, UserInfo userInfo) {
        ScmsOrderInfo scmsOrderInfo = (ScmsOrderInfo) BeanUtils.mapToBean(params, ScmsOrderInfo.class);
		ScmsOrderInfo scmsOrderInfoInDb = scmsOrderInfoRepository.findOne(scmsOrderInfo.getId());
		if(scmsOrderInfoInDb == null){
			return RestfulRetUtils.getErrorMsg("51006","当前编辑的对象不存在");
		}
		//合并两个javabean
		BeanUtils.mergeBean(scmsOrderInfo, scmsOrderInfoInDb);
		scmsOrderInfoRepository.save(scmsOrderInfoInDb);
		return RestfulRetUtils.getRetSuccess();
	}

	@Override
	public JSONObject del(String idsList, UserInfo userInfo) {
		String[] ids = idsList.split(",");
		//判断是否需要移除
		List<Long> idList = new ArrayList<Long>();
		for (int i = 0; i < ids.length; i++) {
			idList.add(StringUtils.toLong(ids[i]));
		}
		//批量更新（设置IsValid 为N）
		if(idList.size() > 0){
			scmsOrderInfoRepository.delEntity(Constants.IS_VALID_INVALID, idList);
		}
		return RestfulRetUtils.getRetSuccess();
	}

}
