/*
 * Copyright(c) 2018 gimplatform(通用信息管理平台) All rights reserved.
 */
package com.scms.modules.user.service.impl;

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

import com.scms.modules.user.service.ScmsUserPointService;
import com.scms.modules.user.entity.ScmsUserPoint;
import com.scms.modules.user.repository.ScmsUserPointRepository;

@Service
public class ScmsUserPointServiceImpl implements ScmsUserPointService {
	
    @Autowired
    private ScmsUserPointRepository scmsUserPointRepository;

	@Override
	public JSONObject getList(Pageable page, ScmsUserPoint scmsUserPoint, Map<String, Object> params) {
		List<Map<String, Object>> list = scmsUserPointRepository.getList(scmsUserPoint, params, page.getPageNumber(), page.getPageSize());
		int count = scmsUserPointRepository.getListCount(scmsUserPoint, params);
		return RestfulRetUtils.getRetSuccessWithPage(list, count);	
	}

	@Override
	public JSONObject add(Map<String, Object> params, UserInfo userInfo) {
	    ScmsUserPoint scmsUserPoint = (ScmsUserPoint) BeanUtils.mapToBean(params, ScmsUserPoint.class);
		scmsUserPoint.setIsValid(Constants.IS_VALID_VALID);
		scmsUserPoint.setCreateDate(new Date());
		scmsUserPointRepository.save(scmsUserPoint);
		return RestfulRetUtils.getRetSuccess();
	}

	@Override
	public JSONObject edit(Map<String, Object> params, UserInfo userInfo) {
        ScmsUserPoint scmsUserPoint = (ScmsUserPoint) BeanUtils.mapToBean(params, ScmsUserPoint.class);
		ScmsUserPoint scmsUserPointInDb = scmsUserPointRepository.findOne(scmsUserPoint.getId());
		if(scmsUserPointInDb == null){
			return RestfulRetUtils.getErrorMsg("51006","当前编辑的对象不存在");
		}
		//合并两个javabean
		BeanUtils.mergeBean(scmsUserPoint, scmsUserPointInDb);
		scmsUserPointRepository.save(scmsUserPointInDb);
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
			scmsUserPointRepository.delEntity(Constants.IS_VALID_INVALID, idList);
		}
		return RestfulRetUtils.getRetSuccess();
	}

}
