package com.zizhuhuhu.tcmConstitutionSystem.web;

import com.zizhuhuhu.tcmConstitutionSystem.seacher.runner.InitLuceneIndexRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Import;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@ComponentScan("com.zizhuhuhu.tcmConstitutionSystem.*")
@EnableScheduling //启用定时任务
/*
@Import({
        InitLuceneIndexRunner.class,
        // 或者其他需要的配置类
})
*/

public class TcmConstitutionSystemApplication {

    public static void main(String[] args) {
        SpringApplication.run(TcmConstitutionSystemApplication.class, args);
    }

}
