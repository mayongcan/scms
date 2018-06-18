package com.scms.restful;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.alibaba.fastjson.JSONObject;
import com.gimplatform.core.entity.UserInfo;
import com.gimplatform.core.utils.RestfulRetUtils;
import com.gimplatform.core.utils.SessionUtils;
import com.scms.modules.report.ReportAllotService;

@RestController
@RequestMapping(value = "/api/scms/report")
public class ReportAllotRestful {

    protected static final Logger logger = LogManager.getLogger(ReportAllotRestful.class);
    
    @Autowired
    private ReportAllotService reportAllotService;
    
    /**
     * 获取调货报表-销售明细(统计)
     * @param request
     * @param params
     * @return
     */
    @RequestMapping(value="/getAllotReportDetailStatistics",method=RequestMethod.GET)
    public JSONObject getAllotReportDetailStatistics(HttpServletRequest request, @RequestParam Map<String, Object> params){
        JSONObject json = new JSONObject();
        try{
            UserInfo userInfo = SessionUtils.getUserInfo();
            if(userInfo == null) json = RestfulRetUtils.getErrorNoUser();
            else {          
                json = reportAllotService.getAllotReportDetailStatistics(params);
            }
        }catch(Exception e){
            json = RestfulRetUtils.getErrorMsg("51001","获取列表失败");
            logger.error(e.getMessage(), e);
        }
        return json;
    }
    
    /**
     * 获取调货报表-销售明细
     * @param request
     * @param params
     * @return
     */
    @RequestMapping(value="/getAllotReportDetailList",method=RequestMethod.GET)
    public JSONObject getAllotReportDetailList(HttpServletRequest request, @RequestParam Map<String, Object> params){
        JSONObject json = new JSONObject();
        try{
            UserInfo userInfo = SessionUtils.getUserInfo();
            if(userInfo == null) json = RestfulRetUtils.getErrorNoUser();
            else {
                Pageable pageable = new PageRequest(SessionUtils.getPageIndex(request), SessionUtils.getPageSize(request));               
                json = reportAllotService.getAllotReportDetailList(pageable, params);
            }
        }catch(Exception e){
            json = RestfulRetUtils.getErrorMsg("51001","获取列表失败");
            logger.error(e.getMessage(), e);
        }
        return json;
    }
    
    /**
     * 获取调货报表-商品汇总
     * @param request
     * @param params
     * @return
     */
    @RequestMapping(value="/getAllotReportGoodsList",method=RequestMethod.GET)
    public JSONObject getAllotReportGoodsList(HttpServletRequest request, @RequestParam Map<String, Object> params){
        JSONObject json = new JSONObject();
        try{
            UserInfo userInfo = SessionUtils.getUserInfo();
            if(userInfo == null) json = RestfulRetUtils.getErrorNoUser();
            else {
                Pageable pageable = new PageRequest(SessionUtils.getPageIndex(request), SessionUtils.getPageSize(request));               
                json = reportAllotService.getAllotReportGoodsList(pageable, params);
            }
        }catch(Exception e){
            json = RestfulRetUtils.getErrorMsg("51001","获取列表失败");
            logger.error(e.getMessage(), e);
        }
        return json;
    }
    
    /**
     * 获取调货报表-订单汇总
     * @param request
     * @param params
     * @return
     */
    @RequestMapping(value="/getAllotReportOrderList",method=RequestMethod.GET)
    public JSONObject getAllotReportOrderList(HttpServletRequest request, @RequestParam Map<String, Object> params){
        JSONObject json = new JSONObject();
        try{
            UserInfo userInfo = SessionUtils.getUserInfo();
            if(userInfo == null) json = RestfulRetUtils.getErrorNoUser();
            else {
                Pageable pageable = new PageRequest(SessionUtils.getPageIndex(request), SessionUtils.getPageSize(request));               
                json = reportAllotService.getAllotReportOrderList(pageable, params);
            }
        }catch(Exception e){
            json = RestfulRetUtils.getErrorMsg("51001","获取列表失败");
            logger.error(e.getMessage(), e);
        }
        return json;
    }
    
    /**
     * 获取调货报表-调出店铺汇总
     * @param request
     * @param params
     * @return
     */
    @RequestMapping(value="/getAllotReportSrcShopList",method=RequestMethod.GET)
    public JSONObject getAllotReportSrcShopList(HttpServletRequest request, @RequestParam Map<String, Object> params){
        JSONObject json = new JSONObject();
        try{
            UserInfo userInfo = SessionUtils.getUserInfo();
            if(userInfo == null) json = RestfulRetUtils.getErrorNoUser();
            else {
                Pageable pageable = new PageRequest(SessionUtils.getPageIndex(request), SessionUtils.getPageSize(request));               
                json = reportAllotService.getAllotReportSrcShopList(pageable, params);
            }
        }catch(Exception e){
            json = RestfulRetUtils.getErrorMsg("51001","获取列表失败");
            logger.error(e.getMessage(), e);
        }
        return json;
    }
    
    /**
     * 获取调货报表-调入店铺汇总
     * @param request
     * @param params
     * @return
     */
    @RequestMapping(value="/getAllotReportDestShopList",method=RequestMethod.GET)
    public JSONObject getAllotReportDestShopList(HttpServletRequest request, @RequestParam Map<String, Object> params){
        JSONObject json = new JSONObject();
        try{
            UserInfo userInfo = SessionUtils.getUserInfo();
            if(userInfo == null) json = RestfulRetUtils.getErrorNoUser();
            else {
                Pageable pageable = new PageRequest(SessionUtils.getPageIndex(request), SessionUtils.getPageSize(request));               
                json = reportAllotService.getAllotReportDestShopList(pageable, params);
            }
        }catch(Exception e){
            json = RestfulRetUtils.getErrorMsg("51001","获取列表失败");
            logger.error(e.getMessage(), e);
        }
        return json;
    }
}
