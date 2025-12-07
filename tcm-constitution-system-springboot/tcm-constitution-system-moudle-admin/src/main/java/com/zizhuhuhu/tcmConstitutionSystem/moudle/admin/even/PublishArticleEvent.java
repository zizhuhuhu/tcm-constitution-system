package com.zizhuhuhu.tcmConstitutionSystem.moudle.admin.even;

import lombok.Getter;
import org.springframework.context.ApplicationEvent;
@Getter
public class PublishArticleEvent extends ApplicationEvent {
    /**
     * 文章 ID
     */
    private Long articleId;


    public PublishArticleEvent(Object source, Long articleId) {
        super(source);
        this.articleId = articleId;
    }
}
