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

import com.scms.modules.base.service.ScmsColorInfoService;
import com.scms.modules.base.entity.ScmsColorInfo;
import com.scms.modules.base.repository.ScmsColorInfoRepository;

@Service
public class ScmsColorInfoServiceImpl implements ScmsColorInfoService {
	
    @Autowired
    private ScmsColorInfoRepository scmsColorInfoRepository;

	@Override
	public JSONObject getList(Pageable page, ScmsColorInfo scmsColorInfo, Map<String, Object> params) {
		List<Map<String, Object>> list = scmsColorInfoRepository.getList(scmsColorInfo, params, page.getPageNumber(), page.getPageSize());
		int count = scmsColorInfoRepository.getListCount(scmsColorInfo, params);
		return RestfulRetUtils.getRetSuccessWithPage(list, count);	
	}

	@Override
	public JSONObject add(Map<String, Object> params, UserInfo userInfo) {
	    ScmsColorInfo scmsColorInfo = (ScmsColorInfo) BeanUtils.mapToBean(params, ScmsColorInfo.class);
        //判断名称是否已存在
        JSONObject json = judgeExist(scmsColorInfo);
        if(json != null) return json;
		scmsColorInfoRepository.save(scmsColorInfo);
		return RestfulRetUtils.getRetSuccess();
	}

	@Override
	public JSONObject edit(Map<String, Object> params, UserInfo userInfo) {
        ScmsColorInfo scmsColorInfo = (ScmsColorInfo) BeanUtils.mapToBean(params, ScmsColorInfo.class);
		ScmsColorInfo scmsColorInfoInDb = scmsColorInfoRepository.findOne(scmsColorInfo.getId());
		if(scmsColorInfoInDb == null){
			return RestfulRetUtils.getErrorMsg("51006","当前编辑的对象不存在");
		}
        //判断名称是否变更
        if(!scmsColorInfo.getColorName().equals(scmsColorInfoInDb.getColorName())) {
            JSONObject json = judgeExist(scmsColorInfo);
            if(json != null) return json;
        }
		//合并两个javabean
		BeanUtils.mergeBean(scmsColorInfo, scmsColorInfoInDb);
		scmsColorInfoRepository.save(scmsColorInfoInDb);
		return RestfulRetUtils.getRetSuccess();
	}

	@Override
	public JSONObject del(String idsList, UserInfo userInfo) {
		String[] ids = idsList.split(",");
		for (int i = 0; i < ids.length; i++) {
			scmsColorInfoRepository.delete(StringUtils.toLong(ids[i]));
		}
		return RestfulRetUtils.getRetSuccess();
	}

    /**
     * 判断名称是否已存在
     * @param scmsSizeInfo
     * @return
     */
    private JSONObject judgeExist(ScmsColorInfo scmsColorInfo) {
        if("1".equals(scmsColorInfo.getType())) {
            List<ScmsColorInfo> list = scmsColorInfoRepository.findByColorNameAndType(scmsColorInfo.getColorName(), scmsColorInfo.getType());
            if(list != null && list.size() > 0) {
                return RestfulRetUtils.getErrorMsg("51006","当前输入的颜色名称已存在！");
            }
        }else {
            //先判断系统是否创建
            List<ScmsColorInfo> list = scmsColorInfoRepository.findByColorNameAndType(scmsColorInfo.getColorName(), "1");
            if(list != null && list.size() > 0) {
                return RestfulRetUtils.getErrorMsg("51006","当前输入的颜色名称已存在！");
            }
            list = scmsColorInfoRepository.findByColorNameAndTypeAndMerchantsId(scmsColorInfo.getColorName(), scmsColorInfo.getType(), scmsColorInfo.getMerchantsId());
            if(list != null && list.size() > 0) {
                return RestfulRetUtils.getErrorMsg("51006","当前输入的颜色名称已存在！");
            }
        }
        return null;
    }

}
