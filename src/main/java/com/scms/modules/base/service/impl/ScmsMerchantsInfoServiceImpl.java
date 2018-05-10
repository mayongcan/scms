/*
 * Copyright(c) 2018 gimplatform(通用信息管理平台) All rights reserved.
 */
package com.scms.modules.base.service.impl;

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
import com.gimplatform.core.service.DistrictService;
import com.gimplatform.core.utils.BeanUtils;
import com.gimplatform.core.utils.RestfulRetUtils;
import com.gimplatform.core.utils.StringUtils;

import com.scms.modules.base.service.ScmsMerchantsInfoService;
import com.scms.modules.base.entity.ScmsMerchantsInfo;
import com.scms.modules.base.entity.ScmsShopInfo;
import com.scms.modules.base.repository.ScmsMerchantsInfoRepository;
import com.scms.modules.base.repository.ScmsShopInfoRepository;

@Service
public class ScmsMerchantsInfoServiceImpl implements ScmsMerchantsInfoService {
	
    @Autowired
    private ScmsMerchantsInfoRepository scmsMerchantsInfoRepository;
    
    @Autowired
    private ScmsShopInfoRepository scmsShopInfoRepository;

    @Autowired
    private DistrictService districtService;

	@Override
	public JSONObject getList(Pageable page, ScmsMerchantsInfo scmsMerchantsInfo, Map<String, Object> params) {
		List<Map<String, Object>> list = scmsMerchantsInfoRepository.getList(scmsMerchantsInfo, params, page.getPageNumber(), page.getPageSize());
		int count = scmsMerchantsInfoRepository.getListCount(scmsMerchantsInfo, params);
		return RestfulRetUtils.getRetSuccessWithPage(list, count);	
	}

	@Override
	public JSONObject add(Map<String, Object> params, UserInfo userInfo) {
	    ScmsMerchantsInfo scmsMerchantsInfo = (ScmsMerchantsInfo) BeanUtils.mapToBean(params, ScmsMerchantsInfo.class);
	    //判断当前选择的用户是否已绑定商户
	    List<ScmsMerchantsInfo> hasList = scmsMerchantsInfoRepository.findByUserIdAndIsValid(scmsMerchantsInfo.getUserId(), "Y");
	    if(hasList != null && hasList.size() > 0) {
	        return RestfulRetUtils.getErrorMsg("51006", "当前选择的用户已绑定其他商户，请重新选择！");
	    }
		scmsMerchantsInfo.setIsValid(Constants.IS_VALID_VALID);
		scmsMerchantsInfo.setCreateBy(userInfo.getUserId());
		scmsMerchantsInfo.setCreateDate(new Date());
		scmsMerchantsInfo = scmsMerchantsInfoRepository.saveAndFlush(scmsMerchantsInfo);
		
		//新增商户成功后，创建一个默认店铺
        ScmsShopInfo scmsShopInfo = new ScmsShopInfo();
        scmsShopInfo.setMerchantsId(scmsMerchantsInfo.getId());
        scmsShopInfo.setShopName("默认店铺");
        scmsShopInfo.setIsValid(Constants.IS_VALID_VALID);
        scmsShopInfo.setCreateBy(userInfo.getUserId());
        scmsShopInfo.setCreateDate(new Date());
        scmsShopInfoRepository.save(scmsShopInfo);
		return RestfulRetUtils.getRetSuccess();
	}

	@Override
	public JSONObject edit(Map<String, Object> params, UserInfo userInfo) {
        ScmsMerchantsInfo scmsMerchantsInfo = (ScmsMerchantsInfo) BeanUtils.mapToBean(params, ScmsMerchantsInfo.class);
		ScmsMerchantsInfo scmsMerchantsInfoInDb = scmsMerchantsInfoRepository.findOne(scmsMerchantsInfo.getId());
		if(scmsMerchantsInfoInDb == null){
			return RestfulRetUtils.getErrorMsg("51006","当前编辑的对象不存在");
		}
		//判断新的绑定用户是否已更改，如果更改了，是否已绑定其他商户
		if(scmsMerchantsInfo.getUserId() != null && !scmsMerchantsInfo.getUserId().equals(scmsMerchantsInfoInDb.getUserId())) {
		    List<ScmsMerchantsInfo> hasList = scmsMerchantsInfoRepository.findByUserIdAndIsValid(scmsMerchantsInfo.getUserId(), "Y");
	        if(hasList != null && hasList.size() > 0) {
	            return RestfulRetUtils.getErrorMsg("51006", "当前选择的用户已绑定其他商户，请重新选择！");
	        }
		}
		//判断是否需要获取areaCode（app传输过来的就需要获取areaCode）
		if(StringUtils.isBlank(scmsMerchantsInfo.getAreaCode()) && !StringUtils.isBlank(scmsMerchantsInfo.getAreaName())) {
		    scmsMerchantsInfo.setAreaCode(districtService.getAreaCode(scmsMerchantsInfo.getAreaName()));
		}
		//合并两个javabean
		BeanUtils.mergeBean(scmsMerchantsInfo, scmsMerchantsInfoInDb);
		scmsMerchantsInfoRepository.save(scmsMerchantsInfoInDb);
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
			scmsMerchantsInfoRepository.delEntity(Constants.IS_VALID_INVALID, idList);
		}
		return RestfulRetUtils.getRetSuccess();
	}

}
