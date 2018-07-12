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

import com.scms.modules.base.service.ScmsSizeGroupService;
import com.scms.modules.base.entity.ScmsSizeGroup;
import com.scms.modules.base.repository.ScmsSizeGroupRepository;

@Service
public class ScmsSizeGroupServiceImpl implements ScmsSizeGroupService {
	
    @Autowired
    private ScmsSizeGroupRepository scmsSizeGroupRepository;

	@Override
	public JSONObject getList(Pageable page, ScmsSizeGroup scmsSizeGroup, Map<String, Object> params) {
		List<Map<String, Object>> list = scmsSizeGroupRepository.getList(scmsSizeGroup, params, page.getPageNumber(), page.getPageSize());
		int count = scmsSizeGroupRepository.getListCount(scmsSizeGroup, params);
		return RestfulRetUtils.getRetSuccessWithPage(list, count);	
	}

	@Override
	public JSONObject add(Map<String, Object> params, UserInfo userInfo) {
	    ScmsSizeGroup scmsSizeGroup = (ScmsSizeGroup) BeanUtils.mapToBean(params, ScmsSizeGroup.class);
		scmsSizeGroupRepository.save(scmsSizeGroup);
		return RestfulRetUtils.getRetSuccess();
	}

	@Override
	public JSONObject edit(Map<String, Object> params, UserInfo userInfo) {
        ScmsSizeGroup scmsSizeGroup = (ScmsSizeGroup) BeanUtils.mapToBean(params, ScmsSizeGroup.class);
		ScmsSizeGroup scmsSizeGroupInDb = scmsSizeGroupRepository.findOne(scmsSizeGroup.getId());
		if(scmsSizeGroupInDb == null){
			return RestfulRetUtils.getErrorMsg("51006","当前编辑的对象不存在");
		}
		//合并两个javabean
		BeanUtils.mergeBean(scmsSizeGroup, scmsSizeGroupInDb);
		scmsSizeGroupRepository.save(scmsSizeGroupInDb);
		return RestfulRetUtils.getRetSuccess();
	}

	@Override
	public JSONObject del(String idsList, UserInfo userInfo) {
		String[] ids = idsList.split(",");
		for (int i = 0; i < ids.length; i++) {
			scmsSizeGroupRepository.delete(StringUtils.toLong(ids[i]));
		}
		return RestfulRetUtils.getRetSuccess();
	}

}
