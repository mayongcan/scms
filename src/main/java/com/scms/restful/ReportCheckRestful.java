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
import com.scms.modules.report.ReportCheckService;

@RestController
@RequestMapping(value = "/api/scms/report")
public class ReportCheckRestful {

    protected static final Logger logger = LogManager.getLogger(ReportCheckRestful.class);
    
    @Autowired
    private ReportCheckService reportCheckService;
    
    /**
     * 获取盘点报表-销售明细(统计)
     * @param request
     * @param params
     * @return
     */
    @RequestMapping(value="/getCheckReportDetailStatistics",method=RequestMethod.GET)
    public JSONObject getCheckReportDetailStatistics(HttpServletRequest request, @RequestParam Map<String, Object> params){
        JSONObject json = new JSONObject();
        try{
            UserInfo userInfo = SessionUtils.getUserInfo();
            if(userInfo == null) json = RestfulRetUtils.getErrorNoUser();
            else {          
                json = reportCheckService.getCheckReportDetailStatistics(params);
            }
        }catch(Exception e){
            json = RestfulRetUtils.getErrorMsg("51001","获取列表失败");
            logger.error(e.getMessage(), e);
        }
        return json;
    }
    
    /**
     * 获取盘点报表-销售明细
     * @param request
     * @param params
     * @return
     */
    @RequestMapping(value="/getCheckReportDetailList",method=RequestMethod.GET)
    public JSONObject getCheckReportDetailList(HttpServletRequest request, @RequestParam Map<String, Object> params){
        JSONObject json = new JSONObject();
        try{
            UserInfo userInfo = SessionUtils.getUserInfo();
            if(userInfo == null) json = RestfulRetUtils.getErrorNoUser();
            else {
                Pageable pageable = new PageRequest(SessionUtils.getPageIndex(request), SessionUtils.getPageSize(request));               
                json = reportCheckService.getCheckReportDetailList(pageable, params);
            }
        }catch(Exception e){
            json = RestfulRetUtils.getErrorMsg("51001","获取列表失败");
            logger.error(e.getMessage(), e);
        }
        return json;
    }
    
    /**
     * 获取盘点报表-商品汇总
     * @param request
     * @param params
     * @return
     */
    @RequestMapping(value="/getCheckReportGoodsList",method=RequestMethod.GET)
    public JSONObject getCheckReportGoodsList(HttpServletRequest request, @RequestParam Map<String, Object> params){
        JSONObject json = new JSONObject();
        try{
            UserInfo userInfo = SessionUtils.getUserInfo();
            if(userInfo == null) json = RestfulRetUtils.getErrorNoUser();
            else {
                Pageable pageable = new PageRequest(SessionUtils.getPageIndex(request), SessionUtils.getPageSize(request));               
                json = reportCheckService.getCheckReportGoodsList(pageable, params);
            }
        }catch(Exception e){
            json = RestfulRetUtils.getErrorMsg("51001","获取列表失败");
            logger.error(e.getMessage(), e);
        }
        return json;
    }
    
    /**
     * 获取盘点报表-订单汇总
     * @param request
     * @param params
     * @return
     */
    @RequestMapping(value="/getCheckReportOrderList",method=RequestMethod.GET)
    public JSONObject getCheckReportOrderList(HttpServletRequest request, @RequestParam Map<String, Object> params){
        JSONObject json = new JSONObject();
        try{
            UserInfo userInfo = SessionUtils.getUserInfo();
            if(userInfo == null) json = RestfulRetUtils.getErrorNoUser();
            else {
                Pageable pageable = new PageRequest(SessionUtils.getPageIndex(request), SessionUtils.getPageSize(request));               
                json = reportCheckService.getCheckReportOrderList(pageable, params);
            }
        }catch(Exception e){
            json = RestfulRetUtils.getErrorMsg("51001","获取列表失败");
            logger.error(e.getMessage(), e);
        }
        return json;
    }
    
    /**
     * 获取盘点报表-店铺汇总
     * @param request
     * @param params
     * @return
     */
    @RequestMapping(value="/getCheckReportShopList",method=RequestMethod.GET)
    public JSONObject getCheckReportShopList(HttpServletRequest request, @RequestParam Map<String, Object> params){
        JSONObject json = new JSONObject();
        try{
            UserInfo userInfo = SessionUtils.getUserInfo();
            if(userInfo == null) json = RestfulRetUtils.getErrorNoUser();
            else {
                Pageable pageable = new PageRequest(SessionUtils.getPageIndex(request), SessionUtils.getPageSize(request));               
                json = reportCheckService.getCheckReportShopList(pageable, params);
            }
        }catch(Exception e){
            json = RestfulRetUtils.getErrorMsg("51001","获取列表失败");
            logger.error(e.getMessage(), e);
        }
        return json;
    }
}
