package com.qxcmp.framework.news.web;

import com.google.common.collect.ImmutableList;
import com.qxcmp.framework.news.Article;
import com.qxcmp.framework.news.ArticleService;
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
@RequestMapping(QXCMP_BACKEND_URL + "/news/article")
@RequiredArgsConstructor
public class AdminNewsArticlePageController extends QXCMPBackendController {

    private final ArticleService articleService;

    private final TableHelper tableHelper;

    @GetMapping("")
    public ModelAndView articlePage(Pageable pageable) {

        User user = currentUser().orElseThrow(RuntimeException::new);

        Page<Article> articles = articleService.findByUserId(user.getId(), pageable);

        return page()
                .setBreadcrumb("控制台", QXCMP_BACKEND_URL, "新闻管理", QXCMP_BACKEND_URL + "/news", "文章管理")
                .setVerticalMenu(getVerticalMenu(""))
                .build();
    }

    private List<String> getVerticalMenu(String activeItem) {
        return ImmutableList.of(activeItem, "待审核文章", QXCMP_BACKEND_URL + "/news/article/auditing", "已发布文章", QXCMP_BACKEND_URL + "/news/article/published", "已禁用文章", QXCMP_BACKEND_URL + "/news/article/disabled");
    }
}
