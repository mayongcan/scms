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
import com.scms.modules.order.entity.ScmsOrderGoodsDetail;
import com.scms.modules.order.entity.ScmsOrderSendLog;
import com.scms.modules.order.repository.ScmsOrderGoodsDetailRepository;
import com.scms.modules.order.repository.ScmsOrderInfoRepository;
import com.scms.modules.order.repository.ScmsOrderSendLogRepository;

@Service
public class ScmsOrderSendLogServiceImpl implements ScmsOrderSendLogService {
	
    @Autowired
    private ScmsOrderSendLogRepository scmsOrderSendLogRepository;
    
    @Autowired
    private ScmsOrderInfoRepository scmsOrderInfoRepository;
    
    @Autowired
    private ScmsOrderGoodsDetailRepository scmsOrderGoodsDetailRepository;

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
		//更新单个商品的发货状态
		String idList = MapUtils.getString(params, "idList");
		String[] ids = idList.split(",");
        List<Long> idArray = new ArrayList<Long>();
        for (int i = 0; i < ids.length; i++) {
            idArray.add(StringUtils.toLong(ids[i]));
        }
        if(idArray.size() > 0){
            scmsOrderGoodsDetailRepository.updateSendStatus("1", idArray);
        }
		
		//判断是否需要更新订单的发货状态
        String retStatus = "1";
        List<ScmsOrderGoodsDetail> tmpList = scmsOrderGoodsDetailRepository.findByOrderIdAndSendStatus(scmsOrderSendLog.getOrderId(), "0");
        if(tmpList.size() == 0) {
            //全部发货
            retStatus = "3";
        }else {
            tmpList = scmsOrderGoodsDetailRepository.findByOrderIdAndSendStatus(scmsOrderSendLog.getOrderId(), "1");
            if(tmpList.size() == 0) {
                //未发货
                retStatus = "1";
            }else {
                //部分发货
                retStatus = "2";
            }
        }
        scmsOrderInfoRepository.updateOrderSendStatus(retStatus, scmsOrderSendLog.getOrderId());
		return RestfulRetUtils.getRetSuccess(retStatus);
	}

}
