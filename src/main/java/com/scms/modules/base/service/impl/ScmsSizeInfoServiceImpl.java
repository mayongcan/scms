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

import com.scms.modules.base.service.ScmsSizeInfoService;
import com.scms.modules.base.entity.ScmsSizeInfo;
import com.scms.modules.base.repository.ScmsSizeInfoRepository;

@Service
public class ScmsSizeInfoServiceImpl implements ScmsSizeInfoService {
	
    @Autowired
    private ScmsSizeInfoRepository scmsSizeInfoRepository;

	@Override
	public JSONObject getList(Pageable page, ScmsSizeInfo scmsSizeInfo, Map<String, Object> params) {
		List<Map<String, Object>> list = scmsSizeInfoRepository.getList(scmsSizeInfo, params, page.getPageNumber(), page.getPageSize());
		int count = scmsSizeInfoRepository.getListCount(scmsSizeInfo, params);
		return RestfulRetUtils.getRetSuccessWithPage(list, count);	
	}

	@Override
	public JSONObject add(Map<String, Object> params, UserInfo userInfo) {
	    ScmsSizeInfo scmsSizeInfo = (ScmsSizeInfo) BeanUtils.mapToBean(params, ScmsSizeInfo.class);
	    //判断名称是否已存在
	    JSONObject json = judgeExist(scmsSizeInfo);
	    if(json != null) return json;
		scmsSizeInfoRepository.save(scmsSizeInfo);
		return RestfulRetUtils.getRetSuccess();
	}

	@Override
	public JSONObject edit(Map<String, Object> params, UserInfo userInfo) {
        ScmsSizeInfo scmsSizeInfo = (ScmsSizeInfo) BeanUtils.mapToBean(params, ScmsSizeInfo.class);
		ScmsSizeInfo scmsSizeInfoInDb = scmsSizeInfoRepository.findOne(scmsSizeInfo.getId());
		if(scmsSizeInfoInDb == null){
			return RestfulRetUtils.getErrorMsg("51006","当前编辑的对象不存在");
		}
		//判断名称是否变更
		if(!scmsSizeInfo.getSizeName().equals(scmsSizeInfoInDb.getSizeName())) {
	        JSONObject json = judgeExist(scmsSizeInfo);
	        if(json != null) return json;
		}
		//合并两个javabean
		BeanUtils.mergeBean(scmsSizeInfo, scmsSizeInfoInDb);
		scmsSizeInfoRepository.save(scmsSizeInfoInDb);
		return RestfulRetUtils.getRetSuccess();
	}

	@Override
	public JSONObject del(String idsList, UserInfo userInfo) {
		String[] ids = idsList.split(",");
		for (int i = 0; i < ids.length; i++) {
			scmsSizeInfoRepository.delete(StringUtils.toLong(ids[i]));
		}
		return RestfulRetUtils.getRetSuccess();
	}
	
	/**
	 * 判断名称是否已存在
	 * @param scmsSizeInfo
	 * @return
	 */
	private JSONObject judgeExist(ScmsSizeInfo scmsSizeInfo) {
	    if("1".equals(scmsSizeInfo.getType())) {
            List<ScmsSizeInfo> list = scmsSizeInfoRepository.findBySizeNameAndType(scmsSizeInfo.getSizeName(), scmsSizeInfo.getType());
            if(list != null && list.size() > 0) {
                return RestfulRetUtils.getErrorMsg("51006","当前输入的尺码名称已存在！");
            }
        }else {
            //先判断系统是否创建
            List<ScmsSizeInfo> list = scmsSizeInfoRepository.findBySizeNameAndType(scmsSizeInfo.getSizeName(), "1");
            if(list != null && list.size() > 0) {
                return RestfulRetUtils.getErrorMsg("51006","当前输入的尺码名称已存在！");
            }
            list = scmsSizeInfoRepository.findBySizeNameAndTypeAndMerchantsId(scmsSizeInfo.getSizeName(), scmsSizeInfo.getType(), scmsSizeInfo.getMerchantsId());
            if(list != null && list.size() > 0) {
                return RestfulRetUtils.getErrorMsg("51006","当前输入的尺码名称已存在！");
            }
        }
	    return null;
	}

}
