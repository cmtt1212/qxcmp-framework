package com.qxcmp.framework.core;

import com.google.common.collect.ImmutableSet;
import com.qxcmp.framework.web.model.navigation.Navigation;
import com.qxcmp.framework.web.model.navigation.NavigationConfigurator;
import com.qxcmp.framework.web.model.navigation.NavigationService;
import org.springframework.context.annotation.Configuration;

import static com.qxcmp.framework.core.QXCMPConfiguration.QXCMP_BACKEND_URL;
import static com.qxcmp.framework.core.QXCMPSecurityConfiguration.*;

/**
 * 平台导航配置
 *
 * @author Aaric
 */
@Configuration
public class QXCMPNavigationConfiguration implements NavigationConfigurator {

    /*
     * 侧边导航栏
     * */
    public static final String NAVIGATION_ADMIN_SIDEBAR = "ADMIN-SIDEBAR";
    public static final String NAVIGATION_ADMIN_SIDEBAR_USER = NAVIGATION_ADMIN_SIDEBAR + "-USER";
    public static final String NAVIGATION_ADMIN_SIDEBAR_NEWS = NAVIGATION_ADMIN_SIDEBAR + "-NEWS";
    public static final String NAVIGATION_ADMIN_SIDEBAR_MESSAGE = NAVIGATION_ADMIN_SIDEBAR + "-MESSAGE";
    public static final String NAVIGATION_ADMIN_SIDEBAR_WEIXIN = NAVIGATION_ADMIN_SIDEBAR + "-WEIXIN";
    public static final String NAVIGATION_ADMIN_SIDEBAR_FINANCE = NAVIGATION_ADMIN_SIDEBAR + "-FINANCE";
    public static final String NAVIGATION_ADMIN_SIDEBAR_TOOLS = NAVIGATION_ADMIN_SIDEBAR + "-TOOLS";
    public static final String NAVIGATION_ADMIN_SIDEBAR_SETTINGS = NAVIGATION_ADMIN_SIDEBAR + "-SETTINGS";

    /*
     * 个人中心导航栏
     * */
    public static final String NAVIGATION_ADMIN_PROFILE = "ADMIN-PROFILE";
    public static final String NAVIGATION_ADMIN_PROFILE_INFO = NAVIGATION_ADMIN_PROFILE + "-INFO";
    public static final String NAVIGATION_ADMIN_PROFILE_SECURITY = NAVIGATION_ADMIN_PROFILE + "-SECURITY";

    /*
     * 新闻管理导航栏
     * */
    public static final String NAVIGATION_ADMIN_NEWS = "ADMIN-NEWS";
    public static final String NAVIGATION_ADMIN_NEWS_USER_ARTICLE = NAVIGATION_ADMIN_NEWS + "-USER-ARTICLE";
    public static final String NAVIGATION_ADMIN_NEWS_USER_CHANNEL = NAVIGATION_ADMIN_NEWS + "-USER-CHANNEL";
    public static final String NAVIGATION_ADMIN_NEWS_ARTICLE = NAVIGATION_ADMIN_NEWS + "-ARTICLE";
    public static final String NAVIGATION_ADMIN_NEWS_CHANNEL = NAVIGATION_ADMIN_NEWS + "-CHANNEL";

    /*
     * 文章管理导航栏
     * */
    public static final String NAVIGATION_ADMIN_NEWS_ARTICLE_MANAGEMENT = "ADMIN-NEWS-ARTICLE-MANAGEMENT";
    public static final String NAVIGATION_ADMIN_NEWS_ARTICLE_MANAGEMENT_AUDITING = NAVIGATION_ADMIN_NEWS_ARTICLE_MANAGEMENT + "-AUDITING";
    public static final String NAVIGATION_ADMIN_NEWS_ARTICLE_MANAGEMENT_PUBLISHED = NAVIGATION_ADMIN_NEWS_ARTICLE_MANAGEMENT + "-PUBLISHED";
    public static final String NAVIGATION_ADMIN_NEWS_ARTICLE_MANAGEMENT_DISABLED = NAVIGATION_ADMIN_NEWS_ARTICLE_MANAGEMENT + "-DISABLED";

