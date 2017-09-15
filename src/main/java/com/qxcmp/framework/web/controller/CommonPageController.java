package com.qxcmp.framework.web.controller;

import com.jcabi.manifests.Manifests;
import com.qxcmp.framework.core.QXCMPConfiguration;
import com.qxcmp.framework.user.User;
import com.qxcmp.framework.web.QXCMPBackendController2;
import com.qxcmp.framework.web.event.HomePageExtensionEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

import java.text.SimpleDateFormat;
import java.util.Date;

import static com.qxcmp.framework.account.AccountService.ACCOUNT_PAGE;
import static com.qxcmp.framework.core.QXCMPConfiguration.QXCMP_BACKEND_URL;

/**
 * 平台通用页面路由
 * <p>
 * 包含以下通用页面
 * <p>
 * <ol> <li>后端首页</li> <li>后端关于我们页面</li> <li>平台登录页面</li> </ol>
 *
 * @author aaric
 */
@Controller
@RequiredArgsConstructor
public class CommonPageController extends QXCMPBackendController2 {

    private final ApplicationEventPublisher eventPublisher;

    /**
     * 获取平台首页
     * <p>
     * 该视图包含了名为 {@code dictionary} 的数据字典
     * <p>
     * 每次访问首页都会发送 {@link HomePageExtensionEvent} 事件以动态扩展首页内容
     *
     * @return 视图 {@code qxcmp-dictionary}
     */
    public ModelAndView home() {
        return builder().addDictionaryView(dictionaryViewBuilder -> {
            User user = currentUser();

            dictionaryViewBuilder.title("欢迎登陆" + siteService.getTitle());

            dictionaryViewBuilder.dictionary("用户名", user.getUsername());
            dictionaryViewBuilder.dictionary("绑定邮箱", user.getEmail());
            dictionaryViewBuilder.dictionary("绑定手机", user.getPhone());
            dictionaryViewBuilder.dictionary("上次登陆", new SimpleDateFormat("yyyy-MM-dd HH:mm").format(user.getDateLogin()));

            eventPublisher.publishEvent(new HomePageExtensionEvent(dictionaryViewBuilder));
        }).build();
    }

    /**
     * 关于页面
     * <p>
     * 该视图包含了平台构建和启动信息，并且以数据字典的形式保存
     *
     * @return 视图 {@code qxcmp-dictionary}
     */
    @GetMapping(QXCMP_BACKEND_URL + "/about")
    public ModelAndView about() {
        return builder().addDictionaryView(dictionaryViewBuilder -> {

            String appVersion = QXCMPConfiguration.class.getPackage().getImplementationVersion();
            String addBuildDate = "development";

            try {
                addBuildDate = Manifests.read("Build-Date");
            } catch (Exception ignored) {

            }

            dictionaryViewBuilder.title("清醒内容管理平台");
            dictionaryViewBuilder.dictionary("平台版本", appVersion != null ? appVersion : "development");
            dictionaryViewBuilder.dictionary("构建日期", addBuildDate);
            dictionaryViewBuilder.dictionary("启动日期", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date(applicationContext.getStartupDate())));
        }).build();
    }

    /**
     * 登录页面
     *
     * @return 前端登录页面
     */
    public ModelAndView frontendLogin() {
        return builder(ACCOUNT_PAGE)
                .setResult(siteService.getTitle(), "", "")
                .addFragment("qxcmp/account", "loginForm")
                .addFragment("qxcmp/account", "loginLink")
                .build();
    }
}
