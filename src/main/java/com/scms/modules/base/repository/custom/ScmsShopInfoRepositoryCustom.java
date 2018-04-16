/*
 * Copyright(c) 2018 gimplatform(通用信息管理平台) All rights reserved.
 */
package com.scms.modules.base.repository.custom;

import java.util.List;
import java.util.Map;
import org.springframework.data.repository.NoRepositoryBean;
import com.scms.modules.base.entity.ScmsShopInfo;

/**
 * 自定义实体资源类接口
 * @version 1.0
 * @author
 *
 */
@NoRepositoryBean
public interface ScmsShopInfoRepositoryCustom {

	/**
	 * 获取ScmsShopInfo列表
	 * @param scmsShopInfo
	 * @param params
	 * @param pageIndex
	 * @param pageSize
	 * @return
	 */
	public List<Map<String, Object>> getList(ScmsShopInfo scmsShopInfo, Map<String, Object> params, int pageIndex, int pageSize);
	
	/**
	 * 获取ScmsShopInfo列表总数
	 * @param scmsShopInfo
	 * @param params
	 * @return
	 */
	public int getListCount(ScmsShopInfo scmsShopInfo, Map<String, Object> params);

    /**
     * 
     * @param userInfo
     * @return
     */
    public List<Map<String, Object>> getShopKeyVal(Long merchantsId);
}