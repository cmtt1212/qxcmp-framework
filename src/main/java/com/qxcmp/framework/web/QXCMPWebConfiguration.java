package com.qxcmp.framework.web;

import com.qxcmp.framework.config.SystemConfigAutowired;
import com.qxcmp.framework.config.SystemConfigService;
import com.qxcmp.framework.core.QXCMPSystemConfigConfiguration;
import com.qxcmp.framework.security.PrivilegeAutowired;
import com.qxcmp.framework.user.UserService;
import com.qxcmp.framework.web.auth.AuthenticationFilter;
import com.qxcmp.framework.web.model.navigation.NavigationService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.core.session.SessionRegistryImpl;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.session.HttpSessionEventPublisher;

import javax.servlet.DispatcherType;
import javax.servlet.Filter;
import java.util.EnumSet;

import static com.qxcmp.framework.core.QXCMPConfiguration.QXCMP_BACKEND_URL;
import static com.qxcmp.framework.core.QXCMPSecurityConfiguration.*;


/**
 * 平台内置模块配置
 *
 * @author aaric
 */
@EnableWebSecurity
@SystemConfigAutowired
@PrivilegeAutowired
@Order
@RequiredArgsConstructor
public class QXCMPWebConfiguration extends WebSecurityConfigurerAdapter {

    private final SystemConfigService systemConfigService;

    private final UserService userService;

    private final UserDetailsService userDetailsService;

    private final AuthenticationFailureHandler authenticationFailureHandler;

    private final AuthenticationSuccessHandler authenticationSuccessHandler;

    /**
     * 平台认证过滤器
     * <p>
     * 该过滤结果平台的认证配置进行相关的认证操作
     *
     * @return 平台认证过滤器
     *
     * @throws Exception
     */
    @Bean
    public AuthenticationFilter authenticationFilter() throws Exception {
        AuthenticationFilter authenticationFilter = new AuthenticationFilter(systemConfigService, userService);
        authenticationFilter.setAuthenticationManager(authenticationManager());
        authenticationFilter.setAuthenticationSuccessHandler(authenticationSuccessHandler);
        authenticationFilter.setAuthenticationFailureHandler(authenticationFailureHandler);
        return authenticationFilter;
    }

    /**
     * Spring Security Bug Fix
     * <p>
     * https://github.com/spring-projects/spring-boot/issues/1048
     * <p>
     * Can't access principle in error page workaround
     *
     * @param springSecurityFilterChain springSecurityFilterChain
     *
     * @return FilterRegistrationBean
     */
    @Bean
    public FilterRegistrationBean getSpringSecurityFilterChainBindedToError(
            @Qualifier("springSecurityFilterChain") Filter springSecurityFilterChain) {

        FilterRegistrationBean registration = new FilterRegistrationBean();
        registration.setFilter(springSecurityFilterChain);
        registration.setDispatcherTypes(EnumSet.allOf(DispatcherType.class));
        return registration;
    }

    /**
     * 增加并发会话控制
     *
     * @return HttpSessionEventPublisher
     */
    @Bean
    public static HttpSessionEventPublisher httpSessionEventPublisher() {
        return new HttpSessionEventPublisher();
    }

    /**
     * work around https://jira.spring.io/browse/SEC-2855
     *
     * @return SessionRegistry
     */
    @Bean
    public SessionRegistry sessionRegistry() {
        SessionRegistry sessionRegistry = new SessionRegistryImpl();
        return sessionRegistry;
    }

