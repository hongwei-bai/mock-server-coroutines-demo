package com.hongwei;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.scheduling.annotation.EnableScheduling;

//@SpringBootApplication
//@EnableScheduling
//public class DemoApplication {
//    public static void main(String[] args) {
//        SpringApplication.run(DemoApplication.class, args);
//    }
//}

// Deploy 3 - deploy war to Tomcat/NGINX server
@EntityScan
@SpringBootApplication
@EnableScheduling
public class DemoApplication extends SpringBootServletInitializer {

    public static void main(String[] args) {
        SpringApplication.run(DemoApplication.class, args);
    }

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
        return builder.sources(DemoApplication.class);
    }
}