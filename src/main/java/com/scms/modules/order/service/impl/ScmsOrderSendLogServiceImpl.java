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

import com.scms.modules.order.service.ScmsOrderSendLogService;
import com.scms.modules.order.entity.ScmsOrderSendLog;
import com.scms.modules.order.repository.ScmsOrderSendLogRepository;

@Service
public class ScmsOrderSendLogServiceImpl implements ScmsOrderSendLogService {
	
    @Autowired
    private ScmsOrderSendLogRepository scmsOrderSendLogRepository;

	@Override
	public JSONObject getList(Pageable page, ScmsOrderSendLog scmsOrderSendLog, Map<String, Object> params) {
		List<Map<String, Object>> list = scmsOrderSendLogRepository.getList(scmsOrderSendLog, params, page.getPageNumber(), page.getPageSize());
		int count = scmsOrderSendLogRepository.getListCount(scmsOrderSendLog, params);
		return RestfulRetUtils.getRetSuccessWithPage(list, count);	
	}

	@Override
	public JSONObject add(Map<String, Object> params, UserInfo userInfo) {
	    ScmsOrderSendLog scmsOrderSendLog = (ScmsOrderSendLog) BeanUtils.mapToBean(params, ScmsOrderSendLog.class);
		scmsOrderSendLogRepository.save(scmsOrderSendLog);
		return RestfulRetUtils.getRetSuccess();
	}

	@Override
	public JSONObject edit(Map<String, Object> params, UserInfo userInfo) {
        ScmsOrderSendLog scmsOrderSendLog = (ScmsOrderSendLog) BeanUtils.mapToBean(params, ScmsOrderSendLog.class);
		ScmsOrderSendLog scmsOrderSendLogInDb = scmsOrderSendLogRepository.findOne(scmsOrderSendLog.getId());
		if(scmsOrderSendLogInDb == null){
			return RestfulRetUtils.getErrorMsg("51006","当前编辑的对象不存在");
		}
		//合并两个javabean
		BeanUtils.mergeBean(scmsOrderSendLog, scmsOrderSendLogInDb);
		scmsOrderSendLogRepository.save(scmsOrderSendLogInDb);
		return RestfulRetUtils.getRetSuccess();
	}

	@Override
	public JSONObject del(String idsList, UserInfo userInfo) {
		String[] ids = idsList.split(",");
		for (int i = 0; i < ids.length; i++) {
			scmsOrderSendLogRepository.delete(StringUtils.toLong(ids[i]));
		}
		return RestfulRetUtils.getRetSuccess();
	}

}
