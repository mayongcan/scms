/*
 * Copyright(c) 2018 gimplatform(通用信息管理平台) All rights reserved.
 */
package com.scms.modules.customer.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;
import org.apache.commons.collections4.MapUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;

import com.alibaba.fastjson.JSONObject;
import com.gimplatform.core.common.Constants;
import com.gimplatform.core.entity.UserInfo;
import com.gimplatform.core.service.DistrictService;
import com.gimplatform.core.utils.BeanUtils;
import com.gimplatform.core.utils.RestfulRetUtils;
import com.gimplatform.core.utils.StringUtils;

import com.scms.modules.customer.service.ScmsCustomerInfoService;
import com.scms.modules.customer.entity.ScmsCustomerInfo;
import com.scms.modules.customer.repository.ScmsCustomerInfoRepository;

@Service
public class ScmsCustomerInfoServiceImpl implements ScmsCustomerInfoService {
	
    @Autowired
    private ScmsCustomerInfoRepository scmsCustomerInfoRepository;

    @Autowired
    private DistrictService districtService;

	@Override
	public JSONObject getList(Pageable page, ScmsCustomerInfo scmsCustomerInfo, Map<String, Object> params) {
		List<Map<String, Object>> list = scmsCustomerInfoRepository.getList(scmsCustomerInfo, params, page.getPageNumber(), page.getPageSize());
		int count = scmsCustomerInfoRepository.getListCount(scmsCustomerInfo, params);
		return RestfulRetUtils.getRetSuccessWithPage(list, count);	
	}

	@Override
	public JSONObject add(Map<String, Object> params, UserInfo userInfo) {
	    ScmsCustomerInfo scmsCustomerInfo = (ScmsCustomerInfo) BeanUtils.mapToBean(params, ScmsCustomerInfo.class);
		scmsCustomerInfo.setIsValid(Constants.IS_VALID_VALID);
		scmsCustomerInfo.setCreateBy(userInfo.getUserId());
		scmsCustomerInfo.setCreateDate(new Date());
        //判断是否需要获取areaCode（app传输过来的就需要获取areaCode）
        if(StringUtils.isBlank(scmsCustomerInfo.getAreaCode()) && !StringUtils.isBlank(scmsCustomerInfo.getAreaName())) {
            scmsCustomerInfo.setAreaCode(districtService.getAreaCode(scmsCustomerInfo.getAreaName()));
        }
		scmsCustomerInfoRepository.save(scmsCustomerInfo);
		return RestfulRetUtils.getRetSuccess();
	}

	@Override
	public JSONObject edit(Map<String, Object> params, UserInfo userInfo) {
        ScmsCustomerInfo scmsCustomerInfo = (ScmsCustomerInfo) BeanUtils.mapToBean(params, ScmsCustomerInfo.class);
		ScmsCustomerInfo scmsCustomerInfoInDb = scmsCustomerInfoRepository.findOne(scmsCustomerInfo.getId());
		if(scmsCustomerInfoInDb == null){
			return RestfulRetUtils.getErrorMsg("51006","当前编辑的对象不存在");
		}
        //判断是否需要获取areaCode（app传输过来的就需要获取areaCode）
        if(StringUtils.isBlank(scmsCustomerInfo.getAreaCode()) && !StringUtils.isBlank(scmsCustomerInfo.getAreaName())) {
            scmsCustomerInfo.setAreaCode(districtService.getAreaCode(scmsCustomerInfo.getAreaName()));
        }
		//合并两个javabean
		BeanUtils.mergeBean(scmsCustomerInfo, scmsCustomerInfoInDb);
		scmsCustomerInfoRepository.save(scmsCustomerInfoInDb);
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
			scmsCustomerInfoRepository.delEntity(Constants.IS_VALID_INVALID, idList);
		}
		return RestfulRetUtils.getRetSuccess();
	}

    @Override
    public JSONObject getCustomerStatistics(Map<String, Object> params) {
        params.put("orderTypeList", "lsd,pfd");
        List<Map<String, Object>> list1 = scmsCustomerInfoRepository.getCustomerStatistics(params);
        params.put("orderTypeList", "thd");
        List<Map<String, Object>> list2 = scmsCustomerInfoRepository.getCustomerStatistics(params);
        params.put("orderTypeList", "syd");
        List<Map<String, Object>> list3 = scmsCustomerInfoRepository.getCustomerStatistics(params);
        List<Map<String, Object>> retList = new ArrayList<Map<String, Object>>();
        Map<String, Object> retMap = new HashMap<String, Object>();
        if(list1 != null && list1.size() > 0) {
            retMap.put("saleNum", MapUtils.getString(list1.get(0), "totalNum"));
            retMap.put("saleUnPay", MapUtils.getString(list1.get(0), "totalUnPay"));
            retMap.put("saleSmallChange", MapUtils.getString(list1.get(0), "totalSmallChange"));
            retMap.put("saleAmount", MapUtils.getString(list1.get(0), "totalAmount"));
            retMap.put("saleTotal", MapUtils.getFloat(list1.get(0), "totalAmount", 0f) + MapUtils.getFloat(list1.get(0), "totalSmallChange", 0f));
        }
        if(list2 != null && list2.size() > 0) {
            retMap.put("returnNum", MapUtils.getString(list2.get(0), "totalNum"));
            retMap.put("returnUnPay", MapUtils.getString(list2.get(0), "totalUnPay"));
            retMap.put("returnSmallChange", MapUtils.getString(list2.get(0), "totalSmallChange"));
            retMap.put("returnAmount", MapUtils.getString(list2.get(0), "totalAmount"));
            retMap.put("returnTotal", MapUtils.getFloat(list2.get(0), "totalAmount", 0f) + MapUtils.getFloat(list2.get(0), "totalSmallChange", 0f));
        }
        if(list3 != null && list3.size() > 0) {
            retMap.put("payTotal", MapUtils.getString(list3.get(0), "totalAmount"));
        }
        retList.add(retMap);
        return RestfulRetUtils.getRetSuccessWithPage(retList, retList.size());
    }

    @Override
    public JSONObject getCustomerCheckBillStatistics(Map<String, Object> params) {
        List<Map<String, Object>> list = scmsCustomerInfoRepository.getCustomerCheckBillStatistics(params);
        return RestfulRetUtils.getRetSuccessWithPage(list, list.size());
    }

}
