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
import com.gimplatform.core.annotation.LogConf;
import com.gimplatform.core.annotation.LogConfOperateType;
import com.gimplatform.core.entity.UserInfo;
import com.gimplatform.core.utils.BeanUtils;
import com.gimplatform.core.utils.RestfulRetUtils;
import com.gimplatform.core.utils.SessionUtils;
import com.scms.modules.base.entity.ScmsAdvertInfo;
import com.scms.modules.base.entity.ScmsColorInfo;
import com.scms.modules.base.entity.ScmsFeedbackInfo;
import com.scms.modules.base.entity.ScmsMerchantsInfo;
import com.scms.modules.base.entity.ScmsPayInfo;
import com.scms.modules.base.entity.ScmsPrintInfo;
import com.scms.modules.base.entity.ScmsPrintRemote;
import com.scms.modules.base.entity.ScmsShopInfo;
import com.scms.modules.base.entity.ScmsSizeInfo;
import com.scms.modules.base.entity.ScmsTagInfo;
import com.scms.modules.base.entity.ScmsTextureInfo;
import com.scms.modules.base.entity.ScmsTransportInfo;
import com.scms.modules.base.entity.ScmsVenderInfo;
import com.scms.modules.base.service.ScmsAdvertInfoService;
import com.scms.modules.base.service.ScmsColorInfoService;
import com.scms.modules.base.service.ScmsFeedbackInfoService;
import com.scms.modules.base.service.ScmsMerchantsInfoService;
import com.scms.modules.base.service.ScmsPayInfoService;
import com.scms.modules.base.service.ScmsPrintInfoService;
import com.scms.modules.base.service.ScmsPrintRemoteService;
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
    private ScmsAdvertInfoService scmsAdvertInfoService;
    
    @Autowired
    private ScmsFeedbackInfoService scmsFeedbackInfoService;
    
    @Autowired
    private ScmsPrintInfoService scmsPrintInfoService;
    
    @Autowired
    private ScmsPrintRemoteService scmsPrintRemoteService;
    
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

    @RequestMapping(value="/advertInfoIndex", method=RequestMethod.GET)
    public JSONObject advertInfoIndex(HttpServletRequest request){ return RestfulRetUtils.getRetSuccess();}

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
    @LogConf(operateType=LogConfOperateType.ADD, logDesc="新增商户")
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
    @LogConf(operateType=LogConfOperateType.EDIT, logDesc="编辑商户")
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
    @LogConf(operateType=LogConfOperateType.DELETE, logDesc="删除商户")
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
    @LogConf(operateType=LogConfOperateType.ADD, logDesc="新增店铺")
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
    @LogConf(operateType=LogConfOperateType.EDIT, logDesc="编辑店铺")
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
    @LogConf(operateType=LogConfOperateType.DELETE, logDesc="删除店铺")
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
    @LogConf(operateType=LogConfOperateType.ADD, logDesc="新增商品颜色")
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
    @LogConf(operateType=LogConfOperateType.EDIT, logDesc="编辑商品颜色")
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
    @LogConf(operateType=LogConfOperateType.DELETE, logDesc="删除商品颜色")
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
    @LogConf(operateType=LogConfOperateType.ADD, logDesc="新增商品尺码")
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
    @LogConf(operateType=LogConfOperateType.EDIT, logDesc="编辑商品尺码")
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
    @LogConf(operateType=LogConfOperateType.DELETE, logDesc="删除商品尺码")
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
    @LogConf(operateType=LogConfOperateType.ADD, logDesc="新增商品材质")
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
    @LogConf(operateType=LogConfOperateType.EDIT, logDesc="编辑商品材质")
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
    @LogConf(operateType=LogConfOperateType.EDIT, logDesc="删除商品材质")
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
    @LogConf(operateType=LogConfOperateType.ADD, logDesc="新增运输方式")
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
    @LogConf(operateType=LogConfOperateType.EDIT, logDesc="编辑运输方式")
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
    @LogConf(operateType=LogConfOperateType.DELETE, logDesc="删除运输方式")
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
    @LogConf(operateType=LogConfOperateType.ADD, logDesc="新增付款方式")
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
    @LogConf(operateType=LogConfOperateType.EDIT, logDesc="编辑付款方式")
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
    @LogConf(operateType=LogConfOperateType.DELETE, logDesc="删除付款方式")
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
    @LogConf(operateType=LogConfOperateType.ADD, logDesc="新增标签")
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
    @LogConf(operateType=LogConfOperateType.EDIT, logDesc="编辑标签")
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
    @LogConf(operateType=LogConfOperateType.DELETE, logDesc="删除标签")
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
    @LogConf(operateType=LogConfOperateType.ADD, logDesc="新增厂家信息")
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
    @LogConf(operateType=LogConfOperateType.EDIT, logDesc="编辑厂家信息")
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
    @LogConf(operateType=LogConfOperateType.DELETE, logDesc="删除厂家信息")
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
    @LogConf(operateType=LogConfOperateType.ADD, logDesc="新增反馈信息")
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
    @LogConf(operateType=LogConfOperateType.EDIT, logDesc="编辑反馈信息")
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
    @LogConf(operateType=LogConfOperateType.DELETE, logDesc="删除反馈信息")
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
    @RequestMapping(value="/getAdvertInfoList",method=RequestMethod.GET)
    public JSONObject getAdvertInfoList(HttpServletRequest request, @RequestParam Map<String, Object> params){
        JSONObject json = new JSONObject();
        try{
            UserInfo userInfo = SessionUtils.getUserInfo();
            if(userInfo == null) json = RestfulRetUtils.getErrorNoUser();
            else {
                Pageable pageable = new PageRequest(SessionUtils.getPageIndex(request), SessionUtils.getPageSize(request));  
                ScmsAdvertInfo scmsAdvertInfo = (ScmsAdvertInfo)BeanUtils.mapToBean(params, ScmsAdvertInfo.class);              
                json = scmsAdvertInfoService.getList(pageable, scmsAdvertInfo, params);
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
    @RequestMapping(value="/addAdvertInfo",method=RequestMethod.POST)
    public JSONObject addAdvertInfo(HttpServletRequest request, @RequestBody Map<String, Object> params){
        JSONObject json = new JSONObject();
        try{
            UserInfo userInfo = SessionUtils.getUserInfo();
            if(userInfo == null) json = RestfulRetUtils.getErrorNoUser();
            else {
                json = scmsAdvertInfoService.add(params, userInfo);
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
    @RequestMapping(value="/editAdvertInfo",method=RequestMethod.POST)
    public JSONObject editAdvertInfo(HttpServletRequest request, @RequestBody Map<String, Object> params){
        JSONObject json = new JSONObject();
        try{
            UserInfo userInfo = SessionUtils.getUserInfo();
            if(userInfo == null) json = RestfulRetUtils.getErrorNoUser();
            else {
                json = scmsAdvertInfoService.edit(params, userInfo);
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
    @RequestMapping(value="/delAdvertInfo",method=RequestMethod.POST)
    public JSONObject delAdvertInfo(HttpServletRequest request,@RequestBody String idsList){
        JSONObject json = new JSONObject();
        try {
            UserInfo userInfo = SessionUtils.getUserInfo();
            if(userInfo == null) json = RestfulRetUtils.getErrorNoUser();
            else {
                json = scmsAdvertInfoService.del(idsList, userInfo);
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
    @RequestMapping(value="/saveOrderAdvertInfo",method=RequestMethod.POST)
    public JSONObject saveOrderAdvertInfo(HttpServletRequest request, @RequestBody String params){
        JSONObject json = new JSONObject();
        try {
            UserInfo userInfo = SessionUtils.getUserInfo();
            if(userInfo == null) json = RestfulRetUtils.getErrorNoUser();
            else {
                json = scmsAdvertInfoService.saveOrderAdvertInfo(params);
            }
        } catch (Exception e) {
            json = RestfulRetUtils.getErrorMsg("51004","保存排序结果失败");
            logger.error(e.getMessage(), e);
        }
        return json;
    }
    
    /**
     * 获取列表
     * @param request
     * @return
     */
    @RequestMapping(value="/getPrintInfoList",method=RequestMethod.GET)
    public JSONObject getPrintInfoList(HttpServletRequest request, @RequestParam Map<String, Object> params){
        JSONObject json = new JSONObject();
        try{
            UserInfo userInfo = SessionUtils.getUserInfo();
            if(userInfo == null) json = RestfulRetUtils.getErrorNoUser();
            else {
                Pageable pageable = new PageRequest(SessionUtils.getPageIndex(request), SessionUtils.getPageSize(request));  
                ScmsPrintInfo scmsPrintInfo = (ScmsPrintInfo)BeanUtils.mapToBean(params, ScmsPrintInfo.class);              
                json = scmsPrintInfoService.getList(pageable, scmsPrintInfo, params);
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
    @RequestMapping(value="/addPrintInfo",method=RequestMethod.POST)
    public JSONObject addPrintInfo(HttpServletRequest request, @RequestBody Map<String, Object> params){
        JSONObject json = new JSONObject();
        try{
            UserInfo userInfo = SessionUtils.getUserInfo();
            if(userInfo == null) json = RestfulRetUtils.getErrorNoUser();
            else {
                json = scmsPrintInfoService.add(params, userInfo);
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
    @LogConf(operateType=LogConfOperateType.EDIT, logDesc="编辑打印模板")
    @RequestMapping(value="/editPrintInfo",method=RequestMethod.POST)
    public JSONObject editPrintInfo(HttpServletRequest request, @RequestBody Map<String, Object> params){
        JSONObject json = new JSONObject();
        try{
            UserInfo userInfo = SessionUtils.getUserInfo();
            if(userInfo == null) json = RestfulRetUtils.getErrorNoUser();
            else {
                json = scmsPrintInfoService.edit(params, userInfo);
            }
        }catch(Exception e){
            json = RestfulRetUtils.getErrorMsg("51003","编辑信息失败");
            logger.error(e.getMessage(), e);
        }
        return json;
    }

    /**
     * 获取列表
     * @param request
     * @return
     */
    @RequestMapping(value="/getPrintRemoteList",method=RequestMethod.GET)
    public JSONObject getPrintRemoteList(HttpServletRequest request, @RequestParam Map<String, Object> params){
        JSONObject json = new JSONObject();
        try{
            UserInfo userInfo = SessionUtils.getUserInfo();
            if(userInfo == null) json = RestfulRetUtils.getErrorNoUser();
            else {
                Pageable pageable = new PageRequest(SessionUtils.getPageIndex(request), SessionUtils.getPageSize(request));  
                ScmsPrintRemote scmsPrintRemote = (ScmsPrintRemote)BeanUtils.mapToBean(params, ScmsPrintRemote.class);              
                json = scmsPrintRemoteService.getList(pageable, scmsPrintRemote, params);
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
    @RequestMapping(value="/addPrintRemote",method=RequestMethod.POST)
    public JSONObject addPrintRemote(HttpServletRequest request, @RequestBody Map<String, Object> params){
        JSONObject json = new JSONObject();
        try{
            UserInfo userInfo = SessionUtils.getUserInfo();
            if(userInfo == null) json = RestfulRetUtils.getErrorNoUser();
            else {
                json = scmsPrintRemoteService.add(params, userInfo);
            }
        }catch(Exception e){
            json = RestfulRetUtils.getErrorMsg("51002","新增信息失败");
            logger.error(e.getMessage(), e);
        }
        return json;
    }

    /**
     * 更新状态
     * @param request
     * @param params
     * @return
     */
    @RequestMapping(value="/updatePrintRemoteStatus",method=RequestMethod.POST)
    public JSONObject updatePrintRemoteStatus(HttpServletRequest request, @RequestBody Map<String, Object> params){
        JSONObject json = new JSONObject();
        try{
            UserInfo userInfo = SessionUtils.getUserInfo();
            if(userInfo == null) json = RestfulRetUtils.getErrorNoUser();
            else {
                json = scmsPrintRemoteService.updatePrintRemoteStatus(params, userInfo);
            }
        }catch(Exception e){
            json = RestfulRetUtils.getErrorMsg("51002","新增信息失败");
            logger.error(e.getMessage(), e);
        }
        return json;
    }
}
