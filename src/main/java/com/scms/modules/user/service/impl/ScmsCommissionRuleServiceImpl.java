/*
 * Copyright(c) 2018 gimplatform(通用信息管理平台) All rights reserved.
 */
package com.scms.modules.user.service.impl;

import java.util.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;
import org.apache.commons.collections4.MapUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;

import com.alibaba.fastjson.JSONObject;
import com.gimplatform.core.common.Constants;
import com.gimplatform.core.entity.UserInfo;
import com.gimplatform.core.utils.BeanUtils;
import com.gimplatform.core.utils.RestfulRetUtils;
import com.gimplatform.core.utils.StringUtils;

import com.scms.modules.user.service.ScmsCommissionRuleService;
import com.scms.modules.user.entity.ScmsCommissionRule;
import com.scms.modules.user.repository.ScmsCommissionRuleRepository;

@Service
public class ScmsCommissionRuleServiceImpl implements ScmsCommissionRuleService {
	
    @Autowired
    private ScmsCommissionRuleRepository scmsCommissionRuleRepository;

	@Override
	public JSONObject getList(Pageable page, ScmsCommissionRule scmsCommissionRule, Map<String, Object> params) {
		List<Map<String, Object>> list = scmsCommissionRuleRepository.getList(scmsCommissionRule, params, page.getPageNumber(), page.getPageSize());
		int count = scmsCommissionRuleRepository.getListCount(scmsCommissionRule, params);
		return RestfulRetUtils.getRetSuccessWithPage(list, count);	
	}

	@Override
	public JSONObject add(Map<String, Object> params, UserInfo userInfo) {
	    ScmsCommissionRule scmsCommissionRule = (ScmsCommissionRule) BeanUtils.mapToBean(params, ScmsCommissionRule.class);
		scmsCommissionRule.setIsValid(Constants.IS_VALID_VALID);
		scmsCommissionRule.setCreateBy(userInfo.getUserId());
		scmsCommissionRule.setCreateDate(new Date());
		scmsCommissionRule.setModifyBy(userInfo.getUserId());
		scmsCommissionRule.setModifyDate(new Date());
		scmsCommissionRule = scmsCommissionRuleRepository.saveAndFlush(scmsCommissionRule);
        
        String userIdList = MapUtils.getString(params, "userIdList");
        if(!StringUtils.isBlank(userIdList)) {
            String array[] = userIdList.split(",");
            Long userId = null;
            for(String str : array) {
                userId = StringUtils.toLong(str, null);
                if(userId != null) {
                    scmsCommissionRuleRepository.saveUserCommission(userId, scmsCommissionRule.getId());
                }
            }
        }
		return RestfulRetUtils.getRetSuccess();
	}

	@Override
	public JSONObject edit(Map<String, Object> params, UserInfo userInfo) {
        ScmsCommissionRule scmsCommissionRule = (ScmsCommissionRule) BeanUtils.mapToBean(params, ScmsCommissionRule.class);
		ScmsCommissionRule scmsCommissionRuleInDb = scmsCommissionRuleRepository.findOne(scmsCommissionRule.getId());
		if(scmsCommissionRuleInDb == null){
			return RestfulRetUtils.getErrorMsg("51006","当前编辑的对象不存在");
		}
		//合并两个javabean
		BeanUtils.mergeBean(scmsCommissionRule, scmsCommissionRuleInDb);
		scmsCommissionRuleInDb.setModifyBy(userInfo.getUserId());
		scmsCommissionRuleInDb.setModifyDate(new Date());
		scmsCommissionRuleRepository.save(scmsCommissionRuleInDb);

        String userIdList = MapUtils.getString(params, "userIdList");
        if(!StringUtils.isBlank(userIdList)) {
            //先删除关联表
            scmsCommissionRuleRepository.delUserCommissionByCommissionId(scmsCommissionRuleInDb.getId());
            //保存新的关联信息
            String array[] = userIdList.split(",");
            Long userId = null;
            for(String str : array) {
                userId = StringUtils.toLong(str, null);
                if(userId != null) {
                    scmsCommissionRuleRepository.saveUserCommission(userId, scmsCommissionRuleInDb.getId());
                }
            }
        }
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
			scmsCommissionRuleRepository.delEntity(Constants.IS_VALID_INVALID, idList);
		}
		return RestfulRetUtils.getRetSuccess();
	}

}
