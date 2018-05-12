package com.scms.restful;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.collections4.MapUtils;
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
import com.scms.modules.base.entity.ScmsCarouselImage;
import com.scms.modules.base.entity.ScmsColorInfo;
import com.scms.modules.base.entity.ScmsFeedbackInfo;
import com.scms.modules.base.entity.ScmsMerchantsInfo;
import com.scms.modules.base.entity.ScmsPayInfo;
import com.scms.modules.base.entity.ScmsShopInfo;
import com.scms.modules.base.entity.ScmsSizeInfo;
import com.scms.modules.base.entity.ScmsTagInfo;
import com.scms.modules.base.entity.ScmsTextureInfo;
import com.scms.modules.base.entity.ScmsTransportInfo;
import com.scms.modules.base.entity.ScmsVenderInfo;
import com.scms.modules.base.service.ScmsCarouselImageService;
import com.scms.modules.base.service.ScmsColorInfoService;
import com.scms.modules.base.service.ScmsFeedbackInfoService;
import com.scms.modules.base.service.ScmsMerchantsInfoService;
import com.scms.modules.base.service.ScmsPayInfoService;
import com.scms.modules.base.service.ScmsShopInfoService;
import com.scms.modules.base.service.ScmsSizeInfoService;
import com.scms.modules.base.service.ScmsTagInfoService;
import com.scms.modules.base.service.ScmsTextureInfoService;
import com.scms.modules.base.service.ScmsTransportInfoService;
import com.scms.modules.base.service.ScmsVenderInfoService;

@RestController
@RequestMapping(value = "/api/scms/base")
public class BaseRestful {
    
    protected static final Logger logger = LogManager.getLogger(BaseRestful.class);
    
    @Autowired
    private ScmsMerchantsInfoService scmsMerchantsInfoService;
    
    @Autowired
    private ScmsShopInfoService scmsShopInfoService;
    
    @Autowired
    private ScmsColorInfoService scmsColorInfoService;
    
    @Autowired
    private ScmsSizeInfoService scmsSizeInfoService;
    
    @Autowired
    private ScmsTextureInfoService scmsTextureInfoService;
    
    @Autowired
    private ScmsTransportInfoService scmsTransportInfoService;
    
    @Autowired
    private ScmsPayInfoService scmsPayInfoService;
    
    @Autowired
    private ScmsTagInfoService scmsTagInfoService;
    
    @Autowired
    private ScmsVenderInfoService scmsVenderInfoService;
    
    @Autowired
    private ScmsCarouselImageService scmsCarouselImageService;
    
    @Autowired
    private ScmsFeedbackInfoService scmsFeedbackInfoService;
    
    @RequestMapping(value="/homePageIndex", method=RequestMethod.GET)
    public JSONObject homePageIndex(HttpServletRequest request){ return RestfulRetUtils.getRetSuccess();}

    @RequestMapping(value="/myMerchantsInfoIndex", method=RequestMethod.GET)
    public JSONObject myMerchantsInfoIndex(HttpServletRequest request){ return RestfulRetUtils.getRetSuccess();}

    @RequestMapping(value="/merchantsInfoIndex", method=RequestMethod.GET)
    public JSONObject merchantsInfoIndex(HttpServletRequest request){ return RestfulRetUtils.getRetSuccess();}

    @RequestMapping(value="/shopInfoIndex", method=RequestMethod.GET)
    public JSONObject shopInfoIndex(HttpServletRequest request){ return RestfulRetUtils.getRetSuccess();}

    @RequestMapping(value="/colorInfoIndex", method=RequestMethod.GET)
    public JSONObject colorInfoIndex(HttpServletRequest request){ return RestfulRetUtils.getRetSuccess();}

    @RequestMapping(value="/sizeInfoIndex", method=RequestMethod.GET)
    public JSONObject sizeInfoIndex(HttpServletRequest request){ return RestfulRetUtils.getRetSuccess();}

