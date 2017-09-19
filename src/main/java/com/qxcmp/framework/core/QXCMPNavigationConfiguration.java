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

    @Override
    public void configureNavigation(NavigationService navigationService) {
        navigationService.add(new Navigation(NAVIGATION_QXCMP_ADMIN_SIDEBAR, "系统后台侧边导航栏")
                .addItem(new Navigation("QXCMP-ADMIN-SIDEBAR-USER", "用户管理", QXCMP_BACKEND_URL + "/user")).setOrder(10)
                .addItem(new Navigation("QXCMP-ADMIN-SIDEBAR-MESSAGE-SERVICE", "消息服务").setOrder(20)
                        .addItem(new Navigation("QXCMP-ADMIN-SIDEBAR-MESSAGE-SERVICE-EMAIL", "邮件服务", QXCMP_BACKEND_URL + "/message/email")).setOrder(10).setPrivilegesAnd(ImmutableSet.of(PRIVILEGE_MESSAGE_MANAGEMENT))
                        .addItem(new Navigation("QXCMP-ADMIN-SIDEBAR-MESSAGE-SERVICE-SMS", "短息服务", QXCMP_BACKEND_URL + "/message/sms")).setOrder(20).setPrivilegesAnd(ImmutableSet.of(PRIVILEGE_MESSAGE_MANAGEMENT))
                )
                .addItem(new Navigation("QXCMP-ADMIN-SIDEBAR-SECURITY", "安全配置").setOrder(30)
                        .addItem(new Navigation("QXCMP-ADMIN-SIDEBAR-SECURITY-AUTHENTICATION", "认证配置", QXCMP_BACKEND_URL + "/security/authentication").setOrder(10).setPrivilegesAnd(ImmutableSet.of(PRIVILEGE_SECURITY_MANAGEMENT)))
                        .addItem(new Navigation("QXCMP-ADMIN-SIDEBAR-SECURITY-PRIVILEGE", "权限管理", QXCMP_BACKEND_URL + "/security/privilege").setOrder(10).setPrivilegesAnd(ImmutableSet.of(PRIVILEGE_SECURITY_MANAGEMENT)))
                        .addItem(new Navigation("QXCMP-ADMIN-SIDEBAR-SECURITY-ROLE", "角色管理", QXCMP_BACKEND_URL + "/security/role").setOrder(12).setPrivilegesAnd(ImmutableSet.of(PRIVILEGE_SECURITY_MANAGEMENT)))
                )
                .addItem(new Navigation("QXCMP-ADMIN-SIDEBAR-SYSTEM-TOOL", "系统工具").setOrder(40)
                        .addItem(new Navigation("QXCMP-ADMIN-SIDEBAR-TOOL-REDEEM", "兑换码", QXCMP_BACKEND_URL + "/redeem").setOrder(10).setPrivilegesAnd(ImmutableSet.of(PRIVILEGE_REDEEM_MANAGEMENT)))
                        .addItem(new Navigation("QXCMP-ADMIN-SIDEBAR-TOOL-SPIDER", "蜘蛛管理", QXCMP_BACKEND_URL + "/spider").setOrder(50).setPrivilegesAnd(ImmutableSet.of(PRIVILEGE_SPIDER_MANAGEMENT)))
                        .addItem(new Navigation("QXCMP-ADMIN-SIDEBAR-TOOL-LOG", "系统日志", QXCMP_BACKEND_URL + "/log").setOrder(100).setPrivilegesAnd(ImmutableSet.of(PRIVILEGE_LOG_MANAGEMENT)))
                )
                .addItem(new Navigation("QXCMP-ADMIN-SIDEBAR-SYSTEM-SETTINGS", "系统设置").setOrder(50)
                        .addItem(new Navigation("QXCMP-ADMIN-SIDEBAR-SETTINGS-SITE", "网站配置", QXCMP_BACKEND_URL + "/settings/site").setOrder(10).setPrivilegesAnd(ImmutableSet.of(PRIVILEGE_SITE_MANAGEMENT)))
                        .addItem(new Navigation("QXCMP-ADMIN-SIDEBAR-SETTINGS-ACCOUNT", "账户配置", QXCMP_BACKEND_URL + "/settings/account").setOrder(20).setPrivilegesAnd(ImmutableSet.of(PRIVILEGE_SITE_MANAGEMENT)))
                )
        );
    }
}
