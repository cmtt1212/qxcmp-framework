package com.qxcmp.framework.core;

import com.qxcmp.framework.security.PrivilegeAutowired;
import org.springframework.context.annotation.Configuration;


/**
 * 平台权限配置
 *
 * @author Aaric
 */
@Configuration
@PrivilegeAutowired
public class QXCMPSecurityConfiguration {
    public static final String PRIVILEGE_SYSTEM_ADMIN = "系统管理员权限";
    public static final String PRIVILEGE_SYSTEM_ADMIN_DESCRIPTION = "该权限允许用户进入平台后台";
    public static final String PRIVILEGE_LOG_MANAGEMENT = "平台日志查看权限";
    public static final String PRIVILEGE_LOG_MANAGEMENT_DESCRIPTION = "该权限可以查看平台日志";
    public static final String PRIVILEGE_MESSAGE_MANAGEMENT = "消息服务管理权限";
    public static final String PRIVILEGE_MESSAGE_MANAGEMENT_DESCRIPTION = "该权限可以管理平台消息服务以及进行相关配置";
    public static final String PRIVILEGE_SECURITY_MANAGEMENT = "平台安全管理权限";
    public static final String PRIVILEGE_SECURITY_MANAGEMENT_DESCRIPTION = "该权限可以对平台的权限，角色进行配置并且可以管理所有用户的状态";
    public static final String PRIVILEGE_SITE_MANAGEMENT = "网站配置管理权限";
    public static final String PRIVILEGE_SITE_MANAGEMENT_DESCRIPTION = "该权限可以对网站配置信息进行管理，如网站关键字，描述信息等等";
    public static final String PRIVILEGE_ADVERTISEMENT_MANAGEMENT = "广告管理权限";
    public static final String PRIVILEGE_ADVERTISEMENT_MANAGEMENT_DESCRIPTION = "该权限可以对平台的广告进行管理";
    public static final String PRIVILEGE_FINANCE_CONFIG_MANAGEMENT = "平台财务管理配置权限";
    public static final String PRIVILEGE_FINANCE_CONFIG_MANAGEMENT_DESCRIPTION = "该权限可以修改平台财务的配置";
    public static final String PRIVILEGE_REDEEM_MANAGEMENT = "兑换码管理权限";
    public static final String PRIVILEGE_REDEEM_MANAGEMENT_DESCRIPTION = "该权限可以生成和管理兑换码";
    public static final String PRIVILEGE_ARTICLE_CREATE = "文章创建权限";
    public static final String PRIVILEGE_ARTICLE_CREATE_DESCRIPTION = "该权限控制用户能否看见创建文章的菜单";
    public static final String PRIVILEGE_ARTICLE_AUDIT = "文章审核权限";
    public static final String PRIVILEGE_ARTICLE_AUDIT_DESCRIPTION = "该权限可以审核所有待发布文章";
    public static final String PRIVILEGE_CHANNEL_CREATE = "栏目创建权限";
    public static final String PRIVILEGE_CHANNEL_CREATE_DESCRIPTION = "该权限可以创建新的栏目";
    public static final String PRIVILEGE_WECHAT_MANAGEMENT = "微信公众平台管理权限";
    public static final String PRIVILEGE_WECHAT_MANAGEMENT_DESCRIPTION = "该权限可以对微信公众平台进行管理";
    public static final String PRIVILEGE_MALL_COMMODITY_MANAGEMENT = "商城商品管理权限";
    public static final String PRIVILEGE_MALL_COMMODITY_MANAGEMENT_DESCRIPTION = "该权限可以对商城的商品进行管理";
    public static final String PRIVILEGE_MALL_ORDER_MANAGEMENT = "商城订单管理权限";
    public static final String PRIVILEGE_MALL_ORDER_MANAGEMENT_DESCRIPTION = "该权限可以对商城订单进行管理";
    public static final String PRIVILEGE_SPIDER_MANAGEMENT = "蜘蛛管理权限";
    public static final String PRIVILEGE_SPIDER_MANAGEMENT_DESCRIPTION = "该权限可以查看";
}
