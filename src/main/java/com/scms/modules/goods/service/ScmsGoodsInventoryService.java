/*
 * Copyright(c) 2018 gimplatform(通用信息管理平台) All rights reserved.
 */
package com.scms.modules.goods.service;

import java.util.List;
import java.util.Map;
import org.springframework.data.domain.Pageable;
import com.alibaba.fastjson.JSONObject;
import com.scms.modules.goods.entity.ScmsGoodsInventory;
import com.scms.modules.order.entity.ScmsOrderGoodsDetail;

/**
 * 服务类接口
 * @version 1.0
 * @author 
 */
public interface ScmsGoodsInventoryService {
	
	/**
	 * 获取列表
	 * @param page
	 * @param scmsGoodsInventory
	 * @return
	 */
	public JSONObject getList(Pageable page, ScmsGoodsInventory scmsGoodsInventory, Map<String, Object> params);
	
	/**
	 * 获取单个商品的库存统计
	 * @param params
	 * @return
	 */
    public JSONObject getStatisticsGoodsInventory(Map<String, Object> params);
	
	/**
	 * 查找库存
	 * @param shopId
	 * @param goodsId
	 * @param colorId
	 * @param inventorySizeId
	 * @param textureId
	 * @return
	 */
	public List<ScmsGoodsInventory> findInventory(Long shopId, Long goodsId, Long colorId, Long inventorySizeId, Long textureId);
    
    /**
     * 保存库存信息
     * @param scmsGoodsInventory
     */
    public void saveInventory(ScmsGoodsInventory scmsGoodsInventory);
	
	/**
	 * 保存库存信息
	 * @param scmsGoodsInventory
	 * @param orderNum
	 * @param orderType
     * @param operatePrefix 操作前缀：创建/取消
	 */
	public void saveInventory(ScmsGoodsInventory scmsGoodsInventory, String orderNum, String orderType, String operatePrefix);
	
	/**
	 * 更新库存
	 * @param orderNum
	 * @param orderType
     * @param operatePrefix 操作前缀：创建/取消
	 * @param inventoryNum
	 * @param shopId
	 * @param goodsId
	 * @param colorId
	 * @param inventorySizeId
	 * @param textureId
	 */
	public void updateGoodsInventoryNum(String orderNum, String orderType, String operatePrefix, Long inventoryNum, Long shopId, Long goodsId, Long colorId, Long inventorySizeId, Long textureId);	
	
	/**
	 * 更新订单库存
	 * @param orderNum
	 * @param orderType
	 * @param operatePrefix
	 * @param shopId
	 * @param goodsId
	 * @param scmsOrderGoodsDetail
	 * @param updateType
	 */
	public void updateOrderInventory(String orderNum, String orderType, String operatePrefix, Long merchantsId, Long shopId, Long goodsId, ScmsOrderGoodsDetail scmsOrderGoodsDetail, String updateType);
}
