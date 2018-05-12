/*
 * Copyright(c) 2018 gimplatform(通用信息管理平台) All rights reserved.
 */
package com.scms.modules.finance.service.impl;

import java.util.List;
import java.util.Map;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import com.alibaba.fastjson.JSONObject;
import com.gimplatform.core.utils.RestfulRetUtils;
import com.scms.modules.finance.service.ScmsFinanceFlowService;
import com.scms.modules.finance.entity.ScmsFinanceFlow;
import com.scms.modules.finance.repository.ScmsFinanceFlowRepository;

@Service
public class ScmsFinanceFlowServiceImpl implements ScmsFinanceFlowService {
	
    @Autowired
    private ScmsFinanceFlowRepository scmsFinanceFlowRepository;

	@Override
	public JSONObject getList(Pageable page, ScmsFinanceFlow scmsFinanceFlow, Map<String, Object> params) {
		List<Map<String, Object>> list = scmsFinanceFlowRepository.getList(scmsFinanceFlow, params, page.getPageNumber(), page.getPageSize());
		int count = scmsFinanceFlowRepository.getListCount(scmsFinanceFlow, params);
		return RestfulRetUtils.getRetSuccessWithPage(list, count);	
	}

    @Override
    public JSONObject getFinanceFlowStatistics(Map<String, Object> params) {
        List<Map<String, Object>> list = scmsFinanceFlowRepository.getFinanceFlowStatistics(params);
        return RestfulRetUtils.getRetSuccessWithPage(list, list.size());  
    }

    @Override
    public void updateIsValidByOrderId(String isValid, String validReason, List<Long> idList) {
        scmsFinanceFlowRepository.updateIsValid(isValid, validReason, idList);
    }
}
