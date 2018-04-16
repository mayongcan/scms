package com.scms.listen;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import com.gimplatform.core.service.FuncInfoService;
import com.gimplatform.core.utils.SpringContextHolder;

/**
 * 应用程序启动后执行的监听器
 * @author zzd
 *
 */
public class StartupListener implements ApplicationListener<ContextRefreshedEvent> {

    private static final Logger logger = LogManager.getLogger(StartupListener.class);
    
    private FuncInfoService funcInfoService = null;
	
	@Override
	public void onApplicationEvent(ContextRefreshedEvent event) {
		logger.info("初始化自定义监听器...");
		if(SpringContextHolder.getApplicationContext() == null) return;
		funcInfoService = SpringContextHolder.getBean(FuncInfoService.class);
		if(funcInfoService == null){
			logger.error("容器注入失败！");
			return;
		}
		//加载所有权限数据到内存中
		if(!funcInfoService.loadFuncDataToCache())
			logger.error("加载权限数据失败！");
	}
}
