/*
 * Copyright(c) 2018 gimplatform(通用信息管理平台) All rights reserved.
 */
package com.scms.modules.goods.service.impl;

import java.util.Date;
import java.util.List;
import java.util.Map;
import org.springframework.stereotype.Service;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.gimplatform.core.service.DictService;
import com.gimplatform.core.utils.RestfulRetUtils;
import com.gimplatform.core.utils.SessionUtils;
import com.gimplatform.core.utils.StringUtils;
import com.scms.modules.goods.service.ScmsGoodsInventoryFlowService;
import com.scms.modules.goods.service.ScmsGoodsInventoryService;
import com.scms.modules.order.entity.ScmsOrderGoodsDetail;
import com.scms.modules.goods.entity.ScmsGoodsInventory;
import com.scms.modules.goods.entity.ScmsGoodsInventoryFlow;
import com.scms.modules.goods.repository.ScmsGoodsInventoryRepository;

@Service
public class ScmsGoodsInventoryServiceImpl implements ScmsGoodsInventoryService {
    
    protected static final Logger logger = LogManager.getLogger(ScmsGoodsInventoryService.class);
	
    @Autowired
    private ScmsGoodsInventoryRepository scmsGoodsInventoryRepository;

    @Autowired
    private ScmsGoodsInventoryFlowService scmsGoodsInventoryFlowService;

    @Autowired
    private DictService dictService;

	@Override
	public JSONObject getList(Pageable page, ScmsGoodsInventory scmsGoodsInventory, Map<String, Object> params) {
		List<Map<String, Object>> list = scmsGoodsInventoryRepository.getList(scmsGoodsInventory, params, page.getPageNumber(), page.getPageSize());
		int count = scmsGoodsInventoryRepository.getListCount(scmsGoodsInventory, params);
		return RestfulRetUtils.getRetSuccessWithPage(list, count);	
	}

    @Override
    public List<ScmsGoodsInventory> findInventory(Long shopId, Long goodsId, Long colorId, Long inventorySizeId, Long textureId) {
        return scmsGoodsInventoryRepository.findByShopIdAndGoodsIdAndColorIdAndInventorySizeIdAndTextureId(shopId, goodsId, colorId, inventorySizeId, textureId);
    }

    @Override
    public void saveInventory(ScmsGoodsInventory scmsGoodsInventory) {
        saveInventory(scmsGoodsInventory, "", "", "");
    }

    @Override
    public void saveInventory(ScmsGoodsInventory scmsGoodsInventory, String orderNum, String orderType, String operatePrefix) {
        scmsGoodsInventoryRepository.save(scmsGoodsInventory);
        //如果是新增库存(不是订单的前提下)，同时库存的变化值为0，则不用记录库存流水
        if(StringUtils.isBlank(orderType) && scmsGoodsInventory.getInventoryNum().equals(0L)) {
            logger.debug("初始化库存值为0，不用更新库存流水");
            return;
        }
        //保存库存流水（库存变更记录）
        addGoodsInventoryFlow(scmsGoodsInventory, orderNum, orderType, operatePrefix, 0L, scmsGoodsInventory.getInventoryNum());
    }

    @Override
    public void updateGoodsInventoryNum(String orderNum, String orderType, String operatePrefix, Long inventoryNum, Long shopId, Long goodsId, Long colorId, Long inventorySizeId, Long textureId) {
        List<ScmsGoodsInventory> list = scmsGoodsInventoryRepository.findByShopIdAndGoodsIdAndColorIdAndInventorySizeIdAndTextureId(shopId, goodsId, colorId, inventorySizeId, textureId);
        if(list != null && list.size() > 0) {
            ScmsGoodsInventory scmsGoodsInventory = list.get(0);
            //更新库存
            scmsGoodsInventoryRepository.updateGoodsInventoryNum(inventoryNum, shopId, goodsId, colorId, inventorySizeId, textureId);
            //添加库存流水
            addGoodsInventoryFlow(scmsGoodsInventory, orderNum, orderType, operatePrefix, scmsGoodsInventory.getInventoryNum(), inventoryNum);
        }
    }

