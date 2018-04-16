/*
 * Copyright(c) 2018 gimplatform(通用信息管理平台) All rights reserved.
 */
package com.scms.modules.base.service.impl;

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

import com.scms.modules.base.service.ScmsShopInfoService;
import com.scms.modules.base.entity.ScmsShopInfo;
import com.scms.modules.base.repository.ScmsShopInfoRepository;

@Service
public class ScmsShopInfoServiceImpl implements ScmsShopInfoService {
	
    @Autowired
    private ScmsShopInfoRepository scmsShopInfoRepository;

	@Override
	public JSONObject getList(Pageable page, ScmsShopInfo scmsShopInfo, Map<String, Object> params) {
		List<Map<String, Object>> list = scmsShopInfoRepository.getList(scmsShopInfo, params, page.getPageNumber(), page.getPageSize());
		int count = scmsShopInfoRepository.getListCount(scmsShopInfo, params);
		return RestfulRetUtils.getRetSuccessWithPage(list, count);	
	}

	@Override
	public JSONObject add(Map<String, Object> params, UserInfo userInfo) {
	    ScmsShopInfo scmsShopInfo = (ScmsShopInfo) BeanUtils.mapToBean(params, ScmsShopInfo.class);
		scmsShopInfo.setIsValid(Constants.IS_VALID_VALID);
		scmsShopInfo.setCreateBy(userInfo.getUserId());
		scmsShopInfo.setCreateDate(new Date());
		scmsShopInfoRepository.save(scmsShopInfo);
		return RestfulRetUtils.getRetSuccess();
	}

	@Override
	public JSONObject edit(Map<String, Object> params, UserInfo userInfo) {
        ScmsShopInfo scmsShopInfo = (ScmsShopInfo) BeanUtils.mapToBean(params, ScmsShopInfo.class);
		ScmsShopInfo scmsShopInfoInDb = scmsShopInfoRepository.findOne(scmsShopInfo.getId());
		if(scmsShopInfoInDb == null){
			return RestfulRetUtils.getErrorMsg("51006","当前编辑的对象不存在");
		}
		//合并两个javabean
		BeanUtils.mergeBean(scmsShopInfo, scmsShopInfoInDb);
		scmsShopInfoRepository.save(scmsShopInfoInDb);
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
			scmsShopInfoRepository.delEntity(Constants.IS_VALID_INVALID, idList);
		}
		return RestfulRetUtils.getRetSuccess();
	}

    @Override
    public JSONObject getShopKeyVal(Long merchantsId) {
        return RestfulRetUtils.getRetSuccess(scmsShopInfoRepository.getShopKeyVal(merchantsId));
    }

}
