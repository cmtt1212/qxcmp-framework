package com.qxcmp.framework.web.controller;

import com.google.gson.GsonBuilder;
import com.qxcmp.framework.audit.ActionException;
import com.qxcmp.framework.core.QXCMPSystemConfigConfiguration;
import com.qxcmp.framework.domain.Channel;
import com.qxcmp.framework.domain.ChannelService;
import com.qxcmp.framework.domain.WechatMpNewsArticleService;
import com.qxcmp.framework.view.list.ListViewItem;
import com.qxcmp.framework.view.nav.Navigation;
import com.qxcmp.framework.web.QXCMPBackendController2;
import com.qxcmp.framework.web.form.AdminArticleNewForm;
import com.qxcmp.framework.web.form.AdminWeixinSettingsConfigForm;
import com.qxcmp.framework.web.form.AdminWeixinSettingsMenuForm;
import com.qxcmp.framework.weixin.WechatService;
import lombok.RequiredArgsConstructor;
import me.chanjar.weixin.common.api.WxConsts;
import me.chanjar.weixin.common.exception.WxErrorException;
import me.chanjar.weixin.mp.api.WxMpConfigStorage;
import me.chanjar.weixin.mp.api.WxMpInMemoryConfigStorage;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.bean.menu.WxMpMenu;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Objects;

import static com.qxcmp.framework.core.QXCMPConfiguration.*;
import static com.qxcmp.framework.view.component.ElementType.H4;
import static com.qxcmp.framework.view.component.ElementType.P;

/**
 * 微信公众平台页面路由
 *
 * @author aaric
 */
@RequestMapping(QXCMP_BACKEND_URL + "/weixin")
@RequiredArgsConstructor
public class WechatMpModuleController extends QXCMPBackendController2 {

    private final WxMpService wxMpService;

    private final WxMpConfigStorage wxMpConfigStorage;

    private final WechatService wechatService;

    private final WechatMpNewsArticleService wechatMpNewsArticleService;

    private final ChannelService channelService;

    @GetMapping("/user")
    public ModelAndView userTable(Pageable pageable) {
        return builder().setTableView("wechat", pageable, userService).build();
    }

    @PostMapping("/user/sync")
    public ModelAndView userSync() {
        return action("微信公众号用户同步", context -> wechatService.doSync(), (context, modelAndViewBuilder) -> modelAndViewBuilder.setResultNavigation("返回用户列表", QXCMP_BACKEND_URL + "/weixin/user")).build();
    }

    @GetMapping("/material")
    public ModelAndView material() {
        return builder().setTitle("公众号素材管理")
                .addElement(H4, "公众号素材管理")
                .addNavigation(Navigation.Type.NORMAL, "公众号素材管理")
                .build();
    }

    @GetMapping("/material/article")
    public ModelAndView materialArticle(Pageable pageable) {
        return builder().setTitle("图文管理")
                .addElement(H4, "图文管理")
                .addDivider()
                .setTableView(new PageRequest(pageable.getPageNumber(), pageable.getPageSize(), Sort.Direction.DESC, "id"), wechatMpNewsArticleService)
                .addNavigation("图文管理", Navigation.Type.NORMAL, "公众号素材管理")
                .build();
    }

    @GetMapping("/material/article/{id}")
    public ModelAndView materialArticle(@PathVariable String id) {

        return wechatMpNewsArticleService.findOne(id).map(article -> builder().setTitle(article.getTitle(), "图文详情")
                .setResult(article.getTitle(), article.getAuthor(), article.getDigest())
                .addDivider()
                .addElement(P, article.getContent())
                .setResultNavigation("返回图文列表", QXCMP_BACKEND_URL + "/weixin/material/article")
                .build()).orElse(error(HttpStatus.NOT_FOUND, "图文素材不存在").build());
    }

    @GetMapping("/material/article/{id}/publish")
    public ModelAndView materialArticlePublish(@PathVariable String id) {
        return wechatMpNewsArticleService.findOne(id).map(article -> {

            List<Channel> channels = channelService.findByUserId(currentUser());

            if (channels.isEmpty()) {
                return error(HttpStatus.UNAUTHORIZED, "您还没有管理任何栏目，暂时不能创建文章").addNavigation("我的文章", Navigation.Type.NORMAL, "文章管理").build();
            }

            final AdminArticleNewForm form = new AdminArticleNewForm();

            form.setCover(article.getThumbUrl());
            form.setAuthor(article.getAuthor());
            form.setTitle(article.getTitle());
            form.setHtmlContent(article.getContent());
            form.setDigest(article.getDigest());

            return builder().setTitle("发布微信图文到网站")
                    .setFormView(form, channels)
                    .addNavigation("新建文章", Navigation.Type.NORMAL, "文章管理")
                    .build();

        }).orElse(error(HttpStatus.NOT_FOUND, "图文素材不存在").build());
    }

