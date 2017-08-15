package com.qxcmp.framework.spdier.web;

import com.qxcmp.framework.core.BaseModuleConfigurator;
import com.qxcmp.framework.view.component.LinkTarget;
import com.qxcmp.framework.view.nav.Navigation;
import com.qxcmp.framework.view.nav.NavigationConfigurator;
import com.qxcmp.framework.view.nav.NavigationService;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;

import static com.qxcmp.framework.core.QXCMPConfiguration.QXCMP_BACKEND_URL;

/**
 * 蜘蛛模块
 *
 * @author aaric
 */
@EnableWebSecurity
@Order(124)
public class SpiderModule extends BaseModuleConfigurator implements NavigationConfigurator {

    /*
    * 模块权限
    * */
    public static final String PRIVILEGE_SPIDER_MANAGEMENT = "蜘蛛管理权限";
    public static final String PRIVILEGE_SPIDER_MANAGEMENT_DESCRIPTION = "该权限可以查看";

    @Override
    protected void configureHttpSecurity(HttpSecurity http) throws Exception {
        http
                .antMatcher(QXCMP_BACKEND_URL + "/spider/**")
                .authorizeRequests()
                .antMatchers(QXCMP_BACKEND_URL + "/spider/**").hasRole(PRIVILEGE_SPIDER_MANAGEMENT)
                .anyRequest()
                .authenticated()
                .and()
                .formLogin()
                .loginPage("/login").permitAll();
    }

    @Override
    public void configureNavigation(NavigationService navigationService) {
        Navigation navigation = navigationService.get(Navigation.Type.NORMAL, "系统工具", 30);
        navigationService.add(navigation, "蜘蛛管理", "", "", QXCMP_BACKEND_URL + "/spider", LinkTarget.SELF, 10, PRIVILEGE_SPIDER_MANAGEMENT);

        navigation = navigationService.get(Navigation.Type.NORMAL, "蜘蛛管理", 60);
        navigationService.add(navigation, "蜘蛛状态", "", "", QXCMP_BACKEND_URL + "/spider/status", LinkTarget.SELF, 10, PRIVILEGE_SPIDER_MANAGEMENT);
        navigationService.add(navigation, "蜘蛛配置", "", "", QXCMP_BACKEND_URL + "/spider/config", LinkTarget.SELF, 20, PRIVILEGE_SPIDER_MANAGEMENT);

        navigation = navigationService.get(Navigation.Type.NORMAL, "系统日志", 0);
        navigationService.add(navigation, "蜘蛛日志", "", "", QXCMP_BACKEND_URL + "/spider/log", LinkTarget.SELF, 30, PRIVILEGE_SPIDER_MANAGEMENT);
    }
}
