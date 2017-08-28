package com.qxcmp.framework.web;

import com.qxcmp.framework.config.SystemConfigService;
import com.qxcmp.framework.config.UserConfigService;
import com.qxcmp.framework.user.User;
import com.qxcmp.framework.user.UserService;
import com.qxcmp.framework.web.view.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Optional;

/**
 * 页面路由器基类
 *
 * @author aaric
 */
public abstract class QXCMPController {

    private static final String DEFAULT_PAGE = "qxcmp";

    protected HttpServletRequest request;

    protected HttpServletResponse response;

    protected ApplicationContext applicationContext;

    protected UserService userService;

    protected UserConfigService userConfigService;

    protected SystemConfigService systemConfigService;

    protected Optional<User> currentUser() {
        return Optional.ofNullable(userService.currentUser());
    }

    protected void refreshUser() {
        currentUser().ifPresent(currentUser -> userService.update(currentUser.getId(), user -> {
            Authentication authentication = new UsernamePasswordAuthenticationToken(user, user.getPassword(), user.getAuthorities());
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }));
    }

    protected ModelAndView redirect(String url) {
        return new ModelAndView("redirect:" + url);
    }

    protected Page.PageBuilder page() {
        return Page.builder();
    }

    @Autowired
    public void setRequest(HttpServletRequest request) {
        this.request = request;
    }

    @Autowired
    public void setResponse(HttpServletResponse response) {
        this.response = response;
    }

    @Autowired
    public void setApplicationContext(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    @Autowired
    public void setUserConfigService(UserConfigService userConfigService) {
        this.userConfigService = userConfigService;
    }

    @Autowired
    public void setSystemConfigService(SystemConfigService systemConfigService) {
        this.systemConfigService = systemConfigService;
    }
}
