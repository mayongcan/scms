/*
 * Copyright(c) 2018 gimplatform(通用信息管理平台) All rights reserved.
 */
package com.scms.modules.order.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;
import org.apache.commons.collections4.MapUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;

import com.alibaba.fastjson.JSONObject;
import com.gimplatform.core.entity.UserInfo;
import com.gimplatform.core.utils.BeanUtils;
import com.gimplatform.core.utils.RestfulRetUtils;
import com.gimplatform.core.utils.StringUtils;

import com.scms.modules.order.service.ScmsOrderSendLogService;
import com.scms.modules.order.entity.ScmsOrderSendLog;
import com.scms.modules.order.repository.ScmsOrderInfoRepository;
import com.scms.modules.order.repository.ScmsOrderSendLogRepository;

@Service
public class ScmsOrderSendLogServiceImpl implements ScmsOrderSendLogService {
	
    @Autowired
    private ScmsOrderSendLogRepository scmsOrderSendLogRepository;
    
    @Autowired
    private ScmsOrderInfoRepository scmsOrderInfoRepository;

	@Override
	public JSONObject getList(Pageable page, ScmsOrderSendLog scmsOrderSendLog, Map<String, Object> params) {
		List<Map<String, Object>> list = scmsOrderSendLogRepository.getList(scmsOrderSendLog, params, page.getPageNumber(), page.getPageSize());
		int count = scmsOrderSendLogRepository.getListCount(scmsOrderSendLog, params);
		return RestfulRetUtils.getRetSuccessWithPage(list, count);	
	}

	@Override
	public JSONObject addOrderSendLog(Map<String, Object> params, UserInfo userInfo) {
	    ScmsOrderSendLog scmsOrderSendLog = (ScmsOrderSendLog) BeanUtils.mapToBean(params, ScmsOrderSendLog.class);
	    scmsOrderSendLog.setSendBy(userInfo.getUserId());
	    scmsOrderSendLog.setSendByName(userInfo.getUserName());
	    scmsOrderSendLog.setSendDate(new Date());
		scmsOrderSendLogRepository.save(scmsOrderSendLog);
		//更新状态
		String orderSendStatus = MapUtils.getString(params, "orderSendStatus");
		if(!StringUtils.isBlank(orderSendStatus)) {
		    List<Long> idList = new ArrayList<Long>();
		    idList.add(scmsOrderSendLog.getOrderId());
		    scmsOrderInfoRepository.updateOrderSendStatus(orderSendStatus, idList);
		}
		return RestfulRetUtils.getRetSuccess();
	}

}
