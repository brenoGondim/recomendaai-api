package com.recomendaai;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.Authorization;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;

@SpringBootApplication
@EnableAsync
@EnableResourceServer
@ApiOperation(value = "", authorizations = { @Authorization(value="jwtToken") })
public class RecomendaAiApplication extends SpringBootServletInitializer {

    public static void main(String[] args) {
        SpringApplication.run(RecomendaAiApplication.class, args);
    }

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(RecomendaAiApplication.class);
    }
}
