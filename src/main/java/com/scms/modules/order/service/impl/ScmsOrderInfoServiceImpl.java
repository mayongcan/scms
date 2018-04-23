/*
 * Copyright(c) 2018 gimplatform(通用信息管理平台) All rights reserved.
 */
package com.scms.modules.order.service.impl;

import java.util.Date;
import java.util.ArrayList;
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
import com.gimplatform.core.utils.BeanUtils;
import com.gimplatform.core.utils.DateUtils;
import com.gimplatform.core.utils.RestfulRetUtils;
import com.gimplatform.core.utils.StringUtils;

import com.scms.modules.order.service.ScmsOrderInfoService;
import com.scms.modules.base.entity.ScmsMerchantsInfo;
import com.scms.modules.base.repository.ScmsMerchantsInfoRepository;
import com.scms.modules.customer.repository.ScmsCustomerInfoRepository;
import com.scms.modules.goods.entity.ScmsGoodsInventory;
import com.scms.modules.goods.repository.ScmsGoodsInventoryRepository;
import com.scms.modules.order.entity.ScmsOrderGoods;
import com.scms.modules.order.entity.ScmsOrderInfo;
import com.scms.modules.order.entity.ScmsOrderPay;
import com.scms.modules.order.repository.ScmsOrderGoodsRepository;
import com.scms.modules.order.repository.ScmsOrderInfoRepository;
import com.scms.modules.order.repository.ScmsOrderPayRepository;

@Service
public class ScmsOrderInfoServiceImpl implements ScmsOrderInfoService {
	
    @Autowired
    private ScmsOrderInfoRepository scmsOrderInfoRepository;
    
    @Autowired
    private ScmsMerchantsInfoRepository scmsMerchantsInfoRepository;
    
    @Autowired
    private ScmsOrderGoodsRepository scmsOrderGoodsRepository;
    
    @Autowired
    private ScmsOrderPayRepository scmsOrderPayRepository;
    
    @Autowired
    private ScmsGoodsInventoryRepository scmsGoodsInventoryRepository;
    
    @Autowired
    private ScmsCustomerInfoRepository scmsCustomerInfoRepository;

	@Override
	public JSONObject getList(Pageable page, ScmsOrderInfo scmsOrderInfo, Map<String, Object> params) {
		List<Map<String, Object>> list = scmsOrderInfoRepository.getList(scmsOrderInfo, params, page.getPageNumber(), page.getPageSize());
		int count = scmsOrderInfoRepository.getListCount(scmsOrderInfo, params);
		return RestfulRetUtils.getRetSuccessWithPage(list, count);	
	}

