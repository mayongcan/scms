package com.scms.conf;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@EnableJpaRepositories(basePackages={"com.scms"})
@EntityScan(basePackages={"com.scms"})
@ComponentScan(basePackages = {"com.scms"})
public class ScanConfiguration {

}