    @RequestMapping(value="/textureInfoIndex", method=RequestMethod.GET)
    public JSONObject textureInfoIndex(HttpServletRequest request){ return RestfulRetUtils.getRetSuccess();}

    @RequestMapping(value="/transportInfoIndex", method=RequestMethod.GET)
    public JSONObject transportInfoIndex(HttpServletRequest request){ return RestfulRetUtils.getRetSuccess();}

    @RequestMapping(value="/payInfoIndex", method=RequestMethod.GET)
    public JSONObject payInfoIndex(HttpServletRequest request){ return RestfulRetUtils.getRetSuccess();}

    @RequestMapping(value="/tagInfoIndex", method=RequestMethod.GET)
    public JSONObject tagInfoIndex(HttpServletRequest request){ return RestfulRetUtils.getRetSuccess();}
    
    @RequestMapping(value="/venderInfoIndex", method=RequestMethod.GET)
    public JSONObject venderInfoIndex(HttpServletRequest request){ return RestfulRetUtils.getRetSuccess();}
    
    @RequestMapping(value="/printIndex", method=RequestMethod.GET)
    public JSONObject printIndex(HttpServletRequest request){ return RestfulRetUtils.getRetSuccess();}

    @RequestMapping(value="/feedbackInfoIndex", method=RequestMethod.GET)
    public JSONObject feedbackInfoIndex(HttpServletRequest request){ return RestfulRetUtils.getRetSuccess();}

    @RequestMapping(value="/carouselImageIndex", method=RequestMethod.GET)
    public JSONObject carouselImageIndex(HttpServletRequest request){ return RestfulRetUtils.getRetSuccess();}

