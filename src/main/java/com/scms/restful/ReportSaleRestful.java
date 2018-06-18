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
import com.scms.modules.report.ReportSaleService;

@RestController
@RequestMapping(value = "/api/scms/report")
public class ReportSaleRestful {

    protected static final Logger logger = LogManager.getLogger(ReportSaleRestful.class);
    
    @Autowired
    private ReportSaleService reportSaleService;

    @RequestMapping(value="/summaryIndex", method=RequestMethod.GET)
    public JSONObject summaryIndex(HttpServletRequest request){ return RestfulRetUtils.getRetSuccess();}

    @RequestMapping(value="/revenueReportIndex", method=RequestMethod.GET)
    public JSONObject revenueReportIndex(HttpServletRequest request){ return RestfulRetUtils.getRetSuccess();}

    @RequestMapping(value="/saleReportIndex", method=RequestMethod.GET)
    public JSONObject saleReportIndex(HttpServletRequest request){ return RestfulRetUtils.getRetSuccess();}

    @RequestMapping(value="/purchaseReportIndex", method=RequestMethod.GET)
    public JSONObject purchaseReportIndex(HttpServletRequest request){ return RestfulRetUtils.getRetSuccess();}

    @RequestMapping(value="/salePurchaseCompareIndex", method=RequestMethod.GET)
    public JSONObject salePurchaseCompareIndex(HttpServletRequest request){ return RestfulRetUtils.getRetSuccess();}

    @RequestMapping(value="/checkRerpotIndex", method=RequestMethod.GET)
    public JSONObject checkRerpotIndex(HttpServletRequest request){ return RestfulRetUtils.getRetSuccess();}

    @RequestMapping(value="/allotReportIndex", method=RequestMethod.GET)
    public JSONObject allotReportIndex(HttpServletRequest request){ return RestfulRetUtils.getRetSuccess();}

    @RequestMapping(value="/statisticsIndex", method=RequestMethod.GET)
    public JSONObject statisticsIndex(HttpServletRequest request){ return RestfulRetUtils.getRetSuccess();}

    /**
     * 获取销售报表-销售明细(统计)
     * @param request
     * @param params
     * @return
     */
    @RequestMapping(value="/getSaleReportDetailStatistics",method=RequestMethod.GET)
    public JSONObject getSaleReportDetailStatistics(HttpServletRequest request, @RequestParam Map<String, Object> params){
        JSONObject json = new JSONObject();
        try{
            UserInfo userInfo = SessionUtils.getUserInfo();
            if(userInfo == null) json = RestfulRetUtils.getErrorNoUser();
            else {          
                json = reportSaleService.getSaleReportDetailStatistics(params);
            }
        }catch(Exception e){
            json = RestfulRetUtils.getErrorMsg("51001","获取列表失败");
            logger.error(e.getMessage(), e);
        }
        return json;
    }
    
    /**
     * 获取销售报表-销售明细
     * @param request
     * @param params
     * @return
     */
    @RequestMapping(value="/getSaleReportDetailList",method=RequestMethod.GET)
    public JSONObject getSaleReportDetailList(HttpServletRequest request, @RequestParam Map<String, Object> params){
        JSONObject json = new JSONObject();
        try{
            UserInfo userInfo = SessionUtils.getUserInfo();
            if(userInfo == null) json = RestfulRetUtils.getErrorNoUser();
            else {
                Pageable pageable = new PageRequest(SessionUtils.getPageIndex(request), SessionUtils.getPageSize(request));               
                json = reportSaleService.getSaleReportDetailList(pageable, params);
            }
        }catch(Exception e){
            json = RestfulRetUtils.getErrorMsg("51001","获取列表失败");
            logger.error(e.getMessage(), e);
        }
        return json;
    }
    
    /**
     * 获取销售报表-商品汇总
     * @param request
     * @param params
     * @return
     */
    @RequestMapping(value="/getSaleReportGoodsList",method=RequestMethod.GET)
    public JSONObject getSaleReportGoodsList(HttpServletRequest request, @RequestParam Map<String, Object> params){
        JSONObject json = new JSONObject();
        try{
            UserInfo userInfo = SessionUtils.getUserInfo();
            if(userInfo == null) json = RestfulRetUtils.getErrorNoUser();
            else {
                Pageable pageable = new PageRequest(SessionUtils.getPageIndex(request), SessionUtils.getPageSize(request));               
                json = reportSaleService.getSaleReportGoodsList(pageable, params);
            }
        }catch(Exception e){
            json = RestfulRetUtils.getErrorMsg("51001","获取列表失败");
            logger.error(e.getMessage(), e);
        }
        return json;
    }
    
