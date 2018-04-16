package com.scms.conf;

import java.util.Enumeration;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import feign.RequestInterceptor;
import feign.RequestTemplate;

@Configuration
public class FeignInterceptorConfiguration implements RequestInterceptor{

	protected static final Logger logger = LogManager.getLogger(FeignInterceptorConfiguration.class);
	
	@Override
    public void apply(RequestTemplate requestTemplate) {
		//使用拦截器，传递access_token
        requestTemplate.query("access_token", getHttpServletRequest().getParameter("access_token"));
        requestTemplate.query("size", getHttpServletRequest().getParameter("size"));
        requestTemplate.query("page", getHttpServletRequest().getParameter("page"));
    }

    private HttpServletRequest getHttpServletRequest() {
        try {
            return ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        } catch (Exception e) {
        		logger.error(e.getMessage(), e);
            return null;
        }
    }

    public Map<String, String> getHeaders(HttpServletRequest request) {
        Map<String, String> map = new LinkedHashMap<>();
        Enumeration<String> enumeration = request.getHeaderNames();
        while (enumeration.hasMoreElements()) {
            String key = enumeration.nextElement();
            String value = request.getHeader(key);
            logger.debug("================Header Key[" + key + "]");
            logger.debug("================Header value[" + value + "]");
            map.put(key, value);
        }
        return map;
    }
}
