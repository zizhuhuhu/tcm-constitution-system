package com.zizhuhuhu.tcmConstitutionSystem.moudle.admin.even;

import lombok.Getter;
import org.springframework.context.ApplicationEvent;

@Getter
public class ReadArticleEvent extends ApplicationEvent {
    //文章id
    private Long articleId;
    public ReadArticleEvent(Object source, Long articleId) {
        super(source);
        this.articleId = articleId;
    }
}