    /**
     * 配置平台用户获取服务
     *
     * @param auth 认证管理器构建器
     *
     * @throws Exception 如果配置失败则平台启动失败
     */
    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(new BCryptPasswordEncoder());
    }

    /**
     * 配置平台通用安全服务
     * <p>
     * 开启资源访问和API访问权限，以及定义登录操作
     * <p>
     * 拦截所有未知请求
     *
     * @param http Spring Security Http 安全配置
     *
     * @throws Exception 如果配置失败则平台启动失败
     */
    @Override
    public void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                .antMatchers("/assets/**", "/login/**", "/api/**", "/account/**").permitAll()
                .antMatchers(QXCMP_BACKEND_URL + "/tools/**").hasRole(PRIVILEGE_ADMIN_TOOL)
                .antMatchers(QXCMP_BACKEND_URL + "/settings/**").hasRole(PRIVILEGE_ADMIN_SETTINGS)
                .antMatchers(QXCMP_BACKEND_URL + "/security/**").hasRole(PRIVILEGE_ADMIN_SECURITY)
                .antMatchers(QXCMP_BACKEND_URL + "/audit/**").hasRole(PRIVILEGE_ADMIN_LOG)
                .antMatchers(QXCMP_BACKEND_URL + "/advertisement/**").hasRole(PRIVILEGE_ADMIN_ADVERTISEMENT)
                .antMatchers(QXCMP_BACKEND_URL + "/redeem/**").hasRole(PRIVILEGE_ADMIN_REDEEM)
                .antMatchers(QXCMP_BACKEND_URL + "/spider/**").hasRole(PRIVILEGE_ADMIN_SPIDER)
                .antMatchers(QXCMP_BACKEND_URL + "/user/**/role/**").hasRole(PRIVILEGE_USER_ROLE)
                .antMatchers(QXCMP_BACKEND_URL + "/user/**/status/**").hasRole(PRIVILEGE_USER_STATUS)
                .antMatchers(QXCMP_BACKEND_URL + "/user/**").hasRole(PRIVILEGE_USER)
                .antMatchers(QXCMP_BACKEND_URL + "/finance/weixin/**").hasRole(PRIVILEGE_FINANCE_WEIXIN)
                .antMatchers(QXCMP_BACKEND_URL + "/finance/**").hasRole(PRIVILEGE_FINANCE)
                .antMatchers(QXCMP_BACKEND_URL + "/message/email/config/**").hasRole(PRIVILEGE_MESSAGE_EMAIL_CONFIG)
                .antMatchers(QXCMP_BACKEND_URL + "/message/email/send/**").hasRole(PRIVILEGE_MESSAGE_EMAIL_SEND)
                .antMatchers(QXCMP_BACKEND_URL + "/message/sms/config/**").hasRole(PRIVILEGE_MESSAGE_SMS_CONFIG)
                .antMatchers(QXCMP_BACKEND_URL + "/message/sms/send/**").hasRole(PRIVILEGE_MESSAGE_SMS_SEND)
                .antMatchers(QXCMP_BACKEND_URL + "/message/**").hasRole(PRIVILEGE_MESSAGE)
                .antMatchers(QXCMP_BACKEND_URL + "/news/channel/**").hasRole(PRIVILEGE_NEWS_CHANNEL)
                .antMatchers(QXCMP_BACKEND_URL + "/news/article/**/preview").hasAnyRole(PRIVILEGE_NEWS_ARTICLE_AUDIT, PRIVILEGE_NEWS_ARTICLE_MANAGEMENT)
                .antMatchers(QXCMP_BACKEND_URL + "/news/article/**/auditing", QXCMP_BACKEND_URL + "/news/article/**/audit", QXCMP_BACKEND_URL + "/news/article/**/publish", QXCMP_BACKEND_URL + "/news/article/**/reject").hasRole(PRIVILEGE_NEWS_ARTICLE_AUDIT)
                .antMatchers(QXCMP_BACKEND_URL + "/news/article/**/published", QXCMP_BACKEND_URL + "/news/article/**/disabled", QXCMP_BACKEND_URL + "/news/article/**/enable", QXCMP_BACKEND_URL + "/news/article/**/disable", QXCMP_BACKEND_URL + "/news/article/**/remove").hasRole(PRIVILEGE_NEWS_ARTICLE_MANAGEMENT)
                .antMatchers(QXCMP_BACKEND_URL + "/news/user/**").hasRole(PRIVILEGE_NEWS)
                .antMatchers(QXCMP_BACKEND_URL + "/news/**").hasRole(PRIVILEGE_NEWS)
                .antMatchers(QXCMP_BACKEND_URL + "/weixin/mp/**").hasRole(PRIVILEGE_WEIXIN_CONFIG)
                .antMatchers(QXCMP_BACKEND_URL + "/weixin/menu/**").hasRole(PRIVILEGE_WEIXIN_MENU)
                .antMatchers(QXCMP_BACKEND_URL + "/weixin/material/**").hasRole(PRIVILEGE_WEIXIN_MATERIAL)
                .antMatchers(QXCMP_BACKEND_URL + "/weixin/**").hasRole(PRIVILEGE_WEIXIN)
                .antMatchers(QXCMP_BACKEND_URL + "/**").hasRole(PRIVILEGE_SYSTEM_ADMIN)
                .anyRequest().authenticated()
                .and()
                .csrf()
                .ignoringAntMatchers("/api/**")
                .and().formLogin().loginPage("/login").permitAll()
                .and().logout()
                .and().sessionManagement()
                .maximumSessions(systemConfigService.getInteger(QXCMPSystemConfigConfiguration.SYSTEM_CONFIG_SESSION_MAX_ACTIVE_COUNT).orElse(QXCMPSystemConfigConfiguration.SYSTEM_CONFIG_SESSION_MAX_ACTIVE_COUNT_DEFAULT_VALUE))
                .maxSessionsPreventsLogin(systemConfigService.getBoolean(QXCMPSystemConfigConfiguration.SYSTEM_CONFIG_SESSION_MAX_PREVENT_LOGIN).orElse(QXCMPSystemConfigConfiguration.SYSTEM_CONFIG_SESSION_MAX_PREVENT_LOGIN_DEFAULT_VALUE))
                .expiredUrl("/login?expired")
                .sessionRegistry(sessionRegistry())
                .and().and().addFilterBefore(authenticationFilter(), UsernamePasswordAuthenticationFilter.class)
                .exceptionHandling();
    }

    public void configureNavigation(NavigationService navigationService) {
//        Navigation navigation = navigationService.get(Navigation.Type.SIDEBAR, "内容管理", 900);
//        navigationService.add(navigation, "文章管理", "", "", QXCMP_BACKEND_URL + "/news/draft", AnchorTarget.SELF, 20, PRIVILEGE_ARTICLE_CREATE);
//        navigationService.add(navigation, "栏目管理", "", "", QXCMP_BACKEND_URL + "/news/channel", AnchorTarget.SELF, 30, PRIVILEGE_CHANNEL_CREATE);
//        navigation = navigationService.get(Navigation.Type.SIDEBAR, "商城管理", 9100);
//        navigationService.add(navigation, "订单管理", "", "", QXCMP_BACKEND_URL + "/mall/order", AnchorTarget.SELF, 2, PRIVILEGE_MALL_ORDER_MANAGEMENT);
//        navigationService.add(navigation, "商品管理", "", "", QXCMP_BACKEND_URL + "/mall/commodity", AnchorTarget.SELF, 5, PRIVILEGE_MALL_COMMODITY_MANAGEMENT);
//
//        navigation = navigationService.get(Navigation.Type.SIDEBAR, "微信公众平台", 9200);
//        navigationService.add(navigation, "素材管理", "", "", QXCMP_BACKEND_URL + "/weixin/material/news", AnchorTarget.SELF, 10, PRIVILEGE_WECHAT_MANAGEMENT);
//        navigationService.add(navigation, "用户管理", "", "", QXCMP_BACKEND_URL + "/weixin/user", AnchorTarget.SELF, 20, PRIVILEGE_WECHAT_MANAGEMENT);
//        navigationService.add(navigation, "公众号设置", "", "", QXCMP_BACKEND_URL + "/weixin/settings/config", AnchorTarget.SELF, 30, PRIVILEGE_WECHAT_MANAGEMENT);
//
//        navigation = navigationService.get(Navigation.Type.SIDEBAR, "系统设置", 10000);
//        navigationService.add(navigation, "网站设置", "", "", QXCMP_BACKEND_URL + "/site/config", AnchorTarget.SELF, 30, PRIVILEGE_ADMIN_SETTINGS_SITE);
//        navigationService.add(navigation, "系统工具", "", "", QXCMP_BACKEND_URL + "/tool", AnchorTarget.SELF, 30, PRIVILEGE_ADMIN_SETTINGS_SITE);
//        navigationService.add(navigation, "消息服务", "", "", QXCMP_BACKEND_URL + "/message", AnchorTarget.SELF, 60, PRIVILEGE_MESSAGE_MANAGEMENT);
//        navigationService.add(navigation, "安全设置", "", "", QXCMP_BACKEND_URL + "/security", AnchorTarget.SELF, 80, PRIVILEGE_SECURITY_MANAGEMENT);
//        navigationService.add(navigation, "系统日志", "", "", QXCMP_BACKEND_URL + "/log/audit", AnchorTarget.SELF, 90, PRIVILEGE_ADMIN_LOG);
//
//        navigation = navigationService.get(Navigation.Type.ACTION, "核心操作", 15000);
//        navigationService.add(navigation, "个人中心", "", "", QXCMP_BACKEND_URL + "/account", AnchorTarget.SELF, 10);
//
//        navigation = navigationService.get(Navigation.Type.NORMAL, "文章管理", 0);
//        navigationService.add(navigation, "文章审核", "", "", QXCMP_BACKEND_URL + "/news/audit", AnchorTarget.SELF, 10, PRIVILEGE_ARTICLE_AUDIT);
//        navigationService.add(navigation, "已发布文章", "", "", QXCMP_BACKEND_URL + "/news/publish", AnchorTarget.SELF, 20, PRIVILEGE_ARTICLE_AUDIT);
//        navigationService.add(navigation, "已禁用文章", "", "", QXCMP_BACKEND_URL + "/news/disable", AnchorTarget.SELF, 21, PRIVILEGE_ARTICLE_AUDIT);
//        navigationService.add(navigation, "新建文章", "", "", QXCMP_BACKEND_URL + "/news/new", AnchorTarget.SELF, 30);
//        navigationService.add(navigation, "我的文章草稿", "", "", QXCMP_BACKEND_URL + "/news/draft", AnchorTarget.SELF, 40);
//        navigationService.add(navigation, "我的待审核文章", "", "", QXCMP_BACKEND_URL + "/news/auditing", AnchorTarget.SELF, 50);
//        navigationService.add(navigation, "我的未通过文章", "", "", QXCMP_BACKEND_URL + "/news/reject", AnchorTarget.SELF, 60);
//        navigationService.add(navigation, "我的已发布文章", "", "", QXCMP_BACKEND_URL + "/news/published", AnchorTarget.SELF, 70);
//        navigationService.add(navigation, "我的被禁用文章", "", "", QXCMP_BACKEND_URL + "/news/disabled", AnchorTarget.SELF, 80);
//
//        navigation = navigationService.get(Navigation.Type.NORMAL, "公众号素材管理", 1);
//        navigationService.add(navigation, "图文管理", "", "", QXCMP_BACKEND_URL + "/weixin/material/news", AnchorTarget.SELF, 10, PRIVILEGE_WECHAT_MANAGEMENT);
//        navigationService.add(navigation, "图片管理", "", "", QXCMP_BACKEND_URL + "/weixin/material/image", AnchorTarget.SELF, 20, PRIVILEGE_WECHAT_MANAGEMENT);
//        navigationService.add(navigation, "视频管理", "", "", QXCMP_BACKEND_URL + "/weixin/material/video", AnchorTarget.SELF, 30, PRIVILEGE_WECHAT_MANAGEMENT);
//        navigationService.add(navigation, "语音管理", "", "", QXCMP_BACKEND_URL + "/weixin/material/voice", AnchorTarget.SELF, 40, PRIVILEGE_WECHAT_MANAGEMENT);
//
//        navigation = navigationService.get(Navigation.Type.NORMAL, "公众号设置", 1);
//        navigationService.add(navigation, "公众号参数", "", "", QXCMP_BACKEND_URL + "/weixin/settings/config", AnchorTarget.SELF, 10, PRIVILEGE_WECHAT_MANAGEMENT);
//        navigationService.add(navigation, "自定义菜单", "", "", QXCMP_BACKEND_URL + "/weixin/settings/menu", AnchorTarget.SELF, 20, PRIVILEGE_WECHAT_MANAGEMENT);
//        navigationService.add(navigation, "微信支付", "", "", QXCMP_BACKEND_URL + "/finance/payment/weixin/settings", AnchorTarget.SELF, 30, PRIVILEGE_FINANCE_CONFIG_MANAGEMENT);
//
//        navigation = navigationService.get(Navigation.Type.NORMAL, "个人中心", 0);
//        navigationService.add(navigation, "我的资料", "", "", QXCMP_BACKEND_URL + "/account/profile", AnchorTarget.SELF, 10);
//        navigationService.add(navigation, "密码修改", "", "", QXCMP_BACKEND_URL + "/account/password", AnchorTarget.SELF, 20);
//        navigationService.add(navigation, "我的密保", "", "", QXCMP_BACKEND_URL + "/account/question", AnchorTarget.SELF, 20);
//        navigationService.add(navigation, "邮箱绑定", "", "", QXCMP_BACKEND_URL + "/account/email", AnchorTarget.SELF, 30);
//        navigationService.add(navigation, "手机绑定", "", "", QXCMP_BACKEND_URL + "/account/phone", AnchorTarget.SELF, 40);
//
//        navigation = navigationService.get(Navigation.Type.NORMAL, "消息服务", 0);
//        navigationService.add(navigation, "邮件服务配置", "", "", QXCMP_BACKEND_URL + "/message/email/config", AnchorTarget.SELF, 10, PRIVILEGE_MESSAGE_MANAGEMENT);
//        navigationService.add(navigation, "短信服务配置", "", "", QXCMP_BACKEND_URL + "/message/sms/config", AnchorTarget.SELF, 20, PRIVILEGE_MESSAGE_MANAGEMENT);
//        navigationService.add(navigation, "短信服务测试", "", "", QXCMP_BACKEND_URL + "/message/sms/test", AnchorTarget.SELF, 30, PRIVILEGE_MESSAGE_MANAGEMENT);
//
//        navigation = navigationService.get(Navigation.Type.NORMAL, "系统日志", 0);
//        navigationService.add(navigation, "审计日志", "", "", QXCMP_BACKEND_URL + "/log/audit", AnchorTarget.SELF, 10, PRIVILEGE_ADMIN_LOG);
//        navigationService.add(navigation, "蜘蛛日志", "", "", QXCMP_BACKEND_URL + "/spider/log", AnchorTarget.SELF, 30, PRIVILEGE_SPIDER_MANAGEMENT);
//
//        navigation = navigationService.get(Navigation.Type.NORMAL, "安全设置", 0);
//        navigationService.add(navigation, "用户状态管理", "", "", QXCMP_BACKEND_URL + "/security/user", AnchorTarget.SELF, 10, PRIVILEGE_SECURITY_MANAGEMENT);
//        navigationService.add(navigation, "平台角色管理", "", "", QXCMP_BACKEND_URL + "/security/role", AnchorTarget.SELF, 20, PRIVILEGE_SECURITY_MANAGEMENT);
//        navigationService.add(navigation, "平台权限管理", "", "", QXCMP_BACKEND_URL + "/security/privilege", AnchorTarget.SELF, 30, PRIVILEGE_SECURITY_MANAGEMENT);
//        navigationService.add(navigation, "平台认证配置", "", "", QXCMP_BACKEND_URL + "/security/authentication", AnchorTarget.SELF, 40, PRIVILEGE_SECURITY_MANAGEMENT);
//
//        navigation = navigationService.get(Navigation.Type.NORMAL, "网站设置", 0);
//        navigationService.add(navigation, "网站配置", "", "", QXCMP_BACKEND_URL + "/site/config", AnchorTarget.SELF, 10, PRIVILEGE_ADMIN_SETTINGS_SITE);
//        navigationService.add(navigation, "账户注册配置", "", "", QXCMP_BACKEND_URL + "/site/account", AnchorTarget.SELF, 20, PRIVILEGE_ADMIN_SETTINGS_SITE);
//        navigationService.add(navigation, "系统字典", "", "", QXCMP_BACKEND_URL + "/site/dictionary", AnchorTarget.SELF, 30, PRIVILEGE_ADMIN_SETTINGS_SITE);
//        navigationService.add(navigation, "系统会话配置", "", "", QXCMP_BACKEND_URL + "/site/session", AnchorTarget.SELF, 40, PRIVILEGE_ADMIN_SETTINGS_SITE);
//        navigationService.add(navigation, "任务调度配置", "", "", QXCMP_BACKEND_URL + "/site/task", AnchorTarget.SELF, 50, PRIVILEGE_ADMIN_SETTINGS_SITE);
//        navigationService.add(navigation, "水印设置", "", "", QXCMP_BACKEND_URL + "/site/watermark", AnchorTarget.SELF, 60, PRIVILEGE_ADMIN_SETTINGS_SITE);
//
//        navigation = navigationService.get(Navigation.Type.NORMAL, "系统工具", 0);
//        navigationService.add(navigation, "账户邀请码", "", "", QXCMP_BACKEND_URL + "/tool/account/invite", AnchorTarget.SELF, 10, PRIVILEGE_ADMIN_SETTINGS_SITE);
//        navigationService.add(navigation, "广告列表", "", "", QXCMP_BACKEND_URL + "/ad", AnchorTarget.SELF, 20, PRIVILEGE_ADMIN_ADVERTISEMENT);
//        navigationService.add(navigation, "兑换码管理", "", "", QXCMP_BACKEND_URL + "/redeem", AnchorTarget.SELF, 30, PRIVILEGE_REDEEM_MANAGEMENT);
//        navigationService.add(navigation, "蜘蛛管理", "", "", QXCMP_BACKEND_URL + "/spider", AnchorTarget.SELF, 40, PRIVILEGE_SPIDER_MANAGEMENT);
//
//        navigation = navigationService.get(Navigation.Type.NORMAL, "兑换码管理", 0);
//        navigationService.add(navigation, "生成兑换码", "", "", QXCMP_BACKEND_URL + "/redeem/generate", AnchorTarget.SELF, 10, PRIVILEGE_REDEEM_MANAGEMENT);
//        navigationService.add(navigation, "兑换码列表", "", "", QXCMP_BACKEND_URL + "/redeem/list", AnchorTarget.SELF, 20, PRIVILEGE_REDEEM_MANAGEMENT);
//        navigationService.add(navigation, "兑换码设置", "", "", QXCMP_BACKEND_URL + "/redeem/settings", AnchorTarget.SELF, 30, PRIVILEGE_REDEEM_MANAGEMENT);
//
//        navigation = navigationService.get(Navigation.Type.NORMAL, "蜘蛛管理", 0);
//        navigationService.add(navigation, "蜘蛛状态", "", "", QXCMP_BACKEND_URL + "/spider/status", AnchorTarget.SELF, 10, PRIVILEGE_SPIDER_MANAGEMENT);
//        navigationService.add(navigation, "蜘蛛配置", "", "", QXCMP_BACKEND_URL + "/spider/config", AnchorTarget.SELF, 20, PRIVILEGE_SPIDER_MANAGEMENT);
    }
}
