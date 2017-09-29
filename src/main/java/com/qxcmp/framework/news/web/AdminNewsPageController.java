package com.qxcmp.framework.news.web;

import com.google.common.collect.ImmutableList;
import com.qxcmp.framework.audit.ActionException;
import com.qxcmp.framework.news.ArticleService;
import com.qxcmp.framework.news.Channel;
import com.qxcmp.framework.news.ChannelService;
import com.qxcmp.framework.web.QXCMPBackendController;
import com.qxcmp.framework.web.view.elements.header.IconHeader;
import com.qxcmp.framework.web.view.elements.html.HtmlText;
import com.qxcmp.framework.web.view.elements.icon.Icon;
import com.qxcmp.framework.web.view.elements.segment.Segment;
import com.qxcmp.framework.web.view.support.Alignment;
import com.qxcmp.framework.web.view.views.Overview;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
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

    @GetMapping("/channel/{id}/preview")
    public ModelAndView newsChannelPreviewPage(@PathVariable String id) {
        return channelService.findOne(id).map(channel -> page().addComponent(new Overview(channel.getName()).setAlignment(Alignment.CENTER)
                .addComponent(new HtmlText(channel.getContent()))
                .addLink("返回", QXCMP_BACKEND_URL + "/news/channel"))
                .setBreadcrumb("控制台", QXCMP_BACKEND_URL, "新闻管理", QXCMP_BACKEND_URL + "/news", "栏目管理", QXCMP_BACKEND_URL + "/news/channel", "栏目预览")
                .setVerticalMenu(getVerticalMenu("栏目管理"))
                .build()).orElse(overviewPage(new Overview(new IconHeader("栏目不存在", new Icon("warning circle"))).addLink("返回", QXCMP_BACKEND_URL + "/news/channel")).build());
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

    @PostMapping("/channel/new")
    public ModelAndView newsChannelNewPage(@Valid final AdminNewsChannelNewForm form, BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            return page().addComponent(new Segment().addComponent(convertToForm(form)))
                    .setBreadcrumb("控制台", QXCMP_BACKEND_URL, "新闻管理", QXCMP_BACKEND_URL + "/news", "栏目管理", QXCMP_BACKEND_URL + "/news/channel", "新建栏目")
                    .setVerticalMenu(getVerticalMenu("栏目管理"))
                    .addObject("selection_items_owner", userService.findAll())
                    .addObject("selection_items_admins", userService.findAll())
                    .build();
        }

        return submitForm(form, context -> {
            try {
                Channel channel = channelService.next();
                channel.setCover(form.getCover());
                channel.setName(form.getName());
                channel.setDescription(form.getDescription());
                channel.setOwner(form.getOwner());
                channel.setAdmins(form.getAdmins());
                channel.setContent(form.getContent());
                channel.setContentQuill(form.getContentQuill());
                channelService.create(() -> channel);
            } catch (Exception e) {
                throw new ActionException(e.getMessage(), e);
            }
        }, (stringObjectMap, overview) -> overview.addLink("返回栏目管理", QXCMP_BACKEND_URL + "/news/channel").addLink("继续新建栏目", QXCMP_BACKEND_URL + "/news/channel/new"));
    }

    private List<String> getVerticalMenu(String activeItem) {
        return ImmutableList.of(activeItem, "文章管理", QXCMP_BACKEND_URL + "/news/article", "栏目管理", QXCMP_BACKEND_URL + "/news/channel");
    }
}
