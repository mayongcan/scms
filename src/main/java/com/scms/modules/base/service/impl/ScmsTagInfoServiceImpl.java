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

import com.scms.modules.base.service.ScmsTagInfoService;
import com.scms.modules.base.entity.ScmsTagInfo;
import com.scms.modules.base.repository.ScmsTagInfoRepository;

@Service
public class ScmsTagInfoServiceImpl implements ScmsTagInfoService {
	
    @Autowired
    private ScmsTagInfoRepository scmsTagInfoRepository;

	@Override
	public JSONObject getList(Pageable page, ScmsTagInfo scmsTagInfo, Map<String, Object> params) {
		List<Map<String, Object>> list = scmsTagInfoRepository.getList(scmsTagInfo, params, page.getPageNumber(), page.getPageSize());
		int count = scmsTagInfoRepository.getListCount(scmsTagInfo, params);
		return RestfulRetUtils.getRetSuccessWithPage(list, count);	
	}

	@Override
	public JSONObject add(Map<String, Object> params, UserInfo userInfo) {
	    ScmsTagInfo scmsTagInfo = (ScmsTagInfo) BeanUtils.mapToBean(params, ScmsTagInfo.class);
        //判断名称是否已存在
        JSONObject json = judgeExist(scmsTagInfo);
        if(json != null) return json;
		scmsTagInfoRepository.save(scmsTagInfo);
		return RestfulRetUtils.getRetSuccess();
	}

	@Override
	public JSONObject edit(Map<String, Object> params, UserInfo userInfo) {
        ScmsTagInfo scmsTagInfo = (ScmsTagInfo) BeanUtils.mapToBean(params, ScmsTagInfo.class);
		ScmsTagInfo scmsTagInfoInDb = scmsTagInfoRepository.findOne(scmsTagInfo.getId());
		if(scmsTagInfoInDb == null){
			return RestfulRetUtils.getErrorMsg("51006","当前编辑的对象不存在");
		}
        //判断名称是否变更
        if(!scmsTagInfo.getTagName().equals(scmsTagInfoInDb.getTagName())) {
            JSONObject json = judgeExist(scmsTagInfo);
            if(json != null) return json;
        }
		//合并两个javabean
		BeanUtils.mergeBean(scmsTagInfo, scmsTagInfoInDb);
		scmsTagInfoRepository.save(scmsTagInfoInDb);
		return RestfulRetUtils.getRetSuccess();
	}

	@Override
	public JSONObject del(String idsList, UserInfo userInfo) {
		String[] ids = idsList.split(",");
		for (int i = 0; i < ids.length; i++) {
			scmsTagInfoRepository.delete(StringUtils.toLong(ids[i]));
		}
		return RestfulRetUtils.getRetSuccess();
	}

    /**
     * 判断名称是否已存在
     * @param scmsSizeInfo
     * @return
     */
    private JSONObject judgeExist(ScmsTagInfo scmsTagInfo) {
        if("1".equals(scmsTagInfo.getType())) {
            List<ScmsTagInfo> list = scmsTagInfoRepository.findByTagTypeAndTagNameAndType(scmsTagInfo.getTagType(), scmsTagInfo.getTagName(), scmsTagInfo.getType());
            if(list != null && list.size() > 0) {
                return RestfulRetUtils.getErrorMsg("51006","当前输入的标签名称已存在！");
            }
        }else {
            //先判断系统是否创建
            List<ScmsTagInfo> list = scmsTagInfoRepository.findByTagTypeAndTagNameAndType(scmsTagInfo.getTagType(), scmsTagInfo.getTagName(), "1");
            if(list != null && list.size() > 0) {
                return RestfulRetUtils.getErrorMsg("51006","当前输入的标签名称已存在！");
            }
            list = scmsTagInfoRepository.findByTagTypeAndTagNameAndTypeAndMerchantsId(scmsTagInfo.getTagType(), scmsTagInfo.getTagName(), scmsTagInfo.getType(), scmsTagInfo.getMerchantsId());
            if(list != null && list.size() > 0) {
                return RestfulRetUtils.getErrorMsg("51006","当前输入的标签名称已存在！");
            }
        }
        return null;
    }

}
