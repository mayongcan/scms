/*
 * Copyright(c) 2018 gimplatform(通用信息管理平台) All rights reserved.
 */
package com.scms.modules.finance.service;

import java.util.List;
import java.util.Map;
import org.springframework.data.domain.Pageable;
import com.alibaba.fastjson.JSONObject;
import com.scms.modules.finance.entity.ScmsFinanceFlow;

/**
 * 服务类接口
 * @version 1.0
 * @author 
 */
public interface ScmsFinanceFlowService {
	
	/**
	 * 获取列表
	 * @param page
	 * @param scmsFinanceFlow
	 * @return
	 */
	public JSONObject getList(Pageable page, ScmsFinanceFlow scmsFinanceFlow, Map<String, Object> params);
	
	/**
	 * 获取统计信息
	 * @param params
	 * @return
	 */
    public JSONObject getStatisticsList(Map<String, Object> params);

	/**
	 * 更新流水账单额状态
	 * @param isValid
	 * @param validReason
	 * @param OrderNum
	 */
	public void updateIsValidByOrderId(String isValid, String validReason, List<Long> idList);
}
