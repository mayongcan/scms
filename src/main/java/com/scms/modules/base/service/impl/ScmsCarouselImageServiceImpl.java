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

import com.scms.modules.base.service.ScmsCarouselImageService;
import com.scms.modules.base.entity.ScmsCarouselImage;
import com.scms.modules.base.repository.ScmsCarouselImageRepository;

@Service
public class ScmsCarouselImageServiceImpl implements ScmsCarouselImageService {
	
    @Autowired
    private ScmsCarouselImageRepository scmsCarouselImageRepository;

	@Override
	public JSONObject getList(Pageable page, ScmsCarouselImage scmsCarouselImage, Map<String, Object> params) {
		List<Map<String, Object>> list = scmsCarouselImageRepository.getList(scmsCarouselImage, params, page.getPageNumber(), page.getPageSize());
		int count = scmsCarouselImageRepository.getListCount(scmsCarouselImage, params);
		return RestfulRetUtils.getRetSuccessWithPage(list, count);	
	}

	@Override
	public JSONObject add(Map<String, Object> params, UserInfo userInfo) {
	    ScmsCarouselImage scmsCarouselImage = (ScmsCarouselImage) BeanUtils.mapToBean(params, ScmsCarouselImage.class);
		scmsCarouselImage.setCreateBy(userInfo.getUserId());
		scmsCarouselImage.setCreateByName(userInfo.getUserName());
		scmsCarouselImage.setCreateDate(new Date());
		scmsCarouselImageRepository.save(scmsCarouselImage);
		return RestfulRetUtils.getRetSuccess();
	}

	@Override
	public JSONObject edit(Map<String, Object> params, UserInfo userInfo) {
        ScmsCarouselImage scmsCarouselImage = (ScmsCarouselImage) BeanUtils.mapToBean(params, ScmsCarouselImage.class);
		ScmsCarouselImage scmsCarouselImageInDb = scmsCarouselImageRepository.findOne(scmsCarouselImage.getId());
		if(scmsCarouselImageInDb == null){
			return RestfulRetUtils.getErrorMsg("51006","当前编辑的对象不存在");
		}
		//合并两个javabean
		BeanUtils.mergeBean(scmsCarouselImage, scmsCarouselImageInDb);
		scmsCarouselImageRepository.save(scmsCarouselImageInDb);
		return RestfulRetUtils.getRetSuccess();
	}

	@Override
	public JSONObject del(String idsList, UserInfo userInfo) {
		String[] ids = idsList.split(",");
		for (int i = 0; i < ids.length; i++) {
			scmsCarouselImageRepository.delete(StringUtils.toLong(ids[i]));
		}
		return RestfulRetUtils.getRetSuccess();
	}

    @Override
    public JSONObject saveOrderCarouselImage(String params) {
        JSONArray jsonArray = JSONArray.parseArray(params);
        if(jsonArray != null && jsonArray.size() > 0) {
            Long id = null, dispOrder = null;;
            for(int i = 0; i < jsonArray.size(); i++) {
                JSONObject json = jsonArray.getJSONObject(i);
                if(json != null) {
                    id = json.getLong("id");
                    dispOrder = json.getLong("disOrder");
                    if(id != null && dispOrder != null)
                        scmsCarouselImageRepository.updateDispOrderById(id, dispOrder);
                }
            }
            return RestfulRetUtils.getRetSuccess();
        }else return RestfulRetUtils.getErrorParams();
    }
}