    /**
     * 获取列表
     * @param request
     * @return
     */
    @RequestMapping(value="/getMerchantsInfoList",method=RequestMethod.GET)
    public JSONObject getMerchantsInfoList(HttpServletRequest request, @RequestParam Map<String, Object> params){
        JSONObject json = new JSONObject();
        try{
            UserInfo userInfo = SessionUtils.getUserInfo();
            if(userInfo == null) json = RestfulRetUtils.getErrorNoUser();
            else {
                Pageable pageable = new PageRequest(SessionUtils.getPageIndex(request), SessionUtils.getPageSize(request));  
                ScmsMerchantsInfo scmsMerchantsInfo = (ScmsMerchantsInfo)BeanUtils.mapToBean(params, ScmsMerchantsInfo.class);              
                json = scmsMerchantsInfoService.getList(pageable, scmsMerchantsInfo, params);
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
    @RequestMapping(value="/addMerchantsInfo",method=RequestMethod.POST)
    public JSONObject addMerchantsInfo(HttpServletRequest request, @RequestBody Map<String, Object> params){
        JSONObject json = new JSONObject();
        try{
            UserInfo userInfo = SessionUtils.getUserInfo();
            if(userInfo == null) json = RestfulRetUtils.getErrorNoUser();
            else {
                json = scmsMerchantsInfoService.add(params, userInfo);
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
    @RequestMapping(value="/editMerchantsInfo",method=RequestMethod.POST)
    public JSONObject editMerchantsInfo(HttpServletRequest request, @RequestBody Map<String, Object> params){
        JSONObject json = new JSONObject();
        try{
            UserInfo userInfo = SessionUtils.getUserInfo();
            if(userInfo == null) json = RestfulRetUtils.getErrorNoUser();
            else {
                json = scmsMerchantsInfoService.edit(params, userInfo);
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
    @RequestMapping(value="/delMerchantsInfo",method=RequestMethod.POST)
    public JSONObject delMerchantsInfo(HttpServletRequest request,@RequestBody String idsList){
        JSONObject json = new JSONObject();
        try {
            UserInfo userInfo = SessionUtils.getUserInfo();
            if(userInfo == null) json = RestfulRetUtils.getErrorNoUser();
            else {
                json = scmsMerchantsInfoService.del(idsList, userInfo);
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
    @RequestMapping(value="/getShopInfoList",method=RequestMethod.GET)
    public JSONObject getShopInfoList(HttpServletRequest request, @RequestParam Map<String, Object> params){
        JSONObject json = new JSONObject();
        try{
            UserInfo userInfo = SessionUtils.getUserInfo();
            if(userInfo == null) json = RestfulRetUtils.getErrorNoUser();
            else {
                Pageable pageable = new PageRequest(SessionUtils.getPageIndex(request), SessionUtils.getPageSize(request));  
                ScmsShopInfo scmsShopInfo = (ScmsShopInfo)BeanUtils.mapToBean(params, ScmsShopInfo.class);              
                json = scmsShopInfoService.getList(pageable, scmsShopInfo, params);
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
    @RequestMapping(value="/addShopInfo",method=RequestMethod.POST)
    public JSONObject addShopInfo(HttpServletRequest request, @RequestBody Map<String, Object> params){
        JSONObject json = new JSONObject();
        try{
            UserInfo userInfo = SessionUtils.getUserInfo();
            if(userInfo == null) json = RestfulRetUtils.getErrorNoUser();
            else {
                json = scmsShopInfoService.add(params, userInfo);
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
    @RequestMapping(value="/editShopInfo",method=RequestMethod.POST)
    public JSONObject editShopInfo(HttpServletRequest request, @RequestBody Map<String, Object> params){
        JSONObject json = new JSONObject();
        try{
            UserInfo userInfo = SessionUtils.getUserInfo();
            if(userInfo == null) json = RestfulRetUtils.getErrorNoUser();
            else {
                json = scmsShopInfoService.edit(params, userInfo);
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
    @RequestMapping(value="/delShopInfo",method=RequestMethod.POST)
    public JSONObject delShopInfo(HttpServletRequest request,@RequestBody String idsList){
        JSONObject json = new JSONObject();
        try {
            UserInfo userInfo = SessionUtils.getUserInfo();
            if(userInfo == null) json = RestfulRetUtils.getErrorNoUser();
            else {
                json = scmsShopInfoService.del(idsList, userInfo);
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
    @RequestMapping(value="/getShopKeyVal",method=RequestMethod.GET)
    public JSONObject getShopKeyVal(HttpServletRequest request, @RequestParam Map<String, Object> params){
        JSONObject json = new JSONObject();
        try{
            UserInfo userInfo = SessionUtils.getUserInfo();
            if(userInfo == null) json = RestfulRetUtils.getErrorNoUser();
            else {
                json = scmsShopInfoService.getShopKeyVal(MapUtils.getLong(params, "merchantsId"));
            }
        }catch(Exception e){
            json = RestfulRetUtils.getErrorMsg("51001","获取列表失败");
            logger.error(e.getMessage(), e);
        }
        return json;
    }

    /**
     * 获取列表
     * @param request
     * @return
     */
    @RequestMapping(value="/getColorInfoList",method=RequestMethod.GET)
    public JSONObject getColorInfoList(HttpServletRequest request, @RequestParam Map<String, Object> params){
        JSONObject json = new JSONObject();
        try{
            UserInfo userInfo = SessionUtils.getUserInfo();
            if(userInfo == null) json = RestfulRetUtils.getErrorNoUser();
            else {
                Pageable pageable = new PageRequest(SessionUtils.getPageIndex(request), SessionUtils.getPageSize(request));  
                ScmsColorInfo scmsColorInfo = (ScmsColorInfo)BeanUtils.mapToBean(params, ScmsColorInfo.class);              
                json = scmsColorInfoService.getList(pageable, scmsColorInfo, params);
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
    @RequestMapping(value="/addColorInfo",method=RequestMethod.POST)
    public JSONObject addColorInfo(HttpServletRequest request, @RequestBody Map<String, Object> params){
        JSONObject json = new JSONObject();
        try{
            UserInfo userInfo = SessionUtils.getUserInfo();
            if(userInfo == null) json = RestfulRetUtils.getErrorNoUser();
            else {
                json = scmsColorInfoService.add(params, userInfo);
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
    @RequestMapping(value="/editColorInfo",method=RequestMethod.POST)
    public JSONObject editColorInfo(HttpServletRequest request, @RequestBody Map<String, Object> params){
        JSONObject json = new JSONObject();
        try{
            UserInfo userInfo = SessionUtils.getUserInfo();
            if(userInfo == null) json = RestfulRetUtils.getErrorNoUser();
            else {
                json = scmsColorInfoService.edit(params, userInfo);
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
    @RequestMapping(value="/delColorInfo",method=RequestMethod.POST)
    public JSONObject delColorInfo(HttpServletRequest request,@RequestBody String idsList){
        JSONObject json = new JSONObject();
        try {
            UserInfo userInfo = SessionUtils.getUserInfo();
            if(userInfo == null) json = RestfulRetUtils.getErrorNoUser();
            else {
                json = scmsColorInfoService.del(idsList, userInfo);
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
    @RequestMapping(value="/getSizeInfoList",method=RequestMethod.GET)
    public JSONObject getSizeInfoList(HttpServletRequest request, @RequestParam Map<String, Object> params){
        JSONObject json = new JSONObject();
        try{
            UserInfo userInfo = SessionUtils.getUserInfo();
            if(userInfo == null) json = RestfulRetUtils.getErrorNoUser();
            else {
                Pageable pageable = new PageRequest(SessionUtils.getPageIndex(request), SessionUtils.getPageSize(request));  
                ScmsSizeInfo scmsSizeInfo = (ScmsSizeInfo)BeanUtils.mapToBean(params, ScmsSizeInfo.class);              
                json = scmsSizeInfoService.getList(pageable, scmsSizeInfo, params);
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
    @RequestMapping(value="/addSizeInfo",method=RequestMethod.POST)
    public JSONObject addSizeInfo(HttpServletRequest request, @RequestBody Map<String, Object> params){
        JSONObject json = new JSONObject();
        try{
            UserInfo userInfo = SessionUtils.getUserInfo();
            if(userInfo == null) json = RestfulRetUtils.getErrorNoUser();
            else {
                json = scmsSizeInfoService.add(params, userInfo);
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
    @RequestMapping(value="/editSizeInfo",method=RequestMethod.POST)
    public JSONObject editSizeInfo(HttpServletRequest request, @RequestBody Map<String, Object> params){
        JSONObject json = new JSONObject();
        try{
            UserInfo userInfo = SessionUtils.getUserInfo();
            if(userInfo == null) json = RestfulRetUtils.getErrorNoUser();
            else {
                json = scmsSizeInfoService.edit(params, userInfo);
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
    @RequestMapping(value="/delSizeInfo",method=RequestMethod.POST)
    public JSONObject delSizeInfo(HttpServletRequest request,@RequestBody String idsList){
        JSONObject json = new JSONObject();
        try {
            UserInfo userInfo = SessionUtils.getUserInfo();
            if(userInfo == null) json = RestfulRetUtils.getErrorNoUser();
            else {
                json = scmsSizeInfoService.del(idsList, userInfo);
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
    @RequestMapping(value="/getTextureInfoList",method=RequestMethod.GET)
    public JSONObject getTextureInfoList(HttpServletRequest request, @RequestParam Map<String, Object> params){
        JSONObject json = new JSONObject();
        try{
            UserInfo userInfo = SessionUtils.getUserInfo();
            if(userInfo == null) json = RestfulRetUtils.getErrorNoUser();
            else {
                Pageable pageable = new PageRequest(SessionUtils.getPageIndex(request), SessionUtils.getPageSize(request));  
                ScmsTextureInfo scmsTextureInfo = (ScmsTextureInfo)BeanUtils.mapToBean(params, ScmsTextureInfo.class);              
                json = scmsTextureInfoService.getList(pageable, scmsTextureInfo, params);
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
    @RequestMapping(value="/addTextureInfo",method=RequestMethod.POST)
    public JSONObject addTextureInfo(HttpServletRequest request, @RequestBody Map<String, Object> params){
        JSONObject json = new JSONObject();
        try{
            UserInfo userInfo = SessionUtils.getUserInfo();
            if(userInfo == null) json = RestfulRetUtils.getErrorNoUser();
            else {
                json = scmsTextureInfoService.add(params, userInfo);
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
    @RequestMapping(value="/editTextureInfo",method=RequestMethod.POST)
    public JSONObject editTextureInfo(HttpServletRequest request, @RequestBody Map<String, Object> params){
        JSONObject json = new JSONObject();
        try{
            UserInfo userInfo = SessionUtils.getUserInfo();
            if(userInfo == null) json = RestfulRetUtils.getErrorNoUser();
            else {
                json = scmsTextureInfoService.edit(params, userInfo);
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
    @RequestMapping(value="/delTextureInfo",method=RequestMethod.POST)
    public JSONObject delTextureInfo(HttpServletRequest request,@RequestBody String idsList){
        JSONObject json = new JSONObject();
        try {
            UserInfo userInfo = SessionUtils.getUserInfo();
            if(userInfo == null) json = RestfulRetUtils.getErrorNoUser();
            else {
                json = scmsTextureInfoService.del(idsList, userInfo);
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
    @RequestMapping(value="/getTransportInfoList",method=RequestMethod.GET)
    public JSONObject getTransportInfoList(HttpServletRequest request, @RequestParam Map<String, Object> params){
        JSONObject json = new JSONObject();
        try{
            UserInfo userInfo = SessionUtils.getUserInfo();
            if(userInfo == null) json = RestfulRetUtils.getErrorNoUser();
            else {
                Pageable pageable = new PageRequest(SessionUtils.getPageIndex(request), SessionUtils.getPageSize(request));  
                ScmsTransportInfo scmsTransportInfo = (ScmsTransportInfo)BeanUtils.mapToBean(params, ScmsTransportInfo.class);              
                json = scmsTransportInfoService.getList(pageable, scmsTransportInfo, params);
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
    @RequestMapping(value="/addTransportInfo",method=RequestMethod.POST)
    public JSONObject addTransportInfo(HttpServletRequest request, @RequestBody Map<String, Object> params){
        JSONObject json = new JSONObject();
        try{
            UserInfo userInfo = SessionUtils.getUserInfo();
            if(userInfo == null) json = RestfulRetUtils.getErrorNoUser();
            else {
                json = scmsTransportInfoService.add(params, userInfo);
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
    @RequestMapping(value="/editTransportInfo",method=RequestMethod.POST)
    public JSONObject editTransportInfo(HttpServletRequest request, @RequestBody Map<String, Object> params){
        JSONObject json = new JSONObject();
        try{
            UserInfo userInfo = SessionUtils.getUserInfo();
            if(userInfo == null) json = RestfulRetUtils.getErrorNoUser();
            else {
                json = scmsTransportInfoService.edit(params, userInfo);
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
    @RequestMapping(value="/delTransportInfo",method=RequestMethod.POST)
    public JSONObject delTransportInfo(HttpServletRequest request,@RequestBody String idsList){
        JSONObject json = new JSONObject();
        try {
            UserInfo userInfo = SessionUtils.getUserInfo();
            if(userInfo == null) json = RestfulRetUtils.getErrorNoUser();
            else {
                json = scmsTransportInfoService.del(idsList, userInfo);
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
    @RequestMapping(value="/getPayInfoList",method=RequestMethod.GET)
    public JSONObject getPayInfoList(HttpServletRequest request, @RequestParam Map<String, Object> params){
        JSONObject json = new JSONObject();
        try{
            UserInfo userInfo = SessionUtils.getUserInfo();
            if(userInfo == null) json = RestfulRetUtils.getErrorNoUser();
            else {
                Pageable pageable = new PageRequest(SessionUtils.getPageIndex(request), SessionUtils.getPageSize(request));  
                ScmsPayInfo scmsPayInfo = (ScmsPayInfo)BeanUtils.mapToBean(params, ScmsPayInfo.class);              
                json = scmsPayInfoService.getList(pageable, scmsPayInfo, params);
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
    @RequestMapping(value="/addPayInfo",method=RequestMethod.POST)
    public JSONObject addPayInfo(HttpServletRequest request, @RequestBody Map<String, Object> params){
        JSONObject json = new JSONObject();
        try{
            UserInfo userInfo = SessionUtils.getUserInfo();
            if(userInfo == null) json = RestfulRetUtils.getErrorNoUser();
            else {
                json = scmsPayInfoService.add(params, userInfo);
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
    @RequestMapping(value="/editPayInfo",method=RequestMethod.POST)
    public JSONObject editPayInfo(HttpServletRequest request, @RequestBody Map<String, Object> params){
        JSONObject json = new JSONObject();
        try{
            UserInfo userInfo = SessionUtils.getUserInfo();
            if(userInfo == null) json = RestfulRetUtils.getErrorNoUser();
            else {
                json = scmsPayInfoService.edit(params, userInfo);
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
    @RequestMapping(value="/delPayInfo",method=RequestMethod.POST)
    public JSONObject delPayInfo(HttpServletRequest request,@RequestBody String idsList){
        JSONObject json = new JSONObject();
        try {
            UserInfo userInfo = SessionUtils.getUserInfo();
            if(userInfo == null) json = RestfulRetUtils.getErrorNoUser();
            else {
                json = scmsPayInfoService.del(idsList, userInfo);
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
    @RequestMapping(value="/getTagInfoList",method=RequestMethod.GET)
    public JSONObject getTagInfoList(HttpServletRequest request, @RequestParam Map<String, Object> params){
        JSONObject json = new JSONObject();
        try{
            UserInfo userInfo = SessionUtils.getUserInfo();
            if(userInfo == null) json = RestfulRetUtils.getErrorNoUser();
            else {
                Pageable pageable = new PageRequest(SessionUtils.getPageIndex(request), SessionUtils.getPageSize(request));  
                ScmsTagInfo scmsTagInfo = (ScmsTagInfo)BeanUtils.mapToBean(params, ScmsTagInfo.class);              
                json = scmsTagInfoService.getList(pageable, scmsTagInfo, params);
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
    @RequestMapping(value="/addTagInfo",method=RequestMethod.POST)
    public JSONObject addTagInfo(HttpServletRequest request, @RequestBody Map<String, Object> params){
        JSONObject json = new JSONObject();
        try{
            UserInfo userInfo = SessionUtils.getUserInfo();
            if(userInfo == null) json = RestfulRetUtils.getErrorNoUser();
            else {
                json = scmsTagInfoService.add(params, userInfo);
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
    @RequestMapping(value="/editTagInfo",method=RequestMethod.POST)
    public JSONObject editTagInfo(HttpServletRequest request, @RequestBody Map<String, Object> params){
        JSONObject json = new JSONObject();
        try{
            UserInfo userInfo = SessionUtils.getUserInfo();
            if(userInfo == null) json = RestfulRetUtils.getErrorNoUser();
            else {
                json = scmsTagInfoService.edit(params, userInfo);
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
    @RequestMapping(value="/delTagInfo",method=RequestMethod.POST)
    public JSONObject delTagInfo(HttpServletRequest request,@RequestBody String idsList){
        JSONObject json = new JSONObject();
        try {
            UserInfo userInfo = SessionUtils.getUserInfo();
            if(userInfo == null) json = RestfulRetUtils.getErrorNoUser();
            else {
                json = scmsTagInfoService.del(idsList, userInfo);
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
    @RequestMapping(value="/getVenderInfoList",method=RequestMethod.GET)
    public JSONObject getVenderInfoList(HttpServletRequest request, @RequestParam Map<String, Object> params){
        JSONObject json = new JSONObject();
        try{
            UserInfo userInfo = SessionUtils.getUserInfo();
            if(userInfo == null) json = RestfulRetUtils.getErrorNoUser();
            else {
                Pageable pageable = new PageRequest(SessionUtils.getPageIndex(request), SessionUtils.getPageSize(request));  
                ScmsVenderInfo scmsVenderInfo = (ScmsVenderInfo)BeanUtils.mapToBean(params, ScmsVenderInfo.class);              
                json = scmsVenderInfoService.getList(pageable, scmsVenderInfo, params);
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
    @RequestMapping(value="/addVenderInfo",method=RequestMethod.POST)
    public JSONObject addVenderInfo(HttpServletRequest request, @RequestBody Map<String, Object> params){
        JSONObject json = new JSONObject();
        try{
            UserInfo userInfo = SessionUtils.getUserInfo();
            if(userInfo == null) json = RestfulRetUtils.getErrorNoUser();
            else {
                json = scmsVenderInfoService.add(params, userInfo);
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
    @RequestMapping(value="/editVenderInfo",method=RequestMethod.POST)
    public JSONObject editVenderInfo(HttpServletRequest request, @RequestBody Map<String, Object> params){
        JSONObject json = new JSONObject();
        try{
            UserInfo userInfo = SessionUtils.getUserInfo();
            if(userInfo == null) json = RestfulRetUtils.getErrorNoUser();
            else {
                json = scmsVenderInfoService.edit(params, userInfo);
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
    @RequestMapping(value="/delVenderInfo",method=RequestMethod.POST)
    public JSONObject delVenderInfo(HttpServletRequest request,@RequestBody String idsList){
        JSONObject json = new JSONObject();
        try {
            UserInfo userInfo = SessionUtils.getUserInfo();
            if(userInfo == null) json = RestfulRetUtils.getErrorNoUser();
            else {
                json = scmsVenderInfoService.del(idsList, userInfo);
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
    @RequestMapping(value="/getFeedbackInfoList",method=RequestMethod.GET)
    public JSONObject getFeedbackInfoList(HttpServletRequest request, @RequestParam Map<String, Object> params){
        JSONObject json = new JSONObject();
        try{
            UserInfo userInfo = SessionUtils.getUserInfo();
            if(userInfo == null) json = RestfulRetUtils.getErrorNoUser();
            else {
                Pageable pageable = new PageRequest(SessionUtils.getPageIndex(request), SessionUtils.getPageSize(request));  
                ScmsFeedbackInfo scmsFeedbackInfo = (ScmsFeedbackInfo)BeanUtils.mapToBean(params, ScmsFeedbackInfo.class);              
                json = scmsFeedbackInfoService.getList(pageable, scmsFeedbackInfo, params);
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
    @RequestMapping(value="/addFeedbackInfo",method=RequestMethod.POST)
    public JSONObject addFeedbackInfo(HttpServletRequest request, @RequestBody Map<String, Object> params){
        JSONObject json = new JSONObject();
        try{
            UserInfo userInfo = SessionUtils.getUserInfo();
            if(userInfo == null) json = RestfulRetUtils.getErrorNoUser();
            else {
                json = scmsFeedbackInfoService.add(params, userInfo);
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
    @RequestMapping(value="/editFeedbackInfo",method=RequestMethod.POST)
    public JSONObject editFeedbackInfo(HttpServletRequest request, @RequestBody Map<String, Object> params){
        JSONObject json = new JSONObject();
        try{
            UserInfo userInfo = SessionUtils.getUserInfo();
            if(userInfo == null) json = RestfulRetUtils.getErrorNoUser();
            else {
                json = scmsFeedbackInfoService.edit(params, userInfo);
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
    @RequestMapping(value="/delFeedbackInfo",method=RequestMethod.POST)
    public JSONObject delFeedbackInfo(HttpServletRequest request,@RequestBody String idsList){
        JSONObject json = new JSONObject();
        try {
            UserInfo userInfo = SessionUtils.getUserInfo();
            if(userInfo == null) json = RestfulRetUtils.getErrorNoUser();
            else {
                json = scmsFeedbackInfoService.del(idsList, userInfo);
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
    @RequestMapping(value="/getCarouselImageList",method=RequestMethod.GET)
    public JSONObject getCarouselImageList(HttpServletRequest request, @RequestParam Map<String, Object> params){
        JSONObject json = new JSONObject();
        try{
            UserInfo userInfo = SessionUtils.getUserInfo();
            if(userInfo == null) json = RestfulRetUtils.getErrorNoUser();
            else {
                Pageable pageable = new PageRequest(SessionUtils.getPageIndex(request), SessionUtils.getPageSize(request));  
                ScmsCarouselImage scmsCarouselImage = (ScmsCarouselImage)BeanUtils.mapToBean(params, ScmsCarouselImage.class);              
                json = scmsCarouselImageService.getList(pageable, scmsCarouselImage, params);
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
    @RequestMapping(value="/addCarouselImage",method=RequestMethod.POST)
    public JSONObject addCarouselImage(HttpServletRequest request, @RequestBody Map<String, Object> params){
        JSONObject json = new JSONObject();
        try{
            UserInfo userInfo = SessionUtils.getUserInfo();
            if(userInfo == null) json = RestfulRetUtils.getErrorNoUser();
            else {
                json = scmsCarouselImageService.add(params, userInfo);
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
    @RequestMapping(value="/editCarouselImage",method=RequestMethod.POST)
    public JSONObject editCarouselImage(HttpServletRequest request, @RequestBody Map<String, Object> params){
        JSONObject json = new JSONObject();
        try{
            UserInfo userInfo = SessionUtils.getUserInfo();
            if(userInfo == null) json = RestfulRetUtils.getErrorNoUser();
            else {
                json = scmsCarouselImageService.edit(params, userInfo);
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
    @RequestMapping(value="/delCarouselImage",method=RequestMethod.POST)
    public JSONObject delCarouselImage(HttpServletRequest request,@RequestBody String idsList){
        JSONObject json = new JSONObject();
        try {
            UserInfo userInfo = SessionUtils.getUserInfo();
            if(userInfo == null) json = RestfulRetUtils.getErrorNoUser();
            else {
                json = scmsCarouselImageService.del(idsList, userInfo);
            }
        } catch (Exception e) {
            json = RestfulRetUtils.getErrorMsg("51004","删除信息失败");
            logger.error(e.getMessage(), e);
        }
        return json;
    }
    
    /**
     * 保存排序结果
     * @param request
     * @param idsList
     * @return
     */
    @RequestMapping(value="/saveOrderCarouselImage",method=RequestMethod.POST)
    public JSONObject saveOrderCarouselImage(HttpServletRequest request, @RequestBody String params){
        JSONObject json = new JSONObject();
        try {
            UserInfo userInfo = SessionUtils.getUserInfo();
            if(userInfo == null) json = RestfulRetUtils.getErrorNoUser();
            else {
                json = scmsCarouselImageService.saveOrderCarouselImage(params);
            }
        } catch (Exception e) {
            json = RestfulRetUtils.getErrorMsg("51004","保存排序结果失败");
            logger.error(e.getMessage(), e);
        }
        return json;
    }
}
