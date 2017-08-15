package com.qxcmp.framework.core;

import com.qxcmp.framework.config.SystemConfigAutowired;
import com.qxcmp.framework.security.PrivilegeAutowired;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

/**
 * 平台模块配置抽象类
 * <p>
 * 该类的子类需要使用 {@link EnableWebSecurity} 注解才会生成
 * <p>
 * 并且需要结合 {@link Order} ， 如果有多个子类，顺序不能重复
 * <p>
 * 平台在启动的时候会自动加载所有有效的子类，对安全，权限，系统配置等相关的内容按照定义的顺序进行自动配置
 *
 * @author aaric
 * @see PrivilegeAutowired
 * @see SystemConfigAutowired
 */
@PrivilegeAutowired
@SystemConfigAutowired
public abstract class BaseModuleConfigurator extends WebSecurityConfigurerAdapter {

    @Override
    protected final void configure(HttpSecurity http) throws Exception {
        configureHttpSecurity(http);
    }

    /**
     * 对模块安全进行配置
     *
     * @param http Spring Security Http 安全配置
     *
     * @throws Exception 如果配置失败会导致平台启动失败
     */
    protected abstract void configureHttpSecurity(HttpSecurity http) throws Exception;
}
