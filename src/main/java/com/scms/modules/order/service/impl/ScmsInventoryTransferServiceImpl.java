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
import com.gimplatform.core.utils.BeanUtils;
import com.gimplatform.core.utils.DateUtils;
import com.gimplatform.core.utils.RestfulRetUtils;
import com.gimplatform.core.utils.StringUtils;

import com.scms.modules.order.service.ScmsInventoryTransferService;
import com.scms.modules.base.entity.ScmsMerchantsInfo;
import com.scms.modules.base.repository.ScmsMerchantsInfoRepository;
import com.scms.modules.goods.entity.ScmsGoodsInventory;
import com.scms.modules.goods.repository.ScmsGoodsInventoryRepository;
import com.scms.modules.order.entity.ScmsInventoryTransfer;
import com.scms.modules.order.entity.ScmsInventoryTransferGoods;
import com.scms.modules.order.entity.ScmsInventoryTransferGoodsDetail;
import com.scms.modules.order.repository.ScmsInventoryTransferGoodsDetailRepository;
import com.scms.modules.order.repository.ScmsInventoryTransferGoodsRepository;
import com.scms.modules.order.repository.ScmsInventoryTransferRepository;

@Service
public class ScmsInventoryTransferServiceImpl implements ScmsInventoryTransferService {
	
    @Autowired
    private ScmsInventoryTransferRepository scmsInventoryTransferRepository;
    
    @Autowired
    private ScmsMerchantsInfoRepository scmsMerchantsInfoRepository;
    
    @Autowired
    private ScmsInventoryTransferGoodsRepository scmsInventoryTransferGoodsRepository;
    
    @Autowired
    private ScmsInventoryTransferGoodsDetailRepository scmsInventoryTransferGoodsDetailRepository;
    
    @Autowired
    private ScmsGoodsInventoryRepository scmsGoodsInventoryRepository;

	@Override
	public JSONObject getList(Pageable page, ScmsInventoryTransfer scmsInventoryTransfer, Map<String, Object> params) {
		List<Map<String, Object>> list = scmsInventoryTransferRepository.getList(scmsInventoryTransfer, params, page.getPageNumber(), page.getPageSize());
		int count = scmsInventoryTransferRepository.getListCount(scmsInventoryTransfer, params);
		return RestfulRetUtils.getRetSuccessWithPage(list, count);	
	}

