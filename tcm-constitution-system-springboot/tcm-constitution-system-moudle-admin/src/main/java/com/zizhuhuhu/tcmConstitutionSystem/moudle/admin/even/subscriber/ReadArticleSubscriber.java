package com.zizhuhuhu.tcmConstitutionSystem.moudle.admin.even.subscriber;

import com.zizhuhuhu.tcmConstitutionSystem.module.common.domain.mapper.ArticalMapper;
import com.zizhuhuhu.tcmConstitutionSystem.module.common.domain.mapper.StatisticsArticlePVMapper;
import com.zizhuhuhu.tcmConstitutionSystem.moudle.admin.even.ReadArticleEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
@Slf4j
public class ReadArticleSubscriber implements ApplicationListener<ReadArticleEvent> {
    @Autowired
    private ArticalMapper articalMapper;
    @Autowired
    private StatisticsArticlePVMapper statisticsArticlePVMapper;
    @Override
    @Async("threadPoolTaskExecutor")
    public void onApplicationEvent(ReadArticleEvent event) {
        //在这里处理收到的事件，可以是任何逻辑操作
        Long articleId = event.getArticleId();
        //获取当前线程名称
        String threadName = Thread.currentThread().getName();
        log.info("===> threadName: {}", threadName);
        log.info("===> 文章阅读事件消费成功，articleId: {}", articleId);
        articalMapper.increaseReadNum(articleId);
        log.info("===> 文章阅读量+1，操作成功，articleId: {}", articleId);
        //当日pv访问量+1
        LocalDate currDate = LocalDate.now();
        statisticsArticlePVMapper.increasePVCount(currDate);
        log.info("===> 当日文章访问量+1，操作成功，date: {}", currDate);
    }

}
