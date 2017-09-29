package com.qxcmp.framework.news.web;

import com.google.common.collect.ImmutableList;
import com.qxcmp.framework.news.ArticleService;
import com.qxcmp.framework.news.ChannelService;
import com.qxcmp.framework.web.QXCMPBackendController;
import com.qxcmp.framework.web.view.elements.segment.Segment;
import com.qxcmp.framework.web.view.views.Overview;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

import static com.qxcmp.framework.core.QXCMPConfiguration.QXCMP_BACKEND_URL;

@Controller
@RequestMapping(QXCMP_BACKEND_URL + "/news")
@RequiredArgsConstructor
public class AdminNewsPageController extends QXCMPBackendController {

    private final ArticleService articleService;

    private final ChannelService channelService;

    @GetMapping("")
    public ModelAndView newsPage() {
        return page().addComponent(new Overview("新闻管理"))
                .setBreadcrumb("控制台", QXCMP_BACKEND_URL, "新闻管理")
                .setVerticalMenu(getVerticalMenu(""))
                .build();
    }

    @GetMapping("/channel")
    public ModelAndView newsChannelPage(Pageable pageable) {
        return page().addComponent(convertToTable(pageable, channelService))
                .setBreadcrumb("控制台", QXCMP_BACKEND_URL, "新闻管理", QXCMP_BACKEND_URL + "/news", "栏目管理")
                .setVerticalMenu(getVerticalMenu("栏目管理"))
                .build();
    }

    @GetMapping("/channel/new")
    public ModelAndView newsChannelNewPage(final AdminNewsChannelNewForm form) {
        return page().addComponent(new Segment().addComponent(convertToForm(form)))
                .setBreadcrumb("控制台", QXCMP_BACKEND_URL, "新闻管理", QXCMP_BACKEND_URL + "/news", "栏目管理", QXCMP_BACKEND_URL + "/news/channel", "新建栏目")
                .setVerticalMenu(getVerticalMenu("栏目管理"))
                .addObject("selection_items_owner", userService.findAll())
                .addObject("selection_items_admins", userService.findAll())
                .build();
    }

    private List<String> getVerticalMenu(String activeItem) {
        return ImmutableList.of(activeItem, "文章管理", QXCMP_BACKEND_URL + "/news/article", "栏目管理", QXCMP_BACKEND_URL + "/news/channel");
    }
}
