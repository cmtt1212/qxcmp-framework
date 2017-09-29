package com.qxcmp.framework.core;

import com.qxcmp.framework.web.model.navigation.Navigation;
import com.qxcmp.framework.web.model.navigation.NavigationConfigurator;
import com.qxcmp.framework.web.model.navigation.NavigationService;
import org.springframework.context.annotation.Configuration;

import static com.qxcmp.framework.core.QXCMPConfiguration.QXCMP_BACKEND_URL;

/**
 * 平台导航配置
 *
 * @author Aaric
 */
@Configuration
public class QXCMPNavigationConfiguration implements NavigationConfigurator {

    public static final String NAVIGATION_QXCMP_ADMIN_SIDEBAR = "QXCMP-ADMIN-SIDEBAR";
    public static final String NAVIGATION_QXCMP_ADMIN_ACCOUNT = "QXCMP-ADMIN-ACCOUNT";

    @Override
    public void configureNavigation(NavigationService navigationService) {
        navigationService.add(new Navigation(NAVIGATION_QXCMP_ADMIN_SIDEBAR, "系统后台侧边导航栏")
                .addItem(new Navigation(NAVIGATION_QXCMP_ADMIN_SIDEBAR + "-USER", "用户管理", QXCMP_BACKEND_URL + "/user")).setOrder(10)
                .addItem(new Navigation(NAVIGATION_QXCMP_ADMIN_SIDEBAR + "-NEWS", "新闻管理", QXCMP_BACKEND_URL + "/news")).setOrder(20)
                .addItem(new Navigation(NAVIGATION_QXCMP_ADMIN_SIDEBAR + "-MALL", "商城管理", QXCMP_BACKEND_URL + "/mall")).setOrder(30)
                .addItem(new Navigation(NAVIGATION_QXCMP_ADMIN_SIDEBAR + "-MESSAGE-SERVICE", "消息服务", QXCMP_BACKEND_URL + "/message").setOrder(40))
                .addItem(new Navigation(NAVIGATION_QXCMP_ADMIN_SIDEBAR + "-WEIXIN", "微信公众平台", QXCMP_BACKEND_URL + "/weixin").setOrder(50))
                .addItem(new Navigation(NAVIGATION_QXCMP_ADMIN_SIDEBAR + "-FINANCE", "财务管理", QXCMP_BACKEND_URL + "/finance").setOrder(60))
                .addItem(new Navigation(NAVIGATION_QXCMP_ADMIN_SIDEBAR + "-TOOLS", "系统工具", QXCMP_BACKEND_URL + "/tools").setOrder(70))
                .addItem(new Navigation(NAVIGATION_QXCMP_ADMIN_SIDEBAR + "-SETTINGS", "系统设置", QXCMP_BACKEND_URL + "/settings").setOrder(80))
        );

        navigationService.add(new Navigation(NAVIGATION_QXCMP_ADMIN_ACCOUNT, "系统后台用户中心导航栏")
                .addItem(new Navigation(NAVIGATION_QXCMP_ADMIN_ACCOUNT + "-INFO", "基本资料", QXCMP_BACKEND_URL + "/profile/info").setOrder(10))
                .addItem(new Navigation(NAVIGATION_QXCMP_ADMIN_ACCOUNT + "-SECURITY", "安全设置", QXCMP_BACKEND_URL + "/profile/security").setOrder(20))
        );
    }
}
