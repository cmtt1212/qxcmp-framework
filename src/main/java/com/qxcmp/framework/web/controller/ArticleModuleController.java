package com.qxcmp.framework.web.controller;

import com.qxcmp.framework.domain.*;
import com.qxcmp.framework.security.RoleService;
import com.qxcmp.framework.view.dictionary.DictionaryView;
import com.qxcmp.framework.view.nav.Navigation;
import com.qxcmp.framework.view.support.TableViewHelper;
import com.qxcmp.framework.web.QXCMPBackendController2;
import com.qxcmp.framework.web.form.*;
import lombok.RequiredArgsConstructor;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.qxcmp.framework.core.QXCMPConfiguration.QXCMP_BACKEND_URL;
import static com.qxcmp.framework.core.QXCMPConfiguration.SYSTEM_CONFIG_ARTICLE_CHANNEL_CATALOG;
import static com.qxcmp.framework.view.component.ElementType.*;
import static com.qxcmp.framework.web.QXCMPWebConfiguration.*;

/**
 * 文章模块后端页面路由
 *
 * @author aaric
 */
@RequestMapping(QXCMP_BACKEND_URL + "/article")
@Controller
@RequiredArgsConstructor
public class ArticleModuleController extends QXCMPBackendController2 {

    private final ArticleService articleService;

    private final ChannelService channelService;

    private final ImageService imageService;

    private final TableViewHelper tableViewHelper;

    private final RoleService roleService;

    @GetMapping("/draft")
    public ModelAndView articleDraft(Pageable pageable) {

        Page<Article> articlePage = articleService.findByUserIdAndStatus(currentUser().getId(), ArticleStatus.NEW, pageable);

        return builder().setTitle("我的文章草稿")
                .setTableView(tableViewHelper.next("draft", Article.class, articlePage))
                .addNavigation("我的文章草稿", Navigation.Type.NORMAL, "文章管理")
                .build();
    }

    @GetMapping("/auditing")
    public ModelAndView articleAuditing(Pageable pageable) {

        Page<Article> articlePage = articleService.findByUserIdAndStatus(currentUser().getId(), ArticleStatus.AUDITING, pageable);

        return builder().setTitle("我的待审核文章")
                .setTableView(tableViewHelper.next("auditing", Article.class, articlePage))
                .addNavigation("我的待审核文章", Navigation.Type.NORMAL, "文章管理")
                .build();
    }

    @GetMapping("/reject")
    public ModelAndView articleReject(Pageable pageable) {

        Page<Article> articlePage = articleService.findByUserIdAndStatus(currentUser().getId(), ArticleStatus.REJECT, pageable);

        return builder().setTitle("我的未通过文章")
                .setTableView(tableViewHelper.next("reject", Article.class, articlePage))
                .addNavigation("我的未通过文章", Navigation.Type.NORMAL, "文章管理")
                .build();
    }

    @GetMapping("/published")
    public ModelAndView articlePublished(Pageable pageable) {

        Page<Article> articlePage = articleService.findByUserIdAndStatus(currentUser().getId(), ArticleStatus.PUBLISHED, pageable);

        return builder().setTitle("我的已发布文章")
                .setTableView(tableViewHelper.next("published", Article.class, articlePage))
                .addNavigation("我的已发布文章", Navigation.Type.NORMAL, "文章管理")
                .build();
    }

    @GetMapping("/disabled")
    public ModelAndView articleDisabled(Pageable pageable) {

        Page<Article> articlePage = articleService.findByUserIdAndStatus(currentUser().getId(), ArticleStatus.DISABLED, pageable);

        return builder().setTitle("我的被禁用文章")
                .setTableView(tableViewHelper.next("disabled", Article.class, articlePage))
                .addNavigation("我的被禁用文章", Navigation.Type.NORMAL, "文章管理")
                .build();
    }

    @GetMapping("/audit")
    public ModelAndView articleAudit(Pageable pageable) {

        if (userService.hasRole(currentUser(), PRIVILEGE_ARTICLE_AUDIT)) {
            Page<Article> articlePage = articleService.findByStatus(ArticleStatus.AUDITING, pageable);

            return builder().setTitle("文章审核")
                    .setTableView(tableViewHelper.next("audit", Article.class, articlePage))
                    .addNavigation("文章审核", Navigation.Type.NORMAL, "文章管理")
                    .build();
        } else {
            return error(HttpStatus.UNAUTHORIZED, "没有文章审核权限").build();
        }
    }

