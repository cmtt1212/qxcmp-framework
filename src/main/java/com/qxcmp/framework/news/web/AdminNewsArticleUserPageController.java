package com.qxcmp.framework.news.web;

import com.google.common.collect.ImmutableList;
import com.qxcmp.framework.audit.ActionException;
import com.qxcmp.framework.news.Article;
import com.qxcmp.framework.news.ArticleService;
import com.qxcmp.framework.news.ArticleStatus;
import com.qxcmp.framework.news.ChannelService;
import com.qxcmp.framework.user.User;
import com.qxcmp.framework.web.QXCMPBackendController;
import com.qxcmp.framework.web.model.RestfulResponse;
import com.qxcmp.framework.web.view.Component;
import com.qxcmp.framework.web.view.elements.grid.AbstractGrid;
import com.qxcmp.framework.web.view.elements.grid.Col;
import com.qxcmp.framework.web.view.elements.grid.Row;
import com.qxcmp.framework.web.view.elements.grid.VerticallyDividedGrid;
import com.qxcmp.framework.web.view.elements.header.IconHeader;
import com.qxcmp.framework.web.view.elements.html.HtmlText;
import com.qxcmp.framework.web.view.elements.icon.Icon;
import com.qxcmp.framework.web.view.elements.image.Image;
import com.qxcmp.framework.web.view.elements.segment.Segment;
import com.qxcmp.framework.web.view.support.Alignment;
import com.qxcmp.framework.web.view.support.Wide;
import com.qxcmp.framework.web.view.support.utils.TableHelper;
import com.qxcmp.framework.web.view.views.Overview;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.util.Date;
import java.util.List;

import static com.qxcmp.framework.core.QXCMPConfiguration.QXCMP_BACKEND_URL;

@Controller
@RequestMapping(QXCMP_BACKEND_URL + "/news/article/user")
@RequiredArgsConstructor
public class AdminNewsArticleUserPageController extends QXCMPBackendController {

    private final ArticleService articleService;

    private final ChannelService channelService;

    private final TableHelper tableHelper;

    private final AdminNewsPageHelper adminNewsPageHelper;

    @GetMapping("")
    public ModelAndView userArticlePage(Pageable pageable) {

        User user = currentUser().orElseThrow(RuntimeException::new);

        Page<Article> articles = articleService.findByUserId(user.getId(), new PageRequest(pageable.getPageNumber(), pageable.getPageSize(), Sort.Direction.DESC, "dateModified"));

        return page().addComponent(tableHelper.convert("user", Article.class, articles))
                .setBreadcrumb("控制台", QXCMP_BACKEND_URL, "新闻管理", QXCMP_BACKEND_URL + "/news", "我的文章")
                .setVerticalMenu(getVerticalMenu(""))
                .build();
    }

