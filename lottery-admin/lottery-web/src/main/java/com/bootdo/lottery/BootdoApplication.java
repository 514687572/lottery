package com.bootdo.lottery;

import org.aspectj.lang.annotation.Aspect;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.transaction.annotation.EnableTransactionManagement;


@EnableAutoConfiguration(exclude = {
        org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration.class
})
@EnableTransactionManagement
@ServletComponentScan
@MapperScan("com.bootdo.*.dao")
@ComponentScan(basePackages = "com.bootdo",includeFilters = {@ComponentScan.Filter(Aspect.class)})
@SpringBootApplication
@EnableCaching
public class BootdoApplication  extends SpringBootServletInitializer{
    public static void main(String[] args) {
        SpringApplication.run(com.bootdo.lottery.BootdoApplication.class, args);
        System.out.println("server start successful!!!");
    }

//
//    @Override//为了打包springboot项目
//    protected SpringApplicationBuilder configure(
//            SpringApplicationBuilder builder) {
//        return builder.sources(this.getClass());
//    }
}
