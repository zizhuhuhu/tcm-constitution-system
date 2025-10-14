package com.example.weblog.web;

import com.example.weblog.seacher.runner.InitLuceneIndexRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Import;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@ComponentScan("com.example.weblog.*")
@EnableScheduling //启用定时任务
/*
@Import({
        InitLuceneIndexRunner.class,
        // 或者其他需要的配置类
})
*/

public class WeblogWebApplication {

    public static void main(String[] args) {
        SpringApplication.run(WeblogWebApplication.class, args);
    }

}
