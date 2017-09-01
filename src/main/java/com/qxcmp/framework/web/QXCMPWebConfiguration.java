package com.qxcmp.framework.web;

import com.qxcmp.framework.config.SystemConfigAutowired;
import com.qxcmp.framework.config.SystemConfigService;
import com.qxcmp.framework.security.PrivilegeAutowired;
import com.qxcmp.framework.user.UserService;
import com.qxcmp.framework.view.component.AnchorTarget;
import com.qxcmp.framework.view.nav.Navigation;
import com.qxcmp.framework.view.nav.NavigationConfigurator;
import com.qxcmp.framework.view.nav.NavigationService;
import com.qxcmp.framework.web.auth.AuthenticationFilter;
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

import static com.qxcmp.framework.core.QXCMPConfiguration.*;


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
public class QXCMPWebConfiguration extends WebSecurityConfigurerAdapter implements NavigationConfigurator {

    private final SystemConfigService systemConfigService;

    private final UserService userService;

    private final UserDetailsService userDetailsService;

    private final AuthenticationFailureHandler authenticationFailureHandler;

    private final AuthenticationSuccessHandler authenticationSuccessHandler;

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
                .antMatchers("/assets/**", "/login/**", "/api/**", "/account/**", "/sample").permitAll()
                .antMatchers(QXCMP_BACKEND_URL + "/**").hasRole(PRIVILEGE_SYSTEM_ADMIN)
                .antMatchers(QXCMP_BACKEND_URL + "/ad/**").hasRole(PRIVILEGE_ADVERTISEMENT_MANAGEMENT)
                .antMatchers(QXCMP_BACKEND_URL + "/site/**").hasRole(PRIVILEGE_SITE_MANAGEMENT)
                .antMatchers(QXCMP_BACKEND_URL + "/security/**").hasRole(PRIVILEGE_SECURITY_MANAGEMENT)
                .antMatchers(QXCMP_BACKEND_URL + "/message/**").hasRole(PRIVILEGE_MESSAGE_MANAGEMENT)
                .antMatchers(QXCMP_BACKEND_URL + "/log/**").hasRole(PRIVILEGE_LOG_MANAGEMENT)
                .antMatchers(QXCMP_BACKEND_URL + "/finance/payment/weixin/**").hasRole(PRIVILEGE_FINANCE_CONFIG_MANAGEMENT)
                .antMatchers(QXCMP_BACKEND_URL + "/redeem/**").hasRole(PRIVILEGE_REDEEM_MANAGEMENT)
                .antMatchers(QXCMP_BACKEND_URL + "/weixin/**").hasRole(PRIVILEGE_WECHAT_MANAGEMENT)
                .antMatchers(QXCMP_BACKEND_URL + "/article/audit").hasRole(PRIVILEGE_ARTICLE_AUDIT)
                .antMatchers(QXCMP_BACKEND_URL + "/article/publish").hasRole(PRIVILEGE_ARTICLE_AUDIT)
                .antMatchers(QXCMP_BACKEND_URL + "/article/disable").hasRole(PRIVILEGE_ARTICLE_AUDIT)
                .antMatchers(QXCMP_BACKEND_URL + "/article/new").hasRole(PRIVILEGE_ARTICLE_CREATE)
                .antMatchers(QXCMP_BACKEND_URL + "/article/audit").hasRole(PRIVILEGE_ARTICLE_CREATE)
                .antMatchers(QXCMP_BACKEND_URL + "/article/channel/new").hasRole(PRIVILEGE_CHANNEL_CREATE)
                .antMatchers(QXCMP_BACKEND_URL + "/mall/commodity/**").hasRole(PRIVILEGE_MALL_COMMODITY_MANAGEMENT)
                .antMatchers(QXCMP_BACKEND_URL + "/mall/order/**").hasRole(PRIVILEGE_MALL_ORDER_MANAGEMENT)
                .antMatchers(QXCMP_BACKEND_URL + "/spider/**").hasRole(PRIVILEGE_SPIDER_MANAGEMENT)
                .anyRequest().authenticated()
                .and()
                .csrf()
                .ignoringAntMatchers("/api/**")
                .and().formLogin().loginPage("/login").permitAll()
                .and().logout()
                .and().sessionManagement()
                .maximumSessions(systemConfigService.getInteger(SYSTEM_CONFIG_SESSION_MAX_ACTIVE_COUNT).orElse(SYSTEM_CONFIG_SESSION_MAX_ACTIVE_COUNT_DEFAULT_VALUE))
                .maxSessionsPreventsLogin(systemConfigService.getBoolean(SYSTEM_CONFIG_SESSION_MAX_PREVENT_LOGIN).orElse(SYSTEM_CONFIG_SESSION_MAX_PREVENT_LOGIN_DEFAULT_VALUE))
                .expiredUrl("/login?expired")
                .sessionRegistry(sessionRegistry())
                .and().and().addFilterBefore(authenticationFilter(), UsernamePasswordAuthenticationFilter.class)
                .exceptionHandling();
    }

    @Override
    public void configureNavigation(NavigationService navigationService) {
        Navigation navigation = navigationService.get(Navigation.Type.SIDEBAR, "内容管理", 900);
        navigationService.add(navigation, "文章管理", "", "", QXCMP_BACKEND_URL + "/article/draft", AnchorTarget.SELF, 20, PRIVILEGE_ARTICLE_CREATE);
        navigationService.add(navigation, "栏目管理", "", "", QXCMP_BACKEND_URL + "/article/channel", AnchorTarget.SELF, 30, PRIVILEGE_CHANNEL_CREATE);
        navigation = navigationService.get(Navigation.Type.SIDEBAR, "商城管理", 9100);
        navigationService.add(navigation, "订单管理", "", "", QXCMP_BACKEND_URL + "/mall/order", AnchorTarget.SELF, 2, PRIVILEGE_MALL_ORDER_MANAGEMENT);
        navigationService.add(navigation, "商品管理", "", "", QXCMP_BACKEND_URL + "/mall/commodity", AnchorTarget.SELF, 5, PRIVILEGE_MALL_COMMODITY_MANAGEMENT);

        navigation = navigationService.get(Navigation.Type.SIDEBAR, "微信公众平台", 9200);
        navigationService.add(navigation, "素材管理", "", "", QXCMP_BACKEND_URL + "/weixin/material/article", AnchorTarget.SELF, 10, PRIVILEGE_WECHAT_MANAGEMENT);
        navigationService.add(navigation, "用户管理", "", "", QXCMP_BACKEND_URL + "/weixin/user", AnchorTarget.SELF, 20, PRIVILEGE_WECHAT_MANAGEMENT);
        navigationService.add(navigation, "公众号设置", "", "", QXCMP_BACKEND_URL + "/weixin/settings/config", AnchorTarget.SELF, 30, PRIVILEGE_WECHAT_MANAGEMENT);

        navigation = navigationService.get(Navigation.Type.SIDEBAR, "系统设置", 10000);
        navigationService.add(navigation, "网站设置", "", "", QXCMP_BACKEND_URL + "/site/config", AnchorTarget.SELF, 30, PRIVILEGE_SITE_MANAGEMENT);
        navigationService.add(navigation, "系统工具", "", "", QXCMP_BACKEND_URL + "/tool", AnchorTarget.SELF, 30, PRIVILEGE_SITE_MANAGEMENT);
        navigationService.add(navigation, "消息服务", "", "", QXCMP_BACKEND_URL + "/message", AnchorTarget.SELF, 60, PRIVILEGE_MESSAGE_MANAGEMENT);
        navigationService.add(navigation, "安全设置", "", "", QXCMP_BACKEND_URL + "/security", AnchorTarget.SELF, 80, PRIVILEGE_SECURITY_MANAGEMENT);
        navigationService.add(navigation, "系统日志", "", "", QXCMP_BACKEND_URL + "/log/audit", AnchorTarget.SELF, 90, PRIVILEGE_LOG_MANAGEMENT);

        navigation = navigationService.get(Navigation.Type.ACTION, "核心操作", 15000);
        navigationService.add(navigation, "个人中心", "", "", QXCMP_BACKEND_URL + "/account", AnchorTarget.SELF, 10);

        navigation = navigationService.get(Navigation.Type.NORMAL, "文章管理", 0);
        navigationService.add(navigation, "文章审核", "", "", QXCMP_BACKEND_URL + "/article/audit", AnchorTarget.SELF, 10, PRIVILEGE_ARTICLE_AUDIT);
        navigationService.add(navigation, "已发布文章", "", "", QXCMP_BACKEND_URL + "/article/publish", AnchorTarget.SELF, 20, PRIVILEGE_ARTICLE_AUDIT);
        navigationService.add(navigation, "已禁用文章", "", "", QXCMP_BACKEND_URL + "/article/disable", AnchorTarget.SELF, 21, PRIVILEGE_ARTICLE_AUDIT);
        navigationService.add(navigation, "新建文章", "", "", QXCMP_BACKEND_URL + "/article/new", AnchorTarget.SELF, 30);
        navigationService.add(navigation, "我的文章草稿", "", "", QXCMP_BACKEND_URL + "/article/draft", AnchorTarget.SELF, 40);
        navigationService.add(navigation, "我的待审核文章", "", "", QXCMP_BACKEND_URL + "/article/auditing", AnchorTarget.SELF, 50);
        navigationService.add(navigation, "我的未通过文章", "", "", QXCMP_BACKEND_URL + "/article/reject", AnchorTarget.SELF, 60);
        navigationService.add(navigation, "我的已发布文章", "", "", QXCMP_BACKEND_URL + "/article/published", AnchorTarget.SELF, 70);
        navigationService.add(navigation, "我的被禁用文章", "", "", QXCMP_BACKEND_URL + "/article/disabled", AnchorTarget.SELF, 80);

        navigation = navigationService.get(Navigation.Type.NORMAL, "公众号素材管理", 1);
        navigationService.add(navigation, "图文管理", "", "", QXCMP_BACKEND_URL + "/weixin/material/article", AnchorTarget.SELF, 10, PRIVILEGE_WECHAT_MANAGEMENT);
        navigationService.add(navigation, "图片管理", "", "", QXCMP_BACKEND_URL + "/weixin/material/image", AnchorTarget.SELF, 20, PRIVILEGE_WECHAT_MANAGEMENT);
        navigationService.add(navigation, "视频管理", "", "", QXCMP_BACKEND_URL + "/weixin/material/video", AnchorTarget.SELF, 30, PRIVILEGE_WECHAT_MANAGEMENT);
        navigationService.add(navigation, "语音管理", "", "", QXCMP_BACKEND_URL + "/weixin/material/voice", AnchorTarget.SELF, 40, PRIVILEGE_WECHAT_MANAGEMENT);

        navigation = navigationService.get(Navigation.Type.NORMAL, "公众号设置", 1);
        navigationService.add(navigation, "公众号参数", "", "", QXCMP_BACKEND_URL + "/weixin/settings/config", AnchorTarget.SELF, 10, PRIVILEGE_WECHAT_MANAGEMENT);
        navigationService.add(navigation, "自定义菜单", "", "", QXCMP_BACKEND_URL + "/weixin/settings/menu", AnchorTarget.SELF, 20, PRIVILEGE_WECHAT_MANAGEMENT);
        navigationService.add(navigation, "微信支付", "", "", QXCMP_BACKEND_URL + "/finance/payment/weixin/settings", AnchorTarget.SELF, 30, PRIVILEGE_FINANCE_CONFIG_MANAGEMENT);

        navigation = navigationService.get(Navigation.Type.NORMAL, "个人中心", 0);
        navigationService.add(navigation, "我的资料", "", "", QXCMP_BACKEND_URL + "/account/profile", AnchorTarget.SELF, 10);
        navigationService.add(navigation, "密码修改", "", "", QXCMP_BACKEND_URL + "/account/password", AnchorTarget.SELF, 20);
        navigationService.add(navigation, "我的密保", "", "", QXCMP_BACKEND_URL + "/account/question", AnchorTarget.SELF, 20);
        navigationService.add(navigation, "邮箱绑定", "", "", QXCMP_BACKEND_URL + "/account/email", AnchorTarget.SELF, 30);
        navigationService.add(navigation, "手机绑定", "", "", QXCMP_BACKEND_URL + "/account/phone", AnchorTarget.SELF, 40);

        navigation = navigationService.get(Navigation.Type.NORMAL, "消息服务", 0);
        navigationService.add(navigation, "邮件服务配置", "", "", QXCMP_BACKEND_URL + "/message/email/config", AnchorTarget.SELF, 10, PRIVILEGE_MESSAGE_MANAGEMENT);
        navigationService.add(navigation, "短信服务配置", "", "", QXCMP_BACKEND_URL + "/message/sms/config", AnchorTarget.SELF, 20, PRIVILEGE_MESSAGE_MANAGEMENT);
        navigationService.add(navigation, "短信服务测试", "", "", QXCMP_BACKEND_URL + "/message/sms/test", AnchorTarget.SELF, 30, PRIVILEGE_MESSAGE_MANAGEMENT);

        navigation = navigationService.get(Navigation.Type.NORMAL, "系统日志", 0);
        navigationService.add(navigation, "审计日志", "", "", QXCMP_BACKEND_URL + "/log/audit", AnchorTarget.SELF, 10, PRIVILEGE_LOG_MANAGEMENT);
        navigationService.add(navigation, "蜘蛛日志", "", "", QXCMP_BACKEND_URL + "/spider/log", AnchorTarget.SELF, 30, PRIVILEGE_SPIDER_MANAGEMENT);

        navigation = navigationService.get(Navigation.Type.NORMAL, "安全设置", 0);
        navigationService.add(navigation, "用户状态管理", "", "", QXCMP_BACKEND_URL + "/security/user", AnchorTarget.SELF, 10, PRIVILEGE_SECURITY_MANAGEMENT);
        navigationService.add(navigation, "平台角色管理", "", "", QXCMP_BACKEND_URL + "/security/role", AnchorTarget.SELF, 20, PRIVILEGE_SECURITY_MANAGEMENT);
        navigationService.add(navigation, "平台权限管理", "", "", QXCMP_BACKEND_URL + "/security/privilege", AnchorTarget.SELF, 30, PRIVILEGE_SECURITY_MANAGEMENT);
        navigationService.add(navigation, "平台认证配置", "", "", QXCMP_BACKEND_URL + "/security/authentication", AnchorTarget.SELF, 40, PRIVILEGE_SECURITY_MANAGEMENT);

        navigation = navigationService.get(Navigation.Type.NORMAL, "网站设置", 0);
        navigationService.add(navigation, "网站配置", "", "", QXCMP_BACKEND_URL + "/site/config", AnchorTarget.SELF, 10, PRIVILEGE_SITE_MANAGEMENT);
        navigationService.add(navigation, "账户注册配置", "", "", QXCMP_BACKEND_URL + "/site/account", AnchorTarget.SELF, 20, PRIVILEGE_SITE_MANAGEMENT);
        navigationService.add(navigation, "系统字典", "", "", QXCMP_BACKEND_URL + "/site/dictionary", AnchorTarget.SELF, 30, PRIVILEGE_SITE_MANAGEMENT);
        navigationService.add(navigation, "系统会话配置", "", "", QXCMP_BACKEND_URL + "/site/session", AnchorTarget.SELF, 40, PRIVILEGE_SITE_MANAGEMENT);
        navigationService.add(navigation, "任务调度配置", "", "", QXCMP_BACKEND_URL + "/site/task", AnchorTarget.SELF, 50, PRIVILEGE_SITE_MANAGEMENT);
        navigationService.add(navigation, "水印设置", "", "", QXCMP_BACKEND_URL + "/site/watermark", AnchorTarget.SELF, 60, PRIVILEGE_SITE_MANAGEMENT);

        navigation = navigationService.get(Navigation.Type.NORMAL, "系统工具", 0);
        navigationService.add(navigation, "账户邀请码", "", "", QXCMP_BACKEND_URL + "/tool/account/invite", AnchorTarget.SELF, 10, PRIVILEGE_SITE_MANAGEMENT);
        navigationService.add(navigation, "广告列表", "", "", QXCMP_BACKEND_URL + "/ad", AnchorTarget.SELF, 20, PRIVILEGE_ADVERTISEMENT_MANAGEMENT);
        navigationService.add(navigation, "兑换码管理", "", "", QXCMP_BACKEND_URL + "/redeem", AnchorTarget.SELF, 30, PRIVILEGE_REDEEM_MANAGEMENT);
        navigationService.add(navigation, "蜘蛛管理", "", "", QXCMP_BACKEND_URL + "/spider", AnchorTarget.SELF, 40, PRIVILEGE_SPIDER_MANAGEMENT);

        navigation = navigationService.get(Navigation.Type.NORMAL, "兑换码管理", 0);
        navigationService.add(navigation, "生成兑换码", "", "", QXCMP_BACKEND_URL + "/redeem/generate", AnchorTarget.SELF, 10, PRIVILEGE_REDEEM_MANAGEMENT);
        navigationService.add(navigation, "兑换码列表", "", "", QXCMP_BACKEND_URL + "/redeem/list", AnchorTarget.SELF, 20, PRIVILEGE_REDEEM_MANAGEMENT);
        navigationService.add(navigation, "兑换码设置", "", "", QXCMP_BACKEND_URL + "/redeem/settings", AnchorTarget.SELF, 30, PRIVILEGE_REDEEM_MANAGEMENT);

        navigation = navigationService.get(Navigation.Type.NORMAL, "蜘蛛管理", 0);
        navigationService.add(navigation, "蜘蛛状态", "", "", QXCMP_BACKEND_URL + "/spider/status", AnchorTarget.SELF, 10, PRIVILEGE_SPIDER_MANAGEMENT);
        navigationService.add(navigation, "蜘蛛配置", "", "", QXCMP_BACKEND_URL + "/spider/config", AnchorTarget.SELF, 20, PRIVILEGE_SPIDER_MANAGEMENT);
    }
}
