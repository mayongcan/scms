/*
 * Copyright(c) 2018 gimplatform(通用信息管理平台) All rights reserved.
 */
package com.scms.modules.base.service.impl;

import java.util.Date;
import java.util.List;
import java.util.Map;
import org.springframework.stereotype.Service;
import org.apache.commons.collections4.MapUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import com.alibaba.fastjson.JSONObject;
import com.gimplatform.core.entity.UserInfo;
import com.gimplatform.core.utils.BeanUtils;
import com.gimplatform.core.utils.RestfulRetUtils;
import com.gimplatform.core.utils.StringUtils;
import com.scms.modules.base.service.ScmsPrintRemoteService;
import com.scms.modules.base.entity.ScmsPrintRemote;
import com.scms.modules.base.repository.ScmsPrintRemoteRepository;

@Service
public class ScmsPrintRemoteServiceImpl implements ScmsPrintRemoteService {
	
    @Autowired
    private ScmsPrintRemoteRepository scmsPrintRemoteRepository;

	@Override
	public JSONObject getList(Pageable page, ScmsPrintRemote scmsPrintRemote, Map<String, Object> params) {
		List<Map<String, Object>> list = scmsPrintRemoteRepository.getList(scmsPrintRemote, params, page.getPageNumber(), page.getPageSize());
		int count = scmsPrintRemoteRepository.getListCount(scmsPrintRemote, params);
		return RestfulRetUtils.getRetSuccessWithPage(list, count);	
	}

	@Override
	public JSONObject add(Map<String, Object> params, UserInfo userInfo) {
	    ScmsPrintRemote scmsPrintRemote = (ScmsPrintRemote) BeanUtils.mapToBean(params, ScmsPrintRemote.class);
	    //判断是否已存在该订单
	    List<ScmsPrintRemote> list = scmsPrintRemoteRepository.findByMerchantsIdAndOrderNum(scmsPrintRemote.getMerchantsId(), scmsPrintRemote.getOrderNum());
	    if(list != null && list.size() > 0) {
	        return RestfulRetUtils.getRetSuccess("1");
	    }
		scmsPrintRemote.setCreateBy(userInfo.getUserId());
		scmsPrintRemote.setCreateDate(new Date());
		scmsPrintRemoteRepository.save(scmsPrintRemote);
		return RestfulRetUtils.getRetSuccess("2");
	}

	@Override
	public JSONObject edit(Map<String, Object> params, UserInfo userInfo) {
        ScmsPrintRemote scmsPrintRemote = (ScmsPrintRemote) BeanUtils.mapToBean(params, ScmsPrintRemote.class);
		ScmsPrintRemote scmsPrintRemoteInDb = scmsPrintRemoteRepository.findOne(scmsPrintRemote.getId());
		if(scmsPrintRemoteInDb == null){
			return RestfulRetUtils.getErrorMsg("51006","当前编辑的对象不存在");
		}
		//合并两个javabean
		BeanUtils.mergeBean(scmsPrintRemote, scmsPrintRemoteInDb);
		scmsPrintRemoteRepository.save(scmsPrintRemoteInDb);
		return RestfulRetUtils.getRetSuccess();
	}

	@Override
	public JSONObject del(String idsList, UserInfo userInfo) {
		String[] ids = idsList.split(",");
		for (int i = 0; i < ids.length; i++) {
			scmsPrintRemoteRepository.delete(StringUtils.toLong(ids[i]));
		}
		return RestfulRetUtils.getRetSuccess();
	}

    @Override
    public JSONObject updatePrintRemoteStatus(Map<String, Object> params, UserInfo userInfo) {
        Long id = MapUtils.getLong(params, "id", null);
        String status = MapUtils.getString(params, "status", "1");
        if(id == null) return RestfulRetUtils.getErrorParams();
        scmsPrintRemoteRepository.updateStatus(id, status, userInfo.getUserId(), new Date());
        return RestfulRetUtils.getRetSuccess();
    }

}