    @Override
    public void configureNavigation(NavigationService navigationService) {
        navigationService.add(new Navigation(NAVIGATION_ADMIN_SIDEBAR, "系统后台侧边导航栏")
                .addItem(new Navigation(NAVIGATION_ADMIN_SIDEBAR_USER, "用户管理", QXCMP_BACKEND_URL + "/user").setOrder(10).setPrivilegesAnd(ImmutableSet.of(PRIVILEGE_USER)))
                .addItem(new Navigation(NAVIGATION_ADMIN_SIDEBAR_NEWS, "新闻管理", QXCMP_BACKEND_URL + "/news").setOrder(20).setPrivilegesAnd(ImmutableSet.of(PRIVILEGE_NEWS)))
                .addItem(new Navigation(NAVIGATION_ADMIN_SIDEBAR_MESSAGE, "消息服务", QXCMP_BACKEND_URL + "/message").setOrder(40).setPrivilegesAnd(ImmutableSet.of(PRIVILEGE_MESSAGE)))
                .addItem(new Navigation(NAVIGATION_ADMIN_SIDEBAR_WEIXIN, "微信公众平台", QXCMP_BACKEND_URL + "/weixin").setOrder(50).setPrivilegesAnd(ImmutableSet.of(PRIVILEGE_WEIXIN)))
                .addItem(new Navigation(NAVIGATION_ADMIN_SIDEBAR_FINANCE, "财务管理", QXCMP_BACKEND_URL + "/finance").setOrder(60).setPrivilegesAnd(ImmutableSet.of(PRIVILEGE_FINANCE)))
                .addItem(new Navigation(NAVIGATION_ADMIN_SIDEBAR_TOOLS, "系统工具", QXCMP_BACKEND_URL + "/tools").setOrder(70).setPrivilegesAnd(ImmutableSet.of(PRIVILEGE_ADMIN_TOOL)))
                .addItem(new Navigation(NAVIGATION_ADMIN_SIDEBAR_SETTINGS, "系统设置", QXCMP_BACKEND_URL + "/settings").setOrder(80).setPrivilegesAnd(ImmutableSet.of(PRIVILEGE_ADMIN_SETTINGS)))
        );

        navigationService.add(new Navigation(NAVIGATION_ADMIN_PROFILE, "系统后台用户中心导航栏")
                .addItem(new Navigation(NAVIGATION_ADMIN_PROFILE_INFO, "基本资料", QXCMP_BACKEND_URL + "/profile/info").setOrder(10).setPrivilegesAnd(ImmutableSet.of(PRIVILEGE_SYSTEM_ADMIN)))
                .addItem(new Navigation(NAVIGATION_ADMIN_PROFILE_SECURITY, "安全设置", QXCMP_BACKEND_URL + "/profile/security").setOrder(20).setPrivilegesAnd(ImmutableSet.of(PRIVILEGE_SYSTEM_ADMIN)))
        );

        navigationService.add(new Navigation(NAVIGATION_ADMIN_NEWS, "系统后台新闻管理页面导航栏")
                .addItem(new Navigation(NAVIGATION_ADMIN_NEWS_USER_ARTICLE, "我的文章", QXCMP_BACKEND_URL + "/news/user/article").setOrder(10).setPrivilegesAnd(ImmutableSet.of(PRIVILEGE_NEWS)))
                .addItem(new Navigation(NAVIGATION_ADMIN_NEWS_USER_CHANNEL, "我的栏目", QXCMP_BACKEND_URL + "/news/user/article").setOrder(10).setPrivilegesAnd(ImmutableSet.of(PRIVILEGE_NEWS)))
                .addItem(new Navigation(NAVIGATION_ADMIN_NEWS_ARTICLE, "文章管理", QXCMP_BACKEND_URL + "/news/user/article").setOrder(10).setPrivilegesOr(ImmutableSet.of(PRIVILEGE_NEWS_ARTICLE_AUDIT, PRIVILEGE_NEWS_ARTICLE_MANAGEMENT)))
                .addItem(new Navigation(NAVIGATION_ADMIN_NEWS_CHANNEL, "栏目管理", QXCMP_BACKEND_URL + "/news/user/article").setOrder(10).setPrivilegesAnd(ImmutableSet.of(PRIVILEGE_NEWS_CHANNEL)))
        );

        navigationService.add(new Navigation(NAVIGATION_ADMIN_NEWS_ARTICLE_MANAGEMENT, "系统后台文章管理页面导航栏")
                .addItem(new Navigation(NAVIGATION_ADMIN_NEWS_ARTICLE_MANAGEMENT_AUDITING, "待审核文章", QXCMP_BACKEND_URL + "/news/article/auditing").setOrder(10).setPrivilegesAnd(ImmutableSet.of(PRIVILEGE_NEWS_ARTICLE_AUDIT)))
                .addItem(new Navigation(NAVIGATION_ADMIN_NEWS_ARTICLE_MANAGEMENT_PUBLISHED, "已发布文章", QXCMP_BACKEND_URL + "/news/article/published").setOrder(20).setPrivilegesAnd(ImmutableSet.of(PRIVILEGE_NEWS_ARTICLE_MANAGEMENT)))
                .addItem(new Navigation(NAVIGATION_ADMIN_NEWS_ARTICLE_MANAGEMENT_DISABLED, "已禁用文章", QXCMP_BACKEND_URL + "/news/article/disabled").setOrder(30).setPrivilegesAnd(ImmutableSet.of(PRIVILEGE_NEWS_ARTICLE_MANAGEMENT)))
        );
    }
}
