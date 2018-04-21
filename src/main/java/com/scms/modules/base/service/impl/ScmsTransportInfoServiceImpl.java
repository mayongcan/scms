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
import com.scms.modules.base.service.ScmsTransportInfoService;
import com.scms.modules.base.entity.ScmsTransportInfo;
import com.scms.modules.base.repository.ScmsTransportInfoRepository;

@Service
public class ScmsTransportInfoServiceImpl implements ScmsTransportInfoService {
	
    @Autowired
    private ScmsTransportInfoRepository scmsTransportInfoRepository;

	@Override
	public JSONObject getList(Pageable page, ScmsTransportInfo scmsTransportInfo, Map<String, Object> params) {
		List<Map<String, Object>> list = scmsTransportInfoRepository.getList(scmsTransportInfo, params, page.getPageNumber(), page.getPageSize());
		int count = scmsTransportInfoRepository.getListCount(scmsTransportInfo, params);
		return RestfulRetUtils.getRetSuccessWithPage(list, count);	
	}

	@Override
	public JSONObject add(Map<String, Object> params, UserInfo userInfo) {
	    ScmsTransportInfo scmsTransportInfo = (ScmsTransportInfo) BeanUtils.mapToBean(params, ScmsTransportInfo.class);
        //判断名称是否已存在
        JSONObject json = judgeExist(scmsTransportInfo);
        if(json != null) return json;
		scmsTransportInfoRepository.save(scmsTransportInfo);
		return RestfulRetUtils.getRetSuccess();
	}

	@Override
	public JSONObject edit(Map<String, Object> params, UserInfo userInfo) {
        ScmsTransportInfo scmsTransportInfo = (ScmsTransportInfo) BeanUtils.mapToBean(params, ScmsTransportInfo.class);
		ScmsTransportInfo scmsTransportInfoInDb = scmsTransportInfoRepository.findOne(scmsTransportInfo.getId());
		if(scmsTransportInfoInDb == null){
			return RestfulRetUtils.getErrorMsg("51006","当前编辑的对象不存在");
		}
        //判断名称是否变更
        if(!scmsTransportInfo.getTransportName().equals(scmsTransportInfoInDb.getTransportName())) {
            JSONObject json = judgeExist(scmsTransportInfo);
            if(json != null) return json;
        }
		//合并两个javabean
		BeanUtils.mergeBean(scmsTransportInfo, scmsTransportInfoInDb);
		scmsTransportInfoRepository.save(scmsTransportInfoInDb);
		return RestfulRetUtils.getRetSuccess();
	}

	@Override
	public JSONObject del(String idsList, UserInfo userInfo) {
		String[] ids = idsList.split(",");
		for (int i = 0; i < ids.length; i++) {
			scmsTransportInfoRepository.delete(StringUtils.toLong(ids[i]));
		}
		return RestfulRetUtils.getRetSuccess();
	}

    /**
     * 判断名称是否已存在
     * @param scmsSizeInfo
     * @return
     */
    private JSONObject judgeExist(ScmsTransportInfo scmsTransportInfo) {
        if("1".equals(scmsTransportInfo.getType())) {
            List<ScmsTransportInfo> list = scmsTransportInfoRepository.findByTransportNameAndType(scmsTransportInfo.getTransportName(), scmsTransportInfo.getType());
            if(list != null && list.size() > 0) {
                return RestfulRetUtils.getErrorMsg("51006","当前输入的运输方式已存在！");
            }
        }else {
            //先判断系统是否创建
            List<ScmsTransportInfo> list = scmsTransportInfoRepository.findByTransportNameAndType(scmsTransportInfo.getTransportName(), "1");
            if(list != null && list.size() > 0) {
                return RestfulRetUtils.getErrorMsg("51006","当前输入的运输方式已存在！");
            }
            list = scmsTransportInfoRepository.findByTransportNameAndTypeAndMerchantsId(scmsTransportInfo.getTransportName(), scmsTransportInfo.getType(), scmsTransportInfo.getMerchantsId());
            if(list != null && list.size() > 0) {
                return RestfulRetUtils.getErrorMsg("51006","当前输入的运输方式已存在！");
            }
        }
        return null;
    }

}
