package com.example.weblog.moudle.admin.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2WebMvc;

@Configuration
@EnableSwagger2WebMvc
@Profile("dev")
public class Knife4AdminConfig {
    @Bean("adminApi")
    public Docket createApiDoc(){
        Docket docket = new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(buildApiInfo())
                .groupName("web 后台接口")
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.example.weblog.moudle.admin.controller"))
                .paths(PathSelectors.any())
                .build();
        return docket;
    }
    private ApiInfo buildApiInfo(){
        return new ApiInfoBuilder()
                .title("Weblog 博客Admin后台接口文档")
                .description("weblog 是一款由Spring Boot + Vue 3.2 + Vite 4.3开发的前后端分离博客")
                .termsOfServiceUrl("https://www.quanxiaoha.com")
                .contact(new Contact("犬小哈", "https://www.quanxiaoha.com","871361652@qq.com"))
                .version("1.0")
                .build();
    }

}