	@Override
	public JSONObject addLsdOrder(Map<String, Object> params, UserInfo userInfo) {
	    ScmsOrderInfo scmsOrderInfo = (ScmsOrderInfo) BeanUtils.mapToBean(params, ScmsOrderInfo.class);
	    //设置商户名称
	    ScmsMerchantsInfo scmsMerchantsInfo = scmsMerchantsInfoRepository.findOne(scmsOrderInfo.getMerchantsId());
	    scmsOrderInfo.setMerchantsName(scmsMerchantsInfo.getMerchantsName());
	    //设置订单编号，格式：订单类型+年月日时分秒毫秒
	    scmsOrderInfo.setOrderNum(scmsOrderInfo.getOrderType() + DateUtils.getDate("yyyyMMddHHmmssSSS"));
	    scmsOrderInfo.setOrderStatus("1");         //订单状态：未修改
	    scmsOrderInfo.setOrderSendStatus("1");     //发货状态：未发货
		scmsOrderInfo.setIsValid(Constants.IS_VALID_VALID);
		scmsOrderInfo.setCreateBy(userInfo.getUserId());
		scmsOrderInfo.setCreateByName(userInfo.getUserName());
		scmsOrderInfo.setCreateDate(new Date());
		scmsOrderInfo = scmsOrderInfoRepository.saveAndFlush(scmsOrderInfo);

        //保存订单商品关联信息
        String orderGoodsList = MapUtils.getString(params, "orderGoodsList");
        JSONArray jsonArray = JSONObject.parseArray(orderGoodsList);
        if(jsonArray != null && jsonArray.size() > 0) {
            ScmsOrderGoods obj = null;
            List<ScmsGoodsInventory> tmpList = null;
            for(int i = 0; i < jsonArray.size(); i++) {
                obj = JSONObject.toJavaObject(jsonArray.getJSONObject(i), ScmsOrderGoods.class);
                if(obj != null) {
                    obj.setOrderId(scmsOrderInfo.getId());
                    scmsOrderGoodsRepository.save(obj);
                    //更新库存
                    tmpList = scmsGoodsInventoryRepository.findByShopIdAndGoodsIdAndColorIdAndInventorySizeIdAndTextureId(scmsOrderInfo.getShopId(), 
                            obj.getGoodsId(), obj.getGoodsColorId(), obj.getGoodsSizeId(), obj.getGoodsTextureId());
                    if(tmpList != null && tmpList.size() > 0) {
                        scmsGoodsInventoryRepository.updateGoodsInventoryNum(tmpList.get(0).getInventoryNum() - obj.getGoodsOrderNum(), 
                                scmsOrderInfo.getShopId(), obj.getGoodsId(), obj.getGoodsColorId(), obj.getGoodsSizeId(), obj.getGoodsTextureId());
                    }else {
                        //如果库存不存在，则新增库存信息
                        ScmsGoodsInventory scmsGoodsInventory = new ScmsGoodsInventory();
                        scmsGoodsInventory.setShopId(scmsOrderInfo.getShopId());
                        scmsGoodsInventory.setGoodsId(obj.getGoodsId());
                        scmsGoodsInventory.setGoodsBarcode(obj.getGoodsBarcode());
                        scmsGoodsInventory.setColorId(obj.getGoodsColorId());
                        scmsGoodsInventory.setColorName(obj.getGoodsColorName());
                        scmsGoodsInventory.setTextureId(obj.getGoodsTextureId());
                        scmsGoodsInventory.setTextureName(obj.getGoodsTextureName());
                        scmsGoodsInventory.setInventorySizeId(obj.getGoodsSizeId());
                        scmsGoodsInventory.setInventorySize(obj.getGoodsSizeName());
                        scmsGoodsInventory.setInventoryNum(-obj.getGoodsOrderNum());
                        scmsGoodsInventoryRepository.save(scmsGoodsInventory);
                    }
                }
            }
        }

        //保存订单支付信息
        String orderPayList = MapUtils.getString(params, "orderPayList");
        jsonArray = JSONObject.parseArray(orderPayList);
        if(jsonArray != null && jsonArray.size() > 0) {
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
        Long customerBalance = MapUtils.getLong(params, "customerBalance", null);
        if(scmsOrderInfo.getCustomerId() != null && !scmsOrderInfo.getCustomerId().equals(-1L) && customerBalance != null) {
            scmsCustomerInfoRepository.updateCustomerBalance(customerBalance, scmsOrderInfo.getCustomerId());
        }
		return RestfulRetUtils.getRetSuccess();
	}

	@Override
	public JSONObject edit(Map<String, Object> params, UserInfo userInfo) {
        ScmsOrderInfo scmsOrderInfo = (ScmsOrderInfo) BeanUtils.mapToBean(params, ScmsOrderInfo.class);
		ScmsOrderInfo scmsOrderInfoInDb = scmsOrderInfoRepository.findOne(scmsOrderInfo.getId());
		if(scmsOrderInfoInDb == null){
			return RestfulRetUtils.getErrorMsg("51006","当前编辑的对象不存在");
		}
		//合并两个javabean
		BeanUtils.mergeBean(scmsOrderInfo, scmsOrderInfoInDb);
		scmsOrderInfoRepository.save(scmsOrderInfoInDb);
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
			scmsOrderInfoRepository.delEntity(Constants.IS_VALID_INVALID, idList);
		}
		return RestfulRetUtils.getRetSuccess();
	}

}
