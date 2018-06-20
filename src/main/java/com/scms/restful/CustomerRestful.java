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
import com.scms.modules.customer.entity.ScmsCustomerInfo;
import com.scms.modules.customer.entity.ScmsCustomerLevel;
import com.scms.modules.customer.entity.ScmsCustomerType;
import com.scms.modules.customer.entity.ScmsSupplierInfo;
import com.scms.modules.customer.service.ScmsCustomerInfoService;
import com.scms.modules.customer.service.ScmsCustomerLevelService;
import com.scms.modules.customer.service.ScmsCustomerTypeService;
import com.scms.modules.customer.service.ScmsSupplierInfoService;
import com.scms.modules.order.service.ScmsOrderInfoService;

@RestController
@RequestMapping(value = "/api/scms/customer")
public class CustomerRestful {
    
    protected static final Logger logger = LogManager.getLogger(CustomerRestful.class);
    
    @Autowired
    private ScmsCustomerTypeService scmsCustomerTypeService;
    
    @Autowired
    private ScmsCustomerLevelService scmsCustomerLevelService;
    
    @Autowired
    private ScmsCustomerInfoService scmsCustomerInfoService;
    
    @Autowired
    private ScmsSupplierInfoService scmsSupplierInfoService;
    
    @Autowired
    private ScmsOrderInfoService scmsOrderInfoService;
    
    @RequestMapping(value="/customerTypeIndex", method=RequestMethod.GET)
    public JSONObject customerTypeIndex(HttpServletRequest request){ return RestfulRetUtils.getRetSuccess();}

    @RequestMapping(value="/customerLevelIndex", method=RequestMethod.GET)
    public JSONObject customerLevelIndex(HttpServletRequest request){ return RestfulRetUtils.getRetSuccess();}

    @RequestMapping(value="/customerInfoIndex", method=RequestMethod.GET)
    public JSONObject customerInfoIndex(HttpServletRequest request){ return RestfulRetUtils.getRetSuccess();}

    @RequestMapping(value="/supplierInfoIndex", method=RequestMethod.GET)
    public JSONObject supplierInfoIndex(HttpServletRequest request){ return RestfulRetUtils.getRetSuccess();}


