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
import com.gimplatform.core.utils.BeanUtils;
import com.gimplatform.core.utils.RestfulRetUtils;
import com.gimplatform.core.utils.SessionUtils;
import com.scms.modules.user.entity.ScmsCommissionRule;
import com.scms.modules.user.entity.ScmsMerchantsUser;
import com.scms.modules.user.entity.ScmsUserPoint;
import com.scms.modules.user.service.ScmsCommissionRuleService;
import com.scms.modules.user.service.ScmsMerchantsUserService;
import com.scms.modules.user.service.ScmsUserPointService;

@RestController
@RequestMapping(value = "/api/scms/user")
public class UserRestful {
    
    protected static final Logger logger = LogManager.getLogger(UserRestful.class);
    
    @Autowired
    private ScmsMerchantsUserService scmsMerchantsUserService;
    
    @Autowired
    private ScmsCommissionRuleService scmsCommissionRuleService;
    
    @Autowired
    private ScmsUserPointService scmsUserPointService;

    @RequestMapping(value="/userListIndex", method=RequestMethod.GET)
    public JSONObject userListIndex(HttpServletRequest request){ return RestfulRetUtils.getRetSuccess();}

    @RequestMapping(value="/userPrivilegeIndex", method=RequestMethod.GET)
    public JSONObject userPrivilegeIndex(HttpServletRequest request){ return RestfulRetUtils.getRetSuccess();}

    @RequestMapping(value="/commissionRuleIndex", method=RequestMethod.GET)
    public JSONObject commissionRuleIndex(HttpServletRequest request){ return RestfulRetUtils.getRetSuccess();}

    @RequestMapping(value="/userPointIndex", method=RequestMethod.GET)
    public JSONObject userPointIndex(HttpServletRequest request){ return RestfulRetUtils.getRetSuccess();}

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

    /**
     * 设置为启用
     * @param request
     * @param idsList
     * @return
     */
    @RequestMapping(value="/unblockUser",method=RequestMethod.POST)
    public JSONObject unblockUser(HttpServletRequest request, @RequestBody String idsList){
        JSONObject json = new JSONObject();
        try {
            UserInfo userInfo = SessionUtils.getUserInfo();
            if(userInfo == null) json = RestfulRetUtils.getErrorNoUser();
            else {
                json = scmsMerchantsUserService.setUserBlock(idsList, "0", userInfo);
            }
        } catch (Exception e) {
            json = RestfulRetUtils.getErrorMsg("51004","启用用户失败");
            logger.error(e.getMessage(), e);
        }
        return json;
    }
    
    /**
     * 设置为停用
     * @param request
     * @param idsList
     * @return
     */
    @RequestMapping(value="/blockUser",method=RequestMethod.POST)
    public JSONObject blockUser(HttpServletRequest request, @RequestBody String idsList){
        JSONObject json = new JSONObject();
        try {
            UserInfo userInfo = SessionUtils.getUserInfo();
            if(userInfo == null) json = RestfulRetUtils.getErrorNoUser();
            else {
                json = scmsMerchantsUserService.setUserBlock(idsList, "1", userInfo);
            }
        } catch (Exception e) {
            json = RestfulRetUtils.getErrorMsg("51004","停用用户失败");
            logger.error(e.getMessage(), e);
        }
        return json;
    }

    /**
     * 新增用户
     * @param request
     * @param params
     * @return
     */
    @RequestMapping(value="/addUser",method=RequestMethod.POST)
    public JSONObject addUser(HttpServletRequest request, @RequestBody Map<String, Object> params){
        JSONObject json = new JSONObject();
        try{
            UserInfo userInfo = SessionUtils.getUserInfo();
            if(userInfo == null) json = RestfulRetUtils.getErrorNoUser();
            else {
                json = scmsMerchantsUserService.addUser(params, userInfo);
            }
        }catch(Exception e){
            json = RestfulRetUtils.getErrorMsg("51002","新增信息失败");
            logger.error(e.getMessage(), e);
        }
        return json;
    }
    
