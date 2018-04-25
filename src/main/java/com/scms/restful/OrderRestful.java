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
import com.scms.modules.order.entity.ScmsOrderGoods;
import com.scms.modules.order.entity.ScmsOrderGoodsDetail;
import com.scms.modules.order.entity.ScmsOrderInfo;
import com.scms.modules.order.entity.ScmsOrderModifyLog;
import com.scms.modules.order.entity.ScmsOrderPay;
import com.scms.modules.order.entity.ScmsOrderReceiveLog;
import com.scms.modules.order.entity.ScmsOrderSendLog;
import com.scms.modules.order.service.ScmsOrderGoodsDetailService;
import com.scms.modules.order.service.ScmsOrderGoodsService;
import com.scms.modules.order.service.ScmsOrderInfoService;
import com.scms.modules.order.service.ScmsOrderModifyLogService;
import com.scms.modules.order.service.ScmsOrderPayService;
import com.scms.modules.order.service.ScmsOrderReceiveLogService;
import com.scms.modules.order.service.ScmsOrderSendLogService;

@RestController
@RequestMapping(value = "/api/scms/order")
public class OrderRestful {
    
    protected static final Logger logger = LogManager.getLogger(OrderRestful.class);
    
    @Autowired
    private ScmsOrderInfoService scmsOrderInfoService;
    
    @Autowired
    private ScmsOrderGoodsService scmsOrderGoodsService;
    
    @Autowired
    private ScmsOrderGoodsDetailService scmsOrderGoodsDetailService;
    
    @Autowired
    private ScmsOrderPayService scmsOrderPayService;
    
    @Autowired
    private ScmsOrderSendLogService scmsOrderSendLogService;
    
    @Autowired
    private ScmsOrderReceiveLogService scmsOrderReceiveLogService;
    
    @Autowired
    private ScmsOrderModifyLogService scmsOrderModifyLogService;

    @RequestMapping(value="/orderLsdListIndex", method=RequestMethod.GET)
    public JSONObject orderLsdListIndex(HttpServletRequest request){ return RestfulRetUtils.getRetSuccess();}

    @RequestMapping(value="/orderPfdListIndex", method=RequestMethod.GET)
    public JSONObject orderPfdListIndex(HttpServletRequest request){ return RestfulRetUtils.getRetSuccess();}

    @RequestMapping(value="/orderThdListIndex", method=RequestMethod.GET)
    public JSONObject orderThdListIndex(HttpServletRequest request){ return RestfulRetUtils.getRetSuccess();}

    @RequestMapping(value="/orderJhdListIndex", method=RequestMethod.GET)
    public JSONObject orderJhdListIndex(HttpServletRequest request){ return RestfulRetUtils.getRetSuccess();}

    @RequestMapping(value="/orderDhdListIndex", method=RequestMethod.GET)
    public JSONObject orderDhdListIndex(HttpServletRequest request){ return RestfulRetUtils.getRetSuccess();}

    @RequestMapping(value="/orderFcdListIndex", method=RequestMethod.GET)
    public JSONObject orderFcdListIndex(HttpServletRequest request){ return RestfulRetUtils.getRetSuccess();}

    @RequestMapping(value="/orderYsdListIndex", method=RequestMethod.GET)
    public JSONObject orderYsdListIndex(HttpServletRequest request){ return RestfulRetUtils.getRetSuccess();}

    @RequestMapping(value="/orderPddListIndex", method=RequestMethod.GET)
    public JSONObject orderPddListIndex(HttpServletRequest request){ return RestfulRetUtils.getRetSuccess();}

    @RequestMapping(value="/orderSydListIndex", method=RequestMethod.GET)
    public JSONObject orderSydListIndex(HttpServletRequest request){ return RestfulRetUtils.getRetSuccess();}
    

