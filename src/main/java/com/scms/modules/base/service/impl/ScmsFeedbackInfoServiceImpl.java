/*
 * Copyright(c) 2018 gimplatform(通用信息管理平台) All rights reserved.
 */
package com.scms.modules.base.service.impl;

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

import com.scms.modules.base.service.ScmsFeedbackInfoService;
import com.scms.modules.base.entity.ScmsFeedbackInfo;
import com.scms.modules.base.repository.ScmsFeedbackInfoRepository;

@Service
public class ScmsFeedbackInfoServiceImpl implements ScmsFeedbackInfoService {
	
    @Autowired
    private ScmsFeedbackInfoRepository scmsFeedbackInfoRepository;

	@Override
	public JSONObject getList(Pageable page, ScmsFeedbackInfo scmsFeedbackInfo, Map<String, Object> params) {
		List<Map<String, Object>> list = scmsFeedbackInfoRepository.getList(scmsFeedbackInfo, params, page.getPageNumber(), page.getPageSize());
		int count = scmsFeedbackInfoRepository.getListCount(scmsFeedbackInfo, params);
		return RestfulRetUtils.getRetSuccessWithPage(list, count);	
	}

	@Override
	public JSONObject add(Map<String, Object> params, UserInfo userInfo) {
	    ScmsFeedbackInfo scmsFeedbackInfo = (ScmsFeedbackInfo) BeanUtils.mapToBean(params, ScmsFeedbackInfo.class);
		scmsFeedbackInfo.setCreateDate(new Date());
		scmsFeedbackInfo.setIsRead("0");
		scmsFeedbackInfo.setIsHandle("0");
		scmsFeedbackInfoRepository.save(scmsFeedbackInfo);
		return RestfulRetUtils.getRetSuccess();
	}

	@Override
	public JSONObject edit(Map<String, Object> params, UserInfo userInfo) {
        ScmsFeedbackInfo scmsFeedbackInfo = (ScmsFeedbackInfo) BeanUtils.mapToBean(params, ScmsFeedbackInfo.class);
		ScmsFeedbackInfo scmsFeedbackInfoInDb = scmsFeedbackInfoRepository.findOne(scmsFeedbackInfo.getId());
		if(scmsFeedbackInfoInDb == null){
			return RestfulRetUtils.getErrorMsg("51006","当前编辑的对象不存在");
		}
		//合并两个javabean
		BeanUtils.mergeBean(scmsFeedbackInfo, scmsFeedbackInfoInDb);
		scmsFeedbackInfoRepository.save(scmsFeedbackInfoInDb);
		return RestfulRetUtils.getRetSuccess();
	}

	@Override
	public JSONObject del(String idsList, UserInfo userInfo) {
		String[] ids = idsList.split(",");
		for (int i = 0; i < ids.length; i++) {
			scmsFeedbackInfoRepository.delete(StringUtils.toLong(ids[i]));
		}
		return RestfulRetUtils.getRetSuccess();
	}

}
