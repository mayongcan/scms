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
import com.scms.modules.customer.entity.ScmsCustomerInfo;
import com.scms.modules.customer.entity.ScmsSupplierInfo;
import com.scms.modules.customer.repository.ScmsCustomerInfoRepository;
import com.scms.modules.customer.repository.ScmsSupplierInfoRepository;
import com.scms.modules.goods.service.ScmsGoodsInventoryService;
import com.scms.modules.order.entity.ScmsOrderGoods;
import com.scms.modules.order.entity.ScmsOrderGoodsDetail;
import com.scms.modules.order.entity.ScmsOrderInfo;
import com.scms.modules.order.entity.ScmsOrderModifyLog;
import com.scms.modules.order.entity.ScmsOrderPay;
import com.scms.modules.order.repository.ScmsOrderGoodsDetailRepository;
import com.scms.modules.order.repository.ScmsOrderGoodsRepository;
import com.scms.modules.order.repository.ScmsOrderInfoRepository;
import com.scms.modules.order.repository.ScmsOrderModifyLogRepository;
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
    private ScmsOrderGoodsDetailRepository scmsOrderGoodsDetailRepository;
    
    @Autowired
    private ScmsOrderPayRepository scmsOrderPayRepository;
    
    @Autowired
    private ScmsOrderModifyLogRepository scmsOrderModifyLogRepository;
    
    @Autowired
    private ScmsGoodsInventoryService scmsGoodsInventoryService;
    
    @Autowired
    private ScmsCustomerInfoRepository scmsCustomerInfoRepository;

    @Autowired
    private ScmsSupplierInfoRepository scmsSupplierInfoRepository;

	@Override
	public JSONObject getList(Pageable page, ScmsOrderInfo scmsOrderInfo, Map<String, Object> params) {
		List<Map<String, Object>> list = scmsOrderInfoRepository.getList(scmsOrderInfo, params, page.getPageNumber(), page.getPageSize());
		int count = scmsOrderInfoRepository.getListCount(scmsOrderInfo, params);
		return RestfulRetUtils.getRetSuccessWithPage(list, count);	
	}

	@Override
	public JSONObject addOrderInfo(Map<String, Object> params, UserInfo userInfo) {
	    ScmsOrderInfo scmsOrderInfo = (ScmsOrderInfo) BeanUtils.mapToBean(params, ScmsOrderInfo.class);
	    //设置商户名称
	    ScmsMerchantsInfo scmsMerchantsInfo = scmsMerchantsInfoRepository.findOne(scmsOrderInfo.getMerchantsId());
	    scmsOrderInfo.setMerchantsName(scmsMerchantsInfo.getMerchantsName());
	    //设置订单编号，格式：订单类型+年月日时分秒毫秒
	    scmsOrderInfo.setOrderNum(scmsOrderInfo.getOrderType() + DateUtils.getDate("yyyyMMddHHmmssSSS"));
	    scmsOrderInfo.setOrderCustomerType("1");
	    scmsOrderInfo.setOrderStatus("1");         //订单状态：未修改
	    scmsOrderInfo.setOrderSendStatus("1");     //发货状态：未发货
        scmsOrderInfo.setOrderReceiveStatus("1");       //收货状态：未收货
		scmsOrderInfo.setIsValid(Constants.IS_VALID_VALID);
		scmsOrderInfo.setCreateBy(userInfo.getUserId());
		scmsOrderInfo.setCreateByName(userInfo.getUserName());
		scmsOrderInfo.setCreateDate(new Date());
		scmsOrderInfo = scmsOrderInfoRepository.saveAndFlush(scmsOrderInfo);

        //保存订单商品关联信息
        String orderGoodsList = MapUtils.getString(params, "orderGoodsList");
        JSONArray jsonArray = JSONObject.parseArray(orderGoodsList);
        if(jsonArray != null && jsonArray.size() > 0) {
            //退货单不增加库存，完成时才增加库存
            if("thd".equals(scmsOrderInfo.getOrderType())) saveOrderGoodsAndDetail(jsonArray, scmsOrderInfo, false, "创建");
            else saveOrderGoodsAndDetail(jsonArray, scmsOrderInfo, true, "创建");
        }

        //保存订单支付信息
        String orderPayList = MapUtils.getString(params, "orderPayList");
        jsonArray = JSONObject.parseArray(orderPayList);
        if(jsonArray != null && jsonArray.size() > 0) {
            saveOrderPayList(jsonArray, scmsOrderInfo, userInfo);
        }
        //更新余额
        updateCustomerBalance(params, scmsOrderInfo);
        
		return RestfulRetUtils.getRetSuccess();
	}

	@Override
	public JSONObject editOrderInfo(Map<String, Object> params, UserInfo userInfo) {
        ScmsOrderInfo scmsOrderInfo = (ScmsOrderInfo) BeanUtils.mapToBean(params, ScmsOrderInfo.class);
		ScmsOrderInfo scmsOrderInfoInDb = scmsOrderInfoRepository.findOne(scmsOrderInfo.getId());
		if(scmsOrderInfoInDb == null){
			return RestfulRetUtils.getErrorMsg("51006","当前编辑的对象不存在");
		}
		//合并两个javabean
		BeanUtils.mergeBean(scmsOrderInfo, scmsOrderInfoInDb);
		scmsOrderInfoRepository.save(scmsOrderInfoInDb);

        //保存订单商品关联信息
        String orderGoodsList = MapUtils.getString(params, "orderGoodsList");
        JSONArray jsonArray = JSONObject.parseArray(orderGoodsList);
        if(jsonArray != null && jsonArray.size() > 0) {
            //退货单不增加库存，完成时才增加库存
            if("thd".equals(scmsOrderInfo.getOrderType())) {
                //删除旧数据
                scmsOrderGoodsRepository.delByOrderId(scmsOrderInfo.getId());
                scmsOrderGoodsDetailRepository.delByOrderId(scmsOrderInfo.getId());
                //保存
                saveOrderGoodsAndDetail(jsonArray, scmsOrderInfo, false, "编辑");
            }else {
                //首先删除旧数据，删除数据之前，需要将库存补回去
                List<ScmsOrderGoods> tmpOrderGoodsList = scmsOrderGoodsRepository.findByOrderId(scmsOrderInfo.getId());
                for(ScmsOrderGoods goodsObj : tmpOrderGoodsList) {
                    List<ScmsOrderGoodsDetail> orderGoodsDetailList = scmsOrderGoodsDetailRepository.findByDetailId(goodsObj.getId());
                    for(ScmsOrderGoodsDetail obj : orderGoodsDetailList) {
                        scmsGoodsInventoryService.updateOrderInventory(scmsOrderInfo.getOrderNum(), 
                                scmsOrderInfo.getOrderType(), 
                                "编辑", 
                                scmsOrderInfo.getMerchantsId(),
                                scmsOrderInfo.getShopId(), 
                                goodsObj.getGoodsId(), 
                                obj, 
                                "add");
                    }
                }
                //删除旧数据
                scmsOrderGoodsRepository.delByOrderId(scmsOrderInfo.getId());
                scmsOrderGoodsDetailRepository.delByOrderId(scmsOrderInfo.getId());
                //保存
                saveOrderGoodsAndDetail(jsonArray, scmsOrderInfo, true, "编辑");
            }
        }

        //保存订单支付信息
        String orderPayList = MapUtils.getString(params, "orderPayList");
        jsonArray = JSONObject.parseArray(orderPayList);
        if(jsonArray != null && jsonArray.size() > 0) {
            //删除旧数据
            scmsOrderPayRepository.delByOrderId(scmsOrderInfo.getId());
            //保存
            saveOrderPayList(jsonArray, scmsOrderInfo, userInfo);
        }
        //更新余额
        updateCustomerBalance(params, scmsOrderInfo);
        
        //增加订单修改记录
        saveModifyLog(params, scmsOrderInfo, userInfo);
        
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

    @Override
    public JSONObject updateOrderStatus(String jsonParams, UserInfo userInfo, String status) {
        JSONObject json = JSONObject.parseObject(jsonParams);
        String idsList = json.getString("idsList");
        String orderType = json.getString("orderType");
        if(StringUtils.isBlank(idsList)) return RestfulRetUtils.getErrorParams();
        
        String[] ids = idsList.split(",");
        //判断是否需要移除
        List<Long> idList = new ArrayList<Long>();
        for (int i = 0; i < ids.length; i++) {
            idList.add(StringUtils.toLong(ids[i]));
        }
        //批量更新（设置IsValid 为N）
        if(idList.size() > 0){
            scmsOrderInfoRepository.updateOrderStatus(status, idList);
        }
        ScmsOrderInfo scmsOrderInfo = null;
        ScmsCustomerInfo scmsCustomerInfo = null;
        ScmsSupplierInfo scmsSupplierInfo = null;
        //如果变更的订单状态为取消，则需要做相应的数据库变更
        if("3".equals(status)) {
            //判断订单类型  如果是取消零售单、批发单、预售单，则需要将库存补回去，同时判断客户类型，将已付的金额退回到客户账号
            if("lsd".equals(orderType) || "pfd".equals(orderType) || "ysd".equals(orderType)) {
                for(Long id : idList) {
                    scmsOrderInfo = scmsOrderInfoRepository.findOne(id);
                    List<ScmsOrderGoods> orderGoodsList = scmsOrderGoodsRepository.findByOrderId(id);
                    for(ScmsOrderGoods orderGoods : orderGoodsList) {
                        List<ScmsOrderGoodsDetail> orderGoodsDetalList = scmsOrderGoodsDetailRepository.findByDetailId(orderGoods.getId());
                        for(ScmsOrderGoodsDetail scmsOrderGoodsDetail : orderGoodsDetalList) {
                            scmsGoodsInventoryService.updateOrderInventory(scmsOrderInfo.getOrderNum(), 
                                    scmsOrderInfo.getOrderType(), 
                                    "取消", 
                                    scmsOrderInfo.getMerchantsId(),
                                    scmsOrderInfo.getShopId(), 
                                    orderGoods.getGoodsId(), 
                                    scmsOrderGoodsDetail, 
                                    "add");
                        }
                    }
                    //更新客户余额, 客户余额 = 原有余额 + 订单总金额 - 订单未支付金额
                    if(scmsOrderInfo.getCustomerId() != null && !scmsOrderInfo.getCustomerId().equals(-1L)) {
                        scmsCustomerInfo = scmsCustomerInfoRepository.findOne(scmsOrderInfo.getCustomerId());
                        if(scmsCustomerInfo != null)  
                            scmsCustomerInfoRepository.updateCustomerBalance(scmsCustomerInfo.getCustomerBalance() + scmsOrderInfo.getTotalAmount() - scmsOrderInfo.getTotalUnPay(), scmsOrderInfo.getCustomerId());
                    }
                }
            }else if("jhd".equals(orderType)) {
                //需要扣除供货商余额，因为是进货
                for(Long id : idList) {
                    scmsOrderInfo = scmsOrderInfoRepository.findOne(id);
                    //更新客户余额, 客户余额 = 原有余额 - 订单总金额 + 订单未支付金额
                    if(scmsOrderInfo.getCustomerId() != null && !scmsOrderInfo.getCustomerId().equals(-1L)) {
                        scmsSupplierInfo = scmsSupplierInfoRepository.findOne(scmsOrderInfo.getCustomerId());
                        if(scmsSupplierInfo != null)  
                            scmsSupplierInfoRepository.updateSupplierBalance(scmsSupplierInfo.getSupplierBalance() - scmsOrderInfo.getTotalAmount() + scmsOrderInfo.getTotalUnPay(), scmsOrderInfo.getCustomerId());
                    }
                }
            }else if("fcd".equals(orderType)) {
                //取消返厂单后，商品退回库存，金额退回供货商
                for(Long id : idList) {
                    scmsOrderInfo = scmsOrderInfoRepository.findOne(id);
                    List<ScmsOrderGoods> orderGoodsList = scmsOrderGoodsRepository.findByOrderId(id);
                    for(ScmsOrderGoods orderGoods : orderGoodsList) {
                        List<ScmsOrderGoodsDetail> orderGoodsDetalList = scmsOrderGoodsDetailRepository.findByDetailId(orderGoods.getId());
                        for(ScmsOrderGoodsDetail scmsOrderGoodsDetail : orderGoodsDetalList) {
                            scmsGoodsInventoryService.updateOrderInventory(scmsOrderInfo.getOrderNum(), 
                                    scmsOrderInfo.getOrderType(), 
                                    "取消", 
                                    scmsOrderInfo.getMerchantsId(),
                                    scmsOrderInfo.getShopId(), 
                                    orderGoods.getGoodsId(), 
                                    scmsOrderGoodsDetail, 
                                    "add");
                        }
                    }
                    //更新客户余额, 客户余额 = 原有余额 + 订单总金额 - 订单未支付金额
                    if(scmsOrderInfo.getCustomerId() != null && !scmsOrderInfo.getCustomerId().equals(-1L)) {
                        scmsSupplierInfo = scmsSupplierInfoRepository.findOne(scmsOrderInfo.getCustomerId());
                        if(scmsSupplierInfo != null)  
                            scmsSupplierInfoRepository.updateSupplierBalance(scmsSupplierInfo.getSupplierBalance() + scmsOrderInfo.getTotalAmount() - scmsOrderInfo.getTotalUnPay(), scmsOrderInfo.getCustomerId());
                    }
                }
            }else if("syd".equals(orderType)) {
                //取消收银单，返回金额到账号。注：如果是合并支付，则金额保存一份到TotalUnPay上
                for(Long id : idList) {
                    scmsOrderInfo = scmsOrderInfoRepository.findOne(id);
                    if("1".equals(scmsOrderInfo.getOrderCustomerType())) {
                        if(scmsOrderInfo.getCustomerId() != null && !scmsOrderInfo.getCustomerId().equals(-1L)) {
                            scmsCustomerInfo = scmsCustomerInfoRepository.findOne(scmsOrderInfo.getCustomerId());
                            if(scmsCustomerInfo != null)  
                                scmsCustomerInfoRepository.updateCustomerBalance(scmsCustomerInfo.getCustomerBalance() + scmsOrderInfo.getTotalUnPay(), scmsOrderInfo.getCustomerId());
                        }
                    }else if("2".equals(scmsOrderInfo.getOrderCustomerType())) {
                        if(scmsOrderInfo.getCustomerId() != null && !scmsOrderInfo.getCustomerId().equals(-1L)) {
                            scmsSupplierInfo = scmsSupplierInfoRepository.findOne(scmsOrderInfo.getCustomerId());
                            if(scmsSupplierInfo != null)  
                                scmsSupplierInfoRepository.updateSupplierBalance(scmsSupplierInfo.getSupplierBalance() + scmsOrderInfo.getTotalUnPay(), scmsOrderInfo.getCustomerId());
                        }
                    }
                }
            }
        }else if("4".equals(status)) {
            //如果订单类型为进货单，则订单结束后，进货单里面的东西需要入库
            if("jhd".equals(orderType)) {
                for(Long id : idList) {
                    scmsOrderInfo = scmsOrderInfoRepository.findOne(id);
                    List<ScmsOrderGoods> orderGoodsList = scmsOrderGoodsRepository.findByOrderId(id);
                    for(ScmsOrderGoods orderGoods : orderGoodsList) {
                        List<ScmsOrderGoodsDetail> orderGoodsDetalList = scmsOrderGoodsDetailRepository.findByDetailId(orderGoods.getId());
                        for(ScmsOrderGoodsDetail scmsOrderGoodsDetail : orderGoodsDetalList) {
                            scmsGoodsInventoryService.updateOrderInventory(scmsOrderInfo.getOrderNum(), 
                                    scmsOrderInfo.getOrderType(), 
                                    "完成", 
                                    scmsOrderInfo.getMerchantsId(),
                                    scmsOrderInfo.getShopId(), 
                                    orderGoods.getGoodsId(), 
                                    scmsOrderGoodsDetail, 
                                    "add");
                        }
                    }
                }
            }else if("thd".equals(orderType)) {
                //退货单完成后，增加库存，增加客户账号余额
                for(Long id : idList) {
                    scmsOrderInfo = scmsOrderInfoRepository.findOne(id);
                    List<ScmsOrderGoods> orderGoodsList = scmsOrderGoodsRepository.findByOrderId(id);
                    for(ScmsOrderGoods orderGoods : orderGoodsList) {
                        List<ScmsOrderGoodsDetail> orderGoodsDetalList = scmsOrderGoodsDetailRepository.findByDetailId(orderGoods.getId());
                        for(ScmsOrderGoodsDetail scmsOrderGoodsDetail : orderGoodsDetalList) {
                            scmsGoodsInventoryService.updateOrderInventory(scmsOrderInfo.getOrderNum(), 
                                    scmsOrderInfo.getOrderType(), 
                                    "完成", 
                                    scmsOrderInfo.getMerchantsId(),
                                    scmsOrderInfo.getShopId(), 
                                    orderGoods.getGoodsId(), 
                                    scmsOrderGoodsDetail, 
                                    "add");
                        }
                    }
                    //更新客户余额(由于出单后不补钱给客户，因此计算公式需要加入账号余额支付的值), 客户余额 = 原有余额 + 订单未支付金额 + 账户使用余额支付的金额
                    if(scmsOrderInfo.getCustomerId() != null && !scmsOrderInfo.getCustomerId().equals(-1L)) {
                        scmsCustomerInfo = scmsCustomerInfoRepository.findOne(scmsOrderInfo.getCustomerId());
                        if(scmsCustomerInfo != null)  {
                            List<ScmsOrderPay> tmpPayList = scmsOrderPayRepository.findByOrderIdAndPayTypeId(scmsOrderInfo.getId(), 1L);
                            Double payAmount = 0d;
                            if(tmpPayList != null && tmpPayList.size() > 0) payAmount = tmpPayList.get(0).getPayAmount();
                            scmsCustomerInfoRepository.updateCustomerBalance(scmsCustomerInfo.getCustomerBalance() + scmsOrderInfo.getTotalUnPay() + payAmount, scmsOrderInfo.getCustomerId());
                        }
                    }
                }
            }
        }
        return RestfulRetUtils.getRetSuccess();
    }

    @Override
    public JSONObject getOrderJhdList(Pageable page, ScmsOrderInfo scmsOrderInfo, Map<String, Object> params) {
        List<Map<String, Object>> list = scmsOrderInfoRepository.getOrderJhdList(scmsOrderInfo, params, page.getPageNumber(), page.getPageSize());
        int count = scmsOrderInfoRepository.getOrderJhdListCount(scmsOrderInfo, params);
        return RestfulRetUtils.getRetSuccessWithPage(list, count);  
    }

    @Override
    public JSONObject addOrderJhd(Map<String, Object> params, UserInfo userInfo) {
        ScmsOrderInfo scmsOrderInfo = (ScmsOrderInfo) BeanUtils.mapToBean(params, ScmsOrderInfo.class);
        //设置商户名称
        ScmsMerchantsInfo scmsMerchantsInfo = scmsMerchantsInfoRepository.findOne(scmsOrderInfo.getMerchantsId());
        scmsOrderInfo.setMerchantsName(scmsMerchantsInfo.getMerchantsName());
        //设置订单编号，格式：订单类型+年月日时分秒毫秒
        scmsOrderInfo.setOrderNum(scmsOrderInfo.getOrderType() + DateUtils.getDate("yyyyMMddHHmmssSSS"));
        scmsOrderInfo.setOrderCustomerType("2");
        scmsOrderInfo.setOrderStatus("1");              //订单状态：未修改
        scmsOrderInfo.setOrderSendStatus("1");          //发货状态：未发货
        scmsOrderInfo.setOrderReceiveStatus("1");       //收货状态：未收货
        scmsOrderInfo.setIsValid(Constants.IS_VALID_VALID);
        scmsOrderInfo.setCreateBy(userInfo.getUserId());
        scmsOrderInfo.setCreateByName(userInfo.getUserName());
        scmsOrderInfo.setCreateDate(new Date());
        scmsOrderInfo = scmsOrderInfoRepository.saveAndFlush(scmsOrderInfo);

        //保存订单商品关联信息
        String orderGoodsList = MapUtils.getString(params, "orderGoodsList");
        JSONArray jsonArray = JSONObject.parseArray(orderGoodsList);
        if(jsonArray != null && jsonArray.size() > 0) {
            //不用更新库存
            if("jhd".equals(scmsOrderInfo.getOrderType())) saveOrderGoodsAndDetail(jsonArray, scmsOrderInfo, false, "创建");
            else if("fcd".equals(scmsOrderInfo.getOrderType())) saveOrderGoodsAndDetail(jsonArray, scmsOrderInfo, true, "创建"); //返厂单删除库存
        }

        //保存订单支付信息
        String orderPayList = MapUtils.getString(params, "orderPayList");
        jsonArray = JSONObject.parseArray(orderPayList);
        if(jsonArray != null && jsonArray.size() > 0) {
            saveOrderPayList(jsonArray, scmsOrderInfo, userInfo);
        }
        //更新供货商余额
        updateSupplierBalance(params, scmsOrderInfo);
        
        return RestfulRetUtils.getRetSuccess();
    }

    @Override
    public JSONObject editOrderJhd(Map<String, Object> params, UserInfo userInfo) {
        ScmsOrderInfo scmsOrderInfo = (ScmsOrderInfo) BeanUtils.mapToBean(params, ScmsOrderInfo.class);
        ScmsOrderInfo scmsOrderInfoInDb = scmsOrderInfoRepository.findOne(scmsOrderInfo.getId());
        if(scmsOrderInfoInDb == null){
            return RestfulRetUtils.getErrorMsg("51006","当前编辑的对象不存在");
        }
        //合并两个javabean
        BeanUtils.mergeBean(scmsOrderInfo, scmsOrderInfoInDb);
        scmsOrderInfoRepository.save(scmsOrderInfoInDb);

        //保存订单商品关联信息
        String orderGoodsList = MapUtils.getString(params, "orderGoodsList");
        JSONArray jsonArray = JSONObject.parseArray(orderGoodsList);
        if(jsonArray != null && jsonArray.size() > 0) {
            if("jhd".equals(scmsOrderInfo.getOrderType())) {
                //删除旧数据
                scmsOrderGoodsRepository.delByOrderId(scmsOrderInfo.getId());
                scmsOrderGoodsDetailRepository.delByOrderId(scmsOrderInfo.getId());
                //保存
                saveOrderGoodsAndDetail(jsonArray, scmsOrderInfo, false, "编辑");
            }else if("fcd".equals(scmsOrderInfo.getOrderType())) {
              //首先删除旧数据，删除数据之前，需要将库存补回去
                List<ScmsOrderGoods> tmpOrderGoodsList = scmsOrderGoodsRepository.findByOrderId(scmsOrderInfo.getId());
                for(ScmsOrderGoods goodsObj : tmpOrderGoodsList) {
                    List<ScmsOrderGoodsDetail> orderGoodsDetailList = scmsOrderGoodsDetailRepository.findByDetailId(goodsObj.getId());
                    for(ScmsOrderGoodsDetail obj : orderGoodsDetailList) {
                        scmsGoodsInventoryService.updateOrderInventory(scmsOrderInfo.getOrderNum(), 
                                scmsOrderInfo.getOrderType(), 
                                "编辑", 
                                scmsOrderInfo.getMerchantsId(),
                                scmsOrderInfo.getShopId(), 
                                goodsObj.getGoodsId(), 
                                obj, 
                                "add");
                    }
                }
                //删除旧数据
                scmsOrderGoodsRepository.delByOrderId(scmsOrderInfo.getId());
                scmsOrderGoodsDetailRepository.delByOrderId(scmsOrderInfo.getId());
                //保存
                saveOrderGoodsAndDetail(jsonArray, scmsOrderInfo, true, "编辑");
            }
        }

        //保存订单支付信息
        String orderPayList = MapUtils.getString(params, "orderPayList");
        jsonArray = JSONObject.parseArray(orderPayList);
        if(jsonArray != null && jsonArray.size() > 0) {
            //删除旧数据
            scmsOrderPayRepository.delByOrderId(scmsOrderInfo.getId());
            //保存
            saveOrderPayList(jsonArray, scmsOrderInfo, userInfo);
        }
        //更新供货商余额
        updateSupplierBalance(params, scmsOrderInfo);
        
        //增加订单修改记录
        saveModifyLog(params, scmsOrderInfo, userInfo);
        
        return RestfulRetUtils.getRetSuccess();
    }

    @Override
    public JSONObject getOrderSydList(Pageable page, ScmsOrderInfo scmsOrderInfo, Map<String, Object> params) {
        List<Map<String, Object>> list = scmsOrderInfoRepository.getList(scmsOrderInfo, params, page.getPageNumber(), page.getPageSize());
        int count = scmsOrderInfoRepository.getListCount(scmsOrderInfo, params);
        return RestfulRetUtils.getRetSuccessWithPage(list, count);  
    }

    @Override
    public JSONObject addOrderSyd(Map<String, Object> params, UserInfo userInfo) {
        ScmsOrderInfo scmsOrderInfo = (ScmsOrderInfo) BeanUtils.mapToBean(params, ScmsOrderInfo.class);
        //设置商户名称
        ScmsMerchantsInfo scmsMerchantsInfo = scmsMerchantsInfoRepository.findOne(scmsOrderInfo.getMerchantsId());
        scmsOrderInfo.setMerchantsName(scmsMerchantsInfo.getMerchantsName());
        //设置订单编号，格式：订单类型+年月日时分秒毫秒
        scmsOrderInfo.setOrderNum(scmsOrderInfo.getOrderType() + DateUtils.getDate("yyyyMMddHHmmssSSS"));
        scmsOrderInfo.setOrderStatus("4");              //订单状态：已完成
        scmsOrderInfo.setOrderSendStatus("1");          //发货状态：未发货
        scmsOrderInfo.setOrderReceiveStatus("1");       //收货状态：未收货
        scmsOrderInfo.setIsValid(Constants.IS_VALID_VALID);
        scmsOrderInfo.setCreateBy(userInfo.getUserId());
        scmsOrderInfo.setCreateByName(userInfo.getUserName());
        scmsOrderInfo.setCreateDate(new Date());
        scmsOrderInfo = scmsOrderInfoRepository.saveAndFlush(scmsOrderInfo);

        //保存订单支付信息
        String orderPayList = MapUtils.getString(params, "orderPayList");
        JSONArray jsonArray = JSONObject.parseArray(orderPayList);
        if(jsonArray != null && jsonArray.size() > 0) {
            saveOrderPayList(jsonArray, scmsOrderInfo, userInfo);
        }
        if(scmsOrderInfo.getOrderCustomerType().equals("1")) {
            //更新客户余额
            updateCustomerBalance(params, scmsOrderInfo);
        }else {
            //更新供货商余额
            updateSupplierBalance(params, scmsOrderInfo);
        }
        
        return RestfulRetUtils.getRetSuccess();
    }
    
    /**
     * 保存订单商品和详情，并更新库存
     * @param jsonArray
     * @param scmsOrderInfo
     * @param isUpdateInventory
     * @param operatePrefix
     */
    private void saveOrderGoodsAndDetail(JSONArray jsonArray, ScmsOrderInfo scmsOrderInfo, boolean isUpdateInventory, String operatePrefix) {
        for(int i = 0; i < jsonArray.size(); i++) {
            JSONObject json = jsonArray.getJSONObject(i);
            //先保存ScmsOrderGoods
            ScmsOrderGoods scmsOrderGoods = JSONObject.toJavaObject(json, ScmsOrderGoods.class);
            if(scmsOrderGoods != null) {
                scmsOrderGoods.setOrderId(scmsOrderInfo.getId());
                scmsOrderGoods = scmsOrderGoodsRepository.save(scmsOrderGoods);
                //保存详细信息表ScmsOrderGoodsDetail
                JSONArray orderGoodsDetailListJsonArray = json.getJSONArray("goodsDataList");
                if(orderGoodsDetailListJsonArray != null && orderGoodsDetailListJsonArray.size() > 0) {
                    for(int j = 0; j < orderGoodsDetailListJsonArray.size(); j++) {
                        //先保存ScmsOrderGoodsDetail
                        ScmsOrderGoodsDetail scmsOrderGoodsDetail = JSONObject.toJavaObject(orderGoodsDetailListJsonArray.getJSONObject(j), ScmsOrderGoodsDetail.class);
                        if(scmsOrderGoodsDetail != null) {
                            scmsOrderGoodsDetail.setOrderId(scmsOrderInfo.getId());
                            scmsOrderGoodsDetail.setDetailId(scmsOrderGoods.getId());
                            scmsOrderGoodsDetail.setSendStatus("0");
                            scmsOrderGoodsDetail.setReceiveStatus("0");
                            scmsOrderGoodsDetailRepository.save(scmsOrderGoodsDetail);
                            //判断是否更新库存
                            if(isUpdateInventory) {
                                scmsGoodsInventoryService.updateOrderInventory(scmsOrderInfo.getOrderNum(), 
                                        scmsOrderInfo.getOrderType(), 
                                        operatePrefix, 
                                        scmsOrderInfo.getMerchantsId(),
                                        scmsOrderInfo.getShopId(), 
                                        scmsOrderGoods.getGoodsId(), 
                                        scmsOrderGoodsDetail, 
                                        "del");
                            }
                        }
                    }
                }
            }
        }
    }
    
    /**
     * 保存订单支付列表
     * @param jsonArray
     * @param scmsOrderInfo
     * @param userInfo
     */
    private void saveOrderPayList(JSONArray jsonArray, ScmsOrderInfo scmsOrderInfo, UserInfo userInfo) {
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

    /**
     * 判断是否更新客户余额
     * @param params
     * @param scmsOrderInfo
     */
    private void updateCustomerBalance(Map<String, Object> params, ScmsOrderInfo scmsOrderInfo) {
        //退货单不提前返现给客户余额
        if("thd".equals(scmsOrderInfo.getOrderType())) return;
        //判断是否需要更新客户余额
        Double customerBalance = MapUtils.getDouble(params, "customerBalance", null);
        if(scmsOrderInfo.getCustomerId() != null && !scmsOrderInfo.getCustomerId().equals(-1L) && customerBalance != null) {
            scmsCustomerInfoRepository.updateCustomerBalance(customerBalance, scmsOrderInfo.getCustomerId());
        }
    }

    /**
     * 判断是否更新供货商余额
     * @param params
     * @param scmsOrderInfo
     */
    private void updateSupplierBalance(Map<String, Object> params, ScmsOrderInfo scmsOrderInfo) {
        //判断是否需要更新客户余额
        Double customerBalance = MapUtils.getDouble(params, "customerBalance", null);
        if(scmsOrderInfo.getCustomerId() != null && !scmsOrderInfo.getCustomerId().equals(-1L) && customerBalance != null) {
            scmsSupplierInfoRepository.updateSupplierBalance(customerBalance, scmsOrderInfo.getCustomerId());
        }
    }
    
    /**
     * 保存订单修改记录
     * @param scmsOrderInfo
     * @param userInfo
     */
    private void saveModifyLog(Map<String, Object> params, ScmsOrderInfo scmsOrderInfo, UserInfo userInfo) {
        ScmsOrderModifyLog scmsOrderModifyLog = new ScmsOrderModifyLog();
        scmsOrderModifyLog.setOrderId(scmsOrderInfo.getId());
        scmsOrderModifyLog.setModifyBy(userInfo.getUserId());
        scmsOrderModifyLog.setModifyByName(userInfo.getUserName());
        scmsOrderModifyLog.setModifyDate(new Date());
        scmsOrderModifyLog.setModifyMemo(MapUtils.getString(params, "modifyMemo"));
        scmsOrderModifyLogRepository.save(scmsOrderModifyLog);
    }
}