    /**
     * 获取列表
     * @param request
     * @return
     */
    @RequestMapping(value="/getOrderInfoList",method=RequestMethod.GET)
    public JSONObject getOrderInfoList(HttpServletRequest request, @RequestParam Map<String, Object> params){
        JSONObject json = new JSONObject();
        try{
            UserInfo userInfo = SessionUtils.getUserInfo();
            if(userInfo == null) json = RestfulRetUtils.getErrorNoUser();
            else {
                Pageable pageable = new PageRequest(SessionUtils.getPageIndex(request), SessionUtils.getPageSize(request));  
                ScmsOrderInfo scmsOrderInfo = (ScmsOrderInfo)BeanUtils.mapToBean(params, ScmsOrderInfo.class);              
                json = scmsOrderInfoService.getList(pageable, scmsOrderInfo, params);
            }
        }catch(Exception e){
            json = RestfulRetUtils.getErrorMsg("51001","获取列表失败");
            logger.error(e.getMessage(), e);
        }
        return json;
    }
    
    
    /**
     * 新增信息（零售单、批发单、预订单）
     * @param request
     * @param params
     * @return
     */
    @RequestMapping(value="/addOrderInfo",method=RequestMethod.POST)
    public JSONObject addOrderInfo(HttpServletRequest request, @RequestBody Map<String, Object> params){
        JSONObject json = new JSONObject();
        try{
            UserInfo userInfo = SessionUtils.getUserInfo();
            if(userInfo == null) json = RestfulRetUtils.getErrorNoUser();
            else {
                json = scmsOrderInfoService.addLsdOrder(params, userInfo);
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
    @RequestMapping(value="/editOrderInfo",method=RequestMethod.POST)
    public JSONObject editOrderInfo(HttpServletRequest request, @RequestBody Map<String, Object> params){
        JSONObject json = new JSONObject();
        try{
            UserInfo userInfo = SessionUtils.getUserInfo();
            if(userInfo == null) json = RestfulRetUtils.getErrorNoUser();
            else {
                json = scmsOrderInfoService.edit(params, userInfo);
            }
        }catch(Exception e){
            json = RestfulRetUtils.getErrorMsg("51003","编辑信息失败");
            logger.error(e.getMessage(), e);
        }
        return json;
    }
    
    /**
     * 锁定订单信息
     * @param request
     * @param idsList
     * @return
     */
    @RequestMapping(value="/lockOrderInfo",method=RequestMethod.POST)
    public JSONObject lockOrderInfo(HttpServletRequest request,@RequestBody String idsList){
        JSONObject json = new JSONObject();
        try {
            UserInfo userInfo = SessionUtils.getUserInfo();
            if(userInfo == null) json = RestfulRetUtils.getErrorNoUser();
            else {
                json = scmsOrderInfoService.updateOrderStatus(idsList, userInfo, "2");
            }
        } catch (Exception e) {
            json = RestfulRetUtils.getErrorMsg("51004","删除信息失败");
            logger.error(e.getMessage(), e);
        }
        return json;
    }
    
    /**
     * 取消订单信息
     * @param request
     * @param idsList
     * @return
     */
    @RequestMapping(value="/cancelOrderInfo",method=RequestMethod.POST)
    public JSONObject cancelOrderInfo(HttpServletRequest request,@RequestBody String idsList){
        JSONObject json = new JSONObject();
        try {
            UserInfo userInfo = SessionUtils.getUserInfo();
            if(userInfo == null) json = RestfulRetUtils.getErrorNoUser();
            else {
                json = scmsOrderInfoService.updateOrderStatus(idsList, userInfo, "3");
            }
        } catch (Exception e) {
            json = RestfulRetUtils.getErrorMsg("51004","删除信息失败");
            logger.error(e.getMessage(), e);
        }
        return json;
    }
    
    /**
     * 完成订单信息
     * @param request
     * @param idsList
     * @return
     */
    @RequestMapping(value="/finishOrderInfo",method=RequestMethod.POST)
    public JSONObject finishOrderInfo(HttpServletRequest request,@RequestBody String idsList){
        JSONObject json = new JSONObject();
        try {
            UserInfo userInfo = SessionUtils.getUserInfo();
            if(userInfo == null) json = RestfulRetUtils.getErrorNoUser();
            else {
                json = scmsOrderInfoService.updateOrderStatus(idsList, userInfo, "4");
            }
        } catch (Exception e) {
            json = RestfulRetUtils.getErrorMsg("51004","删除信息失败");
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
    @RequestMapping(value="/delOrderInfo",method=RequestMethod.POST)
    public JSONObject delOrderInfo(HttpServletRequest request,@RequestBody String idsList){
        JSONObject json = new JSONObject();
        try {
            UserInfo userInfo = SessionUtils.getUserInfo();
            if(userInfo == null) json = RestfulRetUtils.getErrorNoUser();
            else {
                json = scmsOrderInfoService.del(idsList, userInfo);
            }
        } catch (Exception e) {
            json = RestfulRetUtils.getErrorMsg("51004","删除信息失败");
            logger.error(e.getMessage(), e);
        }
        return json;
    }
    
    @RequestMapping(value="/editOrderPay",method=RequestMethod.POST)
    public JSONObject editOrderPay(HttpServletRequest request, @RequestBody Map<String, Object> params){
        JSONObject json = new JSONObject();
        try{
            UserInfo userInfo = SessionUtils.getUserInfo();
            if(userInfo == null) json = RestfulRetUtils.getErrorNoUser();
            else {
                json = scmsOrderInfoService.editOrderPay(params, userInfo);
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
    @RequestMapping(value="/getOrderGoodsList",method=RequestMethod.GET)
    public JSONObject getOrderGoodsList(HttpServletRequest request, @RequestParam Map<String, Object> params){
        JSONObject json = new JSONObject();
        try{
            UserInfo userInfo = SessionUtils.getUserInfo();
            if(userInfo == null) json = RestfulRetUtils.getErrorNoUser();
            else {
                Pageable pageable = new PageRequest(SessionUtils.getPageIndex(request), SessionUtils.getPageSize(request));  
                ScmsOrderGoods scmsOrderGoods = (ScmsOrderGoods)BeanUtils.mapToBean(params, ScmsOrderGoods.class);              
                json = scmsOrderGoodsService.getList(pageable, scmsOrderGoods, params);
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
    @RequestMapping(value="/getOrderGoodsDetailList",method=RequestMethod.GET)
    public JSONObject getOrderGoodsDetailList(HttpServletRequest request, @RequestParam Map<String, Object> params){
        JSONObject json = new JSONObject();
        try{
            UserInfo userInfo = SessionUtils.getUserInfo();
            if(userInfo == null) json = RestfulRetUtils.getErrorNoUser();
            else {
                Pageable pageable = new PageRequest(SessionUtils.getPageIndex(request), SessionUtils.getPageSize(request));  
                ScmsOrderGoodsDetail scmsOrderGoodsDetail = (ScmsOrderGoodsDetail)BeanUtils.mapToBean(params, ScmsOrderGoodsDetail.class);              
                json = scmsOrderGoodsDetailService.getList(pageable, scmsOrderGoodsDetail, params);
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
    @RequestMapping(value="/getOrderPayList",method=RequestMethod.GET)
    public JSONObject getOrderPayList(HttpServletRequest request, @RequestParam Map<String, Object> params){
        JSONObject json = new JSONObject();
        try{
            UserInfo userInfo = SessionUtils.getUserInfo();
            if(userInfo == null) json = RestfulRetUtils.getErrorNoUser();
            else {
                Pageable pageable = new PageRequest(SessionUtils.getPageIndex(request), SessionUtils.getPageSize(request));  
                ScmsOrderPay scmsOrderPay = (ScmsOrderPay)BeanUtils.mapToBean(params, ScmsOrderPay.class);              
                json = scmsOrderPayService.getList(pageable, scmsOrderPay, params);
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
    @RequestMapping(value="/getOrderSendLogList",method=RequestMethod.GET)
    public JSONObject getOrderSendLogList(HttpServletRequest request, @RequestParam Map<String, Object> params){
        JSONObject json = new JSONObject();
        try{
            UserInfo userInfo = SessionUtils.getUserInfo();
            if(userInfo == null) json = RestfulRetUtils.getErrorNoUser();
            else {
                Pageable pageable = new PageRequest(SessionUtils.getPageIndex(request), SessionUtils.getPageSize(request));  
                ScmsOrderSendLog scmsOrderSendLog = (ScmsOrderSendLog)BeanUtils.mapToBean(params, ScmsOrderSendLog.class);              
                json = scmsOrderSendLogService.getList(pageable, scmsOrderSendLog, params);
            }
        }catch(Exception e){
            json = RestfulRetUtils.getErrorMsg("51001","获取列表失败");
            logger.error(e.getMessage(), e);
        }
        return json;
    }
    
    /**
     * 添加发货记录，同时修改订单的发货状态
     * @param request
     * @param params
     * @return
     */
    @RequestMapping(value="/addOrderSendLog",method=RequestMethod.POST)
    public JSONObject addOrderSendLog(HttpServletRequest request, @RequestBody Map<String, Object> params){
        JSONObject json = new JSONObject();
        try{
            UserInfo userInfo = SessionUtils.getUserInfo();
            if(userInfo == null) json = RestfulRetUtils.getErrorNoUser();
            else {
                json = scmsOrderSendLogService.addOrderSendLog(params, userInfo);
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
    @RequestMapping(value="/getReceiveLogList",method=RequestMethod.GET)
    public JSONObject getReceiveLogList(HttpServletRequest request, @RequestParam Map<String, Object> params){
        JSONObject json = new JSONObject();
        try{
            UserInfo userInfo = SessionUtils.getUserInfo();
            if(userInfo == null) json = RestfulRetUtils.getErrorNoUser();
            else {
                Pageable pageable = new PageRequest(SessionUtils.getPageIndex(request), SessionUtils.getPageSize(request));  
                ScmsOrderReceiveLog scmsOrderReceiveLog = (ScmsOrderReceiveLog)BeanUtils.mapToBean(params, ScmsOrderReceiveLog.class);              
                json = scmsOrderReceiveLogService.getList(pageable, scmsOrderReceiveLog, params);
            }
        }catch(Exception e){
            json = RestfulRetUtils.getErrorMsg("51001","获取列表失败");
            logger.error(e.getMessage(), e);
        }
        return json;
    }
    
    /**
     * 添加收货记录，同时修改订单的收货状态
     * @param request
     * @param params
     * @return
     */
    @RequestMapping(value="/addOrderReceiveLog",method=RequestMethod.POST)
    public JSONObject addOrderReceiveLog(HttpServletRequest request, @RequestBody Map<String, Object> params){
        JSONObject json = new JSONObject();
        try{
            UserInfo userInfo = SessionUtils.getUserInfo();
            if(userInfo == null) json = RestfulRetUtils.getErrorNoUser();
            else {
                json = scmsOrderReceiveLogService.addOrderReceiveLog(params, userInfo);
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
    @RequestMapping(value="/getOrderModifyLogList",method=RequestMethod.GET)
    public JSONObject getOrderModifyLogList(HttpServletRequest request, @RequestParam Map<String, Object> params){
        JSONObject json = new JSONObject();
        try{
            UserInfo userInfo = SessionUtils.getUserInfo();
            if(userInfo == null) json = RestfulRetUtils.getErrorNoUser();
            else {
                Pageable pageable = new PageRequest(SessionUtils.getPageIndex(request), SessionUtils.getPageSize(request));  
                ScmsOrderModifyLog scmsOrderModifyLog = (ScmsOrderModifyLog)BeanUtils.mapToBean(params, ScmsOrderModifyLog.class);              
                json = scmsOrderModifyLogService.getList(pageable, scmsOrderModifyLog, params);
            }
        }catch(Exception e){
            json = RestfulRetUtils.getErrorMsg("51001","获取列表失败");
            logger.error(e.getMessage(), e);
        }
        return json;
    }
    
}