    @GetMapping("/publish")
    public ModelAndView articlePublish(Pageable pageable) {

        if (userService.hasRole(currentUser(), PRIVILEGE_ARTICLE_AUDIT)) {
            Page<Article> articlePage = articleService.findByStatus(ArticleStatus.PUBLISHED, pageable);

            return builder().setTitle("已发布文章")
                    .setTableView(tableViewHelper.next("publish", Article.class, articlePage))
                    .addNavigation("已发布文章", Navigation.Type.NORMAL, "文章管理")
                    .build();
        } else {
            return error(HttpStatus.UNAUTHORIZED, "没有文章审核权限").build();
        }
    }

    @GetMapping("/disable")
    public ModelAndView articleDisable(Pageable pageable) {

        if (userService.hasRole(currentUser(), PRIVILEGE_ARTICLE_AUDIT)) {
            Page<Article> articlePage = articleService.findByStatus(ArticleStatus.DISABLED, pageable);

            return builder().setTitle("已禁用文章")
                    .setTableView(tableViewHelper.next("disable", Article.class, articlePage))
                    .addNavigation("已禁用文章", Navigation.Type.NORMAL, "文章管理")
                    .build();
        } else {
            return error(HttpStatus.UNAUTHORIZED, "没有文章审核权限").build();
        }
    }

    @GetMapping("/{id}")
    public ModelAndView articleFind(@PathVariable String id) {
        return articleService.findOne(id).filter(article -> article.getUserId().equals(currentUser().getId())).map(article -> builder().setTitle(article.getTitle(), "预览文章")
                .addElement(H4, article.getTitle())
                .addElement(H6, article.getAuthor())
                .addElement(P, new SimpleDateFormat("创建于：yyyy-MM-dd HH:mm").format(article.getDateCreated()))
                .addDivider()
                .addHtml(article.getHtmlContent())
                .build()).orElse(error(HttpStatus.NOT_FOUND, "文章不存在").build());
    }

    @GetMapping("/new")
    public ModelAndView articleNew(final AdminArticleNewForm form) {

        List<Channel> channels = channelService.findByUserId(currentUser());

        if (channels.isEmpty()) {
            return error(HttpStatus.UNAUTHORIZED, "您还没有管理任何栏目，暂时不能创建文章").addNavigation("我的文章", Navigation.Type.NORMAL, "文章管理").build();
        }

        return builder().setTitle("新建文章")
                .setFormView(form, channels)
                .addNavigation("新建文章", Navigation.Type.NORMAL, "文章管理")
                .build();
    }

    @PostMapping("/new")
    public ModelAndView articleNew(@Valid @ModelAttribute(FORM_OBJECT) final AdminArticleNewForm form, BindingResult bindingResult) {
        List<Channel> channels = channelService.findByUserId(currentUser());

        if (channels.isEmpty()) {
            return error(HttpStatus.UNAUTHORIZED, "您还没有管理任何栏目，暂时不能创建文章").addNavigation("我的文章", Navigation.Type.NORMAL, "文章管理").build();
        }

        if (bindingResult.hasErrors()) {
            return builder().setTitle("新建文章")
                    .setFormView(form, channels)
                    .addNavigation("我的文章", Navigation.Type.NORMAL, "文章管理")
                    .build();
        }

        return action("新建文章", context -> articleService.create(() -> {
            Article article = articleService.next();

            article.setStatus(ArticleStatus.NEW);
            article.setTitle(form.getTitle());
            article.setAuthor(form.getAuthor());
            article.setDigest(form.getDigest());
            article.setChannels(form.getChannelList());
            article.setQuillContent(form.getQuillContent());
            article.setHtmlContent(form.getHtmlContent());
            article.setCover(form.getCover());
            article.setUserId(currentUser().getId());

            if (Objects.nonNull(form.getCoverFile()) && StringUtils.isNotBlank(form.getCoverFile().getOriginalFilename())) {
                try {
                    String fileType = FilenameUtils.getExtension(form.getCoverFile().getOriginalFilename());
                    imageService.store(form.getCoverFile().getInputStream(), fileType).ifPresent(image -> article.setCover(String.format("/api/image/%s.%s", image.getId(), image.getType())));
                } catch (IOException ignored) {

                }
            }

            return article;
        })).addNavigation("我的文章", Navigation.Type.NORMAL, "文章管理")
                .build();
    }