    /**
     * 编辑用户
     * @param request
     * @param params
     * @return
     */
    @RequestMapping(value="/editUser",method=RequestMethod.POST)
    public JSONObject editUser(HttpServletRequest request, @RequestBody Map<String, Object> params){
        JSONObject json = new JSONObject();
        try{
            UserInfo userInfo = SessionUtils.getUserInfo();
            if(userInfo == null) json = RestfulRetUtils.getErrorNoUser();
            else {
                json = scmsMerchantsUserService.editUser(params, userInfo);
            }
        }catch(Exception e){
            json = RestfulRetUtils.getErrorMsg("51003","编辑信息失败");
            logger.error(e.getMessage(), e);
        }
        return json;
    }

    /**
     * 删除用户
     * @param request
     * @param idsList
     * @return
     */
    @RequestMapping(value="/delUser",method=RequestMethod.POST)
    public JSONObject delUser(HttpServletRequest request,@RequestBody String idsList){
        JSONObject json = new JSONObject();
        try {
            UserInfo userInfo = SessionUtils.getUserInfo();
            if(userInfo == null) json = RestfulRetUtils.getErrorNoUser();
            else {
                json = scmsMerchantsUserService.delUser(idsList, userInfo);
            }
        } catch (Exception e) {
            json = RestfulRetUtils.getErrorMsg("51004","设置店铺管理员失败");
            logger.error(e.getMessage(), e);
        }
        return json;
    }
    
    /**
     * 保存用户权限
     * @param request
     * @param idsList
     * @return
     */
    @RequestMapping(value="/savePrivilege",method=RequestMethod.POST)
    public JSONObject savePrivilege(HttpServletRequest request,@RequestBody Map<String, Object> params){
        JSONObject json = new JSONObject();
        try {
            UserInfo userInfo = SessionUtils.getUserInfo();
            if(userInfo == null) json = RestfulRetUtils.getErrorNoUser();
            else {
                json = scmsMerchantsUserService.savePrivilege(params, userInfo);
            }
        } catch (Exception e) {
            json = RestfulRetUtils.getErrorMsg("51004","设置店铺管理员失败");
            logger.error(e.getMessage(), e);
        }
        return json;
    }
    
    /**
     * 获取列表
     * @param request
     * @return
     */
    @RequestMapping(value="/getCommissionRuleList",method=RequestMethod.GET)
    public JSONObject getCommissionRuleList(HttpServletRequest request, @RequestParam Map<String, Object> params){
        JSONObject json = new JSONObject();
        try{
            UserInfo userInfo = SessionUtils.getUserInfo();
            if(userInfo == null) json = RestfulRetUtils.getErrorNoUser();
            else {
                Pageable pageable = new PageRequest(SessionUtils.getPageIndex(request), SessionUtils.getPageSize(request));  
                ScmsCommissionRule scmsCommissionRule = (ScmsCommissionRule)BeanUtils.mapToBean(params, ScmsCommissionRule.class);              
                json = scmsCommissionRuleService.getList(pageable, scmsCommissionRule, params);
            }
        }catch(Exception e){
            json = RestfulRetUtils.getErrorMsg("51001","获取列表失败");
            logger.error(e.getMessage(), e);
        }
        return json;
    }
    
    
    /**
     * 新增信息
     * @param request
     * @param params
     * @return
     */
    @RequestMapping(value="/addCommissionRule",method=RequestMethod.POST)
    public JSONObject addCommissionRule(HttpServletRequest request, @RequestBody Map<String, Object> params){
        JSONObject json = new JSONObject();
        try{
            UserInfo userInfo = SessionUtils.getUserInfo();
            if(userInfo == null) json = RestfulRetUtils.getErrorNoUser();
            else {
                json = scmsCommissionRuleService.add(params, userInfo);
            }
        }catch(Exception e){
            json = RestfulRetUtils.getErrorMsg("51002","新增信息失败");
            logger.error(e.getMessage(), e);
        }
        return json;
    }
    
    /**
     * 编辑信息
     * @param request
     * @param params
     * @return
     */
    @RequestMapping(value="/editCommissionRule",method=RequestMethod.POST)
    public JSONObject editCommissionRule(HttpServletRequest request, @RequestBody Map<String, Object> params){
        JSONObject json = new JSONObject();
        try{
            UserInfo userInfo = SessionUtils.getUserInfo();
            if(userInfo == null) json = RestfulRetUtils.getErrorNoUser();
            else {
                json = scmsCommissionRuleService.edit(params, userInfo);
            }
        }catch(Exception e){
            json = RestfulRetUtils.getErrorMsg("51003","编辑信息失败");
            logger.error(e.getMessage(), e);
        }
        return json;
    }
    
