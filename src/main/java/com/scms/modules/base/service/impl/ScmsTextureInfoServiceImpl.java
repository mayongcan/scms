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

import com.scms.modules.base.service.ScmsTextureInfoService;
import com.scms.modules.base.entity.ScmsTextureInfo;
import com.scms.modules.base.repository.ScmsTextureInfoRepository;

@Service
public class ScmsTextureInfoServiceImpl implements ScmsTextureInfoService {
	
    @Autowired
    private ScmsTextureInfoRepository scmsTextureInfoRepository;

	@Override
	public JSONObject getList(Pageable page, ScmsTextureInfo scmsTextureInfo, Map<String, Object> params) {
		List<Map<String, Object>> list = scmsTextureInfoRepository.getList(scmsTextureInfo, params, page.getPageNumber(), page.getPageSize());
		int count = scmsTextureInfoRepository.getListCount(scmsTextureInfo, params);
		return RestfulRetUtils.getRetSuccessWithPage(list, count);	
	}

	@Override
	public JSONObject add(Map<String, Object> params, UserInfo userInfo) {
	    ScmsTextureInfo scmsTextureInfo = (ScmsTextureInfo) BeanUtils.mapToBean(params, ScmsTextureInfo.class);
        //判断名称是否已存在
        JSONObject json = judgeExist(scmsTextureInfo);
        if(json != null) return json;
		scmsTextureInfoRepository.save(scmsTextureInfo);
		return RestfulRetUtils.getRetSuccess();
	}

	@Override
	public JSONObject edit(Map<String, Object> params, UserInfo userInfo) {
        ScmsTextureInfo scmsTextureInfo = (ScmsTextureInfo) BeanUtils.mapToBean(params, ScmsTextureInfo.class);
		ScmsTextureInfo scmsTextureInfoInDb = scmsTextureInfoRepository.findOne(scmsTextureInfo.getId());
		if(scmsTextureInfoInDb == null){
			return RestfulRetUtils.getErrorMsg("51006","当前编辑的对象不存在");
		}
        //判断名称是否变更
        if(!scmsTextureInfo.getTextureName().equals(scmsTextureInfoInDb.getTextureName())) {
            JSONObject json = judgeExist(scmsTextureInfo);
            if(json != null) return json;
        }
		//合并两个javabean
		BeanUtils.mergeBean(scmsTextureInfo, scmsTextureInfoInDb);
		scmsTextureInfoRepository.save(scmsTextureInfoInDb);
		return RestfulRetUtils.getRetSuccess();
	}

	@Override
	public JSONObject del(String idsList, UserInfo userInfo) {
		String[] ids = idsList.split(",");
		for (int i = 0; i < ids.length; i++) {
			scmsTextureInfoRepository.delete(StringUtils.toLong(ids[i]));
		}
		return RestfulRetUtils.getRetSuccess();
	}

    /**
     * 判断名称是否已存在
     * @param scmsSizeInfo
     * @return
     */
    private JSONObject judgeExist(ScmsTextureInfo scmsTextureInfo) {
        if("1".equals(scmsTextureInfo.getType())) {
            List<ScmsTextureInfo> list = scmsTextureInfoRepository.findByTextureNameAndType(scmsTextureInfo.getTextureName(), scmsTextureInfo.getType());
            if(list != null && list.size() > 0) {
                return RestfulRetUtils.getErrorMsg("51006","当前输入的材质名称已存在！");
            }
        }else {
            //先判断系统是否创建
            List<ScmsTextureInfo> list = scmsTextureInfoRepository.findByTextureNameAndType(scmsTextureInfo.getTextureName(), "1");
            if(list != null && list.size() > 0) {
                return RestfulRetUtils.getErrorMsg("51006","当前输入的材质名称已存在！");
            }
            list = scmsTextureInfoRepository.findByTextureNameAndTypeAndMerchantsId(scmsTextureInfo.getTextureName(), scmsTextureInfo.getType(), scmsTextureInfo.getMerchantsId());
            if(list != null && list.size() > 0) {
                return RestfulRetUtils.getErrorMsg("51006","当前输入的材质名称已存在！");
            }
        }
        return null;
    }

}
