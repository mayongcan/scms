/*
 * Copyright(c) 2018 gimplatform(通用信息管理平台) All rights reserved.
 */
package com.scms.modules.finance.service.impl;

import java.util.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;

import com.alibaba.fastjson.JSONObject;
import com.gimplatform.core.common.Constants;
import com.gimplatform.core.entity.UserInfo;
import com.gimplatform.core.utils.BeanUtils;
import com.gimplatform.core.utils.RestfulRetUtils;
import com.gimplatform.core.utils.StringUtils;
import com.scms.modules.finance.entity.ScmsDailyExpenses;
import com.scms.modules.finance.entity.ScmsFinanceFlow;
import com.scms.modules.finance.repository.ScmsDailyExpensesRepository;
import com.scms.modules.finance.repository.ScmsFinanceFlowRepository;
import com.scms.modules.finance.service.ScmsDailyExpensesService;

@Service
public class ScmsDailyExpensesServiceImpl implements ScmsDailyExpensesService {
	
    @Autowired
    private ScmsDailyExpensesRepository scmsDailyExpensesRepository;

    @Autowired
    private ScmsFinanceFlowRepository scmsFinanceFlowRepository;

	@Override
	public JSONObject getList(Pageable page, ScmsDailyExpenses scmsDailyExpenses, Map<String, Object> params) {
		List<Map<String, Object>> list = scmsDailyExpensesRepository.getList(scmsDailyExpenses, params, page.getPageNumber(), page.getPageSize());
		int count = scmsDailyExpensesRepository.getListCount(scmsDailyExpenses, params);
		return RestfulRetUtils.getRetSuccessWithPage(list, count);	
	}

	@Override
	public JSONObject add(Map<String, Object> params, UserInfo userInfo) {
	    ScmsDailyExpenses scmsDailyExpenses = (ScmsDailyExpenses) BeanUtils.mapToBean(params, ScmsDailyExpenses.class);
		scmsDailyExpenses.setIsValid(Constants.IS_VALID_VALID);
		scmsDailyExpenses.setCreateBy(userInfo.getUserId());
		scmsDailyExpenses.setCreateDate(new Date());
		scmsDailyExpenses = scmsDailyExpensesRepository.saveAndFlush(scmsDailyExpenses);
        
		//保存财务流水记录
		saveFinanceFlow(scmsDailyExpenses, userInfo); 
		return RestfulRetUtils.getRetSuccess();
	}

	@Override
	public JSONObject edit(Map<String, Object> params, UserInfo userInfo) {
        ScmsDailyExpenses scmsDailyExpenses = (ScmsDailyExpenses) BeanUtils.mapToBean(params, ScmsDailyExpenses.class);
		ScmsDailyExpenses scmsDailyExpensesInDb = scmsDailyExpensesRepository.findOne(scmsDailyExpenses.getId());
		if(scmsDailyExpensesInDb == null){
			return RestfulRetUtils.getErrorMsg("51006","当前编辑的对象不存在");
		}
		boolean isEditNum = false;
		if(scmsDailyExpenses.getExpensesNum().equals(scmsDailyExpensesInDb.getExpensesNum()) && scmsDailyExpenses.getIncomeType().equals(scmsDailyExpensesInDb.getIncomeType())) 
		    isEditNum = false;
		else  isEditNum = true;
		//合并两个javabean
		BeanUtils.mergeBean(scmsDailyExpenses, scmsDailyExpensesInDb);
		scmsDailyExpensesRepository.save(scmsDailyExpensesInDb);
		
		//将旧记录设为不可用，再保存新纪录(判断金额有没有变更)
		if(isEditNum) {
	        scmsFinanceFlowRepository.updateIsValidByExpensesId(Constants.IS_VALID_INVALID, "修改日常支出", scmsDailyExpenses.getId());
	        saveFinanceFlow(scmsDailyExpenses, userInfo);
		}
		return RestfulRetUtils.getRetSuccess();
	}

	@Override
	public JSONObject del(String idsList, UserInfo userInfo) {
		String[] ids = idsList.split(",");
		//判断是否需要移除
		List<Long> idList = new ArrayList<Long>();
		for (int i = 0; i < ids.length; i++) {
			idList.add(StringUtils.toLong(ids[i]));
		}
		//批量更新（设置IsValid 为N）
		if(idList.size() > 0){
			scmsDailyExpensesRepository.delEntity(Constants.IS_VALID_INVALID, idList);
			scmsFinanceFlowRepository.updateIsValidByExpensesId(Constants.IS_VALID_INVALID, "删除日常支出", idList);
		}
		return RestfulRetUtils.getRetSuccess();
	}
	
	private void saveFinanceFlow(ScmsDailyExpenses scmsDailyExpenses, UserInfo userInfo) {
        //保存财务流水信息
        ScmsFinanceFlow scmsFinanceFlow = new ScmsFinanceFlow();
        scmsFinanceFlow.setMerchantsId(scmsDailyExpenses.getMerchantsId());
        scmsFinanceFlow.setShopId(scmsDailyExpenses.getShopId());
        scmsFinanceFlow.setFlowType("日常支出");
        scmsFinanceFlow.setExpensesId(scmsDailyExpenses.getId());
        scmsFinanceFlow.setIncomeType(scmsDailyExpenses.getIncomeType());
        scmsFinanceFlow.setPayAmount(scmsDailyExpenses.getExpensesNum());
        scmsFinanceFlow.setPayMemo(scmsDailyExpenses.getMemo());
        scmsFinanceFlow.setCreateBy(userInfo.getUserId());
        scmsFinanceFlow.setCreateByName(userInfo.getUserName());
        scmsFinanceFlow.setCreateDate(new Date());
        scmsFinanceFlow.setValidReason("");
        scmsFinanceFlow.setIsValid(Constants.IS_VALID_VALID);
        scmsFinanceFlowRepository.save(scmsFinanceFlow);
	}

}
