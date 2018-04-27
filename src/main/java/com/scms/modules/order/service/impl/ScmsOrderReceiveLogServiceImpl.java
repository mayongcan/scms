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
import com.scms.modules.order.entity.ScmsOrderGoodsDetail;
import com.scms.modules.order.entity.ScmsOrderReceiveLog;
import com.scms.modules.order.repository.ScmsOrderGoodsDetailRepository;
import com.scms.modules.order.repository.ScmsOrderInfoRepository;
import com.scms.modules.order.repository.ScmsOrderReceiveLogRepository;

@Service
public class ScmsOrderReceiveLogServiceImpl implements ScmsOrderReceiveLogService {
	
    @Autowired
    private ScmsOrderReceiveLogRepository scmsOrderReceiveLogRepository;

    @Autowired
    private ScmsOrderInfoRepository scmsOrderInfoRepository;
    
    @Autowired
    private ScmsOrderGoodsDetailRepository scmsOrderGoodsDetailRepository;

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
        //更新单个商品的发货状态
        String idList = MapUtils.getString(params, "idList");
        String[] ids = idList.split(",");
        List<Long> idArray = new ArrayList<Long>();
        for (int i = 0; i < ids.length; i++) {
            idArray.add(StringUtils.toLong(ids[i]));
        }
        if(idArray.size() > 0){
            scmsOrderGoodsDetailRepository.updateReceiveStatus("1", idArray);
        }
        
        //判断是否需要更新订单的发货状态
        String retStatus = "1";
        List<ScmsOrderGoodsDetail> tmpList = scmsOrderGoodsDetailRepository.findByOrderIdAndReceiveStatus(scmsOrderReceiveLog.getOrderId(), "0");
        if(tmpList.size() == 0) {
            //全部收货
            retStatus = "3";
        }else {
            tmpList = scmsOrderGoodsDetailRepository.findByOrderIdAndReceiveStatus(scmsOrderReceiveLog.getOrderId(), "1");
            if(tmpList.size() == 0) {
                //未收货
                retStatus = "1";
            }else {
                //部分收货
                retStatus = "2";
            }
        }
        scmsOrderInfoRepository.updateOrderReceiveStatus(retStatus, scmsOrderReceiveLog.getOrderId());
		return RestfulRetUtils.getRetSuccess(retStatus);
	}

}
