package com.qxcmp.framework.core;

import com.google.common.collect.ImmutableSet;
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
public class QXCMPNavigationConfiguration implements NavigationConfigurator{

    public static final String NAVIGATION_QXCMP_ADMIN_SIDEBAR = "QXCMP-ADMIN-SIDEBAR";

    @Override
    public void configureNavigation(NavigationService navigationService) {
        navigationService.add(new Navigation(NAVIGATION_QXCMP_ADMIN_SIDEBAR, "系统后台侧边导航栏")
                .addItem(new Navigation("QXCMP-ADMIN-SIDEBAR-SYSTEM-SETTINGS", "内容管理")
                        .addItem(new Navigation("QXCMP-ADMIN-SIDEBAR-ARTICLE-MANAGEMENT", "文章管理", QXCMP_BACKEND_URL + "/article/draft").setOrder(10).setPrivilegesAnd(ImmutableSet.of(QXCMPSecurityConfiguration.PRIVILEGE_ARTICLE_CREATE)))
                        .addItem(new Navigation("QXCMP-ADMIN-SIDEBAR-ARTICLE-CHANNEL", "栏目管理", QXCMP_BACKEND_URL + "/article/channel").setOrder(20).setPrivilegesAnd(ImmutableSet.of(QXCMPSecurityConfiguration.PRIVILEGE_CHANNEL_CREATE)))
                )
                .addItem(new Navigation("QXCMP-ADMIN-SIDEBAR-SYSTEM-SETTINGS", "商城管理")
                        .addItem(new Navigation("QXCMP-ADMIN-SIDEBAR-MALL-ORDER", "订单管理", QXCMP_BACKEND_URL + "/mall/order").setOrder(10).setPrivilegesAnd(ImmutableSet.of(QXCMPSecurityConfiguration.PRIVILEGE_MALL_ORDER_MANAGEMENT)))
                        .addItem(new Navigation("QXCMP-ADMIN-SIDEBAR-MALL-COMMODITY", "商品管理", QXCMP_BACKEND_URL + "/mall/commodity").setOrder(20).setPrivilegesAnd(ImmutableSet.of(QXCMPSecurityConfiguration.PRIVILEGE_MALL_COMMODITY_MANAGEMENT)))
                )
                .addItem(new Navigation("QXCMP-ADMIN-SIDEBAR-SYSTEM-SETTINGS", "微信公众平台")
                        .addItem(new Navigation("QXCMP-ADMIN-SIDEBAR-WEIXIN-MATERIAL", "素材管理", QXCMP_BACKEND_URL + "/weixin/material/article").setOrder(10).setPrivilegesAnd(ImmutableSet.of(QXCMPSecurityConfiguration.PRIVILEGE_WECHAT_MANAGEMENT)))
                        .addItem(new Navigation("QXCMP-ADMIN-SIDEBAR-WEIXIN-USER", "用户管理", QXCMP_BACKEND_URL + "/weixin/user").setOrder(20).setPrivilegesAnd(ImmutableSet.of(QXCMPSecurityConfiguration.PRIVILEGE_WECHAT_MANAGEMENT)))
                        .addItem(new Navigation("QXCMP-ADMIN-SIDEBAR-WEIXIN-SETTING", "公众号", QXCMP_BACKEND_URL + "/weixin/settings/config").setOrder(30).setPrivilegesAnd(ImmutableSet.of(QXCMPSecurityConfiguration.PRIVILEGE_WECHAT_MANAGEMENT)))
                )
                .addItem(new Navigation("QXCMP-ADMIN-SIDEBAR-SYSTEM-SETTINGS", "系统设置")
                        .addItem(new Navigation("QXCMP-ADMIN-SIDEBAR-SIDE-CONFIG", "网站配置", QXCMP_BACKEND_URL + "/site/config").setOrder(10).setPrivilegesAnd(ImmutableSet.of(QXCMPSecurityConfiguration.PRIVILEGE_SITE_MANAGEMENT)))
                        .addItem(new Navigation("QXCMP-ADMIN-SIDEBAR-SYSTEM-TOOL", "系统工具", QXCMP_BACKEND_URL + "/tool").setOrder(20).setPrivilegesAnd(ImmutableSet.of(QXCMPSecurityConfiguration.PRIVILEGE_SITE_MANAGEMENT)))
                        .addItem(new Navigation("QXCMP-ADMIN-SIDEBAR-MESSAGE-CONFIG", "消息服务", QXCMP_BACKEND_URL + "/message").setOrder(30).setPrivilegesAnd(ImmutableSet.of(QXCMPSecurityConfiguration.PRIVILEGE_MESSAGE_MANAGEMENT)))
                        .addItem(new Navigation("QXCMP-ADMIN-SIDEBAR-SECURITY", "安全设置", QXCMP_BACKEND_URL + "/security").setOrder(40).setPrivilegesAnd(ImmutableSet.of(QXCMPSecurityConfiguration.PRIVILEGE_SECURITY_MANAGEMENT)))
                        .addItem(new Navigation("QXCMP-ADMIN-SIDEBAR-LOG", "系统日志", QXCMP_BACKEND_URL + "/log/audit").setOrder(50).setPrivilegesAnd(ImmutableSet.of(QXCMPSecurityConfiguration.PRIVILEGE_LOG_MANAGEMENT)))
                )
        );
    }
}