    /**
     * 获取销售报表-订单汇总
     * @param request
     * @param params
     * @return
     */
    @RequestMapping(value="/getSaleReportOrderList",method=RequestMethod.GET)
    public JSONObject getSaleReportOrderList(HttpServletRequest request, @RequestParam Map<String, Object> params){
        JSONObject json = new JSONObject();
        try{
            UserInfo userInfo = SessionUtils.getUserInfo();
            if(userInfo == null) json = RestfulRetUtils.getErrorNoUser();
            else {
                Pageable pageable = new PageRequest(SessionUtils.getPageIndex(request), SessionUtils.getPageSize(request));               
                json = reportSaleService.getSaleReportOrderList(pageable, params);
            }
        }catch(Exception e){
            json = RestfulRetUtils.getErrorMsg("51001","获取列表失败");
            logger.error(e.getMessage(), e);
        }
        return json;
    }
    
    /**
     * 获取销售报表-客户汇总
     * @param request
     * @param params
     * @return
     */
    @RequestMapping(value="/getSaleReportCustomerList",method=RequestMethod.GET)
    public JSONObject getSaleReportCustomerList(HttpServletRequest request, @RequestParam Map<String, Object> params){
        JSONObject json = new JSONObject();
        try{
            UserInfo userInfo = SessionUtils.getUserInfo();
            if(userInfo == null) json = RestfulRetUtils.getErrorNoUser();
            else {
                Pageable pageable = new PageRequest(SessionUtils.getPageIndex(request), SessionUtils.getPageSize(request));               
                json = reportSaleService.getSaleReportCustomerList(pageable, params);
            }
        }catch(Exception e){
            json = RestfulRetUtils.getErrorMsg("51001","获取列表失败");
            logger.error(e.getMessage(), e);
        }
        return json;
    }
    
    /**
     * 获取销售报表-订单创建人
     * @param request
     * @param params
     * @return
     */
    @RequestMapping(value="/getSaleReportCreateByList",method=RequestMethod.GET)
    public JSONObject getSaleReportCreateByList(HttpServletRequest request, @RequestParam Map<String, Object> params){
        JSONObject json = new JSONObject();
        try{
            UserInfo userInfo = SessionUtils.getUserInfo();
            if(userInfo == null) json = RestfulRetUtils.getErrorNoUser();
            else {
                Pageable pageable = new PageRequest(SessionUtils.getPageIndex(request), SessionUtils.getPageSize(request));               
                json = reportSaleService.getSaleReportCreateByList(pageable, params);
            }
        }catch(Exception e){
            json = RestfulRetUtils.getErrorMsg("51001","获取列表失败");
            logger.error(e.getMessage(), e);
        }
        return json;
    }
    
    /**
     * 获取销售报表-订单销售人
     * @param request
     * @param params
     * @return
     */
    @RequestMapping(value="/getSaleReportSellerByList",method=RequestMethod.GET)
    public JSONObject getSaleReportSellerByList(HttpServletRequest request, @RequestParam Map<String, Object> params){
        JSONObject json = new JSONObject();
        try{
            UserInfo userInfo = SessionUtils.getUserInfo();
            if(userInfo == null) json = RestfulRetUtils.getErrorNoUser();
            else {
                Pageable pageable = new PageRequest(SessionUtils.getPageIndex(request), SessionUtils.getPageSize(request));               
                json = reportSaleService.getSaleReportSellerByList(pageable, params);
            }
        }catch(Exception e){
            json = RestfulRetUtils.getErrorMsg("51001","获取列表失败");
            logger.error(e.getMessage(), e);
        }
        return json;
    }
    
    /**
     * 获取销售报表-业绩归属人
     * @param request
     * @param params
     * @return
     */
    @RequestMapping(value="/getSaleReportPerformanceByList",method=RequestMethod.GET)
    public JSONObject getSaleReportPerformanceByList(HttpServletRequest request, @RequestParam Map<String, Object> params){
        JSONObject json = new JSONObject();
        try{
            UserInfo userInfo = SessionUtils.getUserInfo();
            if(userInfo == null) json = RestfulRetUtils.getErrorNoUser();
            else {
                Pageable pageable = new PageRequest(SessionUtils.getPageIndex(request), SessionUtils.getPageSize(request));               
                json = reportSaleService.getSaleReportPerformanceByList(pageable, params);
            }
        }catch(Exception e){
            json = RestfulRetUtils.getErrorMsg("51001","获取列表失败");
            logger.error(e.getMessage(), e);
        }
        return json;
    }
    
    /**
     * 获取销售报表-店铺汇总
     * @param request
     * @param params
     * @return
     */
    @RequestMapping(value="/getSaleReportShopList",method=RequestMethod.GET)
    public JSONObject getSaleReportShopList(HttpServletRequest request, @RequestParam Map<String, Object> params){
        JSONObject json = new JSONObject();
        try{
            UserInfo userInfo = SessionUtils.getUserInfo();
            if(userInfo == null) json = RestfulRetUtils.getErrorNoUser();
            else {
                Pageable pageable = new PageRequest(SessionUtils.getPageIndex(request), SessionUtils.getPageSize(request));               
                json = reportSaleService.getSaleReportShopList(pageable, params);
            }
        }catch(Exception e){
            json = RestfulRetUtils.getErrorMsg("51001","获取列表失败");
            logger.error(e.getMessage(), e);
        }
        return json;
    }
}
