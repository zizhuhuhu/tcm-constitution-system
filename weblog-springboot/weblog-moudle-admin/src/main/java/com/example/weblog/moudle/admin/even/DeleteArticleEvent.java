package com.example.weblog.moudle.admin.even;

import com.example.weblog.module.common.domain.mapper.ArticleMapper;
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
