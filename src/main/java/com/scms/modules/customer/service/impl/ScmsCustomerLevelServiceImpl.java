/*
 * Copyright(c) 2018 gimplatform(通用信息管理平台) All rights reserved.
 */
package com.scms.modules.customer.service.impl;

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

import com.scms.modules.customer.service.ScmsCustomerLevelService;
import com.scms.modules.customer.entity.ScmsCustomerLevel;
import com.scms.modules.customer.repository.ScmsCustomerLevelRepository;

@Service
public class ScmsCustomerLevelServiceImpl implements ScmsCustomerLevelService {
	
    @Autowired
    private ScmsCustomerLevelRepository scmsCustomerLevelRepository;

	@Override
	public JSONObject getList(Pageable page, ScmsCustomerLevel scmsCustomerLevel, Map<String, Object> params) {
		List<Map<String, Object>> list = scmsCustomerLevelRepository.getList(scmsCustomerLevel, params, page.getPageNumber(), page.getPageSize());
		int count = scmsCustomerLevelRepository.getListCount(scmsCustomerLevel, params);
		return RestfulRetUtils.getRetSuccessWithPage(list, count);	
	}

	@Override
	public JSONObject add(Map<String, Object> params, UserInfo userInfo) {
	    ScmsCustomerLevel scmsCustomerLevel = (ScmsCustomerLevel) BeanUtils.mapToBean(params, ScmsCustomerLevel.class);
        //判断名称是否已存在
        JSONObject json = judgeExist(scmsCustomerLevel);
        if(json != null) return json;
		scmsCustomerLevelRepository.save(scmsCustomerLevel);
		return RestfulRetUtils.getRetSuccess();
	}

	@Override
	public JSONObject edit(Map<String, Object> params, UserInfo userInfo) {
        ScmsCustomerLevel scmsCustomerLevel = (ScmsCustomerLevel) BeanUtils.mapToBean(params, ScmsCustomerLevel.class);
		ScmsCustomerLevel scmsCustomerLevelInDb = scmsCustomerLevelRepository.findOne(scmsCustomerLevel.getId());
		if(scmsCustomerLevelInDb == null){
			return RestfulRetUtils.getErrorMsg("51006","当前编辑的对象不存在");
		}
        //判断名称是否变更
        if(!scmsCustomerLevel.getLevelName().equals(scmsCustomerLevelInDb.getLevelName())) {
            JSONObject json = judgeExist(scmsCustomerLevel);
            if(json != null) return json;
        }
		//合并两个javabean
		BeanUtils.mergeBean(scmsCustomerLevel, scmsCustomerLevelInDb);
		scmsCustomerLevelRepository.save(scmsCustomerLevelInDb);
		return RestfulRetUtils.getRetSuccess();
	}

	@Override
	public JSONObject del(String idsList, UserInfo userInfo) {
		String[] ids = idsList.split(",");
		for (int i = 0; i < ids.length; i++) {
			scmsCustomerLevelRepository.delete(StringUtils.toLong(ids[i]));
		}
		return RestfulRetUtils.getRetSuccess();
	}

    
    /**
     * 判断名称是否已存在
     * @param scmsCustomerLevel
     * @return
     */
    private JSONObject judgeExist(ScmsCustomerLevel scmsCustomerLevel) {
        if("1".equals(scmsCustomerLevel.getType())) {
            List<ScmsCustomerLevel> list = scmsCustomerLevelRepository.findByLevelNameAndType(scmsCustomerLevel.getLevelName(), scmsCustomerLevel.getType());
            if(list != null && list.size() > 0) {
                return RestfulRetUtils.getErrorMsg("51006","当前输入的客户等级名称已存在！");
            }
        }else {
            //先判断系统是否创建
            List<ScmsCustomerLevel> list = scmsCustomerLevelRepository.findByLevelNameAndType(scmsCustomerLevel.getLevelName(), "1");
            if(list != null && list.size() > 0) {
                return RestfulRetUtils.getErrorMsg("51006","当前输入的客户等级名称已存在！");
            }
            list = scmsCustomerLevelRepository.findByLevelNameAndTypeAndMerchantsId(scmsCustomerLevel.getLevelName(), scmsCustomerLevel.getType(), scmsCustomerLevel.getMerchantsId());
            if(list != null && list.size() > 0) {
                return RestfulRetUtils.getErrorMsg("51006","当前输入的客户等级名称已存在！");
            }
        }
        return null;
    }
}
