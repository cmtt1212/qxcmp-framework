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

    public static final String NAVIGATION_QXCMP_ADMIN_SIDEBAR = "QXCMP-ADMIN-SIDEBAR";
    public static final String NAVIGATION_QXCMP_ADMIN_ACCOUNT = "QXCMP-ADMIN-ACCOUNT";

    @Override
    public void configureNavigation(NavigationService navigationService) {
        navigationService.add(new Navigation(NAVIGATION_QXCMP_ADMIN_SIDEBAR, "系统后台侧边导航栏")
                .addItem(new Navigation("QXCMP-ADMIN-SIDEBAR-USER", "用户管理", QXCMP_BACKEND_URL + "/user")).setOrder(10)
                .addItem(new Navigation("QXCMP-ADMIN-SIDEBAR-MESSAGE-SERVICE", "消息服务", QXCMP_BACKEND_URL + "/message").setOrder(20))
                .addItem(new Navigation("QXCMP-ADMIN-SIDEBAR-SECURITY", "安全配置", QXCMP_BACKEND_URL + "/security").setOrder(30))
                .addItem(new Navigation("QXCMP-ADMIN-SIDEBAR-SYSTEM-SETTINGS", "系统设置", QXCMP_BACKEND_URL + "/settings").setOrder(40))
                .addItem(new Navigation("QXCMP-ADMIN-SIDEBAR-SYSTEM-TOOL", "系统工具").setOrder(50)
                        .addItem(new Navigation("QXCMP-ADMIN-SIDEBAR-TOOL-REDEEM", "兑换码", QXCMP_BACKEND_URL + "/redeem").setOrder(10).setPrivilegesAnd(ImmutableSet.of(PRIVILEGE_REDEEM_MANAGEMENT)))
                        .addItem(new Navigation("QXCMP-ADMIN-SIDEBAR-TOOL-SPIDER", "蜘蛛管理", QXCMP_BACKEND_URL + "/spider").setOrder(50).setPrivilegesAnd(ImmutableSet.of(PRIVILEGE_SPIDER_MANAGEMENT)))
                        .addItem(new Navigation("QXCMP-ADMIN-SIDEBAR-TOOL-LOG", "系统日志", QXCMP_BACKEND_URL + "/log").setOrder(100).setPrivilegesAnd(ImmutableSet.of(PRIVILEGE_LOG_MANAGEMENT)))
                ));

        navigationService.add(new Navigation(NAVIGATION_QXCMP_ADMIN_ACCOUNT, "系统后台用户中心导航栏")
                .addItem(new Navigation("QXCMP-ADMIN-ACCOUNT-INFO", "基本资料", QXCMP_BACKEND_URL + "/profile/info"))
                .addItem(new Navigation("QXCMP-ADMIN-ACCOUNT-SECURITY", "安全设置", QXCMP_BACKEND_URL + "/profile/security"))
        );
    }
}