    /**
     * 获取列表
     * @param request
     * @return
     */
    @RequestMapping(value="/getCustomerTypeList",method=RequestMethod.GET)
    public JSONObject getCustomerTypeList(HttpServletRequest request, @RequestParam Map<String, Object> params){
        JSONObject json = new JSONObject();
        try{
            UserInfo userInfo = SessionUtils.getUserInfo();
            if(userInfo == null) json = RestfulRetUtils.getErrorNoUser();
            else {
                Pageable pageable = new PageRequest(SessionUtils.getPageIndex(request), SessionUtils.getPageSize(request));  
                ScmsCustomerType scmsCustomerType = (ScmsCustomerType)BeanUtils.mapToBean(params, ScmsCustomerType.class);              
                json = scmsCustomerTypeService.getList(pageable, scmsCustomerType, params);
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
    @RequestMapping(value="/addCustomerType",method=RequestMethod.POST)
    public JSONObject addCustomerType(HttpServletRequest request, @RequestBody Map<String, Object> params){
        JSONObject json = new JSONObject();
        try{
            UserInfo userInfo = SessionUtils.getUserInfo();
            if(userInfo == null) json = RestfulRetUtils.getErrorNoUser();
            else {
                json = scmsCustomerTypeService.add(params, userInfo);
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
    @RequestMapping(value="/editCustomerType",method=RequestMethod.POST)
    public JSONObject editCustomerType(HttpServletRequest request, @RequestBody Map<String, Object> params){
        JSONObject json = new JSONObject();
        try{
            UserInfo userInfo = SessionUtils.getUserInfo();
            if(userInfo == null) json = RestfulRetUtils.getErrorNoUser();
            else {
                json = scmsCustomerTypeService.edit(params, userInfo);
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
    @RequestMapping(value="/delCustomerType",method=RequestMethod.POST)
    public JSONObject delCustomerType(HttpServletRequest request,@RequestBody String idsList){
        JSONObject json = new JSONObject();
        try {
            UserInfo userInfo = SessionUtils.getUserInfo();
            if(userInfo == null) json = RestfulRetUtils.getErrorNoUser();
            else {
                json = scmsCustomerTypeService.del(idsList, userInfo);
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
    @RequestMapping(value="/getCustomerLevelList",method=RequestMethod.GET)
    public JSONObject getCustomerLevelList(HttpServletRequest request, @RequestParam Map<String, Object> params){
        JSONObject json = new JSONObject();
        try{
            UserInfo userInfo = SessionUtils.getUserInfo();
            if(userInfo == null) json = RestfulRetUtils.getErrorNoUser();
            else {
                Pageable pageable = new PageRequest(SessionUtils.getPageIndex(request), SessionUtils.getPageSize(request));  
                ScmsCustomerLevel scmsCustomerLevel = (ScmsCustomerLevel)BeanUtils.mapToBean(params, ScmsCustomerLevel.class);              
                json = scmsCustomerLevelService.getList(pageable, scmsCustomerLevel, params);
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
    @RequestMapping(value="/addCustomerLevel",method=RequestMethod.POST)
    public JSONObject addCustomerLevel(HttpServletRequest request, @RequestBody Map<String, Object> params){
        JSONObject json = new JSONObject();
        try{
            UserInfo userInfo = SessionUtils.getUserInfo();
            if(userInfo == null) json = RestfulRetUtils.getErrorNoUser();
            else {
                json = scmsCustomerLevelService.add(params, userInfo);
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
    @RequestMapping(value="/editCustomerLevel",method=RequestMethod.POST)
    public JSONObject editCustomerLevel(HttpServletRequest request, @RequestBody Map<String, Object> params){
        JSONObject json = new JSONObject();
        try{
            UserInfo userInfo = SessionUtils.getUserInfo();
            if(userInfo == null) json = RestfulRetUtils.getErrorNoUser();
            else {
                json = scmsCustomerLevelService.edit(params, userInfo);
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
    @RequestMapping(value="/delCustomerLevel",method=RequestMethod.POST)
    public JSONObject delCustomerLevel(HttpServletRequest request,@RequestBody String idsList){
        JSONObject json = new JSONObject();
        try {
            UserInfo userInfo = SessionUtils.getUserInfo();
            if(userInfo == null) json = RestfulRetUtils.getErrorNoUser();
            else {
                json = scmsCustomerLevelService.del(idsList, userInfo);
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
    @RequestMapping(value="/getCustomerInfoList",method=RequestMethod.GET)
    public JSONObject getCustomerInfoList(HttpServletRequest request, @RequestParam Map<String, Object> params){
        JSONObject json = new JSONObject();
        try{
            UserInfo userInfo = SessionUtils.getUserInfo();
            if(userInfo == null) json = RestfulRetUtils.getErrorNoUser();
            else {
                Pageable pageable = new PageRequest(SessionUtils.getPageIndex(request), SessionUtils.getPageSize(request));  
                ScmsCustomerInfo scmsCustomerInfo = (ScmsCustomerInfo)BeanUtils.mapToBean(params, ScmsCustomerInfo.class);              
                json = scmsCustomerInfoService.getList(pageable, scmsCustomerInfo, params);
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
    @RequestMapping(value="/addCustomerInfo",method=RequestMethod.POST)
    public JSONObject addCustomerInfo(HttpServletRequest request, @RequestBody Map<String, Object> params){
        JSONObject json = new JSONObject();
        try{
            UserInfo userInfo = SessionUtils.getUserInfo();
            if(userInfo == null) json = RestfulRetUtils.getErrorNoUser();
            else {
                json = scmsCustomerInfoService.add(params, userInfo);
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
    @RequestMapping(value="/editCustomerInfo",method=RequestMethod.POST)
    public JSONObject editCustomerInfo(HttpServletRequest request, @RequestBody Map<String, Object> params){
        JSONObject json = new JSONObject();
        try{
            UserInfo userInfo = SessionUtils.getUserInfo();
            if(userInfo == null) json = RestfulRetUtils.getErrorNoUser();
            else {
                json = scmsCustomerInfoService.edit(params, userInfo);
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
    @RequestMapping(value="/delCustomerInfo",method=RequestMethod.POST)
    public JSONObject delCustomerInfo(HttpServletRequest request,@RequestBody String idsList){
        JSONObject json = new JSONObject();
        try {
            UserInfo userInfo = SessionUtils.getUserInfo();
            if(userInfo == null) json = RestfulRetUtils.getErrorNoUser();
            else {
                json = scmsCustomerInfoService.del(idsList, userInfo);
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
    @RequestMapping(value="/getSupplierInfoList",method=RequestMethod.GET)
    public JSONObject getSupplierInfoList(HttpServletRequest request, @RequestParam Map<String, Object> params){
        JSONObject json = new JSONObject();
        try{
            UserInfo userInfo = SessionUtils.getUserInfo();
            if(userInfo == null) json = RestfulRetUtils.getErrorNoUser();
            else {
                Pageable pageable = new PageRequest(SessionUtils.getPageIndex(request), SessionUtils.getPageSize(request));  
                ScmsSupplierInfo scmsSupplierInfo = (ScmsSupplierInfo)BeanUtils.mapToBean(params, ScmsSupplierInfo.class);              
                json = scmsSupplierInfoService.getList(pageable, scmsSupplierInfo, params);
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
    @RequestMapping(value="/addSupplierInfo",method=RequestMethod.POST)
    public JSONObject addSupplierInfo(HttpServletRequest request, @RequestBody Map<String, Object> params){
        JSONObject json = new JSONObject();
        try{
            UserInfo userInfo = SessionUtils.getUserInfo();
            if(userInfo == null) json = RestfulRetUtils.getErrorNoUser();
            else {
                json = scmsSupplierInfoService.add(params, userInfo);
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
    @RequestMapping(value="/editSupplierInfo",method=RequestMethod.POST)
    public JSONObject editSupplierInfo(HttpServletRequest request, @RequestBody Map<String, Object> params){
        JSONObject json = new JSONObject();
        try{
            UserInfo userInfo = SessionUtils.getUserInfo();
            if(userInfo == null) json = RestfulRetUtils.getErrorNoUser();
            else {
                json = scmsSupplierInfoService.edit(params, userInfo);
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
    @RequestMapping(value="/delSupplierInfo",method=RequestMethod.POST)
    public JSONObject delSupplierInfo(HttpServletRequest request,@RequestBody String idsList){
        JSONObject json = new JSONObject();
        try {
            UserInfo userInfo = SessionUtils.getUserInfo();
            if(userInfo == null) json = RestfulRetUtils.getErrorNoUser();
            else {
                json = scmsSupplierInfoService.del(idsList, userInfo);
            }
        } catch (Exception e) {
            json = RestfulRetUtils.getErrorMsg("51004","删除信息失败");
            logger.error(e.getMessage(), e);
        }
        return json;
    }

    /**
     * 获取交易历史统计
     * @param request
     * @param params
     * @return
     */
    @RequestMapping(value="/getDealHistoryStatistics",method=RequestMethod.GET)
    public JSONObject getDealHistoryStatistics(HttpServletRequest request, @RequestParam Map<String, Object> params){
        JSONObject json = new JSONObject();
        try{
            UserInfo userInfo = SessionUtils.getUserInfo();
            if(userInfo == null) json = RestfulRetUtils.getErrorNoUser();
            else {          
                json = scmsOrderInfoService.getDealHistoryStatistics(params);
            }
        }catch(Exception e){
            json = RestfulRetUtils.getErrorMsg("51001","获取列表失败");
            logger.error(e.getMessage(), e);
        }
        return json;
    }

    /**
     * 获取客户收款历史统计
     * @param request
     * @param params
     * @return
     */
    @RequestMapping(value="/getReceiptHistoryStatistics",method=RequestMethod.GET)
    public JSONObject getReceiptHistoryStatistics(HttpServletRequest request, @RequestParam Map<String, Object> params){
        JSONObject json = new JSONObject();
        try{
            UserInfo userInfo = SessionUtils.getUserInfo();
            if(userInfo == null) json = RestfulRetUtils.getErrorNoUser();
            else {          
                json = scmsOrderInfoService.getReceiptHistoryStatistics(params);
            }
        }catch(Exception e){
            json = RestfulRetUtils.getErrorMsg("51001","获取列表失败");
            logger.error(e.getMessage(), e);
        }
        return json;
    }

    /**
     * 获取客户统计
     * @param request
     * @param params
     * @return
     */
    @RequestMapping(value="/getCustomerStatistics",method=RequestMethod.GET)
    public JSONObject getCustomerStatistics(HttpServletRequest request, @RequestParam Map<String, Object> params){
        JSONObject json = new JSONObject();
        try{
            UserInfo userInfo = SessionUtils.getUserInfo();
            if(userInfo == null) json = RestfulRetUtils.getErrorNoUser();
            else {          
                json = scmsCustomerInfoService.getCustomerStatistics(params);
            }
        }catch(Exception e){
            json = RestfulRetUtils.getErrorMsg("51001","获取列表失败");
            logger.error(e.getMessage(), e);
        }
        return json;
    }

    /**
     * 获取供货商统计
     * @param request
     * @param params
     * @return
     */
    @RequestMapping(value="/getSupplierStatistics",method=RequestMethod.GET)
    public JSONObject getSupplierStatistics(HttpServletRequest request, @RequestParam Map<String, Object> params){
        JSONObject json = new JSONObject();
        try{
            UserInfo userInfo = SessionUtils.getUserInfo();
            if(userInfo == null) json = RestfulRetUtils.getErrorNoUser();
            else {          
                json = scmsSupplierInfoService.getSupplierStatistics(params);
            }
        }catch(Exception e){
            json = RestfulRetUtils.getErrorMsg("51001","获取列表失败");
            logger.error(e.getMessage(), e);
        }
        return json;
    }

    /**
     * 获取客户对账统计
     * @param request
     * @param params
     * @return
     */
    @RequestMapping(value="/getCustomerCheckBillStatistics",method=RequestMethod.GET)
    public JSONObject getCustomerCheckBillStatistics(HttpServletRequest request, @RequestParam Map<String, Object> params){
        JSONObject json = new JSONObject();
        try{
            UserInfo userInfo = SessionUtils.getUserInfo();
            if(userInfo == null) json = RestfulRetUtils.getErrorNoUser();
            else {          
                json = scmsCustomerInfoService.getCustomerCheckBillStatistics(params);
            }
        }catch(Exception e){
            json = RestfulRetUtils.getErrorMsg("51001","获取列表失败");
            logger.error(e.getMessage(), e);
        }
        return json;
    }

    /**
     * 获取供货商对账统计
     * @param request
     * @param params
     * @return
     */
    @RequestMapping(value="/getSupplierCheckBillStatistics",method=RequestMethod.GET)
    public JSONObject getSupplierCheckBillStatistics(HttpServletRequest request, @RequestParam Map<String, Object> params){
        JSONObject json = new JSONObject();
        try{
            UserInfo userInfo = SessionUtils.getUserInfo();
            if(userInfo == null) json = RestfulRetUtils.getErrorNoUser();
            else {          
                json = scmsSupplierInfoService.getSupplierCheckBillStatistics(params);
            }
        }catch(Exception e){
            json = RestfulRetUtils.getErrorMsg("51001","获取列表失败");
            logger.error(e.getMessage(), e);
        }
        return json;
    }
}
