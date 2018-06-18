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
import com.scms.modules.report.ReportService;

@RestController
@RequestMapping(value = "/api/scms/report")
public class ReportRestful {

    protected static final Logger logger = LogManager.getLogger(ReportRestful.class);
    
    @Autowired
    private ReportService reportService;

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
     * 获取进销对比(统计)
     * @param request
     * @param params
     * @return
     */
    @RequestMapping(value="/getSalePurchaseCompareStatistics",method=RequestMethod.GET)
    public JSONObject getSalePurchaseCompareStatistics(HttpServletRequest request, @RequestParam Map<String, Object> params){
        JSONObject json = new JSONObject();
        try{
            UserInfo userInfo = SessionUtils.getUserInfo();
            if(userInfo == null) json = RestfulRetUtils.getErrorNoUser();
            else {          
                json = reportService.getSalePurchaseCompareStatistics(params);
            }
        }catch(Exception e){
            json = RestfulRetUtils.getErrorMsg("51001","获取列表失败");
            logger.error(e.getMessage(), e);
        }
        return json;
    }
    
    /**
     * 获取进销对比
     * @param request
     * @param params
     * @return
     */
    @RequestMapping(value="/getSalePurchaseCompareList",method=RequestMethod.GET)
    public JSONObject getSalePurchaseCompareList(HttpServletRequest request, @RequestParam Map<String, Object> params){
        JSONObject json = new JSONObject();
        try{
            UserInfo userInfo = SessionUtils.getUserInfo();
            if(userInfo == null) json = RestfulRetUtils.getErrorNoUser();
            else {
                Pageable pageable = new PageRequest(SessionUtils.getPageIndex(request), SessionUtils.getPageSize(request));               
                json = reportService.getSalePurchaseCompareList(pageable, params);
            }
        }catch(Exception e){
            json = RestfulRetUtils.getErrorMsg("51001","获取列表失败");
            logger.error(e.getMessage(), e);
        }
        return json;
    }
}
