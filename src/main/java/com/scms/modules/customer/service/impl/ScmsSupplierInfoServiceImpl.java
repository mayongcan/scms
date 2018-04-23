/*
 * Copyright(c) 2018 gimplatform(通用信息管理平台) All rights reserved.
 */
package com.scms.modules.customer.service.impl;

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

import com.scms.modules.customer.service.ScmsSupplierInfoService;
import com.scms.modules.customer.entity.ScmsSupplierInfo;
import com.scms.modules.customer.repository.ScmsSupplierInfoRepository;

@Service
public class ScmsSupplierInfoServiceImpl implements ScmsSupplierInfoService {
	
    @Autowired
    private ScmsSupplierInfoRepository scmsSupplierInfoRepository;

	@Override
	public JSONObject getList(Pageable page, ScmsSupplierInfo scmsSupplierInfo, Map<String, Object> params) {
		List<Map<String, Object>> list = scmsSupplierInfoRepository.getList(scmsSupplierInfo, params, page.getPageNumber(), page.getPageSize());
		int count = scmsSupplierInfoRepository.getListCount(scmsSupplierInfo, params);
		return RestfulRetUtils.getRetSuccessWithPage(list, count);	
	}

	@Override
	public JSONObject add(Map<String, Object> params, UserInfo userInfo) {
	    ScmsSupplierInfo scmsSupplierInfo = (ScmsSupplierInfo) BeanUtils.mapToBean(params, ScmsSupplierInfo.class);
		scmsSupplierInfo.setIsValid(Constants.IS_VALID_VALID);
		scmsSupplierInfo.setCreateBy(userInfo.getUserId());
		scmsSupplierInfo.setCreateDate(new Date());
		scmsSupplierInfoRepository.save(scmsSupplierInfo);
		return RestfulRetUtils.getRetSuccess();
	}

	@Override
	public JSONObject edit(Map<String, Object> params, UserInfo userInfo) {
        ScmsSupplierInfo scmsSupplierInfo = (ScmsSupplierInfo) BeanUtils.mapToBean(params, ScmsSupplierInfo.class);
		ScmsSupplierInfo scmsSupplierInfoInDb = scmsSupplierInfoRepository.findOne(scmsSupplierInfo.getId());
		if(scmsSupplierInfoInDb == null){
			return RestfulRetUtils.getErrorMsg("51006","当前编辑的对象不存在");
		}
		//合并两个javabean
		BeanUtils.mergeBean(scmsSupplierInfo, scmsSupplierInfoInDb);
		scmsSupplierInfoRepository.save(scmsSupplierInfoInDb);
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
			scmsSupplierInfoRepository.delEntity(Constants.IS_VALID_INVALID, idList);
		}
		return RestfulRetUtils.getRetSuccess();
	}

}
