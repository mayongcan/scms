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

import com.scms.modules.base.service.ScmsVenderInfoService;
import com.scms.modules.base.entity.ScmsVenderInfo;
import com.scms.modules.base.repository.ScmsVenderInfoRepository;

@Service
public class ScmsVenderInfoServiceImpl implements ScmsVenderInfoService {
	
    @Autowired
    private ScmsVenderInfoRepository scmsVenderInfoRepository;

	@Override
	public JSONObject getList(Pageable page, ScmsVenderInfo scmsVenderInfo, Map<String, Object> params) {
		List<Map<String, Object>> list = scmsVenderInfoRepository.getList(scmsVenderInfo, params, page.getPageNumber(), page.getPageSize());
		int count = scmsVenderInfoRepository.getListCount(scmsVenderInfo, params);
		return RestfulRetUtils.getRetSuccessWithPage(list, count);	
	}

	@Override
	public JSONObject add(Map<String, Object> params, UserInfo userInfo) {
	    ScmsVenderInfo scmsVenderInfo = (ScmsVenderInfo) BeanUtils.mapToBean(params, ScmsVenderInfo.class);
        //判断名称是否已存在
        JSONObject json = judgeExist(scmsVenderInfo);
        if(json != null) return json;
		scmsVenderInfo.setIsValid(Constants.IS_VALID_VALID);
		scmsVenderInfo.setCreateBy(userInfo.getUserId());
		scmsVenderInfo.setCreateDate(new Date());
		scmsVenderInfoRepository.save(scmsVenderInfo);
		return RestfulRetUtils.getRetSuccess();
	}

	@Override
	public JSONObject edit(Map<String, Object> params, UserInfo userInfo) {
        ScmsVenderInfo scmsVenderInfo = (ScmsVenderInfo) BeanUtils.mapToBean(params, ScmsVenderInfo.class);
		ScmsVenderInfo scmsVenderInfoInDb = scmsVenderInfoRepository.findOne(scmsVenderInfo.getId());
		if(scmsVenderInfoInDb == null){
			return RestfulRetUtils.getErrorMsg("51006","当前编辑的对象不存在");
		}
        //判断名称是否变更
        if(!scmsVenderInfo.getVenderName().equals(scmsVenderInfoInDb.getVenderName())) {
            JSONObject json = judgeExist(scmsVenderInfo);
            if(json != null) return json;
        }
		//合并两个javabean
		BeanUtils.mergeBean(scmsVenderInfo, scmsVenderInfoInDb);
		//更新图片
		scmsVenderInfoInDb.setVenderPhoto(scmsVenderInfo.getVenderPhoto());
		scmsVenderInfoRepository.save(scmsVenderInfoInDb);
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
			scmsVenderInfoRepository.delEntity(Constants.IS_VALID_INVALID, idList);
		}
		return RestfulRetUtils.getRetSuccess();
	}

    /**
     * 判断名称是否已存在
     * @param scmsSizeInfo
     * @return
     */
    private JSONObject judgeExist(ScmsVenderInfo scmsVenderInfo) {
        if("1".equals(scmsVenderInfo.getType())) {
            List<ScmsVenderInfo> list = scmsVenderInfoRepository.findByVenderNameAndTypeAndIsValid(scmsVenderInfo.getVenderName(), scmsVenderInfo.getType(), Constants.IS_VALID_VALID);
            if(list != null && list.size() > 0) {
                return RestfulRetUtils.getErrorMsg("51006","当前输入的厂家名称已存在！");
            }
        }else {
            //先判断系统是否创建
            List<ScmsVenderInfo> list = scmsVenderInfoRepository.findByVenderNameAndTypeAndIsValid(scmsVenderInfo.getVenderName(), "1", Constants.IS_VALID_VALID);
            if(list != null && list.size() > 0) {
                return RestfulRetUtils.getErrorMsg("51006","当前输入的厂家名称已存在！");
            }
            list = scmsVenderInfoRepository.findByVenderNameAndTypeAndMerchantsIdAndIsValid(scmsVenderInfo.getVenderName(), scmsVenderInfo.getType(), scmsVenderInfo.getMerchantsId(), Constants.IS_VALID_VALID);
            if(list != null && list.size() > 0) {
                return RestfulRetUtils.getErrorMsg("51006","当前输入的厂家名称已存在！");
            }
        }
        return null;
    }

}
