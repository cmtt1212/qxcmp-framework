package com.qxcmp.framework.web;

import com.qxcmp.framework.config.SystemConfigAutowired;
import com.qxcmp.framework.config.SystemConfigService;
import com.qxcmp.framework.security.PrivilegeAutowired;
import com.qxcmp.framework.user.UserService;
import com.qxcmp.framework.view.component.LinkTarget;
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
                .antMatchers(QXCMP_BACKEND_URL + "/**").hasRole(PRIVILEGE_SYSTEM_ADMIN)
                .antMatchers(QXCMP_BACKEND_URL + "/ad/**").hasRole(PRIVILEGE_ADVERTISEMENT_MANAGEMENT)
                .antMatchers(QXCMP_BACKEND_URL + "/site/**").hasRole(PRIVILEGE_SITE_MANAGEMENT)
                .antMatchers(QXCMP_BACKEND_URL + "/security/**").hasRole(PRIVILEGE_SECURITY_MANAGEMENT)
                .antMatchers(QXCMP_BACKEND_URL + "/message/**").hasRole(PRIVILEGE_MESSAGE_MANAGEMENT)
                .antMatchers(QXCMP_BACKEND_URL + "/log/**").hasRole(PRIVILEGE_LOG_MANAGEMENT)
                .antMatchers(QXCMP_BACKEND_URL + "/finance/payment/weixin/**").hasRole(PRIVILEGE_FINANCE_CONFIG_MANAGEMENT)
                .antMatchers(QXCMP_BACKEND_URL + "/redeem/**").hasRole(PRIVILEGE_REDEEM_MANAGEMENT)
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
        Navigation navigation = navigationService.get(Navigation.Type.SIDEBAR, "系统设置", 10000);
        navigationService.add(navigation, "网站设置", "", "", QXCMP_BACKEND_URL + "/site/config", LinkTarget.SELF, 30, PRIVILEGE_SITE_MANAGEMENT);
        navigationService.add(navigation, "系统工具", "", "", QXCMP_BACKEND_URL + "/tool", LinkTarget.SELF, 30, PRIVILEGE_SITE_MANAGEMENT);
        navigationService.add(navigation, "消息服务", "", "", QXCMP_BACKEND_URL + "/message", LinkTarget.SELF, 60, PRIVILEGE_MESSAGE_MANAGEMENT);
        navigationService.add(navigation, "安全设置", "", "", QXCMP_BACKEND_URL + "/security", LinkTarget.SELF, 80, PRIVILEGE_SECURITY_MANAGEMENT);
        navigationService.add(navigation, "系统日志", "", "", QXCMP_BACKEND_URL + "/log/audit", LinkTarget.SELF, 90, PRIVILEGE_LOG_MANAGEMENT);
        navigationService.add(navigation, "微信支付", "", "", QXCMP_BACKEND_URL + "/finance/payment/weixin/settings", LinkTarget.SELF, 40, PRIVILEGE_FINANCE_CONFIG_MANAGEMENT);

        navigation = navigationService.get(Navigation.Type.ACTION, "核心操作", 15000);
        navigationService.add(navigation, "个人中心", "", "", QXCMP_BACKEND_URL + "/account", LinkTarget.SELF, 10);

        navigation = navigationService.get(Navigation.Type.NORMAL, "个人中心", 0);
        navigationService.add(navigation, "我的资料", "", "", QXCMP_BACKEND_URL + "/account/profile", LinkTarget.SELF, 10);
        navigationService.add(navigation, "密码修改", "", "", QXCMP_BACKEND_URL + "/account/password", LinkTarget.SELF, 20);
        navigationService.add(navigation, "我的密保", "", "", QXCMP_BACKEND_URL + "/account/question", LinkTarget.SELF, 20);
        navigationService.add(navigation, "邮箱绑定", "", "", QXCMP_BACKEND_URL + "/account/email", LinkTarget.SELF, 30);
        navigationService.add(navigation, "手机绑定", "", "", QXCMP_BACKEND_URL + "/account/phone", LinkTarget.SELF, 40);

        navigation = navigationService.get(Navigation.Type.NORMAL, "消息服务", 0);
        navigationService.add(navigation, "邮件服务配置", "", "", QXCMP_BACKEND_URL + "/message/email/config", LinkTarget.SELF, 10, PRIVILEGE_MESSAGE_MANAGEMENT);
        navigationService.add(navigation, "短信服务配置", "", "", QXCMP_BACKEND_URL + "/message/sms/config", LinkTarget.SELF, 20, PRIVILEGE_MESSAGE_MANAGEMENT);
        navigationService.add(navigation, "短信服务测试", "", "", QXCMP_BACKEND_URL + "/message/sms/test", LinkTarget.SELF, 30, PRIVILEGE_MESSAGE_MANAGEMENT);

        navigation = navigationService.get(Navigation.Type.NORMAL, "系统日志", 0);
        navigationService.add(navigation, "审计日志", "", "", QXCMP_BACKEND_URL + "/log/audit", LinkTarget.SELF, 10, PRIVILEGE_LOG_MANAGEMENT);

        navigation = navigationService.get(Navigation.Type.NORMAL, "安全设置", 0);
        navigationService.add(navigation, "用户状态管理", "", "", QXCMP_BACKEND_URL + "/security/user", LinkTarget.SELF, 10, PRIVILEGE_SECURITY_MANAGEMENT);
        navigationService.add(navigation, "平台角色管理", "", "", QXCMP_BACKEND_URL + "/security/role", LinkTarget.SELF, 20, PRIVILEGE_SECURITY_MANAGEMENT);
        navigationService.add(navigation, "平台权限管理", "", "", QXCMP_BACKEND_URL + "/security/privilege", LinkTarget.SELF, 30, PRIVILEGE_SECURITY_MANAGEMENT);
        navigationService.add(navigation, "平台认证配置", "", "", QXCMP_BACKEND_URL + "/security/authentication", LinkTarget.SELF, 40, PRIVILEGE_SECURITY_MANAGEMENT);

        navigation = navigationService.get(Navigation.Type.NORMAL, "网站设置", 0);
        navigationService.add(navigation, "网站配置", "", "", QXCMP_BACKEND_URL + "/site/config", LinkTarget.SELF, 10, PRIVILEGE_SITE_MANAGEMENT);
        navigationService.add(navigation, "账户注册配置", "", "", QXCMP_BACKEND_URL + "/site/account", LinkTarget.SELF, 20, PRIVILEGE_SITE_MANAGEMENT);
        navigationService.add(navigation, "系统字典", "", "", QXCMP_BACKEND_URL + "/site/dictionary", LinkTarget.SELF, 30, PRIVILEGE_SITE_MANAGEMENT);
        navigationService.add(navigation, "系统会话配置", "", "", QXCMP_BACKEND_URL + "/site/session", LinkTarget.SELF, 40, PRIVILEGE_SITE_MANAGEMENT);
        navigationService.add(navigation, "任务调度配置", "", "", QXCMP_BACKEND_URL + "/site/task", LinkTarget.SELF, 50, PRIVILEGE_SITE_MANAGEMENT);
        navigationService.add(navigation, "水印设置", "", "", QXCMP_BACKEND_URL + "/site/watermark", LinkTarget.SELF, 60, PRIVILEGE_SITE_MANAGEMENT);

        navigation = navigationService.get(Navigation.Type.NORMAL, "系统工具", 0);
        navigationService.add(navigation, "账户邀请码", "", "", QXCMP_BACKEND_URL + "/tool/account/invite", LinkTarget.SELF, 10, PRIVILEGE_SITE_MANAGEMENT);
        navigationService.add(navigation, "广告列表", "", "", QXCMP_BACKEND_URL + "/ad", LinkTarget.SELF, 20, PRIVILEGE_ADVERTISEMENT_MANAGEMENT);
        navigationService.add(navigation, "兑换码管理", "", "", QXCMP_BACKEND_URL + "/redeem", LinkTarget.SELF, 30, PRIVILEGE_REDEEM_MANAGEMENT);

        navigation = navigationService.get(Navigation.Type.NORMAL, "兑换码管理", 0);
        navigationService.add(navigation, "生成兑换码", "", "", QXCMP_BACKEND_URL + "/redeem/generate", LinkTarget.SELF, 10, PRIVILEGE_REDEEM_MANAGEMENT);
        navigationService.add(navigation, "兑换码列表", "", "", QXCMP_BACKEND_URL + "/redeem/list", LinkTarget.SELF, 20, PRIVILEGE_REDEEM_MANAGEMENT);
        navigationService.add(navigation, "兑换码设置", "", "", QXCMP_BACKEND_URL + "/redeem/settings", LinkTarget.SELF, 30, PRIVILEGE_REDEEM_MANAGEMENT);

    }
}
