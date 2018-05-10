/*
 * Copyright(c) 2018 gimplatform(通用信息管理平台) All rights reserved.
 */
package com.scms.modules.finance.service.impl;

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

import com.scms.modules.finance.service.ScmsFinanceFlowService;
import com.scms.modules.finance.entity.ScmsFinanceFlow;
import com.scms.modules.finance.repository.ScmsFinanceFlowRepository;

@Service
public class ScmsFinanceFlowServiceImpl implements ScmsFinanceFlowService {
	
    @Autowired
    private ScmsFinanceFlowRepository scmsFinanceFlowRepository;

	@Override
	public JSONObject getList(Pageable page, ScmsFinanceFlow scmsFinanceFlow, Map<String, Object> params) {
		List<Map<String, Object>> list = scmsFinanceFlowRepository.getList(scmsFinanceFlow, params, page.getPageNumber(), page.getPageSize());
		int count = scmsFinanceFlowRepository.getListCount(scmsFinanceFlow, params);
		return RestfulRetUtils.getRetSuccessWithPage(list, count);	
	}

	@Override
	public JSONObject add(Map<String, Object> params, UserInfo userInfo) {
	    ScmsFinanceFlow scmsFinanceFlow = (ScmsFinanceFlow) BeanUtils.mapToBean(params, ScmsFinanceFlow.class);
		scmsFinanceFlow.setIsValid(Constants.IS_VALID_VALID);
		scmsFinanceFlow.setCreateBy(userInfo.getUserId());
		scmsFinanceFlow.setCreateDate(new Date());
		scmsFinanceFlowRepository.save(scmsFinanceFlow);
		return RestfulRetUtils.getRetSuccess();
	}

	@Override
	public JSONObject edit(Map<String, Object> params, UserInfo userInfo) {
        ScmsFinanceFlow scmsFinanceFlow = (ScmsFinanceFlow) BeanUtils.mapToBean(params, ScmsFinanceFlow.class);
		ScmsFinanceFlow scmsFinanceFlowInDb = scmsFinanceFlowRepository.findOne(scmsFinanceFlow.getId());
		if(scmsFinanceFlowInDb == null){
			return RestfulRetUtils.getErrorMsg("51006","当前编辑的对象不存在");
		}
		//合并两个javabean
		BeanUtils.mergeBean(scmsFinanceFlow, scmsFinanceFlowInDb);
		scmsFinanceFlowRepository.save(scmsFinanceFlowInDb);
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
			scmsFinanceFlowRepository.delEntity(Constants.IS_VALID_INVALID, idList);
		}
		return RestfulRetUtils.getRetSuccess();
	}

}
