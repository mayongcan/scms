package com.scms.restful;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONObject;
import com.gimplatform.core.entity.UserInfo;
import com.gimplatform.core.utils.RestfulRetUtils;
import com.gimplatform.core.utils.SessionUtils;
import com.scms.modules.user.entity.ScmsMerchantsUser;
import com.scms.modules.user.service.ScmsMerchantsUserService;

@RestController
@RequestMapping(value = "/api/scms/user")
public class UserRestful {
    
    protected static final Logger logger = LogManager.getLogger(UserRestful.class);
    
    @Autowired
    private ScmsMerchantsUserService scmsMerchantsUserService;

    /**
     * 获取列表
     * @param request
     * @return
     */
    @RequestMapping(value="/getUserMerchantsId",method=RequestMethod.GET)
    public JSONObject getUserMerchantsId(HttpServletRequest request, @RequestParam Map<String, Object> params){
        JSONObject json = new JSONObject();
        try{
            UserInfo userInfo = SessionUtils.getUserInfo();
            if(userInfo == null) json = RestfulRetUtils.getErrorNoUser();
            else {
                ScmsMerchantsUser obj = scmsMerchantsUserService.findByUserId(userInfo.getUserId());
                if(obj != null) json = RestfulRetUtils.getRetSuccess(obj.getMerchantsId());
            }
        }catch(Exception e){
            json = RestfulRetUtils.getErrorMsg("51001","获取列表失败");
            logger.error(e.getMessage(), e);
        }
        return json;
    }

    /**
     * 获取商户员工列表
     * @param request
     * @param params
     * @return
     */
    @RequestMapping(value="/getMerchantsUserList",method=RequestMethod.GET)
    public JSONObject getMerchantsUserList(HttpServletRequest request, @RequestParam Map<String, Object> params){
        JSONObject json = new JSONObject();
        try{
            UserInfo userInfo = SessionUtils.getUserInfo();
            if(userInfo == null) json = RestfulRetUtils.getErrorNoUser();
            else {
                Pageable pageable = new PageRequest(SessionUtils.getPageIndex(request), SessionUtils.getPageSize(request));               
                json = scmsMerchantsUserService.getMerchantsUserList(pageable, params);
            }
        }catch(Exception e){
            json = RestfulRetUtils.getErrorMsg("51001","获取列表失败");
            logger.error(e.getMessage(), e);
        }
        return json;
    }

    @RequestMapping(value="/bindUser",method=RequestMethod.POST)
    public JSONObject bindUser(HttpServletRequest request,@RequestBody Map<String, Object> params){
        JSONObject json = new JSONObject();
        try {
            UserInfo userInfo = SessionUtils.getUserInfo();
            if(userInfo == null) json = RestfulRetUtils.getErrorNoUser();
            else {
                json = scmsMerchantsUserService.bindUser(params, userInfo);
            }
        } catch (Exception e) {
            json = RestfulRetUtils.getErrorMsg("51004","设置店铺管理员失败");
            logger.error(e.getMessage(), e);
        }
        return json;
    }

    @RequestMapping(value="/unBindUser",method=RequestMethod.POST)
    public JSONObject unBindUser(HttpServletRequest request,@RequestBody String idsList){
        JSONObject json = new JSONObject();
        try {
            UserInfo userInfo = SessionUtils.getUserInfo();
            if(userInfo == null) json = RestfulRetUtils.getErrorNoUser();
            else {
                json = scmsMerchantsUserService.unBindUser(idsList);
            }
        } catch (Exception e) {
            json = RestfulRetUtils.getErrorMsg("51004","设置店铺管理员失败");
            logger.error(e.getMessage(), e);
        }
        return json;
    }
    
    /**
     * 设置为店铺管理员
     * @param request
     * @param idsList
     * @return
     */
    @RequestMapping(value="/setShopAdmin",method=RequestMethod.POST)
    public JSONObject setShopAdmin(HttpServletRequest request,@RequestBody String idsList){
        JSONObject json = new JSONObject();
        try {
            UserInfo userInfo = SessionUtils.getUserInfo();
            if(userInfo == null) json = RestfulRetUtils.getErrorNoUser();
            else {
                json = scmsMerchantsUserService.setShopAdmin(idsList, "1", userInfo);
            }
        } catch (Exception e) {
            json = RestfulRetUtils.getErrorMsg("51004","设置店铺管理员失败");
            logger.error(e.getMessage(), e);
        }
        return json;
    }
    
    /**
     * 取消店铺管理员
     * @param request
     * @param idsList
     * @return
     */
    @RequestMapping(value="/cancelShopAdmin",method=RequestMethod.POST)
    public JSONObject cancelShopAdmin(HttpServletRequest request,@RequestBody String idsList){
        JSONObject json = new JSONObject();
        try {
            UserInfo userInfo = SessionUtils.getUserInfo();
            if(userInfo == null) json = RestfulRetUtils.getErrorNoUser();
            else {
                json = scmsMerchantsUserService.setShopAdmin(idsList, "0", userInfo);
            }
        } catch (Exception e) {
            json = RestfulRetUtils.getErrorMsg("51004","取消店铺管理员失败");
            logger.error(e.getMessage(), e);
        }
        return json;
    }
}
