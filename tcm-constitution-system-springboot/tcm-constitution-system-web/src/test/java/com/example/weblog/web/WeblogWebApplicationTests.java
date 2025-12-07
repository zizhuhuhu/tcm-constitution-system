package com.zizhuhuhu.tcmConstitutionSystem.web;

import com.zizhuhuhu.tcmConstitutionSystem.module.common.domain.dos.UserDO;
import com.zizhuhuhu.tcmConstitutionSystem.module.common.domain.mapper.UserMapper;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Date;

@SpringBootTest
@Slf4j
class tcmConstitutionSystemWebApplicationTests {

    @Test
    void contextLoads() {
    }
    @Test
    void teatLog(){
        log.info("这是一行Info级别日志");
        log.warn("这是一行warn级别日志");
        log.error("这是一行Error级别日志");
        String author = "竹紫";
        log.info("这是一行带有占位符的日志，作者： {}", author);
    }
    @Autowired
    private UserMapper userMapper;
    @Test
    void insertTest(){
        UserDO userDo = UserDO.builder()
                .username("犬小哈")
                .password("123456")
                .createTime(new Date())
                .updateTime(new Date())
                .isDeleted(false)
                .build();
        userMapper.insert(userDo);
    }

}
