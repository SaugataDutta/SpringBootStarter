package com.tgt.mkt.cam.document;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;


/**
 * API documentation
 */
@Configuration
@EnableSwagger2
@PropertySource("classpath:swagger.properties")
@Slf4j
public class ApiDocument {
    @Value("${springfox.documentation.swagger.v2.path}")
    private String shootPath;

    @Bean
    public Docket usageRightsMgmtApi() {
        log.info("Setting up swagger for spring-boot starter api, with swagger endpoint of {}",shootPath);
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(new ApiInfo("Spring Boot Starter Rest API", "Spring Boot Starter Rest API",
                        "1.0", null,
                        ": Target", null,null))
                .groupName("spring-boot starter")
                .select()
                .build();
    }
}
