package com.scms;

import org.springframework.boot.Banner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.feign.EnableFeignClients;

import com.scms.listen.StartupListener;

/**
 * 服务启动主函数
 * @author zzd
 */
@SpringBootApplication
@EnableEurekaClient
@EnableFeignClients
public class ScmsApplication {

    public static void main(String[] args) {
        SpringApplication app = new SpringApplication(ScmsApplication.class);
        app.setBannerMode(Banner.Mode.OFF);
        app.addListeners(new StartupListener());
        app.run(args);
    }
}
