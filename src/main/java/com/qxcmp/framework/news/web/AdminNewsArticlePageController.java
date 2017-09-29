package com.qxcmp.framework.news.web;

import com.google.common.collect.ImmutableList;
import com.qxcmp.framework.audit.ActionException;
import com.qxcmp.framework.news.Article;
import com.qxcmp.framework.news.ArticleService;
import com.qxcmp.framework.news.ArticleStatus;
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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.Date;
import java.util.List;

import static com.qxcmp.framework.core.QXCMPConfiguration.QXCMP_BACKEND_URL;

@Controller
@RequestMapping(QXCMP_BACKEND_URL + "/news/article")
@RequiredArgsConstructor
public class AdminNewsArticlePageController extends QXCMPBackendController {

    private final ArticleService articleService;

    private final TableHelper tableHelper;

    private final AdminNewsPageHelper adminNewsPageHelper;

    @GetMapping("")
    public ModelAndView articlePage() {

        Long auditingArticleCount = articleService.count((root, criteriaQuery, criteriaBuilder) -> criteriaBuilder.equal(root.get("status"), ArticleStatus.AUDITING));
        Long publishedArticleCount = articleService.count((root, criteriaQuery, criteriaBuilder) -> criteriaBuilder.equal(root.get("status"), ArticleStatus.PUBLISHED));
        Long disabledArticleCount = articleService.count((root, criteriaQuery, criteriaBuilder) -> criteriaBuilder.equal(root.get("status"), ArticleStatus.DISABLED));

        return page().addComponent(new Overview("文章管理")
                .addComponent(convertToTable(stringObjectMap -> {
                    stringObjectMap.put("待审核文章数量", auditingArticleCount);
                    stringObjectMap.put("已发布文章数量", publishedArticleCount);
                    stringObjectMap.put("已禁用文章数量", disabledArticleCount);
                })))
                .setBreadcrumb("控制台", QXCMP_BACKEND_URL, "新闻管理", QXCMP_BACKEND_URL + "/news", "文章管理")
                .setVerticalMenu(getVerticalMenu(""))
                .build();
    }

    @GetMapping("/auditing")
    public ModelAndView articleAuditingPage(Pageable pageable) {

        Page<Article> articles = articleService.findByStatus(ArticleStatus.AUDITING, new PageRequest(pageable.getPageNumber(), pageable.getPageSize(), Sort.Direction.DESC, "dateAuditing"));

        return page().addComponent(tableHelper.convert("auditing", Article.class, articles))
                .setBreadcrumb("控制台", QXCMP_BACKEND_URL, "新闻管理", QXCMP_BACKEND_URL + "/news", "文章管理", QXCMP_BACKEND_URL + "/news/article", "待审核文章")
                .setVerticalMenu(getVerticalMenu("待审核文章"))
                .build();
    }

    @GetMapping("/published")
    public ModelAndView articlePublishedPage(Pageable pageable) {

        Page<Article> articles = articleService.findByStatus(ArticleStatus.PUBLISHED, new PageRequest(pageable.getPageNumber(), pageable.getPageSize(), Sort.Direction.DESC, "datePublished"));

        return page().addComponent(tableHelper.convert("published", Article.class, articles))
                .setBreadcrumb("控制台", QXCMP_BACKEND_URL, "新闻管理", QXCMP_BACKEND_URL + "/news", "文章管理", QXCMP_BACKEND_URL + "/news/article", "已发布文章")
                .setVerticalMenu(getVerticalMenu("已发布文章"))
                .build();
    }

    @GetMapping("/disabled")
    public ModelAndView articleDisabledPage(Pageable pageable) {

        Page<Article> articles = articleService.findByStatus(ArticleStatus.DISABLED, new PageRequest(pageable.getPageNumber(), pageable.getPageSize(), Sort.Direction.DESC, "dateDisabled"));

        return page().addComponent(tableHelper.convert("disabled", Article.class, articles))
                .setBreadcrumb("控制台", QXCMP_BACKEND_URL, "新闻管理", QXCMP_BACKEND_URL + "/news", "文章管理", QXCMP_BACKEND_URL + "/news/article", "已禁用文章")
                .setVerticalMenu(getVerticalMenu("已禁用文章"))
                .build();
    }

    @GetMapping("/{id}/preview")
    public ModelAndView userArticlePreviewPage(@PathVariable String id) {
        return articleService.findOne(id)
                .map(article -> page().addComponent(new Overview(article.getTitle(), article.getAuthor()).setAlignment(Alignment.CENTER)
                        .addComponent(getArticlePreviewContent(article))
                        .addLink("返回", QXCMP_BACKEND_URL + "/news/article"))
                        .setBreadcrumb("控制台", QXCMP_BACKEND_URL, "新闻管理", QXCMP_BACKEND_URL + "/news", "文章管理", QXCMP_BACKEND_URL + "/news/article", "查看文章")
                        .setVerticalMenu(getVerticalMenu(""))
                        .build())
                .orElse(overviewPage(new Overview(new IconHeader("文章不存在", new Icon("warning circle"))).addLink("返回", QXCMP_BACKEND_URL + "/news/article")).build());
    }

