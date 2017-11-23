package com.qxcmp.framework.weixin.web;

import com.google.gson.GsonBuilder;
import com.qxcmp.framework.audit.ActionException;
import com.qxcmp.framework.core.QXCMPSystemConfigConfiguration;
import com.qxcmp.framework.news.Article;
import com.qxcmp.framework.news.ArticleService;
import com.qxcmp.framework.news.ArticleStatus;
import com.qxcmp.framework.web.QxcmpController;
import com.qxcmp.framework.web.model.RestfulResponse;
import com.qxcmp.framework.web.view.elements.button.Button;
import com.qxcmp.framework.web.view.elements.grid.Col;
import com.qxcmp.framework.web.view.elements.grid.Grid;
import com.qxcmp.framework.web.view.elements.grid.Row;
import com.qxcmp.framework.web.view.elements.header.IconHeader;
import com.qxcmp.framework.web.view.elements.html.HtmlText;
import com.qxcmp.framework.web.view.elements.html.P;
import com.qxcmp.framework.web.view.elements.icon.Icon;
import com.qxcmp.framework.web.view.elements.message.InfoMessage;
import com.qxcmp.framework.web.view.elements.segment.Segment;
import com.qxcmp.framework.web.view.support.Wide;
import com.qxcmp.framework.web.view.views.Overview;
import com.qxcmp.framework.weixin.WeixinMpMaterialService;
import com.qxcmp.framework.weixin.WeixinMpMaterialType;
import com.qxcmp.framework.weixin.WeixinService;
import lombok.RequiredArgsConstructor;
import me.chanjar.weixin.common.api.WxConsts;
import me.chanjar.weixin.mp.api.WxMpConfigStorage;
import me.chanjar.weixin.mp.api.WxMpInMemoryConfigStorage;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.bean.menu.WxMpMenu;
import org.apache.commons.lang3.StringUtils;
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

import javax.validation.Valid;
import java.util.Objects;

import static com.qxcmp.framework.core.QXCMPConfiguration.QXCMP_BACKEND_URL;
import static com.qxcmp.framework.core.QXCMPNavigationConfiguration.*;
import static com.qxcmp.framework.core.QXCMPSystemConfigConfiguration.*;

/**
 * @author Aaric
 */
@Controller
@RequestMapping(QXCMP_BACKEND_URL + "/weixin")
@RequiredArgsConstructor
public class AdminWeixinPageController extends QxcmpController {

    private final WxMpService wxMpService;
    private final WxMpConfigStorage wxMpConfigStorage;
    private final WeixinMpMaterialService weixinMpMaterialService;
    private final WeixinService weixinService;
    private final ArticleService articleService;

    @GetMapping("")
    public ModelAndView weixinPage() {
        return page().addComponent(new Overview("微信公众平台")
                .addComponent(convertToTable(stringObjectMap -> {
                    stringObjectMap.put("App ID", systemConfigService.getString(SYSTEM_CONFIG_WECHAT_APP_ID).orElse(""));
                    stringObjectMap.put("App Secret", systemConfigService.getString(SYSTEM_CONFIG_WECHAT_SECRET).orElse(""));
                    stringObjectMap.put("Token", systemConfigService.getString(SYSTEM_CONFIG_WECHAT_TOKEN).orElse(""));
                    stringObjectMap.put("AES Key", systemConfigService.getString(SYSTEM_CONFIG_WECHAT_AES_KEY).orElse(""));
                    stringObjectMap.put("授权回调链接", systemConfigService.getString(SYSTEM_CONFIG_WECHAT_OAUTH2_CALLBACK_URL).orElse(""));
                    stringObjectMap.put("网页授权链接", systemConfigService.getString(SYSTEM_CONFIG_WECHAT_OAUTH2_AUTHORIZATION_URL).orElse(""));
                    stringObjectMap.put("调试模式", systemConfigService.getString(SYSTEM_CONFIG_WECHAT_DEBUG).orElse(""));
                    stringObjectMap.put("欢迎语", systemConfigService.getString(SYSTEM_CONFIG_WECHAT_SUBSCRIBE_WELCOME_MESSAGE).orElse(""));
                })))
                .setBreadcrumb("控制台", "", "微信公众平台")
                .setVerticalNavigation(NAVIGATION_ADMIN_WEIXIN, "")
                .build();
    }

