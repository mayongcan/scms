/*
 * Copyright(c) 2018 gimplatform(通用信息管理平台) All rights reserved.
 */
package com.scms.modules.order.repository.custom;

import java.util.List;
import java.util.Map;
import org.springframework.data.repository.NoRepositoryBean;
import com.scms.modules.order.entity.ScmsOrderInfo;

/**
 * 自定义实体资源类接口
 * @version 1.0
 * @author
 *
 */
@NoRepositoryBean
public interface ScmsOrderInfoRepositoryCustom {

	/**
	 * 获取ScmsOrderInfo列表
	 * @param scmsOrderInfo
	 * @param params
	 * @param pageIndex
	 * @param pageSize
	 * @return
	 */
	public List<Map<String, Object>> getList(ScmsOrderInfo scmsOrderInfo, Map<String, Object> params, int pageIndex, int pageSize);
	
	/**
	 * 获取ScmsOrderInfo列表总数
	 * @param scmsOrderInfo
	 * @param params
	 * @return
	 */
	public int getListCount(ScmsOrderInfo scmsOrderInfo, Map<String, Object> params);

	/**
	 * 获取进货单列表
	 * @param scmsOrderInfo
	 * @param params
	 * @param pageIndex
	 * @param pageSize
	 * @return
	 */
    public List<Map<String, Object>> getOrderJhdList(ScmsOrderInfo scmsOrderInfo, Map<String, Object> params, int pageIndex, int pageSize);
    
    /**
     * 获取进货单列表总数
     * @param scmsOrderInfo
     * @param params
     * @return
     */
    public int getOrderJhdListCount(ScmsOrderInfo scmsOrderInfo, Map<String, Object> params);
}