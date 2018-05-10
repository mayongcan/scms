/*
 * Copyright(c) 2018 gimplatform(通用信息管理平台) All rights reserved.
 */
package com.scms.modules.user.service;

import java.util.Map;

import org.springframework.data.domain.Pageable;

import com.alibaba.fastjson.JSONObject;
import com.gimplatform.core.entity.UserInfo;
import com.scms.modules.user.entity.ScmsMerchantsUser;

/**
 * 服务类接口
 * @version 1.0
 * @author 
 */
public interface ScmsMerchantsUserService {
    
    public ScmsMerchantsUser findByUserId(Long userId);
    
    public JSONObject getMerchantsUserList(Pageable page, Map<String, Object> params);
    
    public JSONObject bindUser(Map<String, Object> params, UserInfo userInfo);
    
    public JSONObject unBindUser(String idsList);
    
    public JSONObject setShopAdmin(String idsList, String isAdmin, UserInfo userInfo);
    
    public JSONObject setUserBlock(String idsList, String isBlock, UserInfo userInfo);
    
    public JSONObject addUser(Map<String, Object> params, UserInfo userInfo);
    
    public JSONObject editUser(Map<String, Object> params, UserInfo userInfo);
    
    public JSONObject delUser(String idsList, UserInfo userInfo);
    
    public JSONObject savePrivilege(Map<String, Object> params, UserInfo userInfo);

}