    @GetMapping("/material")
    public ModelAndView materialPage(Pageable pageable) {

        Grid grid = new Grid();
        Col col = new Col(Wide.SIXTEEN);

        if (weixinService.isWeixinMaterialSync()) {
            col.addComponent(new InfoMessage("微信用户正在同步中，请稍后刷新查看").setCloseable());
        }

        col.addComponent(convertToTable(new PageRequest(pageable.getPageNumber(), pageable.getPageSize(), new Sort("type")), weixinMpMaterialService));

        return page().addComponent(grid.addItem(new Row().addCol(col)))
                .setBreadcrumb("控制台", "", "微信公众平台", "weixin", "素材管理")
                .setVerticalNavigation(NAVIGATION_ADMIN_WEIXIN, NAVIGATION_ADMIN_WEIXIN_MATERIAL)
                .build();
    }

    @PostMapping("/material/sync")
    public ResponseEntity<RestfulResponse> userWeixinSyncPage() {
        weixinService.doWeixinMaterialSync();
        return ResponseEntity.ok(new RestfulResponse(HttpStatus.OK.value()));
    }

    @GetMapping("/material/{id}/preview")
    public ModelAndView materialPreviewPage(@PathVariable String id) {
        return weixinMpMaterialService.findOne(id)
                .filter(weixinMpMaterial -> weixinMpMaterial.getType().equals(WeixinMpMaterialType.NEWS))
                .map(weixinMpMaterial -> page()
                        .addComponent(new Segment().addComponent(new Button("转换为文章", String.format(QXCMP_BACKEND_URL + "/weixin/material/%s/convert", id)).setPrimary())
                                .addComponent(new Overview(weixinMpMaterial.getTitle(), weixinMpMaterial.getAuthor()).addComponent(new HtmlText(weixinMpMaterial.getContent()))))
                        .setBreadcrumb("控制台", "", "微信公众平台", "weixin", "素材管理", "weixin/material", "图文查看")
                        .setVerticalNavigation(NAVIGATION_ADMIN_WEIXIN, NAVIGATION_ADMIN_WEIXIN_MATERIAL))
                .orElse(page(viewHelper.nextWarningOverview("素材不存在或者不为图文素材", "目前仅支持图文素材的查看"))).build();
    }

    @GetMapping("/material/{id}/convert")
    public ModelAndView materialConvertPage(@PathVariable String id) {
        return weixinMpMaterialService.findOne(id)
                .filter(weixinMpMaterial -> weixinMpMaterial.getType().equals(WeixinMpMaterialType.NEWS))
                .map(weixinMpMaterial -> {

                    Article next = articleService.next();
                    next.setUserId(currentUser().orElseThrow(RuntimeException::new).getId());
                    next.setTitle(weixinMpMaterial.getTitle());
                    next.setAuthor(weixinMpMaterial.getAuthor());
                    next.setDigest(weixinMpMaterial.getDigest());
                    next.setContent(weixinMpMaterial.getContent());
                    next.setCover(weixinMpMaterial.getThumbUrl());
                    next.setStatus(ArticleStatus.NEW);

                    return articleService.create(() -> next).map(article -> page(viewHelper.nextSuccessOverview("转换文章成功", "现在可以前往新闻中心发布该文章")
                            .addLink("返回素材管理", QXCMP_BACKEND_URL + "/weixin/material")
                            .addLink("我的文章", QXCMP_BACKEND_URL + "/news/user/article/draft")
                            .addLink("申请发布该文章", QXCMP_BACKEND_URL + "/news/user/article/" + article.getId() + "/audit")
                    )).orElse(page(viewHelper.nextWarningOverview("转换文章失败", "").addLink("返回", String.format(QXCMP_BACKEND_URL + "/weixin/material/%s/preview", id))));
                })
                .orElse(page(viewHelper.nextWarningOverview("素材不存在或者不为图文素材", "目前仅支持图文素材的查看"))).build();
    }

    @GetMapping("/settings")
    public ModelAndView weixinMpPage(final AdminWeixinSettingsForm form) {

        form.setDebug(systemConfigService.getBoolean(QXCMPSystemConfigConfiguration.SYSTEM_CONFIG_WECHAT_DEBUG).orElse(false));
        form.setAppId(systemConfigService.getString(QXCMPSystemConfigConfiguration.SYSTEM_CONFIG_WECHAT_APP_ID).orElse(""));
        form.setSecret(systemConfigService.getString(QXCMPSystemConfigConfiguration.SYSTEM_CONFIG_WECHAT_SECRET).orElse(""));
        form.setToken(systemConfigService.getString(QXCMPSystemConfigConfiguration.SYSTEM_CONFIG_WECHAT_TOKEN).orElse(""));
        form.setAesKey(systemConfigService.getString(QXCMPSystemConfigConfiguration.SYSTEM_CONFIG_WECHAT_AES_KEY).orElse(""));
        form.setOauth2Url(systemConfigService.getString(QXCMPSystemConfigConfiguration.SYSTEM_CONFIG_WECHAT_OAUTH2_CALLBACK_URL).orElse(""));
        form.setSubscribeMessage(systemConfigService.getString(QXCMPSystemConfigConfiguration.SYSTEM_CONFIG_WECHAT_SUBSCRIBE_WELCOME_MESSAGE).orElse(""));

        return page()
                .addComponent(new Segment().addComponent(convertToForm(form)))
                .setBreadcrumb("控制台", "", "微信公众平台", "weixin", "公众号配置")
                .setVerticalNavigation(NAVIGATION_ADMIN_WEIXIN, NAVIGATION_ADMIN_WEIXIN_SETTINGS)
                .build();
    }

