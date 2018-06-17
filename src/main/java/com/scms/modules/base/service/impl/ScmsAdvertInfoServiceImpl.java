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

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.gimplatform.core.entity.UserInfo;
import com.gimplatform.core.utils.BeanUtils;
import com.gimplatform.core.utils.RestfulRetUtils;
import com.gimplatform.core.utils.StringUtils;

import com.scms.modules.base.service.ScmsAdvertInfoService;
import com.scms.modules.base.entity.ScmsAdvertInfo;
import com.scms.modules.base.repository.ScmsAdvertInfoRepository;

@Service
public class ScmsAdvertInfoServiceImpl implements ScmsAdvertInfoService {
	
    @Autowired
    private ScmsAdvertInfoRepository scmsAdvertInfoRepository;

	@Override
	public JSONObject getList(Pageable page, ScmsAdvertInfo scmsAdvertInfo, Map<String, Object> params) {
		List<Map<String, Object>> list = scmsAdvertInfoRepository.getList(scmsAdvertInfo, params, page.getPageNumber(), page.getPageSize());
		int count = scmsAdvertInfoRepository.getListCount(scmsAdvertInfo, params);
		return RestfulRetUtils.getRetSuccessWithPage(list, count);	
	}

	@Override
	public JSONObject add(Map<String, Object> params, UserInfo userInfo) {
	    ScmsAdvertInfo scmsAdvertInfo = (ScmsAdvertInfo) BeanUtils.mapToBean(params, ScmsAdvertInfo.class);
		scmsAdvertInfo.setCreateBy(userInfo.getUserId());
		scmsAdvertInfo.setCreateByName(userInfo.getUserName());
		scmsAdvertInfo.setCreateDate(new Date());
		scmsAdvertInfoRepository.save(scmsAdvertInfo);
		return RestfulRetUtils.getRetSuccess();
	}

	@Override
	public JSONObject edit(Map<String, Object> params, UserInfo userInfo) {
        ScmsAdvertInfo scmsAdvertInfo = (ScmsAdvertInfo) BeanUtils.mapToBean(params, ScmsAdvertInfo.class);
		ScmsAdvertInfo scmsAdvertInfoInDb = scmsAdvertInfoRepository.findOne(scmsAdvertInfo.getId());
		if(scmsAdvertInfoInDb == null){
			return RestfulRetUtils.getErrorMsg("51006","当前编辑的对象不存在");
		}
		//合并两个javabean
		BeanUtils.mergeBean(scmsAdvertInfo, scmsAdvertInfoInDb);
		scmsAdvertInfoRepository.save(scmsAdvertInfoInDb);
		return RestfulRetUtils.getRetSuccess();
	}

	@Override
	public JSONObject del(String idsList, UserInfo userInfo) {
		String[] ids = idsList.split(",");
		for (int i = 0; i < ids.length; i++) {
			scmsAdvertInfoRepository.delete(StringUtils.toLong(ids[i]));
		}
		return RestfulRetUtils.getRetSuccess();
	}

    @Override
    public JSONObject saveOrderAdvertInfo(String params) {
        JSONArray jsonArray = JSONArray.parseArray(params);
        if(jsonArray != null && jsonArray.size() > 0) {
            Long id = null, dispOrder = null;;
            for(int i = 0; i < jsonArray.size(); i++) {
                JSONObject json = jsonArray.getJSONObject(i);
                if(json != null) {
                    id = json.getLong("id");
                    dispOrder = json.getLong("disOrder");
                    if(id != null && dispOrder != null)
                        scmsAdvertInfoRepository.updateDispOrderById(id, dispOrder);
                }
            }
            return RestfulRetUtils.getRetSuccess();
        }else return RestfulRetUtils.getErrorParams();
    }
}
