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
import com.gimplatform.core.service.DistrictService;
import com.gimplatform.core.utils.BeanUtils;
import com.gimplatform.core.utils.RestfulRetUtils;
import com.gimplatform.core.utils.StringUtils;

import com.scms.modules.customer.service.ScmsCustomerInfoService;
import com.scms.modules.customer.entity.ScmsCustomerInfo;
import com.scms.modules.customer.repository.ScmsCustomerInfoRepository;

@Service
public class ScmsCustomerInfoServiceImpl implements ScmsCustomerInfoService {
	
    @Autowired
    private ScmsCustomerInfoRepository scmsCustomerInfoRepository;

    @Autowired
    private DistrictService districtService;

	@Override
	public JSONObject getList(Pageable page, ScmsCustomerInfo scmsCustomerInfo, Map<String, Object> params) {
		List<Map<String, Object>> list = scmsCustomerInfoRepository.getList(scmsCustomerInfo, params, page.getPageNumber(), page.getPageSize());
		int count = scmsCustomerInfoRepository.getListCount(scmsCustomerInfo, params);
		return RestfulRetUtils.getRetSuccessWithPage(list, count);	
	}

	@Override
	public JSONObject add(Map<String, Object> params, UserInfo userInfo) {
	    ScmsCustomerInfo scmsCustomerInfo = (ScmsCustomerInfo) BeanUtils.mapToBean(params, ScmsCustomerInfo.class);
		scmsCustomerInfo.setIsValid(Constants.IS_VALID_VALID);
		scmsCustomerInfo.setCreateBy(userInfo.getUserId());
		scmsCustomerInfo.setCreateDate(new Date());
        //判断是否需要获取areaCode（app传输过来的就需要获取areaCode）
        if(StringUtils.isBlank(scmsCustomerInfo.getAreaCode()) && !StringUtils.isBlank(scmsCustomerInfo.getAreaName())) {
            scmsCustomerInfo.setAreaCode(districtService.getAreaCode(scmsCustomerInfo.getAreaName()));
        }
		scmsCustomerInfoRepository.save(scmsCustomerInfo);
		return RestfulRetUtils.getRetSuccess();
	}

	@Override
	public JSONObject edit(Map<String, Object> params, UserInfo userInfo) {
        ScmsCustomerInfo scmsCustomerInfo = (ScmsCustomerInfo) BeanUtils.mapToBean(params, ScmsCustomerInfo.class);
		ScmsCustomerInfo scmsCustomerInfoInDb = scmsCustomerInfoRepository.findOne(scmsCustomerInfo.getId());
		if(scmsCustomerInfoInDb == null){
			return RestfulRetUtils.getErrorMsg("51006","当前编辑的对象不存在");
		}
        //判断是否需要获取areaCode（app传输过来的就需要获取areaCode）
        if(StringUtils.isBlank(scmsCustomerInfo.getAreaCode()) && !StringUtils.isBlank(scmsCustomerInfo.getAreaName())) {
            scmsCustomerInfo.setAreaCode(districtService.getAreaCode(scmsCustomerInfo.getAreaName()));
        }
		//合并两个javabean
		BeanUtils.mergeBean(scmsCustomerInfo, scmsCustomerInfoInDb);
		scmsCustomerInfoRepository.save(scmsCustomerInfoInDb);
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
			scmsCustomerInfoRepository.delEntity(Constants.IS_VALID_INVALID, idList);
		}
		return RestfulRetUtils.getRetSuccess();
	}

}
