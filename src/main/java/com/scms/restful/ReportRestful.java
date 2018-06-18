package com.scms.restful;

import javax.servlet.http.HttpServletRequest;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import com.alibaba.fastjson.JSONObject;
import com.gimplatform.core.utils.RestfulRetUtils;

@RestController
@RequestMapping(value = "/api/scms/report")
public class ReportRestful {

    protected static final Logger logger = LogManager.getLogger(ReportRestful.class);

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
}
