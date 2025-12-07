package com.zizhuhuhu.tcmConstitutionSystem.moudle.admin.even;

import lombok.Getter;
import org.springframework.context.ApplicationEvent;
@Getter
public class DeleteArticleEvent extends ApplicationEvent {
    private Long articleId;
    public DeleteArticleEvent(Object source, Long articleId) {
        super(source);
        this.articleId = articleId;
    }
}
