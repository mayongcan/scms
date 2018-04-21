/*
 * Copyright(c) 2018 gimplatform(通用信息管理平台) All rights reserved.
 */
package com.scms.modules.order.service.impl;

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

import com.scms.modules.order.service.ScmsOrderModifyLogService;
import com.scms.modules.order.entity.ScmsOrderModifyLog;
import com.scms.modules.order.repository.ScmsOrderModifyLogRepository;

@Service
public class ScmsOrderModifyLogServiceImpl implements ScmsOrderModifyLogService {
	
    @Autowired
    private ScmsOrderModifyLogRepository scmsOrderModifyLogRepository;

	@Override
	public JSONObject getList(Pageable page, ScmsOrderModifyLog scmsOrderModifyLog, Map<String, Object> params) {
		List<Map<String, Object>> list = scmsOrderModifyLogRepository.getList(scmsOrderModifyLog, params, page.getPageNumber(), page.getPageSize());
		int count = scmsOrderModifyLogRepository.getListCount(scmsOrderModifyLog, params);
		return RestfulRetUtils.getRetSuccessWithPage(list, count);	
	}

	@Override
	public JSONObject add(Map<String, Object> params, UserInfo userInfo) {
	    ScmsOrderModifyLog scmsOrderModifyLog = (ScmsOrderModifyLog) BeanUtils.mapToBean(params, ScmsOrderModifyLog.class);
		scmsOrderModifyLog.setModifyBy(userInfo.getUserId());
		scmsOrderModifyLog.setModifyDate(new Date());
		scmsOrderModifyLogRepository.save(scmsOrderModifyLog);
		return RestfulRetUtils.getRetSuccess();
	}

	@Override
	public JSONObject edit(Map<String, Object> params, UserInfo userInfo) {
        ScmsOrderModifyLog scmsOrderModifyLog = (ScmsOrderModifyLog) BeanUtils.mapToBean(params, ScmsOrderModifyLog.class);
		ScmsOrderModifyLog scmsOrderModifyLogInDb = scmsOrderModifyLogRepository.findOne(scmsOrderModifyLog.getId());
		if(scmsOrderModifyLogInDb == null){
			return RestfulRetUtils.getErrorMsg("51006","当前编辑的对象不存在");
		}
		//合并两个javabean
		BeanUtils.mergeBean(scmsOrderModifyLog, scmsOrderModifyLogInDb);
		scmsOrderModifyLogInDb.setModifyBy(userInfo.getUserId());
		scmsOrderModifyLogInDb.setModifyDate(new Date());
		scmsOrderModifyLogRepository.save(scmsOrderModifyLogInDb);
		return RestfulRetUtils.getRetSuccess();
	}

	@Override
	public JSONObject del(String idsList, UserInfo userInfo) {
		String[] ids = idsList.split(",");
		for (int i = 0; i < ids.length; i++) {
			scmsOrderModifyLogRepository.delete(StringUtils.toLong(ids[i]));
		}
		return RestfulRetUtils.getRetSuccess();
	}

}