    @GetMapping("/material/image")
    public ModelAndView materialImage() {

        if (wechatService.getImages().isEmpty()) {
            return builder().setTitle("图片管理")
                    .addElement(H4, "图片管理")
                    .addDivider()
                    .addElement(P, "暂无素材")
                    .addElement(P, "请通过微信公众平台上传素材")
                    .addNavigation("图片管理", Navigation.Type.NORMAL, "公众号素材管理")
                    .build();
        }

        return builder().setTitle("图片管理")
                .addElement(H4, "图片管理")
                .addDivider()
                .addListView(listViewBuilder -> wechatService.getImages().forEach(wxMaterialFileBatchGetNewsItem -> listViewBuilder.item(ListViewItem.builder()
                        .title(wxMaterialFileBatchGetNewsItem.getName())
                        .description(new SimpleDateFormat("更新于：yyyy-MM-dd HH:mm:ss").format(wxMaterialFileBatchGetNewsItem.getUpdateTime()))
                        .image(wxMaterialFileBatchGetNewsItem.getUrl())
                        .build())))
                .addNavigation("图片管理", Navigation.Type.NORMAL, "公众号素材管理")
                .build();
    }

    @GetMapping("/material/video")
    public ModelAndView materialVideo() {

        if (wechatService.getVideos().isEmpty()) {
            return builder().setTitle("视频管理")
                    .addElement(H4, "视频管理")
                    .addDivider()
                    .addElement(P, "暂无素材")
                    .addElement(P, "请通过微信公众平台上传素材")
                    .addNavigation("视频管理", Navigation.Type.NORMAL, "公众号素材管理")
                    .build();
        }

        return builder().setTitle("视频管理")
                .addElement(H4, "视频管理")
                .addDivider()
                .addListView(listViewBuilder -> wechatService.getVideos().forEach(wxMaterialFileBatchGetNewsItem -> listViewBuilder.item(ListViewItem.builder()
                        .title(wxMaterialFileBatchGetNewsItem.getName())
                        .description(new SimpleDateFormat("更新于：yyyy-MM-dd HH:mm:ss").format(wxMaterialFileBatchGetNewsItem.getUpdateTime()))
                        .build())))
                .addNavigation("视频管理", Navigation.Type.NORMAL, "公众号素材管理")
                .build();
    }

    @GetMapping("/material/voice")
    public ModelAndView materialVoice() {

        if (wechatService.getVoices().isEmpty()) {
            return builder().setTitle("语音管理")
                    .addElement(H4, "语音管理")
                    .addDivider()
                    .addElement(P, "暂无素材")
                    .addElement(P, "请通过微信公众平台上传素材")
                    .addNavigation("语音管理", Navigation.Type.NORMAL, "公众号素材管理")
                    .build();
        }

        return builder().setTitle("语音管理")
                .addElement(H4, "语音管理")
                .addDivider()
                .addListView(listViewBuilder -> wechatService.getVoices().forEach(wxMaterialFileBatchGetNewsItem -> listViewBuilder.item(ListViewItem.builder()
                        .title(wxMaterialFileBatchGetNewsItem.getName())
                        .description(new SimpleDateFormat("更新于：yyyy-MM-dd HH:mm:ss").format(wxMaterialFileBatchGetNewsItem.getUpdateTime()))
                        .build())))
                .addNavigation("语音管理", Navigation.Type.NORMAL, "公众号素材管理")
                .build();
    }

    @GetMapping("/settings")
    public ModelAndView settings() {
        return builder().setTitle("公众号设置")
                .addElement(H4, "公众号设置")
                .addNavigation(Navigation.Type.NORMAL, "公众号设置")
                .build();
    }

    @GetMapping("/settings/config")
    public ModelAndView serviceGet(final AdminWeixinSettingsConfigForm form) {
        form.setAppId(systemConfigService.getString(QXCMPSystemConfigConfiguration.SYSTEM_CONFIG_WECHAT_APP_ID).orElse(""));
        form.setSecret(systemConfigService.getString(QXCMPSystemConfigConfiguration.SYSTEM_CONFIG_WECHAT_SECRET).orElse(""));
        form.setToken(systemConfigService.getString(QXCMPSystemConfigConfiguration.SYSTEM_CONFIG_WECHAT_TOKEN).orElse(""));
        form.setAesKey(systemConfigService.getString(QXCMPSystemConfigConfiguration.SYSTEM_CONFIG_WECHAT_AES_KEY).orElse(""));
        form.setOauth2Url(systemConfigService.getString(QXCMPSystemConfigConfiguration.SYSTEM_CONFIG_WECHAT_OAUTH2_CALLBACK_URL).orElse(""));
        form.setSubscribeMessage(systemConfigService.getString(QXCMPSystemConfigConfiguration.SYSTEM_CONFIG_WECHAT_SUBSCRIBE_WELCOME_MESSAGE).orElse(""));
        form.setDebug(systemConfigService.getBoolean(QXCMPSystemConfigConfiguration.SYSTEM_CONFIG_WECHAT_DEBUG).orElse(false));
        return builder().setTitle("公众号设置")
                .setFormView(form)
                .addNavigation("公众号参数", Navigation.Type.NORMAL, "公众号设置")
                .build();
    }