    @GetMapping("/{id}/edit")
    public ModelAndView articleEdit(@PathVariable String id) {
        return articleService.findOne(id).filter(article -> article.getUserId().equals(currentUser().getId())).map(article -> {

            if (article.getStatus().equals(ArticleStatus.NEW) || article.getStatus().equals(ArticleStatus.AUDITING) || article.getStatus().equals(ArticleStatus.REJECT)) {
                final AdminArticleEditForm form = new AdminArticleEditForm();
                form.setCover(article.getCover());
                form.setTitle(article.getTitle());
                form.setAuthor(article.getAuthor());
                form.setDigest(article.getDigest());
                form.setChannelList(article.getChannels());
                form.setQuillContent(article.getQuillContent());
                form.setHtmlContent(article.getHtmlContent());
                return builder().setTitle("编辑文章")
                        .setFormView(form, channelService.findByUserId(currentUser()))
                        .addNavigation("我的文章", Navigation.Type.NORMAL, "文章管理")
                        .build();
            } else {
                return error(HttpStatus.BAD_REQUEST, "不能编辑已经发布或者被禁用的文章").build();
            }

        }).orElse(error(HttpStatus.NOT_FOUND, "文章不存在").build());
    }

    @PostMapping("/{id}/edit")
    public ModelAndView articleEdit(@PathVariable String id, @Valid @ModelAttribute(FORM_OBJECT) final AdminArticleEditForm form, BindingResult bindingResult) {
        return articleService.findOne(id).filter(article -> article.getUserId().equals(currentUser().getId())).map(article -> {

            if (bindingResult.hasErrors()) {
                return builder().setTitle("编辑文章")
                        .setFormView(form, channelService.findByUserId(currentUser()))
                        .addNavigation("我的文章", Navigation.Type.NORMAL, "文章管理")
                        .build();
            }

            return action("编辑文章", context -> articleService.update(Long.parseLong(id), a -> {

                a.setTitle(form.getTitle());
                a.setAuthor(form.getAuthor());
                a.setDigest(form.getDigest());
                a.setChannels(form.getChannelList());
                a.setQuillContent(form.getQuillContent());
                a.setHtmlContent(form.getHtmlContent());
                a.setCover(form.getCover());
                a.setUserId(currentUser().getId());

                if (Objects.nonNull(form.getCoverFile()) && StringUtils.isNotBlank(form.getCoverFile().getOriginalFilename())) {
                    try {
                        String fileType = FilenameUtils.getExtension(form.getCoverFile().getOriginalFilename());
                        imageService.store(form.getCoverFile().getInputStream(), fileType).ifPresent(image -> a.setCover(String.format("/api/image/%s.%s", image.getId(), image.getType())));
                    } catch (IOException ignored) {

                    }
                }

            })).addNavigation("我的文章", Navigation.Type.NORMAL, "文章管理")
                    .build();

        }).orElse(error(HttpStatus.NOT_FOUND, "文章不存在").build());
    }

    @PostMapping("/{id}/delete")
    public ModelAndView articleDelete(@PathVariable String id) {
        return articleService.findOne(id).filter(article -> article.getUserId().equals(currentUser().getId()) && !article.getStatus().equals(ArticleStatus.PUBLISHED)).map(article -> action("删除文章", context -> articleService.remove(article.getId()), (context, modelAndViewBuilder) -> modelAndViewBuilder.setResultNavigation("新建文章", QXCMP_BACKEND_URL + "/article/new", "我的草稿", QXCMP_BACKEND_URL + "/article/draft")).build()).orElse(error(HttpStatus.BAD_REQUEST, "文章不存在").build());
    }

    @PostMapping("/{id}/auditing")
    public ModelAndView articleAuditing(@PathVariable String id) {
        return articleService.findOne(id).filter(article -> article.getUserId().equals(currentUser().getId()) && (article.getStatus().equals(ArticleStatus.NEW) || article.getStatus().equals(ArticleStatus.REJECT))).map(article -> {
            articleService.update(article.getId(), a -> a.setStatus(ArticleStatus.AUDITING));
            return builder().setTitle("申请审核文章")
                    .setResult("申请审核成功", article.getTitle())
                    .setResultNavigation("新建文章", QXCMP_BACKEND_URL + "/article/new", "我的草稿", QXCMP_BACKEND_URL + "/article/draft")
                    .build();
        }).orElse(error(HttpStatus.NOT_FOUND, "文章不存在").build());
    }