    /**
     * 删除信息
     * @param request
     * @param idsList
     * @return
     */
    @RequestMapping(value="/delCommissionRule",method=RequestMethod.POST)
    public JSONObject delCommissionRule(HttpServletRequest request,@RequestBody String idsList){
        JSONObject json = new JSONObject();
        try {
            UserInfo userInfo = SessionUtils.getUserInfo();
            if(userInfo == null) json = RestfulRetUtils.getErrorNoUser();
            else {
                json = scmsCommissionRuleService.del(idsList, userInfo);
            }
        } catch (Exception e) {
            json = RestfulRetUtils.getErrorMsg("51004","删除信息失败");
            logger.error(e.getMessage(), e);
        }
        return json;
    }

    /**
     * 获取列表
     * @param request
     * @return
     */
    @RequestMapping(value="/getUserPointList",method=RequestMethod.GET)
    public JSONObject getUserPointList(HttpServletRequest request, @RequestParam Map<String, Object> params){
        JSONObject json = new JSONObject();
        try{
            UserInfo userInfo = SessionUtils.getUserInfo();
            if(userInfo == null) json = RestfulRetUtils.getErrorNoUser();
            else {
                Pageable pageable = new PageRequest(SessionUtils.getPageIndex(request), SessionUtils.getPageSize(request));  
                ScmsUserPoint scmsUserPoint = (ScmsUserPoint)BeanUtils.mapToBean(params, ScmsUserPoint.class);              
                json = scmsUserPointService.getList(pageable, scmsUserPoint, params);
            }
        }catch(Exception e){
            json = RestfulRetUtils.getErrorMsg("51001","获取列表失败");
            logger.error(e.getMessage(), e);
        }
        return json;
    }
    
    
    /**
     * 新增信息
     * @param request
     * @param params
     * @return
     */
    @RequestMapping(value="/addUserPoint",method=RequestMethod.POST)
    public JSONObject addUserPoint(HttpServletRequest request, @RequestBody Map<String, Object> params){
        JSONObject json = new JSONObject();
        try{
            UserInfo userInfo = SessionUtils.getUserInfo();
            if(userInfo == null) json = RestfulRetUtils.getErrorNoUser();
            else {
                json = scmsUserPointService.add(params, userInfo);
            }
        }catch(Exception e){
            json = RestfulRetUtils.getErrorMsg("51002","新增信息失败");
            logger.error(e.getMessage(), e);
        }
        return json;
    }
    
    /**
     * 编辑信息
     * @param request
     * @param params
     * @return
     */
    @RequestMapping(value="/editUserPoint",method=RequestMethod.POST)
    public JSONObject editUserPoint(HttpServletRequest request, @RequestBody Map<String, Object> params){
        JSONObject json = new JSONObject();
        try{
            UserInfo userInfo = SessionUtils.getUserInfo();
            if(userInfo == null) json = RestfulRetUtils.getErrorNoUser();
            else {
                json = scmsUserPointService.edit(params, userInfo);
            }
        }catch(Exception e){
            json = RestfulRetUtils.getErrorMsg("51003","编辑信息失败");
            logger.error(e.getMessage(), e);
        }
        return json;
    }
    
    /**
     * 删除信息
     * @param request
     * @param idsList
     * @return
     */
    @RequestMapping(value="/delUserPoint",method=RequestMethod.POST)
    public JSONObject delUserPoint(HttpServletRequest request,@RequestBody String idsList){
        JSONObject json = new JSONObject();
        try {
            UserInfo userInfo = SessionUtils.getUserInfo();
            if(userInfo == null) json = RestfulRetUtils.getErrorNoUser();
            else {
                json = scmsUserPointService.del(idsList, userInfo);
            }
        } catch (Exception e) {
            json = RestfulRetUtils.getErrorMsg("51004","删除信息失败");
            logger.error(e.getMessage(), e);
        }
        return json;
    }
}
