/*
 * Copyright(c) 2018 gimplatform(通用信息管理平台) All rights reserved.
 */
package com.scms.modules.order.service.impl;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;
import org.apache.commons.collections4.MapUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.gimplatform.core.entity.UserInfo;
import com.gimplatform.core.utils.BeanUtils;
import com.gimplatform.core.utils.RestfulRetUtils;
import com.gimplatform.core.utils.StringUtils;

import com.scms.modules.order.service.ScmsOrderPayService;
import com.scms.modules.customer.repository.ScmsCustomerInfoRepository;
import com.scms.modules.customer.repository.ScmsSupplierInfoRepository;
import com.scms.modules.order.entity.ScmsOrderInfo;
import com.scms.modules.order.entity.ScmsOrderPay;
import com.scms.modules.order.repository.ScmsOrderInfoRepository;
import com.scms.modules.order.repository.ScmsOrderPayRepository;

@Service
public class ScmsOrderPayServiceImpl implements ScmsOrderPayService {
	
    @Autowired
    private ScmsOrderPayRepository scmsOrderPayRepository;
    
    @Autowired
    private ScmsOrderInfoRepository scmsOrderInfoRepository;
    
    @Autowired
    private ScmsCustomerInfoRepository scmsCustomerInfoRepository;

    @Autowired
    private ScmsSupplierInfoRepository scmsSupplierInfoRepository;

	@Override
	public JSONObject getList(Pageable page, ScmsOrderPay scmsOrderPay, Map<String, Object> params) {
		List<Map<String, Object>> list = scmsOrderPayRepository.getList(scmsOrderPay, params, page.getPageNumber(), page.getPageSize());
		int count = scmsOrderPayRepository.getListCount(scmsOrderPay, params);
		return RestfulRetUtils.getRetSuccessWithPage(list, count);	
	}

	@Override
	public JSONObject add(Map<String, Object> params, UserInfo userInfo) {
	    ScmsOrderPay scmsOrderPay = (ScmsOrderPay) BeanUtils.mapToBean(params, ScmsOrderPay.class);
		scmsOrderPayRepository.save(scmsOrderPay);
		return RestfulRetUtils.getRetSuccess();
	}

	@Override
	public JSONObject edit(Map<String, Object> params, UserInfo userInfo) {
        ScmsOrderPay scmsOrderPay = (ScmsOrderPay) BeanUtils.mapToBean(params, ScmsOrderPay.class);
		ScmsOrderPay scmsOrderPayInDb = scmsOrderPayRepository.findOne(scmsOrderPay.getId());
		if(scmsOrderPayInDb == null){
			return RestfulRetUtils.getErrorMsg("51006","当前编辑的对象不存在");
		}
		//合并两个javabean
		BeanUtils.mergeBean(scmsOrderPay, scmsOrderPayInDb);
		scmsOrderPayRepository.save(scmsOrderPayInDb);
		return RestfulRetUtils.getRetSuccess();
	}

	@Override
	public JSONObject del(String idsList, UserInfo userInfo) {
		String[] ids = idsList.split(",");
		for (int i = 0; i < ids.length; i++) {
			scmsOrderPayRepository.delete(StringUtils.toLong(ids[i]));
		}
		return RestfulRetUtils.getRetSuccess();
	}

    @Override
    public JSONObject editOrderPay(Map<String, Object> params, UserInfo userInfo) {
        ScmsOrderInfo scmsOrderInfo = (ScmsOrderInfo) BeanUtils.mapToBean(params, ScmsOrderInfo.class);
        ScmsOrderInfo scmsOrderInfoInDb = scmsOrderInfoRepository.findOne(scmsOrderInfo.getId());
        if(scmsOrderInfoInDb == null){
            return RestfulRetUtils.getErrorMsg("51006","当前编辑的对象不存在");
        }
        //合并两个javabean
        BeanUtils.mergeBean(scmsOrderInfo, scmsOrderInfoInDb);
        scmsOrderInfoRepository.save(scmsOrderInfoInDb);

        //保存订单支付信息
        String orderPayList = MapUtils.getString(params, "orderPayList");
        JSONArray jsonArray = JSONObject.parseArray(orderPayList);
        if(jsonArray != null && jsonArray.size() > 0) {
            //删除旧数据
            scmsOrderPayRepository.delByOrderId(scmsOrderInfo.getId());
            ScmsOrderPay obj = null;
            for(int i = 0; i < jsonArray.size(); i++) {
                obj = JSONObject.toJavaObject(jsonArray.getJSONObject(i), ScmsOrderPay.class);
                if(obj != null) {
                    obj.setOrderId(scmsOrderInfo.getId());
                    obj.setPayDate(new Date());
                    obj.setOperateUserId(userInfo.getUserId());
                    obj.setOperateUserName(userInfo.getUserName());
                    scmsOrderPayRepository.save(obj);
                }
            }
        }
        
        //判断是否需要更新客户余额
        if("lsd".equals(scmsOrderInfoInDb.getOrderType()) || "pfd".equals(scmsOrderInfoInDb.getOrderType()) || "ysd".equals(scmsOrderInfoInDb.getOrderType()) ) {
            Double customerBalance = MapUtils.getDouble(params, "customerBalance", null);
            if(scmsOrderInfo.getCustomerId() != null && !scmsOrderInfo.getCustomerId().equals(-1L) && customerBalance != null) {
                scmsCustomerInfoRepository.updateCustomerBalance(customerBalance, scmsOrderInfo.getCustomerId());
            }
        }else if("jhd".equals(scmsOrderInfoInDb.getOrderType())){
            Double customerBalance = MapUtils.getDouble(params, "customerBalance", null);
            if(scmsOrderInfo.getCustomerId() != null && !scmsOrderInfo.getCustomerId().equals(-1L) && customerBalance != null) {
                scmsSupplierInfoRepository.updateSupplierBalance(customerBalance, scmsOrderInfo.getCustomerId());
            }
        }
        return RestfulRetUtils.getRetSuccess();
    }

}
