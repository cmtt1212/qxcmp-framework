package com.qxcmp.framework.news.event;

import com.qxcmp.framework.config.SiteService;
import com.qxcmp.framework.message.MessageService;
import com.qxcmp.framework.news.Article;
import com.qxcmp.framework.user.User;
import com.qxcmp.framework.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

import static com.qxcmp.framework.core.QxcmpSecurityConfiguration.PRIVILEGE_NEWS_ARTICLE_MANAGEMENT;

/**
 * @author Aaric
 */
@Component
@RequiredArgsConstructor
public class AdminNewsArticleEventListener {

    private final MessageService messageService;
    private final UserService userService;
    private final SiteService siteService;

    @EventListener
    public void onDisableEvent(AdminNewsArticleDisableEvent event) {
        Article article = event.getArticle();
        User user = event.getUser();
        List<User> feedUsers = userService.findByAuthority(PRIVILEGE_NEWS_ARTICLE_MANAGEMENT);
        feedUsers.add(user);
        userService.findOne(article.getAuthor()).ifPresent(feedUsers::add);

        messageService.feed(feedUsers.stream().map(User::getId).collect(Collectors.toList()), user,
                String.format("%s 删除了文章 <a href='https://%s/admin/news/user/article'>%s</a>",
                        user.getDisplayName(),
                        siteService.getDomain(),
                        article.getTitle()));
    }

    @EventListener
    public void onEnableEvent(AdminNewsArticleEnableEvent event) {
        Article article = event.getArticle();
        User user = event.getUser();
        List<User> feedUsers = userService.findByAuthority(PRIVILEGE_NEWS_ARTICLE_MANAGEMENT);
        feedUsers.add(user);
        userService.findOne(article.getAuthor()).ifPresent(feedUsers::add);

        messageService.feed(feedUsers.stream().map(User::getId).collect(Collectors.toList()), user,
                String.format("%s 启用了文章 <a href='https://%s/admin/news/user/article'>%s</a>",
                        user.getDisplayName(),
                        siteService.getDomain(),
                        article.getTitle()));
    }

    @EventListener
    public void onPublishEvent(AdminNewsArticlePublishEvent event) {
        Article article = event.getArticle();
        User user = event.getUser();
        List<User> feedUsers = userService.findByAuthority(PRIVILEGE_NEWS_ARTICLE_MANAGEMENT);
        feedUsers.add(user);
        userService.findOne(article.getAuthor()).ifPresent(feedUsers::add);

        messageService.feed(feedUsers.stream().map(User::getId).collect(Collectors.toList()), user,
                String.format("%s 发布了文章 <a href='https://%s/admin/news/user/article'>%s</a>",
                        user.getDisplayName(),
                        siteService.getDomain(),
                        article.getTitle()));
    }
}
