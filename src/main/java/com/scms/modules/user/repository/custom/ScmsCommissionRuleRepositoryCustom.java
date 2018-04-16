/*
 * Copyright(c) 2018 gimplatform(通用信息管理平台) All rights reserved.
 */
package com.scms.modules.user.repository.custom;

import java.util.List;
import java.util.Map;
import org.springframework.data.repository.NoRepositoryBean;
import com.scms.modules.user.entity.ScmsCommissionRule;

/**
 * 自定义实体资源类接口
 * @version 1.0
 * @author
 *
 */
@NoRepositoryBean
public interface ScmsCommissionRuleRepositoryCustom {

	/**
	 * 获取ScmsCommissionRule列表
	 * @param scmsCommissionRule
	 * @param params
	 * @param pageIndex
	 * @param pageSize
	 * @return
	 */
	public List<Map<String, Object>> getList(ScmsCommissionRule scmsCommissionRule, Map<String, Object> params, int pageIndex, int pageSize);
	
	/**
	 * 获取ScmsCommissionRule列表总数
	 * @param scmsCommissionRule
	 * @param params
	 * @return
	 */
	public int getListCount(ScmsCommissionRule scmsCommissionRule, Map<String, Object> params);
}