    @PostMapping("/settings")
    public ModelAndView weixinMpPage(@Valid final AdminWeixinSettingsForm form, BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            return page()
                    .addComponent(new Segment().addComponent(convertToForm(form).setErrorMessage(convertToErrorMessage(bindingResult, form))))
                    .setBreadcrumb("控制台", "", "微信公众平台", "weixin", "公众号配置")
                    .setVerticalNavigation(NAVIGATION_ADMIN_WEIXIN, NAVIGATION_ADMIN_WEIXIN_SETTINGS)
                    .build();
        }

        return submitForm(form, context -> {

            systemConfigService.update(QXCMPSystemConfigConfiguration.SYSTEM_CONFIG_WECHAT_DEBUG, String.valueOf(form.isDebug()));
            systemConfigService.update(QXCMPSystemConfigConfiguration.SYSTEM_CONFIG_WECHAT_APP_ID, form.getAppId());
            systemConfigService.update(QXCMPSystemConfigConfiguration.SYSTEM_CONFIG_WECHAT_SECRET, form.getSecret());
            systemConfigService.update(QXCMPSystemConfigConfiguration.SYSTEM_CONFIG_WECHAT_TOKEN, form.getToken());
            systemConfigService.update(QXCMPSystemConfigConfiguration.SYSTEM_CONFIG_WECHAT_AES_KEY, form.getAesKey());
            systemConfigService.update(QXCMPSystemConfigConfiguration.SYSTEM_CONFIG_WECHAT_OAUTH2_CALLBACK_URL, form.getOauth2Url());
            systemConfigService.update(QXCMPSystemConfigConfiguration.SYSTEM_CONFIG_WECHAT_SUBSCRIBE_WELCOME_MESSAGE, form.getSubscribeMessage());

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
        }, (stringObjectMap, overview) -> overview.addComponent(convertToTable(map -> map.put("网页授权链接", stringObjectMap.get("oauth2Url")))));
    }

    @GetMapping("/menu")
    public ModelAndView weixinMenuPage(final AdminWeixinMenuForm form) {
        try {
            WxMpMenu wxMpMenu = wxMpService.getMenuService().menuGet();
            if (Objects.nonNull(wxMpMenu)) {
                form.setContent(new GsonBuilder().setPrettyPrinting().create().toJson(wxMpService.getMenuService().menuGet().getMenu()));
            } else {
                form.setContent("当前公众号还未设置自定义菜单");
            }
            return page()
                    .addComponent(new Segment().addComponent(convertToForm(form)))
                    .setBreadcrumb("控制台", "", "微信公众平台", "weixin", "公众号菜单")
                    .setVerticalNavigation(NAVIGATION_ADMIN_WEIXIN, NAVIGATION_ADMIN_WEIXIN_MENU)
                    .build();
        } catch (Exception e) {
            return page(new Overview(new IconHeader("无法获取公众号菜单", new Icon("warning circle")))
                    .addComponent(new P(e.getMessage()))
                    .addLink("返回", QXCMP_BACKEND_URL + "/weixin")).build();
        }
    }

    @PostMapping("/menu")
    public ModelAndView weixinMenuPage(@Valid final AdminWeixinMenuForm form, BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            return page()
                    .addComponent(new Segment().addComponent(convertToForm(form).setErrorMessage(convertToErrorMessage(bindingResult, form))))
                    .setBreadcrumb("控制台", "", "微信公众平台", "weixin", "公众号菜单")
                    .setVerticalNavigation(NAVIGATION_ADMIN_WEIXIN, NAVIGATION_ADMIN_WEIXIN_MENU)
                    .build();
        }

        return submitForm(form, context -> {
            try {
                wxMpService.getMenuService().menuCreate(form.getContent());
            } catch (Exception e) {
                throw new ActionException(e.getMessage(), e);
            }
        });
    }
}
