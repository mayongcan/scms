/*
 * Copyright(c) 2018 gimplatform(通用信息管理平台) All rights reserved.
 */
package com.scms.modules.base.service.impl;

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

import com.scms.modules.base.service.ScmsPrintInfoService;
import com.scms.modules.base.entity.ScmsPrintInfo;
import com.scms.modules.base.repository.ScmsPrintInfoRepository;

@Service
public class ScmsPrintInfoServiceImpl implements ScmsPrintInfoService {
	
    @Autowired
    private ScmsPrintInfoRepository scmsPrintInfoRepository;

	@Override
	public JSONObject getList(Pageable page, ScmsPrintInfo scmsPrintInfo, Map<String, Object> params) {
		List<Map<String, Object>> list = scmsPrintInfoRepository.getList(scmsPrintInfo, params, page.getPageNumber(), page.getPageSize());
		int count = scmsPrintInfoRepository.getListCount(scmsPrintInfo, params);
		return RestfulRetUtils.getRetSuccessWithPage(list, count);	
	}

	@Override
	public JSONObject add(Map<String, Object> params, UserInfo userInfo) {
	    ScmsPrintInfo scmsPrintInfo = (ScmsPrintInfo) BeanUtils.mapToBean(params, ScmsPrintInfo.class);
		scmsPrintInfoRepository.save(scmsPrintInfo);
		return RestfulRetUtils.getRetSuccess();
	}

	@Override
	public JSONObject edit(Map<String, Object> params, UserInfo userInfo) {
        ScmsPrintInfo scmsPrintInfo = (ScmsPrintInfo) BeanUtils.mapToBean(params, ScmsPrintInfo.class);
		ScmsPrintInfo scmsPrintInfoInDb = scmsPrintInfoRepository.findOne(scmsPrintInfo.getId());
		if(scmsPrintInfoInDb == null){
			return RestfulRetUtils.getErrorMsg("51006","当前编辑的对象不存在");
		}
		//合并两个javabean
		BeanUtils.mergeBean(scmsPrintInfo, scmsPrintInfoInDb);
		scmsPrintInfoRepository.save(scmsPrintInfoInDb);
		return RestfulRetUtils.getRetSuccess();
	}

	@Override
	public JSONObject del(String idsList, UserInfo userInfo) {
		String[] ids = idsList.split(",");
		for (int i = 0; i < ids.length; i++) {
			scmsPrintInfoRepository.delete(StringUtils.toLong(ids[i]));
		}
		return RestfulRetUtils.getRetSuccess();
	}

}
