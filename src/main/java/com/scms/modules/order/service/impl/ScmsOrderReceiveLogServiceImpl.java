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

import com.scms.modules.order.service.ScmsOrderReceiveLogService;
import com.scms.modules.order.entity.ScmsOrderReceiveLog;
import com.scms.modules.order.repository.ScmsOrderInfoRepository;
import com.scms.modules.order.repository.ScmsOrderReceiveLogRepository;

@Service
public class ScmsOrderReceiveLogServiceImpl implements ScmsOrderReceiveLogService {
	
    @Autowired
    private ScmsOrderReceiveLogRepository scmsOrderReceiveLogRepository;

    @Autowired
    private ScmsOrderInfoRepository scmsOrderInfoRepository;

	@Override
	public JSONObject getList(Pageable page, ScmsOrderReceiveLog scmsOrderReceiveLog, Map<String, Object> params) {
		List<Map<String, Object>> list = scmsOrderReceiveLogRepository.getList(scmsOrderReceiveLog, params, page.getPageNumber(), page.getPageSize());
		int count = scmsOrderReceiveLogRepository.getListCount(scmsOrderReceiveLog, params);
		return RestfulRetUtils.getRetSuccessWithPage(list, count);	
	}

	@Override
	public JSONObject addOrderReceiveLog(Map<String, Object> params, UserInfo userInfo) {
	    ScmsOrderReceiveLog scmsOrderReceiveLog = (ScmsOrderReceiveLog) BeanUtils.mapToBean(params, ScmsOrderReceiveLog.class);
	    scmsOrderReceiveLog.setReceiveBy(userInfo.getUserId());
	    scmsOrderReceiveLog.setReceiveByName(userInfo.getUserName());
	    scmsOrderReceiveLog.setReceiveDate(new Date());
		scmsOrderReceiveLogRepository.save(scmsOrderReceiveLog);
        //更新状态
        String orderReceiveStatus = MapUtils.getString(params, "orderReceiveStatus");
        if(!StringUtils.isBlank(orderReceiveStatus)) {
            List<Long> idList = new ArrayList<Long>();
            idList.add(scmsOrderReceiveLog.getOrderId());
            scmsOrderInfoRepository.updateOrderReceiveStatus(orderReceiveStatus, idList);
        }
		return RestfulRetUtils.getRetSuccess();
	}

}
