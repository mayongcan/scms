/*
 * Copyright(c) 2018 gimplatform(通用信息管理平台) All rights reserved.
 */
package com.scms.modules.user.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;
import org.apache.commons.collections4.MapUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;

import com.scms.modules.user.service.ScmsMerchantsUserService;
import com.alibaba.fastjson.JSONObject;
import com.gimplatform.core.entity.UserInfo;
import com.gimplatform.core.service.RoleInfoService;
import com.gimplatform.core.service.UserInfoService;
import com.gimplatform.core.utils.BeanUtils;
import com.gimplatform.core.utils.RestfulRetUtils;
import com.gimplatform.core.utils.StringUtils;
import com.scms.modules.user.entity.ScmsMerchantsUser;
import com.scms.modules.user.repository.ScmsMerchantsUserRepository;

@Service
public class ScmsMerchantsUserServiceImpl implements ScmsMerchantsUserService {
	
    @Autowired
    private ScmsMerchantsUserRepository scmsMerchantsUserRepository;

    @Autowired
    private UserInfoService userInfoService;

    @Autowired
    private RoleInfoService roleInfoService;

    @Override
    public ScmsMerchantsUser findByUserId(Long userId) {
        if(userId == null) return null;
        List<ScmsMerchantsUser> list = scmsMerchantsUserRepository.findByUserId(userId);
        if(list == null || list.size() == 0) return null;
        return list.get(0);
    }

    @Override
    public JSONObject getMerchantsUserList(Pageable page, Map<String, Object> params) {
        List<Map<String, Object>> list = scmsMerchantsUserRepository.getMerchantsUserList(null, params, page.getPageNumber(), page.getPageSize());
        int count = scmsMerchantsUserRepository.getMerchantsUserListCount(null, params);
        return RestfulRetUtils.getRetSuccessWithPage(list, count);  
    }

    @Override
    public JSONObject bindUser(Map<String, Object> params, UserInfo userInfo) {
        String userIdList = MapUtils.getString(params, "userIdList");
        String arrayId[] = userIdList.split(",");
        Long userId = null;
        for(String str : arrayId) {
            userId = StringUtils.toLong(str);
            if(userId != null) {
                ScmsMerchantsUser scmsMerchantsUser = (ScmsMerchantsUser) BeanUtils.mapToBean(params, ScmsMerchantsUser.class);
                scmsMerchantsUser.setUserId(userId);
                scmsMerchantsUser.setIsBlock("0");
                scmsMerchantsUserRepository.save(scmsMerchantsUser);
            }
        }
        return RestfulRetUtils.getRetSuccess();
    }

    @Override
    public JSONObject unBindUser(String idsList) {
        String[] ids = idsList.split(",");
        for (int i = 0; i < ids.length; i++) {
            scmsMerchantsUserRepository.delete(StringUtils.toLong(ids[i]));
        }
        return RestfulRetUtils.getRetSuccess();
    }

    @Override
    public JSONObject setShopAdmin(String idsList, String isAdmin, UserInfo userInfo) {
        String[] ids = idsList.split(",");
        //判断是否需要移除
        List<Long> idList = new ArrayList<Long>();
        for (int i = 0; i < ids.length; i++) {
            idList.add(StringUtils.toLong(ids[i]));
        }
        //批量更新（设置IsValid 为N）
        if(idList.size() > 0){
            scmsMerchantsUserRepository.setShopAdmin(isAdmin, idList);
        }
        for (int i = 0; i < ids.length; i++) {
            ScmsMerchantsUser scmsMerchantsUser = scmsMerchantsUserRepository.getOne(StringUtils.toLong(ids[i]));
            if(scmsMerchantsUser != null && scmsMerchantsUser.getUserId() != null) {
                if("1".equals(isAdmin)) {
                    //更改为店铺管理员角色
                    roleInfoService.setUserRole("店铺管理员", userInfo.getTenantsId(), scmsMerchantsUser.getUserId());
                }else {
                    //更改为店铺销售员角色
                    roleInfoService.setUserRole("店铺销售员", userInfo.getTenantsId(), scmsMerchantsUser.getUserId());
                }
            }
        }
        return RestfulRetUtils.getRetSuccess();
    }

    @Override
    public JSONObject setUserBlock(String idsList, String isBlock, UserInfo userInfo) {
        String[] ids = idsList.split(",");
        //判断是否需要移除
        List<Long> idList = new ArrayList<Long>();
        for (int i = 0; i < ids.length; i++) {
            idList.add(StringUtils.toLong(ids[i]));
        }
        //批量更新（设置IsValid 为N）
        if(idList.size() > 0){
            scmsMerchantsUserRepository.setUserBlock(isBlock, idList);
        }
        return RestfulRetUtils.getRetSuccess();
    }

    @Override
    public JSONObject addUser(Map<String, Object> params, UserInfo loginUser) {
        UserInfo userInfo = (UserInfo) BeanUtils.mapToBean(params, UserInfo.class);
        JSONObject retJson = userInfoService.addUser(userInfo, loginUser, null, null);
        if("000000".equals(retJson.getString("RetCode"))) {
            String userStr = retJson.getString("RetData");
            if(StringUtils.isBlank(userStr)) return retJson;
            else {
                JSONObject userJson = JSONObject.parseObject(userStr);
                Long userId = userJson.getLong("userId");
                
                //设置用户角色，默认为店铺销售员角色
                roleInfoService.setUserRole("店铺销售员", userInfo.getTenantsId(), userId);
                //绑定关联
                ScmsMerchantsUser scmsMerchantsUser = (ScmsMerchantsUser) BeanUtils.mapToBean(params, ScmsMerchantsUser.class);
                scmsMerchantsUser.setUserId(userId);
                scmsMerchantsUser.setIsAdmin("0");
                scmsMerchantsUser.setIsBlock("0");
                scmsMerchantsUserRepository.save(scmsMerchantsUser);
                return RestfulRetUtils.getRetSuccess();
            }
        }else return retJson;
    }

    @Override
    public JSONObject editUser(Map<String, Object> params, UserInfo loginUser) {
        UserInfo userInfo = (UserInfo) BeanUtils.mapToBean(params, UserInfo.class);
        JSONObject retJson = userInfoService.editUser(userInfo, loginUser, null, null);
        if("000000".equals(retJson.getString("RetCode"))) {
            //绑定关联
            ScmsMerchantsUser scmsMerchantsUser = (ScmsMerchantsUser) BeanUtils.mapToBean(params, ScmsMerchantsUser.class);
            scmsMerchantsUserRepository.save(scmsMerchantsUser);
            return RestfulRetUtils.getRetSuccess();
        }else return retJson;
    }

    @Override
    public JSONObject delUser(String idsList, UserInfo userInfo) {
        //先解绑用户，再设置用户为N
        String[] ids = idsList.split(",");
        for (int i = 0; i < ids.length; i++) {
            Long userId = StringUtils.toLong(ids[i]);
            if(userId == null) continue;
            scmsMerchantsUserRepository.delByUserId(userId);
        }
        userInfoService.delUser(idsList, userInfo);
        return RestfulRetUtils.getRetSuccess();
    }

    @Override
    public JSONObject savePrivilege(Map<String, Object> params, UserInfo userInfo) {
        String privilegeContent = MapUtils.getString(params, "privilegeContent");
        Long id = MapUtils.getLong(params, "id");
        scmsMerchantsUserRepository.updatePrivilegeContent(privilegeContent, id);
        return RestfulRetUtils.getRetSuccess();
    }

}