    @GetMapping("/{id}/audit")
    public ModelAndView articleAuditPage(@PathVariable String id, final AdminNewsArticleAuditForm form) {
        return articleService.findOne(id)
                .filter(article -> article.getStatus().equals(ArticleStatus.AUDITING))
                .map(article -> {
                    form.setOperation("通过文章");
                    return page().addComponent(new Overview(article.getTitle(), article.getAuthor()).setAlignment(Alignment.CENTER)
                            .addComponent(getArticleAuditContent(article, form))
                            .addLink("返回", QXCMP_BACKEND_URL + "/news/article/auditing"))
                            .setBreadcrumb("控制台", QXCMP_BACKEND_URL, "新闻管理", QXCMP_BACKEND_URL + "/news", "文章管理", QXCMP_BACKEND_URL + "/news/article", "审核文章")
                            .setVerticalMenu(getVerticalMenu("待审核文章"))
                            .addObject("selection_items_operation", ImmutableList.of("通过文章", "驳回文章"))
                            .build();
                })
                .orElse(overviewPage(new Overview(new IconHeader("文章不存在", new Icon("warning circle"))).addLink("返回", QXCMP_BACKEND_URL + "/news/article/auditing")).build());
    }

    @PostMapping("/{id}/audit")
    public ModelAndView articleAuditPage(@PathVariable String id, final AdminNewsArticleAuditForm form, BindingResult bindingResult) {

        User user = currentUser().orElseThrow(RuntimeException::new);

        return articleService.findOne(id)
                .filter(article -> article.getStatus().equals(ArticleStatus.AUDITING))
                .map(article -> submitForm("审核文章", form, context -> {
                    try {
                        if (StringUtils.equals("通过文章", form.getOperation())) {
                            articleService.update(article.getId(), a -> {
                                a.setAuditor(user.getId());
                                a.setAuditResponse(form.getResponse());
                                a.setDatePublished(new Date());
                                a.setStatus(ArticleStatus.PUBLISHED);
                            });
                        } else {
                            articleService.update(article.getId(), a -> {
                                a.setAuditor(user.getId());
                                a.setAuditResponse(form.getResponse());
                                a.setDateRejected(new Date());
                                a.setStatus(ArticleStatus.REJECT);
                            });
                        }
                    } catch (Exception e) {
                        throw new ActionException(e.getMessage(), e);
                    }
                }, (stringObjectMap, overview) -> overview.addLink("返回", QXCMP_BACKEND_URL + "/news/article/auditing")))
                .orElse(overviewPage(new Overview(new IconHeader("文章不存在", new Icon("warning circle"))).addLink("返回", QXCMP_BACKEND_URL + "/news/article/auditing")).build());
    }

    @PostMapping("/{id}/remove")
    public ResponseEntity<RestfulResponse> articleRemove(@PathVariable String id) {
        return articleService.findOne(id)
                .filter(article -> !article.getStatus().equals(ArticleStatus.PUBLISHED))
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

    @PostMapping("/{id}/disable")
    public ResponseEntity<RestfulResponse> articleDisable(@PathVariable String id) {

        User user = currentUser().orElseThrow(RuntimeException::new);

        return articleService.findOne(id)
                .filter(article -> article.getStatus().equals(ArticleStatus.PUBLISHED))
                .map(article -> {
                    RestfulResponse restfulResponse = audit("禁用文章", context -> {
                        try {
                            articleService.update(article.getId(), a -> {
                                a.setDatePublished(new Date());
                                a.setStatus(ArticleStatus.DISABLED);
                                a.setDisableUser(user.getId());
                            });
                        } catch (Exception e) {
                            throw new ActionException(e.getMessage(), e);
                        }
                    });
                    return ResponseEntity.status(restfulResponse.getStatus()).body(restfulResponse);
                }).orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).body(new RestfulResponse(HttpStatus.NOT_FOUND.value())));
    }

    @PostMapping("/{id}/enable")
    public ResponseEntity<RestfulResponse> articleEnable(@PathVariable String id) {

        return articleService.findOne(id)
                .filter(article -> article.getStatus().equals(ArticleStatus.DISABLED))
                .map(article -> {
                    RestfulResponse restfulResponse = audit("启用文章", context -> {
                        try {
                            articleService.update(article.getId(), a -> {
                                a.setDatePublished(new Date());
                                a.setStatus(ArticleStatus.PUBLISHED);
                            });
                        } catch (Exception e) {
                            throw new ActionException(e.getMessage(), e);
                        }
                    });
                    return ResponseEntity.status(restfulResponse.getStatus()).body(restfulResponse);
                }).orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).body(new RestfulResponse(HttpStatus.NOT_FOUND.value())));
    }

    private List<String> getVerticalMenu(String activeItem) {
        return ImmutableList.of(activeItem, "待审核文章", QXCMP_BACKEND_URL + "/news/article/auditing", "已发布文章", QXCMP_BACKEND_URL + "/news/article/published", "已禁用文章", QXCMP_BACKEND_URL + "/news/article/disabled");
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

    private Component getArticleAuditContent(Article article, AdminNewsArticleAuditForm form) {
        final AbstractGrid grid = new VerticallyDividedGrid().setVerticallyPadded();
        grid.addItem(new Row()
                .addCol(new Col().setComputerWide(Wide.FOUR).setMobileWide(Wide.SIXTEEN).addComponent(new Image(article.getCover()).setCentered().setBordered().setRounded()))
                .addCol(new Col().setComputerWide(Wide.TWELVE).setMobileWide(Wide.SIXTEEN).addComponent(convertToTable(adminNewsPageHelper.getArticleInfoTable(article))).addComponent(convertToForm(form)))
        );
        grid.addItem(new Row().addCol(new Col().setGeneralWide(Wide.SIXTEEN).addComponent(new HtmlText(article.getContent()))));
        return grid;
    }

}