    @GetMapping("/{id}/audit")
    public ModelAndView articleAudit(@PathVariable String id) {

        if (userService.hasRole(currentUser(), PRIVILEGE_ARTICLE_AUDIT)) {
            return articleService.findOne(id).filter(article -> article.getStatus().equals(ArticleStatus.AUDITING)).map(article -> builder().setTitle("文章审核")
                    .addFragment("qxcmp/article-widget", "auditor")
                    .addElement(P, "正文内容")
                    .addDivider()
                    .addHtml(article.getHtmlContent())
                    .addNavigation("文章审核", Navigation.Type.NORMAL, "文章管理")
                    .addObject(article)
                    .addObject("dictionaryView", DictionaryView.builder().dictionary("作者", article.getAuthor()).dictionary("摘要", article.getDigest()).dictionary("所属栏目", article.getChannels().stream().map(Channel::getName).collect(Collectors.toList()).toString()).build())
                    .build()).orElse(error(HttpStatus.NOT_FOUND, "文章不存在").build());
        } else {
            return error(HttpStatus.UNAUTHORIZED, "没有文章审核权限").build();
        }
    }

    @GetMapping("/{id}/reject")
    public ModelAndView articleReject(@PathVariable String id) {

        if (userService.hasRole(currentUser(), PRIVILEGE_ARTICLE_AUDIT)) {
            return articleService.findOne(id).filter(article -> article.getStatus().equals(ArticleStatus.AUDITING)).map(article -> builder().setTitle("驳回文章")
                    .setFormView(new AdminArticleRejectForm())
                    .addDictionaryView(DictionaryView.builder().dictionary("作者", article.getAuthor()).dictionary("摘要", article.getDigest()).dictionary("所属栏目", article.getChannels().stream().map(Channel::getName).collect(Collectors.toList()).toString()).build())
                    .addHtml(article.getHtmlContent())
                    .addNavigation("文章审核", Navigation.Type.NORMAL, "文章管理")
                    .build()).orElse(error(HttpStatus.NOT_FOUND, "文章不存在").build());
        } else {
            return error(HttpStatus.UNAUTHORIZED, "没有文章审核权限").build();
        }
    }

    @PostMapping("/{id}/reject")
    public ModelAndView articleReject(@Valid @ModelAttribute(FORM_OBJECT) final AdminArticleRejectForm form, BindingResult bindingResult, @PathVariable String id) {
        if (userService.hasRole(currentUser(), PRIVILEGE_ARTICLE_AUDIT)) {
            return articleService.findOne(id).filter(article -> article.getStatus().equals(ArticleStatus.AUDITING)).map(article -> {

                if (bindingResult.hasErrors()) {
                    return builder().setTitle("驳回文章")
                            .setFormView(form)
                            .addDictionaryView(DictionaryView.builder().dictionary("作者", article.getAuthor()).dictionary("摘要", article.getDigest()).dictionary("所属栏目", article.getChannels().stream().map(Channel::getName).collect(Collectors.toList()).toString()).build())
                            .addHtml(article.getHtmlContent())
                            .addNavigation("文章审核", Navigation.Type.NORMAL, "文章管理").build();
                }

                return action("驳回文章", context -> articleService.update(article.getId(), a -> {
                    a.setStatus(ArticleStatus.REJECT);
                    a.setAuditResponse(form.getReason());
                    a.setAuditor(currentUser().getUsername());
                }), (stringObjectMap, modelAndViewBuilder) -> modelAndViewBuilder.setResultNavigation("返回", QXCMP_BACKEND_URL + "/article/audit")).build();
            }).orElse(error(HttpStatus.NOT_FOUND, "文章不存在").build());
        } else {
            return error(HttpStatus.UNAUTHORIZED, "没有文章审核权限").build();
        }
    }


