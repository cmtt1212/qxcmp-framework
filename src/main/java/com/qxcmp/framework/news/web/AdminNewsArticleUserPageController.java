package com.qxcmp.framework.news.web;

import com.google.common.collect.ImmutableList;
import com.qxcmp.framework.news.Article;
import com.qxcmp.framework.news.ArticleService;
import com.qxcmp.framework.news.ArticleStatus;
import com.qxcmp.framework.user.User;
import com.qxcmp.framework.web.QXCMPBackendController;
import com.qxcmp.framework.web.view.support.utils.TableHelper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

import static com.qxcmp.framework.core.QXCMPConfiguration.QXCMP_BACKEND_URL;

@Controller
@RequestMapping(QXCMP_BACKEND_URL + "/news/article/user")
@RequiredArgsConstructor
public class AdminNewsArticleUserPageController extends QXCMPBackendController {

    private final ArticleService articleService;

    private final TableHelper tableHelper;

    @GetMapping("")
    public ModelAndView userArticlePage(Pageable pageable) {

        User user = currentUser().orElseThrow(RuntimeException::new);

        Page<Article> articles = articleService.findByUserId(user.getId(), pageable);

        return page().addComponent(tableHelper.convert("user", Article.class, articles))
                .setBreadcrumb("控制台", QXCMP_BACKEND_URL, "新闻管理", QXCMP_BACKEND_URL + "/news", "我的文章")
                .setVerticalMenu(getVerticalMenu(""))
                .build();
    }

    @GetMapping("/draft")
    public ModelAndView userArticleDraftPage(Pageable pageable) {

        User user = currentUser().orElseThrow(RuntimeException::new);

        Page<Article> articles = articleService.findByUserIdAndStatus(user.getId(), ArticleStatus.NEW, pageable);

        return page().addComponent(tableHelper.convert("userDraft", Article.class, articles))
                .setBreadcrumb("控制台", QXCMP_BACKEND_URL, "新闻管理", QXCMP_BACKEND_URL + "/news", "我的文章", QXCMP_BACKEND_URL + "/news/article/user", "草稿箱")
                .setVerticalMenu(getVerticalMenu("草稿箱"))
                .build();
    }

    @GetMapping("/auditing")
    public ModelAndView userArticleAuditingPage(Pageable pageable) {

        User user = currentUser().orElseThrow(RuntimeException::new);

        Page<Article> articles = articleService.findByUserIdAndStatus(user.getId(), ArticleStatus.AUDITING, pageable);

        return page().addComponent(tableHelper.convert("userAuditing", Article.class, articles))
                .setBreadcrumb("控制台", QXCMP_BACKEND_URL, "新闻管理", QXCMP_BACKEND_URL + "/news", "我的文章", QXCMP_BACKEND_URL + "/news/article/user", "审核中文章")
                .setVerticalMenu(getVerticalMenu("审核中文章"))
                .build();
    }

    @GetMapping("/rejected")
    public ModelAndView userArticleRejectedPage(Pageable pageable) {

        User user = currentUser().orElseThrow(RuntimeException::new);

        Page<Article> articles = articleService.findByUserIdAndStatus(user.getId(), ArticleStatus.REJECT, pageable);

        return page().addComponent(tableHelper.convert("userRejected", Article.class, articles))
                .setBreadcrumb("控制台", QXCMP_BACKEND_URL, "新闻管理", QXCMP_BACKEND_URL + "/news", "我的文章", QXCMP_BACKEND_URL + "/news/article/user", "未通过文章")
                .setVerticalMenu(getVerticalMenu("未通过文章"))
                .build();
    }

    @GetMapping("/published")
    public ModelAndView userArticlePublishedPage(Pageable pageable) {

        User user = currentUser().orElseThrow(RuntimeException::new);

        Page<Article> articles = articleService.findByUserIdAndStatus(user.getId(), ArticleStatus.PUBLISHED, pageable);

        return page().addComponent(tableHelper.convert("userPublished", Article.class, articles))
                .setBreadcrumb("控制台", QXCMP_BACKEND_URL, "新闻管理", QXCMP_BACKEND_URL + "/news", "我的文章", QXCMP_BACKEND_URL + "/news/article/user", "已发布文章")
                .setVerticalMenu(getVerticalMenu("已发布文章"))
                .build();
    }

    @GetMapping("/disabled")
    public ModelAndView userArticleDisabledPage(Pageable pageable) {

        User user = currentUser().orElseThrow(RuntimeException::new);

        Page<Article> articles = articleService.findByUserIdAndStatus(user.getId(), ArticleStatus.DISABLED, pageable);

        return page().addComponent(tableHelper.convert("userDisabled", Article.class, articles))
                .setBreadcrumb("控制台", QXCMP_BACKEND_URL, "新闻管理", QXCMP_BACKEND_URL + "/news", "我的文章", QXCMP_BACKEND_URL + "/news/article/user", "已禁用文章")
                .setVerticalMenu(getVerticalMenu("已禁用文章"))
                .build();
    }

    private List<String> getVerticalMenu(String activeItem) {
        return ImmutableList.of(activeItem, "草稿箱", QXCMP_BACKEND_URL + "/news/article/user/draft", "审核中文章", QXCMP_BACKEND_URL + "/news/article/user/auditing", "未通过文章", QXCMP_BACKEND_URL + "/news/article/user/rejected", "已发布文章", QXCMP_BACKEND_URL + "/news/article/user/published", "已禁用文章", QXCMP_BACKEND_URL + "/news/article/user/disabled");
    }
}
