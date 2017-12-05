package com.qxcmp.framework.news.event;

import com.qxcmp.framework.news.Article;
import com.qxcmp.framework.user.User;
import lombok.Getter;

/**
 * @author Aaric
 */
@Getter
public class AdminNewsArticleDisableEvent {

    private final User user;
    private final Article article;

    public AdminNewsArticleDisableEvent(User user, Article article) {
        this.user = user;
        this.article = article;
    }
}