    @GetMapping("/{id}/publish")
    public ModelAndView articlePublish(@PathVariable String id) {

        if (userService.hasRole(currentUser(), PRIVILEGE_ARTICLE_AUDIT)) {
            return articleService.findOne(id).filter(article -> article.getStatus().equals(ArticleStatus.AUDITING)).map(article -> builder().setTitle("发布文章")
                    .setFormView(new AdminArticlePublishForm())
                    .addDictionaryView(DictionaryView.builder().dictionary("作者", article.getAuthor()).dictionary("摘要", article.getDigest()).dictionary("所属栏目", article.getChannels().stream().map(Channel::getName).collect(Collectors.toList()).toString()).build())
                    .addHtml(article.getHtmlContent())
                    .addNavigation("文章审核", Navigation.Type.NORMAL, "文章管理")
                    .build()).orElse(error(HttpStatus.NOT_FOUND, "文章不存在").build());
        } else {
            return error(HttpStatus.UNAUTHORIZED, "没有文章审核权限").build();
        }
    }

    @PostMapping("/{id}/publish")
    public ModelAndView articlePublish(@Valid @ModelAttribute(FORM_OBJECT) final AdminArticlePublishForm form, BindingResult bindingResult, @PathVariable String id) {
        if (userService.hasRole(currentUser(), PRIVILEGE_ARTICLE_AUDIT)) {
            return articleService.findOne(id).filter(article -> article.getStatus().equals(ArticleStatus.AUDITING)).map(article -> {

                if (bindingResult.hasErrors()) {
                    return builder().setTitle("发布文章")
                            .setFormView(form)
                            .addDictionaryView(DictionaryView.builder().dictionary("作者", article.getAuthor()).dictionary("摘要", article.getDigest()).dictionary("所属栏目", article.getChannels().stream().map(Channel::getName).collect(Collectors.toList()).toString()).build())
                            .addHtml(article.getHtmlContent())
                            .addNavigation("文章审核", Navigation.Type.NORMAL, "文章管理").build();
                }

                return action("发布文章", context -> articleService.update(article.getId(), a -> {
                    a.setStatus(ArticleStatus.PUBLISHED);
                    a.setAuditResponse(form.getReason());
                    a.setAuditor(currentUser().getUsername());
                    a.setDatePublished(new Date());
                }), (stringObjectMap, modelAndViewBuilder) -> modelAndViewBuilder.setResultNavigation("返回", QXCMP_BACKEND_URL + "/article/audit")).build();
            }).orElse(error(HttpStatus.NOT_FOUND, "文章不存在").build());
        } else {
            return error(HttpStatus.UNAUTHORIZED, "没有文章审核权限").build();
        }
    }

    @PostMapping("/{id}/disable")
    public ModelAndView articleDisable(@PathVariable String id) {
        if (userService.hasRole(currentUser(), PRIVILEGE_ARTICLE_AUDIT)) {
            return articleService.findOne(id).filter(article -> article.getStatus().equals(ArticleStatus.PUBLISHED)).map(article -> action("禁用文章", context -> articleService.update(article.getId(), a -> a.setStatus(ArticleStatus.DISABLED)), (stringObjectMap, modelAndViewBuilder) -> modelAndViewBuilder.setResultNavigation("返回", QXCMP_BACKEND_URL + "/article/publish")).build()).orElse(error(HttpStatus.NOT_FOUND, "文章不存在").build());
        } else {
            return error(HttpStatus.UNAUTHORIZED, "没有文章审核权限").build();
        }
    }

    @PostMapping("/{id}/enable")
    public ModelAndView articleEnable(@PathVariable String id) {
        if (userService.hasRole(currentUser(), PRIVILEGE_ARTICLE_AUDIT)) {
            return articleService.findOne(id).filter(article -> article.getStatus().equals(ArticleStatus.DISABLED)).map(article -> action("启用文章", context -> articleService.update(article.getId(), a -> a.setStatus(ArticleStatus.PUBLISHED)), (stringObjectMap, modelAndViewBuilder) -> modelAndViewBuilder.setResultNavigation("返回", QXCMP_BACKEND_URL + "/article/disable")).build()).orElse(error(HttpStatus.NOT_FOUND, "文章不存在").build());
        } else {
            return error(HttpStatus.UNAUTHORIZED, "没有文章审核权限").build();
        }
    }

    @GetMapping("/channel")
    public ModelAndView channelTable(Pageable pageable) {
        return builder().setTitle("栏目列表")
                .setTableView(pageable, channelService)
                .build();
    }

