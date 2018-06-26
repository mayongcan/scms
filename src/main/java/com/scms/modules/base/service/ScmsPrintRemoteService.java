/*
 * Copyright(c) 2018 gimplatform(通用信息管理平台) All rights reserved.
 */
package com.scms.modules.base.service;

import java.util.Map;

import org.springframework.data.domain.Pageable;
import com.alibaba.fastjson.JSONObject;
import com.gimplatform.core.entity.UserInfo;

import com.scms.modules.base.entity.ScmsPrintRemote;

/**
 * 服务类接口
 * @version 1.0
 * @author 
 */
public interface ScmsPrintRemoteService {
	
	/**
	 * 获取列表
	 * @param page
	 * @param scmsPrintRemote
	 * @return
	 */
	public JSONObject getList(Pageable page, ScmsPrintRemote scmsPrintRemote, Map<String, Object> params);
	
	/**
	 * 新增
	 * @param params
	 * @param userInfo
	 * @return
	 */
	public JSONObject add(Map<String, Object> params, UserInfo userInfo);
	
	/**
	 * 编辑
	 * @param params
	 * @param userInfo
	 * @return
	 */
	public JSONObject edit(Map<String, Object> params, UserInfo userInfo);
	
	/**
	 * 删除
	 * @param idsList
	 * @param userInfo
	 * @return
	 */
	public JSONObject del(String idsList, UserInfo userInfo);

	/**
	 * 更新状态
	 * @param params
	 * @param userInfo
	 * @return
	 */
    public JSONObject updatePrintRemoteStatus(Map<String, Object> params, UserInfo userInfo);

}
