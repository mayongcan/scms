/*
 * Copyright(c) 2018 gimplatform(通用信息管理平台) All rights reserved.
 */
package com.scms.modules.goods.repository.custom;

import java.util.List;
import java.util.Map;
import org.springframework.data.repository.NoRepositoryBean;
import com.scms.modules.goods.entity.ScmsGoodsInfo;

/**
 * 自定义实体资源类接口
 * @version 1.0
 * @author
 *
 */
@NoRepositoryBean
public interface ScmsGoodsInfoRepositoryCustom {

	/**
	 * 获取ScmsGoodsInfo列表
	 * @param scmsGoodsInfo
	 * @param params
	 * @param pageIndex
	 * @param pageSize
	 * @return
	 */
	public List<Map<String, Object>> getList(ScmsGoodsInfo scmsGoodsInfo, Map<String, Object> params, int pageIndex, int pageSize);
	
	/**
	 * 获取ScmsGoodsInfo列表明细
	 * @param scmsGoodsInfo
	 * @param params
	 * @param pageIndex
	 * @param pageSize
	 * @return
	 */
	public List<Map<String, Object>> getList2(ScmsGoodsInfo scmsGoodsInfo, Map<String, Object> params, int pageIndex, int pageSize);
	
	/**
	 * 获取ScmsGoodsInfo列表总数
	 * @param scmsGoodsInfo
	 * @param params
	 * @return
	 */
	public int getListCount(ScmsGoodsInfo scmsGoodsInfo, Map<String, Object> params);
    
    /**
     * 统计所有商品的库存
     * @param params
     * @return
     */
    public List<Map<String, Object>> getAllGoodsInventoryStatistics(Map<String, Object> params);
}