    @GetMapping("/channel/new")
    public ModelAndView newChannel() {
        if (userService.hasRole(currentUser(), PRIVILEGE_CHANNEL_CREATE)) {

            final AdminArticleChannelNewForm form = new AdminArticleChannelNewForm();

            form.setOwner(currentUser());

            return builder().setTitle("新建栏目")
                    .setFormView(form, systemConfigService.getList(SYSTEM_CONFIG_ARTICLE_CHANNEL_CATALOG),
                            userService.findByAuthority(PRIVILEGE_SYSTEM_ADMIN), userService.findByAuthority(PRIVILEGE_SYSTEM_ADMIN))
                    .build();
        } else {
            return error(HttpStatus.FORBIDDEN, "抱歉，您没有创建新栏目的权限").build();
        }
    }

    @PostMapping("/channel/new")
    public ModelAndView newChannel(@Valid @ModelAttribute(FORM_OBJECT) final AdminArticleChannelNewForm form, BindingResult bindingResult) {

        if (userService.hasRole(currentUser(), PRIVILEGE_CHANNEL_CREATE)) {
            if (StringUtils.isBlank(form.getName())) {
                bindingResult.rejectValue("name", "", "栏目名称不能为空");
            }

            if (StringUtils.isBlank(form.getAlias())) {
                bindingResult.rejectValue("alias", "", "栏目Url不能为空");
            }

            if (channelService.findByAlias(form.getAlias()).isPresent()) {
                bindingResult.rejectValue("alias", "", "栏目Url已经存在");
            }

            if (bindingResult.hasErrors()) {
                return builder().setTitle("新建栏目")
                        .setFormView(form, systemConfigService.getList(SYSTEM_CONFIG_ARTICLE_CHANNEL_CATALOG),
                                userService.findByAuthority(PRIVILEGE_SYSTEM_ADMIN), userService.findByAuthority(PRIVILEGE_SYSTEM_ADMIN))
                        .build();
            }

            return action("新建栏目", context -> channelService.create(() -> {
                Channel channel = channelService.next();
                channel.setName(form.getName().replaceAll("\\s+", ""));
                channel.setAlias(form.getAlias());
                channel.setOwner(currentUser());
                channel.setAdmins(form.getAdmins());
                channel.setQuillContent(form.getQuillContent());
                channel.setHtmlContent(form.getHtmlContent());
                channel.setDescription(form.getDescription());
                channel.setCatalog(form.getCatalogs());

                if (Objects.nonNull(form.getCoverFile()) && StringUtils.isNotBlank(form.getCoverFile().getOriginalFilename())) {
                    try {
                        String fileType = FilenameUtils.getExtension(form.getCoverFile().getOriginalFilename());
                        imageService.store(form.getCoverFile().getInputStream(), fileType).ifPresent(image -> channel.setCover(String.format("/api/image/%s.%s", image.getId(), image.getType())));
                    } catch (IOException ignored) {

                    }
                }

                userService.update(channel.getOwner().getId(), user -> roleService.findByName("文章编辑").ifPresent(role -> user.getRoles().add(role)));

                channel.getAdmins().forEach(admin -> userService.update(admin.getId(), user -> roleService.findByName("文章编辑").ifPresent(role -> user.getRoles().add(role))));

                return channel;
            }), (stringObjectMap, modelAndViewBuilder) -> modelAndViewBuilder.setResultNavigation("继续新建", "", "返回栏目管理列表", QXCMP_BACKEND_URL + "/article/channel", "添加文章到栏目", QXCMP_BACKEND_URL + "/article/new")).build();
        } else {
            return error(HttpStatus.FORBIDDEN, "抱歉，您没有创建新栏目的权限").build();
        }

    }

