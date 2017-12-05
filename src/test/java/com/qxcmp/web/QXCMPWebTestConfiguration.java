package com.qxcmp.web;

import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;


/**
 * 平台内置模块配置
 *
 * @author aaric
 */
@EnableWebSecurity
public class QXCMPWebTestConfiguration extends WebSecurityConfigurerAdapter {
    @Override
    public void configure(HttpSecurity http) throws Exception {
        http
                .requestMatchers()
                .antMatchers("/")
                .antMatchers("/test/**")
                .antMatchers("/assets/**")
                .antMatchers("/api/**")
                .antMatchers("/account/**")
                .and()
                .authorizeRequests()
                .antMatchers("/**").permitAll()
                .and()
                .csrf()
                .ignoringAntMatchers("/api/**")
                .and()
                .formLogin()
                .loginPage("/login").permitAll();
    }

}
