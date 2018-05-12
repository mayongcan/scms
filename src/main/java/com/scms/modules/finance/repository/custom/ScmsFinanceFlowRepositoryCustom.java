/*
 * Copyright(c) 2018 gimplatform(通用信息管理平台) All rights reserved.
 */
package com.scms.modules.finance.repository.custom;

import java.util.List;
import java.util.Map;
import org.springframework.data.repository.NoRepositoryBean;
import com.scms.modules.finance.entity.ScmsFinanceFlow;

/**
 * 自定义实体资源类接口
 * @version 1.0
 * @author
 *
 */
@NoRepositoryBean
public interface ScmsFinanceFlowRepositoryCustom {

	/**
	 * 获取ScmsFinanceFlow列表
	 * @param scmsFinanceFlow
	 * @param params
	 * @param pageIndex
	 * @param pageSize
	 * @return
	 */
	public List<Map<String, Object>> getList(ScmsFinanceFlow scmsFinanceFlow, Map<String, Object> params, int pageIndex, int pageSize);
	
	/**
	 * 获取ScmsFinanceFlow列表总数
	 * @param scmsFinanceFlow
	 * @param params
	 * @return
	 */
	public int getListCount(ScmsFinanceFlow scmsFinanceFlow, Map<String, Object> params);
	
	/**
	 * 获取统计结果
	 * @param params
	 * @return
	 */
    public List<Map<String, Object>> getFinanceFlowStatistics(Map<String, Object> params);
}