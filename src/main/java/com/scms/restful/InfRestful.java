package com.scms.restful;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * app接口模块Restful
 * @author zzd
 *
 */
@RestController
@RequestMapping(value = "/api/scms/inf")
public class InfRestful {
    
    protected static final Logger logger = LogManager.getLogger(InfRestful.class);
}
