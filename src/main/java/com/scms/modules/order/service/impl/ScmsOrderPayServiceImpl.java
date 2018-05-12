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
import com.gimplatform.core.common.Constants;
import com.gimplatform.core.entity.UserInfo;
import com.gimplatform.core.service.DictService;
import com.gimplatform.core.utils.BeanUtils;
import com.gimplatform.core.utils.RestfulRetUtils;
import com.gimplatform.core.utils.SessionUtils;
import com.scms.modules.order.service.ScmsOrderPayService;
import com.scms.modules.customer.repository.ScmsCustomerInfoRepository;
import com.scms.modules.customer.repository.ScmsSupplierInfoRepository;
import com.scms.modules.finance.entity.ScmsFinanceFlow;
import com.scms.modules.finance.repository.ScmsFinanceFlowRepository;
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

    @Autowired
    private ScmsFinanceFlowRepository scmsFinanceFlowRepository;

    @Autowired
    private DictService dictService;

	@Override
	public JSONObject getList(Pageable page, ScmsOrderPay scmsOrderPay, Map<String, Object> params) {
		List<Map<String, Object>> list = scmsOrderPayRepository.getList(scmsOrderPay, params, page.getPageNumber(), page.getPageSize());
		int count = scmsOrderPayRepository.getListCount(scmsOrderPay, params);
		return RestfulRetUtils.getRetSuccessWithPage(list, count);	
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
            delOrderPay(scmsOrderInfo, "修改订单");
            saveOrderPayList(jsonArray, scmsOrderInfo, userInfo);
        }
        
        //判断是否需要更新客户余额
        if("lsd".equals(scmsOrderInfoInDb.getOrderType()) || "pfd".equals(scmsOrderInfoInDb.getOrderType()) || "ysd".equals(scmsOrderInfoInDb.getOrderType()) ) {
            Double customerBalance = MapUtils.getDouble(params, "customerBalance", null);
            if(scmsOrderInfo.getCustomerId() != null && !scmsOrderInfo.getCustomerId().equals(-1L) && customerBalance != null) {
                scmsCustomerInfoRepository.updateCustomerBalance(customerBalance, scmsOrderInfo.getCustomerId());
            }
        }else if("jhd".equals(scmsOrderInfoInDb.getOrderType()) || "fcd".equals(scmsOrderInfoInDb.getOrderType())){
            Double customerBalance = MapUtils.getDouble(params, "customerBalance", null);
            if(scmsOrderInfo.getCustomerId() != null && !scmsOrderInfo.getCustomerId().equals(-1L) && customerBalance != null) {
                scmsSupplierInfoRepository.updateSupplierBalance(customerBalance, scmsOrderInfo.getCustomerId());
            }
        }
        return RestfulRetUtils.getRetSuccess();
    }

    @Override
    public Double getPayAmount(ScmsOrderInfo scmsOrderInfo) {
        List<ScmsOrderPay> tmpPayList = scmsOrderPayRepository.findByOrderIdAndPayTypeId(scmsOrderInfo.getId(), 1L);
        Double payAmount = 0d;
        if(tmpPayList != null && tmpPayList.size() > 0) payAmount = tmpPayList.get(0).getPayAmount();
        return payAmount;
    }

    @Override
    public void delOrderPay(ScmsOrderInfo scmsOrderInfo, String validReason) {
        //删除订单支付信息前，将财务流水对应的条码设置为不可用
        scmsFinanceFlowRepository.updateIsValid(Constants.IS_VALID_INVALID, validReason, scmsOrderInfo.getOrderNum());
        //删除订单支付信息
        scmsOrderPayRepository.delByOrderId(scmsOrderInfo.getId());
    }

    @Override
    public void saveOrderPayList(JSONArray jsonArray, ScmsOrderInfo scmsOrderInfo, UserInfo userInfo) {
        //获取单号字典值
        String operateName = "";
        JSONArray tmpJsonArray = dictService.getDictDataByDictTypeValue("SCMS_ORDER_TYPE", SessionUtils.getUserInfo());
        for(int i = 0; i < tmpJsonArray.size(); i++) {
            JSONObject json = tmpJsonArray.getJSONObject(i);
            if(scmsOrderInfo.getOrderType().equals(json.get("ID"))) {
                operateName = json.getString("NAME");
                break;
            }
        }
        //设置收支类型
        String incomeType = "1";
        //进货单和退货单的收支类型为2，即支出
        if(scmsOrderInfo.getOrderType().equals("jhd") || scmsOrderInfo.getOrderType().equals("thd")) incomeType = "2";
        
        ScmsOrderPay obj = null;
        for(int i = 0; i < jsonArray.size(); i++) {
            obj = JSONObject.toJavaObject(jsonArray.getJSONObject(i), ScmsOrderPay.class);
            if(obj != null) {
                obj.setOrderId(scmsOrderInfo.getId());
                obj.setIncomeType(incomeType);
                obj.setPayDate(new Date());
                obj.setOperateUserId(userInfo.getUserId());
                obj.setOperateUserName(userInfo.getUserName());
                scmsOrderPayRepository.save(obj);
                
                //保存财务流水信息
                ScmsFinanceFlow scmsFinanceFlow = new ScmsFinanceFlow();
                scmsFinanceFlow.setMerchantsId(scmsOrderInfo.getMerchantsId());
                scmsFinanceFlow.setShopId(scmsOrderInfo.getShopId());
                scmsFinanceFlow.setFlowType(operateName);
                scmsFinanceFlow.setOrderId(scmsOrderInfo.getId());
                scmsFinanceFlow.setOrderNum(scmsOrderInfo.getOrderNum());
                scmsFinanceFlow.setOrderType(scmsOrderInfo.getOrderType());
                scmsFinanceFlow.setPayTypeId(obj.getPayTypeId());
                scmsFinanceFlow.setPayTypeName(obj.getPayTypeName());
                scmsFinanceFlow.setIncomeType(incomeType);
                scmsFinanceFlow.setPayAmount(obj.getPayAmount());
                scmsFinanceFlow.setPayMemo(obj.getPayMemo());
                scmsFinanceFlow.setCreateBy(userInfo.getUserId());
                scmsFinanceFlow.setCreateByName(userInfo.getUserName());
                scmsFinanceFlow.setCreateDate(new Date());
                scmsFinanceFlow.setValidReason("");
                scmsFinanceFlow.setIsValid(Constants.IS_VALID_VALID);
                scmsFinanceFlowRepository.save(scmsFinanceFlow);
            }
        }
    }
}
