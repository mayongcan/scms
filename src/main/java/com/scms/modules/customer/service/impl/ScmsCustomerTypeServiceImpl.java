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

import com.scms.modules.customer.service.ScmsCustomerTypeService;
import com.scms.modules.customer.entity.ScmsCustomerType;
import com.scms.modules.customer.repository.ScmsCustomerTypeRepository;

@Service
public class ScmsCustomerTypeServiceImpl implements ScmsCustomerTypeService {
	
    @Autowired
    private ScmsCustomerTypeRepository scmsCustomerTypeRepository;

	@Override
	public JSONObject getList(Pageable page, ScmsCustomerType scmsCustomerType, Map<String, Object> params) {
		List<Map<String, Object>> list = scmsCustomerTypeRepository.getList(scmsCustomerType, params, page.getPageNumber(), page.getPageSize());
		int count = scmsCustomerTypeRepository.getListCount(scmsCustomerType, params);
		return RestfulRetUtils.getRetSuccessWithPage(list, count);	
	}

	@Override
	public JSONObject add(Map<String, Object> params, UserInfo userInfo) {
	    ScmsCustomerType scmsCustomerType = (ScmsCustomerType) BeanUtils.mapToBean(params, ScmsCustomerType.class);
        //判断名称是否已存在
        JSONObject json = judgeExist(scmsCustomerType);
        if(json != null) return json;
		scmsCustomerTypeRepository.save(scmsCustomerType);
		return RestfulRetUtils.getRetSuccess();
	}

	@Override
	public JSONObject edit(Map<String, Object> params, UserInfo userInfo) {
        ScmsCustomerType scmsCustomerType = (ScmsCustomerType) BeanUtils.mapToBean(params, ScmsCustomerType.class);
		ScmsCustomerType scmsCustomerTypeInDb = scmsCustomerTypeRepository.findOne(scmsCustomerType.getId());
		if(scmsCustomerTypeInDb == null){
			return RestfulRetUtils.getErrorMsg("51006","当前编辑的对象不存在");
		}
        //判断名称是否变更
        if(!scmsCustomerType.getTypeName().equals(scmsCustomerTypeInDb.getTypeName())) {
            JSONObject json = judgeExist(scmsCustomerType);
            if(json != null) return json;
        }
		//合并两个javabean
		BeanUtils.mergeBean(scmsCustomerType, scmsCustomerTypeInDb);
		scmsCustomerTypeRepository.save(scmsCustomerTypeInDb);
		return RestfulRetUtils.getRetSuccess();
	}

	@Override
	public JSONObject del(String idsList, UserInfo userInfo) {
		String[] ids = idsList.split(",");
		for (int i = 0; i < ids.length; i++) {
			scmsCustomerTypeRepository.delete(StringUtils.toLong(ids[i]));
		}
		return RestfulRetUtils.getRetSuccess();
	}
    
    /**
     * 判断名称是否已存在
     * @param scmsCustomerType
     * @return
     */
    private JSONObject judgeExist(ScmsCustomerType scmsCustomerType) {
        if("1".equals(scmsCustomerType.getType())) {
            List<ScmsCustomerType> list = scmsCustomerTypeRepository.findByTypeNameAndType(scmsCustomerType.getTypeName(), scmsCustomerType.getType());
            if(list != null && list.size() > 0) {
                return RestfulRetUtils.getErrorMsg("51006","当前输入的客户类型名称已存在！");
            }
        }else {
            //先判断系统是否创建
            List<ScmsCustomerType> list = scmsCustomerTypeRepository.findByTypeNameAndType(scmsCustomerType.getTypeName(), "1");
            if(list != null && list.size() > 0) {
                return RestfulRetUtils.getErrorMsg("51006","当前输入的客户类型名称已存在！");
            }
            list = scmsCustomerTypeRepository.findByTypeNameAndTypeAndMerchantsId(scmsCustomerType.getTypeName(), scmsCustomerType.getType(), scmsCustomerType.getMerchantsId());
            if(list != null && list.size() > 0) {
                return RestfulRetUtils.getErrorMsg("51006","当前输入的客户类型名称已存在！");
            }
        }
        return null;
    }
}