    @GetMapping("/draft")
    public ModelAndView userArticleDraftPage(Pageable pageable) {

        User user = currentUser().orElseThrow(RuntimeException::new);

        Page<Article> articles = articleService.findByUserIdAndStatus(user.getId(), ArticleStatus.NEW, new PageRequest(pageable.getPageNumber(), pageable.getPageSize(), Sort.Direction.DESC, "dateModified"));

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

    @GetMapping("/new")
    public ModelAndView userArticleNewPage(final AdminNewsArticleUserNewForm form) {

        User user = currentUser().orElseThrow(RuntimeException::new);

        form.setAuthor(user.getDisplayName());

        return page().addComponent(new Segment().addComponent(convertToForm(form)))
                .setBreadcrumb("控制台", QXCMP_BACKEND_URL, "新闻管理", QXCMP_BACKEND_URL + "/news", "我的文章", QXCMP_BACKEND_URL + "/news/article/user", "新建文章")
                .setVerticalMenu(getVerticalMenu(""))
                .addObject("selection_items_channels", channelService.findAll())
                .build();
    }

    @PostMapping("/new")
    public ModelAndView userArticleNewPage(@Valid final AdminNewsArticleUserNewForm form, BindingResult bindingResult) {

        User user = currentUser().orElseThrow(RuntimeException::new);

        if (bindingResult.hasErrors()) {
            return page().addComponent(new Segment().addComponent(convertToForm(form).setErrorMessage(convertToErrorMessage(bindingResult, form))))
                    .setBreadcrumb("控制台", QXCMP_BACKEND_URL, "新闻管理", QXCMP_BACKEND_URL + "/news", "我的文章", QXCMP_BACKEND_URL + "/news/article/user", "新建文章")
                    .setVerticalMenu(getVerticalMenu(""))
                    .addObject("selection_items_channels", channelService.findAll())
                    .build();
        }

        return submitForm(form, context -> {
            try {
                Article article = articleService.next();
                article.setUserId(user.getId());
                article.setStatus(ArticleStatus.NEW);
                article.setCover(form.getCover());
                article.setTitle(form.getTitle());
                article.setAuthor(form.getAuthor());
                article.setDigest(form.getDigest());
                article.setChannels(form.getChannels());
                article.setContent(form.getContent());
                article.setContentQuill(form.getContentQuill());
                articleService.create(() -> article).ifPresent(a -> context.put("article", a));
            } catch (Exception e) {
                throw new ActionException(e.getMessage(), e);
            }
        }, (context, overview) -> {
            Article article = (Article) context.get("article");
            overview
                    .addLink("我的文章", QXCMP_BACKEND_URL + "/news/article/user")
                    .addLink("草稿箱", QXCMP_BACKEND_URL + "/news/article/user/draft")
                    .addLink("预览文章", QXCMP_BACKEND_URL + "/news/article/user/" + article.getId() + "/preview")
                    .addLink("继续新建文章", QXCMP_BACKEND_URL + "/news/article/user/new");
        });
    }

    @GetMapping("/{id}/edit")
    public ModelAndView userArticleEditPage(@PathVariable String id, final AdminNewsArticleUserEditForm form) {

        User user = currentUser().orElseThrow(RuntimeException::new);

        return articleService.findOne(id)
                .filter(article -> StringUtils.equals(article.getUserId(), user.getId()))
                .filter(article -> article.getStatus().equals(ArticleStatus.NEW) || article.getStatus().equals(ArticleStatus.REJECT))
                .map(article -> {

                    form.setCover(article.getCover());
                    form.setTitle(article.getTitle());
                    form.setAuthor(article.getAuthor());
                    form.setDigest(article.getDigest());
                    form.setChannels(article.getChannels());
                    form.setContent(article.getContent());
                    form.setContentQuill(article.getContentQuill());

                    return page().addComponent(new Segment().addComponent(convertToForm(form)))
                            .setBreadcrumb("控制台", QXCMP_BACKEND_URL, "新闻管理", QXCMP_BACKEND_URL + "/news", "我的文章", QXCMP_BACKEND_URL + "/news/article/user", "编辑文章")
                            .setVerticalMenu(getVerticalMenu(""))
                            .addObject("selection_items_channels", channelService.findAll())
                            .build();
                }).orElse(overviewPage(new Overview(new IconHeader("文章不存在", new Icon("warning circle"))).addLink("返回", QXCMP_BACKEND_URL + "/news/article/user")).build());
    }

    @PostMapping("/{id}/edit")
    public ModelAndView userArticleEditPage(@PathVariable String id, final AdminNewsArticleUserEditForm form, BindingResult bindingResult) {

        User user = currentUser().orElseThrow(RuntimeException::new);

        return articleService.findOne(id)
                .filter(article -> StringUtils.equals(article.getUserId(), user.getId()))
                .filter(article -> article.getStatus().equals(ArticleStatus.NEW) || article.getStatus().equals(ArticleStatus.REJECT))
                .map(article -> {

                    if (bindingResult.hasErrors()) {
                        return page().addComponent(new Segment().addComponent(convertToForm(form).setErrorMessage(convertToErrorMessage(bindingResult, form))))
                                .setBreadcrumb("控制台", QXCMP_BACKEND_URL, "新闻管理", QXCMP_BACKEND_URL + "/news", "我的文章", QXCMP_BACKEND_URL + "/news/article/user", "编辑文章")
                                .setVerticalMenu(getVerticalMenu(""))
                                .addObject("selection_items_channels", channelService.findAll())
                                .build();
                    }

                    return submitForm(form, context -> {
                        try {
                            articleService.update(article.getId(), a -> {
                                a.setCover(form.getCover());
                                a.setTitle(form.getTitle());
                                a.setAuthor(form.getAuthor());
                                a.setDigest(form.getDigest());
                                a.setChannels(form.getChannels());
                                a.setContent(form.getContent());
                                a.setContentQuill(form.getContentQuill());
                                a.setDateModified(new Date());
                            });
                        } catch (Exception e) {
                            throw new ActionException(e.getMessage(), e);
                        }
                    }, (context, overview) -> overview
                            .addLink("我的文章", QXCMP_BACKEND_URL + "/news/article/user")
                            .addLink("草稿箱", QXCMP_BACKEND_URL + "/news/article/user/draft")
                            .addLink("预览文章", QXCMP_BACKEND_URL + "/news/article/user/" + article.getId() + "/preview")
                            .addLink("新建文章", QXCMP_BACKEND_URL + "/news/article/user/new"));
                }).orElse(overviewPage(new Overview(new IconHeader("文章不存在", new Icon("warning circle"))).addLink("返回", QXCMP_BACKEND_URL + "/news/article/user")).build());
    }

    @GetMapping("/{id}/preview")
    public ModelAndView userArticlePreviewPage(@PathVariable String id) {

        User user = currentUser().orElseThrow(RuntimeException::new);

        return articleService.findOne(id)
                .filter(article -> StringUtils.equals(article.getUserId(), user.getId()))
                .map(article -> page().addComponent(new Overview(article.getTitle(), article.getAuthor()).setAlignment(Alignment.CENTER)
                        .addComponent(getArticlePreviewContent(article))
                        .addLink("返回我的文章", QXCMP_BACKEND_URL + "/news/article/user")
                        .addLink("返回草稿箱", QXCMP_BACKEND_URL + "/news/article/user/draft")
                        .addLink("新建文章", QXCMP_BACKEND_URL + "/news/article/user/new"))
                        .setBreadcrumb("控制台", QXCMP_BACKEND_URL, "新闻管理", QXCMP_BACKEND_URL + "/news", "我的文章", QXCMP_BACKEND_URL + "/news/article/user", "预览文章")
                        .setVerticalMenu(getVerticalMenu(""))
                        .build())
                .orElse(overviewPage(new Overview(new IconHeader("文章不存在", new Icon("warning circle"))).addLink("返回", QXCMP_BACKEND_URL + "/news/article/user")).build());
    }

    @PostMapping("/{id}/remove")
    public ResponseEntity<RestfulResponse> userArticleRemove(@PathVariable String id) {

        User user = currentUser().orElseThrow(RuntimeException::new);

        return articleService.findOne(id)
                .filter(article -> StringUtils.equals(article.getUserId(), user.getId()))
                .filter(article -> article.getStatus().equals(ArticleStatus.NEW) || article.getStatus().equals(ArticleStatus.REJECT))
                .map(article -> {
                    RestfulResponse restfulResponse = audit("删除文章", context -> {
                        try {
                            articleService.remove(article);
                        } catch (Exception e) {
                            throw new ActionException(e.getMessage(), e);
                        }
                    });
                    return ResponseEntity.status(restfulResponse.getStatus()).body(restfulResponse);
                }).orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).body(new RestfulResponse(HttpStatus.NOT_FOUND.value())));
    }

    @PostMapping("/remove")
    public ResponseEntity<RestfulResponse> userArticleBatchRemove(@RequestParam("keys[]") List<String> keys) {

        User user = currentUser().orElseThrow(RuntimeException::new);

        RestfulResponse restfulResponse = audit("批量删除文章", context -> {
            try {
                for (String key : keys) {
                    articleService.findOne(key)
                            .filter(article -> StringUtils.equals(article.getUserId(), user.getId()))
                            .filter(article -> article.getStatus().equals(ArticleStatus.NEW) || article.getStatus().equals(ArticleStatus.REJECT))
                            .ifPresent(articleService::remove);
                }
            } catch (Exception e) {
                throw new ActionException(e.getMessage(), e);
            }
        });
        return ResponseEntity.status(restfulResponse.getStatus()).body(restfulResponse);
    }

    @GetMapping("/{id}/audit")
    public ModelAndView userArticleAuditPage(@PathVariable String id, final AdminNewsArticleUserAuditForm form) {

        User user = currentUser().orElseThrow(RuntimeException::new);

        return articleService.findOne(id)
                .filter(article -> StringUtils.equals(article.getUserId(), user.getId()))
                .filter(article -> article.getStatus().equals(ArticleStatus.NEW) || article.getStatus().equals(ArticleStatus.REJECT))
                .map(article -> page().addComponent(new Overview(article.getTitle(), article.getAuthor()).setAlignment(Alignment.CENTER)
                        .addComponent(getArticleAuditContent(article, form))
                        .addLink("返回我的文章", QXCMP_BACKEND_URL + "/news/article/user")
                        .addLink("返回草稿箱", QXCMP_BACKEND_URL + "/news/article/user/draft")
                        .addLink("新建文章", QXCMP_BACKEND_URL + "/news/article/user/new"))
                        .setBreadcrumb("控制台", QXCMP_BACKEND_URL, "新闻管理", QXCMP_BACKEND_URL + "/news", "我的文章", QXCMP_BACKEND_URL + "/news/article/user", "申请审核")
                        .setVerticalMenu(getVerticalMenu(""))
                        .build())
                .orElse(overviewPage(new Overview(new IconHeader("文章不存在", new Icon("warning circle"))).addLink("返回", QXCMP_BACKEND_URL + "/news/article/user")).build());
    }

    @PostMapping("/{id}/audit")
    public ModelAndView userArticleAuditPage(@PathVariable String id, final AdminNewsArticleUserAuditForm form, BindingResult bindingResult) {

        User user = currentUser().orElseThrow(RuntimeException::new);

        return articleService.findOne(id)
                .filter(article -> StringUtils.equals(article.getUserId(), user.getId()))
                .filter(article -> article.getStatus().equals(ArticleStatus.NEW) || article.getStatus().equals(ArticleStatus.REJECT))
                .map(article -> submitForm(form, context -> {
                            try {
                                articleService.update(article.getId(), a -> {
                                    a.setAuditRequest(form.getAuditRequest());
                                    a.setDateAuditing(new Date());
                                    a.setStatus(ArticleStatus.AUDITING);
                                });
                            } catch (Exception e) {
                                throw new ActionException(e.getMessage(), e);
                            }
                        }, (stringObjectMap, overview) -> {
                            overview.setHeader(new IconHeader("申请审核成功", new Icon("info circle")));
                            overview
                                    .addLink("返回我的文章", QXCMP_BACKEND_URL + "/news/article/user")
                                    .addLink("返回草稿箱", QXCMP_BACKEND_URL + "/news/article/user/draft");
                        }
                ))
                .orElse(overviewPage(new Overview(new IconHeader("文章不存在", new Icon("warning circle"))).addLink("返回", QXCMP_BACKEND_URL + "/news/article/user")).build());
    }

    @PostMapping("/{id}/repeal")
    public ResponseEntity<RestfulResponse> userArticleRepeal(@PathVariable String id) {

        User user = currentUser().orElseThrow(RuntimeException::new);

        return articleService.findOne(id)
                .filter(article -> StringUtils.equals(article.getUserId(), user.getId()))
                .filter(article -> article.getStatus().equals(ArticleStatus.AUDITING))
                .map(article -> {
                    RestfulResponse restfulResponse = audit("撤销文章审核申请", context -> {
                        try {
                            articleService.update(article.getId(), a -> {
                                a.setStatus(ArticleStatus.NEW);
                                a.setDateModified(new Date());
                            });
                        } catch (Exception e) {
                            throw new ActionException(e.getMessage(), e);
                        }
                    });
                    return ResponseEntity.status(restfulResponse.getStatus()).body(restfulResponse);
                }).orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).body(new RestfulResponse(HttpStatus.NOT_FOUND.value())));
    }

    private List<String> getVerticalMenu(String activeItem) {
        return ImmutableList.of(activeItem, "草稿箱", QXCMP_BACKEND_URL + "/news/article/user/draft", "审核中文章", QXCMP_BACKEND_URL + "/news/article/user/auditing", "未通过文章", QXCMP_BACKEND_URL + "/news/article/user/rejected", "已发布文章", QXCMP_BACKEND_URL + "/news/article/user/published", "已禁用文章", QXCMP_BACKEND_URL + "/news/article/user/disabled");
    }

    private Component getArticlePreviewContent(Article article) {
        final AbstractGrid grid = new VerticallyDividedGrid().setVerticallyPadded();
        grid.addItem(new Row()
                .addCol(new Col().setComputerWide(Wide.FOUR).setMobileWide(Wide.SIXTEEN).addComponent(new Image(article.getCover()).setCentered().setBordered().setRounded()))
                .addCol(new Col().setComputerWide(Wide.TWELVE).setMobileWide(Wide.SIXTEEN).addComponent(convertToTable(adminNewsPageHelper.getArticleInfoTable(article))))
        );
        grid.addItem(new Row().addCol(new Col().setGeneralWide(Wide.SIXTEEN).addComponent(new HtmlText(article.getContent()))));
        return grid;
    }

    private Component getArticleAuditContent(Article article, AdminNewsArticleUserAuditForm form) {
        final AbstractGrid grid = new VerticallyDividedGrid().setVerticallyPadded();
        grid.addItem(new Row()
                .addCol(new Col().setComputerWide(Wide.FOUR).setMobileWide(Wide.SIXTEEN).addComponent(new Image(article.getCover()).setCentered().setBordered().setRounded()))
                .addCol(new Col().setComputerWide(Wide.TWELVE).setMobileWide(Wide.SIXTEEN).addComponent(convertToTable(adminNewsPageHelper.getArticleInfoTable(article))).addComponent(convertToForm(form)))
        );
        grid.addItem(new Row().addCol(new Col().setGeneralWide(Wide.SIXTEEN).addComponent(new HtmlText(article.getContent()))));
        return grid;
    }
}