	@Override
	public JSONObject add(Map<String, Object> params, UserInfo userInfo) {
	    ScmsInventoryTransfer scmsInventoryTransfer = (ScmsInventoryTransfer) BeanUtils.mapToBean(params, ScmsInventoryTransfer.class);
        //设置商户名称
        ScmsMerchantsInfo scmsMerchantsInfo = scmsMerchantsInfoRepository.findOne(scmsInventoryTransfer.getMerchantsId());
        scmsInventoryTransfer.setMerchantsName(scmsMerchantsInfo.getMerchantsName());
        //设置订单编号，格式：订单类型+年月日时分秒毫秒
        scmsInventoryTransfer.setOrderNum("dhd" + DateUtils.getDate("yyyyMMddHHmmssSSS"));
        scmsInventoryTransfer.setOrderStatus("1");         //订单状态：已完成
		scmsInventoryTransfer.setIsValid(Constants.IS_VALID_VALID);
		scmsInventoryTransfer.setCreateBy(userInfo.getUserId());
		scmsInventoryTransfer.setCreateByName(userInfo.getUserName());
		scmsInventoryTransfer.setCreateDate(new Date());
		scmsInventoryTransfer = scmsInventoryTransferRepository.saveAndFlush(scmsInventoryTransfer);

        //保存订单商品关联信息,并更新仓库信息
        String orderGoodsList = MapUtils.getString(params, "orderGoodsList");
        JSONArray jsonArray = JSONObject.parseArray(orderGoodsList);
        if(jsonArray != null && jsonArray.size() > 0) {
            for(int i = 0; i < jsonArray.size(); i++) {
                JSONObject json = jsonArray.getJSONObject(i);
                //先保存ScmsInventoryTransferGoods
                ScmsInventoryTransferGoods scmsInventoryTransferGoods = JSONObject.toJavaObject(json, ScmsInventoryTransferGoods.class);
                if(scmsInventoryTransferGoods != null) {
                    scmsInventoryTransferGoods.setOrderId(scmsInventoryTransfer.getId());
                    scmsInventoryTransferGoods = scmsInventoryTransferGoodsRepository.save(scmsInventoryTransferGoods);
                    //保存详细信息表ScmsInventoryTransferGoodsDetail
                    JSONArray InventoryTransferGoodsDetailListJsonArray = json.getJSONArray("goodsDataList");
                    if(InventoryTransferGoodsDetailListJsonArray != null && InventoryTransferGoodsDetailListJsonArray.size() > 0) {
                        for(int j = 0; j < InventoryTransferGoodsDetailListJsonArray.size(); j++) {
                            //先保存ScmsInventoryTransferGoodsDetail
                            ScmsInventoryTransferGoodsDetail scmsInventoryTransferGoodsDetail = JSONObject.toJavaObject(InventoryTransferGoodsDetailListJsonArray.getJSONObject(j), ScmsInventoryTransferGoodsDetail.class);
                            if(scmsInventoryTransferGoodsDetail != null) {
                                scmsInventoryTransferGoodsDetail.setOrderId(scmsInventoryTransfer.getId());
                                scmsInventoryTransferGoodsDetail.setDetailId(scmsInventoryTransferGoods.getId());
                                scmsInventoryTransferGoodsDetailRepository.save(scmsInventoryTransferGoodsDetail);
                                
                                //更新库存，首先获取源库存和目标库存
                                List<ScmsGoodsInventory> srcGoodsInventoryList = scmsGoodsInventoryRepository.findByShopIdAndGoodsIdAndColorIdAndInventorySizeIdAndTextureId(scmsInventoryTransfer.getSrcShopId(), 
                                        scmsInventoryTransferGoods.getGoodsId(), scmsInventoryTransferGoodsDetail.getGoodsColorId(), scmsInventoryTransferGoodsDetail.getGoodsSizeId(), scmsInventoryTransferGoodsDetail.getGoodsTextureId());
                                //减去源库存的对应商品数
                                if(srcGoodsInventoryList != null && srcGoodsInventoryList.size() > 0) {
                                    scmsGoodsInventoryRepository.updateGoodsInventoryNum(srcGoodsInventoryList.get(0).getInventoryNum() - scmsInventoryTransferGoodsDetail.getGoodsOrderNum(), scmsInventoryTransfer.getSrcShopId(), 
                                            scmsInventoryTransferGoods.getGoodsId(), scmsInventoryTransferGoodsDetail.getGoodsColorId(), scmsInventoryTransferGoodsDetail.getGoodsSizeId(), scmsInventoryTransferGoodsDetail.getGoodsTextureId());
                                    //增加目标库存的商品数
                                    List<ScmsGoodsInventory> destGoodsInventoryList = scmsGoodsInventoryRepository.findByShopIdAndGoodsIdAndColorIdAndInventorySizeIdAndTextureId(scmsInventoryTransfer.getDestShopId(), 
                                            scmsInventoryTransferGoods.getGoodsId(), scmsInventoryTransferGoodsDetail.getGoodsColorId(), scmsInventoryTransferGoodsDetail.getGoodsSizeId(), scmsInventoryTransferGoodsDetail.getGoodsTextureId());
                                    if(destGoodsInventoryList != null && destGoodsInventoryList.size() > 0) {
                                        scmsGoodsInventoryRepository.updateGoodsInventoryNum(destGoodsInventoryList.get(0).getInventoryNum() + scmsInventoryTransferGoodsDetail.getGoodsOrderNum(), scmsInventoryTransfer.getDestShopId(), 
                                                scmsInventoryTransferGoods.getGoodsId(), scmsInventoryTransferGoodsDetail.getGoodsColorId(), scmsInventoryTransferGoodsDetail.getGoodsSizeId(), scmsInventoryTransferGoodsDetail.getGoodsTextureId());
                                    }else {
                                        //如果库存不存在，则新增库存信息
                                        ScmsGoodsInventory scmsGoodsInventory = new ScmsGoodsInventory();
                                        scmsGoodsInventory.setShopId(scmsInventoryTransfer.getDestShopId());
                                        scmsGoodsInventory.setGoodsId(scmsInventoryTransferGoods.getGoodsId());
                                        scmsGoodsInventory.setGoodsBarcode(scmsInventoryTransferGoodsDetail.getGoodsBarcode());
                                        scmsGoodsInventory.setColorId(scmsInventoryTransferGoodsDetail.getGoodsColorId());
                                        scmsGoodsInventory.setColorName(scmsInventoryTransferGoodsDetail.getGoodsColorName());
                                        scmsGoodsInventory.setTextureId(scmsInventoryTransferGoodsDetail.getGoodsTextureId());
                                        scmsGoodsInventory.setTextureName(scmsInventoryTransferGoodsDetail.getGoodsTextureName());
                                        scmsGoodsInventory.setInventorySizeId(scmsInventoryTransferGoodsDetail.getGoodsSizeId());
                                        scmsGoodsInventory.setInventorySize(scmsInventoryTransferGoodsDetail.getGoodsSizeName());
                                        scmsGoodsInventory.setInventoryNum(scmsInventoryTransferGoodsDetail.getGoodsOrderNum());
                                        scmsGoodsInventoryRepository.save(scmsGoodsInventory);
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }

		return RestfulRetUtils.getRetSuccess();
	}

	@Override
	public JSONObject repeal(String idsList, UserInfo userInfo) {
		String[] ids = idsList.split(",");
		ScmsInventoryTransfer scmsInventoryTransfer = null;
		for (int i = 0; i < ids.length; i++) {
		    Long id = StringUtils.toLong(ids[i], null);
		    if(id != null) {
		        //首先获取记录
		        scmsInventoryTransfer = scmsInventoryTransferRepository.findOne(id);
		        //更新状态
		        scmsInventoryTransfer.setOrderStatus("2");
		        scmsInventoryTransferRepository.save(scmsInventoryTransfer);
		        //获取商品列表
	            List<ScmsInventoryTransferGoods> inventoryTransferGoodsList = scmsInventoryTransferGoodsRepository.findByOrderId(id);
	            for(ScmsInventoryTransferGoods inventoryTransferGoods : inventoryTransferGoodsList) {
	                List<ScmsInventoryTransferGoodsDetail> inventoryTransferGoodsDetalList = scmsInventoryTransferGoodsDetailRepository.findByDetailId(inventoryTransferGoods.getId());
	                for(ScmsInventoryTransferGoodsDetail scmsInventoryTransferGoodsDetail : inventoryTransferGoodsDetalList) {
	                    //首先加上源库存的值，然后减去目标库存的值
                        List<ScmsGoodsInventory> srcGoodsInventoryList = scmsGoodsInventoryRepository.findByShopIdAndGoodsIdAndColorIdAndInventorySizeIdAndTextureId(scmsInventoryTransfer.getSrcShopId(), 
                                inventoryTransferGoods.getGoodsId(), scmsInventoryTransferGoodsDetail.getGoodsColorId(), scmsInventoryTransferGoodsDetail.getGoodsSizeId(), scmsInventoryTransferGoodsDetail.getGoodsTextureId());
                        //减去源库存的对应商品数
                        if(srcGoodsInventoryList != null && srcGoodsInventoryList.size() > 0) {
                            scmsGoodsInventoryRepository.updateGoodsInventoryNum(srcGoodsInventoryList.get(0).getInventoryNum() + scmsInventoryTransferGoodsDetail.getGoodsOrderNum(), scmsInventoryTransfer.getSrcShopId(), 
                                    inventoryTransferGoods.getGoodsId(), scmsInventoryTransferGoodsDetail.getGoodsColorId(), scmsInventoryTransferGoodsDetail.getGoodsSizeId(), scmsInventoryTransferGoodsDetail.getGoodsTextureId());
                            //增加目标库存的商品数
                            List<ScmsGoodsInventory> destGoodsInventoryList = scmsGoodsInventoryRepository.findByShopIdAndGoodsIdAndColorIdAndInventorySizeIdAndTextureId(scmsInventoryTransfer.getDestShopId(), 
                                    inventoryTransferGoods.getGoodsId(), scmsInventoryTransferGoodsDetail.getGoodsColorId(), scmsInventoryTransferGoodsDetail.getGoodsSizeId(), scmsInventoryTransferGoodsDetail.getGoodsTextureId());
                            if(destGoodsInventoryList != null && destGoodsInventoryList.size() > 0) {
                                scmsGoodsInventoryRepository.updateGoodsInventoryNum(destGoodsInventoryList.get(0).getInventoryNum() - scmsInventoryTransferGoodsDetail.getGoodsOrderNum(), scmsInventoryTransfer.getDestShopId(), 
                                        inventoryTransferGoods.getGoodsId(), scmsInventoryTransferGoodsDetail.getGoodsColorId(), scmsInventoryTransferGoodsDetail.getGoodsSizeId(), scmsInventoryTransferGoodsDetail.getGoodsTextureId());
                            }
                        }
	                }
	            }
		    }
		}
		return RestfulRetUtils.getRetSuccess();
	}

}