    @PostMapping("/settings/config")
    public ModelAndView servicePost(@Valid @ModelAttribute(FORM_OBJECT) AdminWeixinSettingsConfigForm form, BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            return builder().setFormView(form).build();
        }

        return action("修改微信公众号配置", context -> {
            systemConfigService.update(QXCMPSystemConfigConfiguration.SYSTEM_CONFIG_WECHAT_APP_ID, form.getAppId());
            systemConfigService.update(QXCMPSystemConfigConfiguration.SYSTEM_CONFIG_WECHAT_SECRET, form.getSecret());
            systemConfigService.update(QXCMPSystemConfigConfiguration.SYSTEM_CONFIG_WECHAT_TOKEN, form.getToken());
            systemConfigService.update(QXCMPSystemConfigConfiguration.SYSTEM_CONFIG_WECHAT_AES_KEY, form.getAesKey());
            systemConfigService.update(QXCMPSystemConfigConfiguration.SYSTEM_CONFIG_WECHAT_OAUTH2_CALLBACK_URL, form.getOauth2Url());
            systemConfigService.update(QXCMPSystemConfigConfiguration.SYSTEM_CONFIG_WECHAT_SUBSCRIBE_WELCOME_MESSAGE, form.getSubscribeMessage());
            systemConfigService.update(QXCMPSystemConfigConfiguration.SYSTEM_CONFIG_WECHAT_DEBUG, String.valueOf(form.isDebug()));

            WxMpInMemoryConfigStorage configStorage = (WxMpInMemoryConfigStorage) wxMpConfigStorage;
            configStorage.setAppId(form.getAppId());
            configStorage.setSecret(form.getSecret());
            configStorage.setToken(form.getToken());
            configStorage.setAesKey(form.getAesKey());

            if (StringUtils.isNotBlank(form.getOauth2Url())) {
                try {
                    String oauth2Url = wxMpService.oauth2buildAuthorizationUrl(form.getOauth2Url(), WxConsts.OAUTH2_SCOPE_USER_INFO, null);
                    context.put("oauth2Url", oauth2Url);
                    systemConfigService.update(QXCMPSystemConfigConfiguration.SYSTEM_CONFIG_WECHAT_OAUTH2_AUTHORIZATION_URL, oauth2Url);
                } catch (Exception e) {
                    throw new ActionException("Can't build Oauth2 Url", e);
                }
            }

        }, (context, modelAndViewBuilder) -> {
            Object oauth2Url = context.get("oauth2Url");

            if (Objects.nonNull(oauth2Url)) {
                modelAndViewBuilder.addDictionaryView(dictionaryViewBuilder -> dictionaryViewBuilder.dictionary("网页授权链接", oauth2Url.toString()));
            }

        }).build();
    }

    @GetMapping("/settings/menu")
    public ModelAndView menuGet(final AdminWeixinSettingsMenuForm form) throws WxErrorException {
        WxMpMenu wxMpMenu = wxMpService.getMenuService().menuGet();
        if (Objects.nonNull(wxMpMenu)) {
            form.setContent(new GsonBuilder().setPrettyPrinting().create().toJson(wxMpService.getMenuService().menuGet().getMenu()));
        } else {
            form.setContent("当前菜单为空");
        }
        return builder().setTitle("自定义菜单")
                .setFormView(form)
                .addNavigation("自定义菜单", Navigation.Type.NORMAL, "公众号设置")
                .build();
    }

    @PostMapping("/settings/menu")
    public ModelAndView menuPost(@Valid @ModelAttribute("object") AdminWeixinSettingsMenuForm form, BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            return builder().setFormView(form).build();
        }

        return action("修改微信公众号自定义菜单", context -> {
            try {
                wxMpService.getMenuService().menuCreate(form.getContent());
            } catch (Exception e) {
                throw new ActionException(String.format("创建自定义菜单失败\n原因：%s", e.getMessage()));
            }
        }).build();
    }
}