    @GetMapping("/channel/{id}/edit")
    public ModelAndView editChannel(@PathVariable String id) {
        return channelService.findOne(id).map(channel -> {

            if (channel.getOwner().equals(currentUser()) || channel.getAdmins().contains(currentUser())) {
                final AdminArticleChannelEditForm form = new AdminArticleChannelEditForm();

                form.setId(channel.getId());
                form.setName(channel.getName());
                form.setCover(channel.getCover());
                form.setAlias(channel.getAlias());
                form.setOwner(channel.getOwner());
                form.setDescription(channel.getDescription());
                form.setAdmins(channel.getAdmins());
                form.setQuillContent(channel.getQuillContent());
                form.setHtmlContent(channel.getHtmlContent());
                form.setCatalogs(channel.getCatalog());

                return builder().setTitle("编辑栏目")
                        .setFormView(form, systemConfigService.getList(SYSTEM_CONFIG_ARTICLE_CHANNEL_CATALOG),
                                userService.findByAuthority(PRIVILEGE_SYSTEM_ADMIN), userService.findByAuthority(PRIVILEGE_SYSTEM_ADMIN))
                        .build();
            } else {
                return error(HttpStatus.FORBIDDEN, "只能编辑自己的栏目").build();
            }

        }).orElse(error(HttpStatus.NOT_FOUND, "栏目不存在").build());
    }

    @GetMapping("/channel/{id}/preview")
    public ModelAndView previewChannel(@PathVariable String id) {
        return channelService.findOne(id).map(channel -> builder().setTitle("预览栏目")
                .addElement(H4, channel.getName())
                .addDivider()
                .addHtml(channel.getHtmlContent())
                .build()).orElse(error(HttpStatus.NOT_FOUND, "栏目不存在").build());
    }

    @PostMapping("/channel")
    public ModelAndView editChannel(@Valid @ModelAttribute(FORM_OBJECT) final AdminArticleChannelEditForm form, BindingResult bindingResult) {

        if (userService.hasRole(currentUser(), PRIVILEGE_CHANNEL_CREATE)) {
            if (StringUtils.isBlank(form.getName())) {
                bindingResult.rejectValue("name", "", "栏目名称不能为空");
            }

            if (StringUtils.isBlank(form.getAlias())) {
                bindingResult.rejectValue("alias", "", "栏目Url不能为空");
            }

            if (channelService.findByAlias(form.getAlias()).isPresent() && !channelService.findByAlias(form.getAlias()).get().getId().equals(form.getId())) {
                bindingResult.rejectValue("alias", "", "栏目Url已经存在");
            }

            if (bindingResult.hasErrors()) {
                return builder().setTitle("编辑栏目")
                        .setFormView(form, systemConfigService.getList(SYSTEM_CONFIG_ARTICLE_CHANNEL_CATALOG),
                                userService.findByAuthority(PRIVILEGE_SYSTEM_ADMIN), userService.findByAuthority(PRIVILEGE_SYSTEM_ADMIN))
                        .build();
            }

            Optional<Channel> channelOptional = channelService.findOne(form.getId());

            if (!channelOptional.isPresent()) {
                return error(HttpStatus.BAD_REQUEST, "栏目不存在").build();
            }

            if (!channelOptional.get().getOwner().equals(currentUser()) && !channelOptional.get().getAdmins().contains(currentUser())) {
                return error(HttpStatus.FORBIDDEN, "只能编辑自己的栏目").build();
            }

            return action("编辑栏目", context -> channelService.update(form.getId(), channel -> {
                channel.setName(form.getName());
                channel.setAlias(form.getAlias());
                channel.setOwner(form.getOwner());
                channel.setAdmins(form.getAdmins());
                channel.setDescription(form.getDescription());
                channel.setQuillContent(form.getQuillContent());
                channel.setHtmlContent(form.getHtmlContent());
                channel.setCatalog(form.getCatalogs());

                if (Objects.nonNull(form.getCoverFile()) && StringUtils.isNotBlank(form.getCoverFile().getOriginalFilename())) {
                    try {
                        String fileType = FilenameUtils.getExtension(form.getCoverFile().getOriginalFilename());
                        imageService.store(form.getCoverFile().getInputStream(), fileType).ifPresent(image -> channel.setCover(String.format("/api/image/%s.%s", image.getId(), image.getType())));
                    } catch (IOException ignored) {

                    }
                }

                userService.update(channel.getOwner().getId(), user -> roleService.findByName("文章编辑").ifPresent(role -> user.getRoles().add(role)));

                channel.getAdmins().forEach(admin -> userService.update(admin.getId(), user -> roleService.findByName("文章编辑").ifPresent(role -> user.getRoles().add(role))));

            })).build();
        } else {
            return error(HttpStatus.FORBIDDEN, "抱歉，您没有创建新栏目的权限").build();
        }

    }
}