    @Override
    public void updateOrderInventory(String orderNum, String orderType, String operatePrefix, Long merchantsId, Long shopId, Long goodsId, ScmsOrderGoodsDetail scmsOrderGoodsDetail, String updateType) {
        //更新库存
        List<ScmsGoodsInventory> tmpList = scmsGoodsInventoryRepository.findByShopIdAndGoodsIdAndColorIdAndInventorySizeIdAndTextureId(shopId, 
                goodsId, scmsOrderGoodsDetail.getGoodsColorId(), scmsOrderGoodsDetail.getGoodsSizeId(), scmsOrderGoodsDetail.getGoodsTextureId());
        if(tmpList != null && tmpList.size() > 0) {
            ScmsGoodsInventory scmsGoodsInventory = tmpList.get(0);
            if("del".equals(updateType)) {
                Long inventoryNum = scmsGoodsInventory.getInventoryNum() - scmsOrderGoodsDetail.getGoodsOrderNum();
                //更新库存
                scmsGoodsInventoryRepository.updateGoodsInventoryNum(inventoryNum, 
                        shopId, 
                        goodsId, 
                        scmsOrderGoodsDetail.getGoodsColorId(), 
                        scmsOrderGoodsDetail.getGoodsSizeId(), 
                        scmsOrderGoodsDetail.getGoodsTextureId());
                //添加库存流水
                addGoodsInventoryFlow(scmsGoodsInventory, orderNum, orderType, operatePrefix, scmsGoodsInventory.getInventoryNum(), inventoryNum);
            }
            else if("add".equals(updateType)) {
                Long inventoryNum = scmsGoodsInventory.getInventoryNum() + scmsOrderGoodsDetail.getGoodsOrderNum();
                //更新库存
                scmsGoodsInventoryRepository.updateGoodsInventoryNum(inventoryNum, 
                        shopId, 
                        goodsId, 
                        scmsOrderGoodsDetail.getGoodsColorId(), 
                        scmsOrderGoodsDetail.getGoodsSizeId(), 
                        scmsOrderGoodsDetail.getGoodsTextureId());
                //添加库存流水
                addGoodsInventoryFlow(scmsGoodsInventory, orderNum, orderType, operatePrefix, scmsGoodsInventory.getInventoryNum(), inventoryNum);
            }
        }else {
            //如果库存不存在，则新增库存信息
            ScmsGoodsInventory scmsGoodsInventory = new ScmsGoodsInventory();
            scmsGoodsInventory.setMerchantsId(merchantsId);
            scmsGoodsInventory.setShopId(shopId);
            scmsGoodsInventory.setGoodsId(goodsId);
            scmsGoodsInventory.setGoodsBarcode(scmsOrderGoodsDetail.getGoodsBarcode());
            scmsGoodsInventory.setColorId(scmsOrderGoodsDetail.getGoodsColorId());
            scmsGoodsInventory.setColorName(scmsOrderGoodsDetail.getGoodsColorName());
            scmsGoodsInventory.setTextureId(scmsOrderGoodsDetail.getGoodsTextureId());
            scmsGoodsInventory.setTextureName(scmsOrderGoodsDetail.getGoodsTextureName());
            scmsGoodsInventory.setInventorySizeId(scmsOrderGoodsDetail.getGoodsSizeId());
            scmsGoodsInventory.setInventorySize(scmsOrderGoodsDetail.getGoodsSizeName());
            if("del".equals(updateType)) scmsGoodsInventory.setInventoryNum(-scmsOrderGoodsDetail.getGoodsOrderNum());
            else if("add".equals(updateType)) scmsGoodsInventory.setInventoryNum(scmsOrderGoodsDetail.getGoodsOrderNum());
            saveInventory(scmsGoodsInventory, orderNum, orderType, operatePrefix);
        }
    }
    
    /**
     * 添加库存流水
     * @param scmsGoodsInventory
     * @param orderNum
     * @param orderType
     * @param operatePrefix 操作前缀：创建/取消
     * @param oldNum
     * @param newNum
     */
    private void addGoodsInventoryFlow(ScmsGoodsInventory scmsGoodsInventory, String orderNum, String orderType, String operatePrefix, Long oldNum, Long newNum) {
        //判断旧的值和新的库存值是否一致，如果一致，则不用更新
        if(oldNum.equals(newNum)) {
            logger.debug("新旧库存一致，不用更新库存流水");
            return;
        }
        String operateName = "";
        if(StringUtils.isBlank(orderType)) operateName = "初始化库存";
        else {
            //获取订单类型字典
            JSONArray jsonArray = dictService.getDictDataByDictTypeValue("SCMS_ORDER_TYPE", SessionUtils.getUserInfo());
            for(int i = 0; i < jsonArray.size(); i++) {
                JSONObject json = jsonArray.getJSONObject(i);
                if(orderType.equals(json.get("ID"))) {
                    operateName = operatePrefix + json.getString("NAME");
                    break;
                }
            }
        }
        //更新库存流水
        ScmsGoodsInventoryFlow scmsGoodsInventoryFlow = new ScmsGoodsInventoryFlow();
        scmsGoodsInventoryFlow.setMerchantsId(scmsGoodsInventory.getMerchantsId());
        scmsGoodsInventoryFlow.setShopId(scmsGoodsInventory.getShopId());
        scmsGoodsInventoryFlow.setGoodsId(scmsGoodsInventory.getGoodsId());
        scmsGoodsInventoryFlow.setGoodsBarcode(scmsGoodsInventory.getGoodsBarcode());
        scmsGoodsInventoryFlow.setColorId(scmsGoodsInventory.getColorId());
        scmsGoodsInventoryFlow.setColorName(scmsGoodsInventory.getColorName());
        scmsGoodsInventoryFlow.setTextureId(scmsGoodsInventory.getTextureId());
        scmsGoodsInventoryFlow.setTextureName(scmsGoodsInventory.getTextureName());
        scmsGoodsInventoryFlow.setSizeId(scmsGoodsInventory.getInventorySizeId());
        scmsGoodsInventoryFlow.setSizeName(scmsGoodsInventory.getInventorySize());
        scmsGoodsInventoryFlow.setOldNum(oldNum);
        scmsGoodsInventoryFlow.setNewNum(newNum);
        scmsGoodsInventoryFlow.setOperateName(operateName);
        scmsGoodsInventoryFlow.setOrderNum(orderNum);             //初始化库存操作不存在订单号和订单类型
        scmsGoodsInventoryFlow.setOrderType(orderType);
        scmsGoodsInventoryFlow.setCreateDate(new Date());
        scmsGoodsInventoryFlowService.add(scmsGoodsInventoryFlow);
    }
}
