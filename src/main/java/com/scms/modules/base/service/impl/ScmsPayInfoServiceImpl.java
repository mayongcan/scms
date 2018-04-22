/*
 * Copyright(c) 2018 gimplatform(通用信息管理平台) All rights reserved.
 */
package com.scms.modules.base.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import com.alibaba.fastjson.JSONObject;
import com.gimplatform.core.entity.UserInfo;
import com.gimplatform.core.utils.BeanUtils;
import com.gimplatform.core.utils.RestfulRetUtils;
import com.gimplatform.core.utils.StringUtils;

import com.scms.modules.base.service.ScmsPayInfoService;
import com.scms.modules.base.entity.ScmsPayInfo;
import com.scms.modules.base.repository.ScmsPayInfoRepository;

@Service
public class ScmsPayInfoServiceImpl implements ScmsPayInfoService {
	
    @Autowired
    private ScmsPayInfoRepository scmsPayInfoRepository;

	@Override
	public JSONObject getList(Pageable page, ScmsPayInfo scmsPayInfo, Map<String, Object> params) {
		List<Map<String, Object>> list = scmsPayInfoRepository.getList(scmsPayInfo, params, page.getPageNumber(), page.getPageSize());
		int count = scmsPayInfoRepository.getListCount(scmsPayInfo, params);
		return RestfulRetUtils.getRetSuccessWithPage(list, count);	
	}

	@Override
	public JSONObject add(Map<String, Object> params, UserInfo userInfo) {
	    ScmsPayInfo scmsPayInfo = (ScmsPayInfo) BeanUtils.mapToBean(params, ScmsPayInfo.class);
        //判断名称是否已存在
        JSONObject json = judgeExist(scmsPayInfo);
        if(json != null) return json;
		scmsPayInfoRepository.save(scmsPayInfo);
		return RestfulRetUtils.getRetSuccess();
	}

	@Override
	public JSONObject edit(Map<String, Object> params, UserInfo userInfo) {
        ScmsPayInfo scmsPayInfo = (ScmsPayInfo) BeanUtils.mapToBean(params, ScmsPayInfo.class);
		ScmsPayInfo scmsPayInfoInDb = scmsPayInfoRepository.findOne(scmsPayInfo.getId());
		if(scmsPayInfoInDb == null){
			return RestfulRetUtils.getErrorMsg("51006","当前编辑的对象不存在");
		}
        //判断名称是否变更
        if(!scmsPayInfo.getPayName().equals(scmsPayInfoInDb.getPayName())) {
            JSONObject json = judgeExist(scmsPayInfo);
            if(json != null) return json;
        }
		//合并两个javabean
		BeanUtils.mergeBean(scmsPayInfo, scmsPayInfoInDb);
		scmsPayInfoRepository.save(scmsPayInfoInDb);
		return RestfulRetUtils.getRetSuccess();
	}

	@Override
	public JSONObject del(String idsList, UserInfo userInfo) {
		String[] ids = idsList.split(",");
		for (int i = 0; i < ids.length; i++) {
			scmsPayInfoRepository.delete(StringUtils.toLong(ids[i]));
		}
		return RestfulRetUtils.getRetSuccess();
	}

    /**
     * 判断名称是否已存在
     * @param scmsSizeInfo
     * @return
     */
    private JSONObject judgeExist(ScmsPayInfo scmsPayInfo) {
        if("1".equals(scmsPayInfo.getType())) {
            List<ScmsPayInfo> list = scmsPayInfoRepository.findByPayNameAndType(scmsPayInfo.getPayName(), scmsPayInfo.getType());
            if(list != null && list.size() > 0) {
                return RestfulRetUtils.getErrorMsg("51006","当前输入的支付名称已存在！");
            }
        }else {
            //先判断系统是否创建
            List<ScmsPayInfo> list = scmsPayInfoRepository.findByPayNameAndType(scmsPayInfo.getPayName(), "1");
            if(list != null && list.size() > 0) {
                return RestfulRetUtils.getErrorMsg("51006","当前输入的支付名称已存在！");
            }
            list = scmsPayInfoRepository.findByPayNameAndTypeAndMerchantsId(scmsPayInfo.getPayName(), scmsPayInfo.getType(), scmsPayInfo.getMerchantsId());
            if(list != null && list.size() > 0) {
                return RestfulRetUtils.getErrorMsg("51006","当前输入的支付名称已存在！");
            }
        }
        return null;
    }

}
