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

    @RequestMapping(value="/goodsProfitIndex", method=RequestMethod.GET)
    public JSONObject goodsProfitIndex(HttpServletRequest request){ return RestfulRetUtils.getRetSuccess();}

    @RequestMapping(value="/orderProfitIndex", method=RequestMethod.GET)
    public JSONObject orderProfitIndex(HttpServletRequest request){ return RestfulRetUtils.getRetSuccess();}
}
