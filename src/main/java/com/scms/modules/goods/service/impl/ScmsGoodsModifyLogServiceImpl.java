/*
 * Copyright(c) 2018 gimplatform(通用信息管理平台) All rights reserved.
 */
package com.scms.modules.goods.service.impl;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;

import com.alibaba.fastjson.JSONObject;
import com.gimplatform.core.entity.UserInfo;
import com.gimplatform.core.utils.RestfulRetUtils;
import com.scms.modules.goods.service.ScmsGoodsModifyLogService;
import com.scms.modules.goods.entity.ScmsGoodsModifyLog;
import com.scms.modules.goods.repository.ScmsGoodsModifyLogRepository;

@Service
public class ScmsGoodsModifyLogServiceImpl implements ScmsGoodsModifyLogService {
	
    @Autowired
    private ScmsGoodsModifyLogRepository scmsGoodsModifyLogRepository;

	@Override
	public JSONObject getList(Pageable page, ScmsGoodsModifyLog scmsGoodsModifyLog, Map<String, Object> params) {
		List<Map<String, Object>> list = scmsGoodsModifyLogRepository.getList(scmsGoodsModifyLog, params, page.getPageNumber(), page.getPageSize());
		int count = scmsGoodsModifyLogRepository.getListCount(scmsGoodsModifyLog, params);
		return RestfulRetUtils.getRetSuccessWithPage(list, count);	
	}

	@Override
	public JSONObject add(ScmsGoodsModifyLog scmsGoodsModifyLog, UserInfo userInfo) {
		scmsGoodsModifyLog.setModifyBy(userInfo.getUserId());
		scmsGoodsModifyLog.setModifyByName(userInfo.getUserName());
		scmsGoodsModifyLog.setModifyDate(new Date());
		scmsGoodsModifyLogRepository.save(scmsGoodsModifyLog);
		return RestfulRetUtils.getRetSuccess();
	}

}
