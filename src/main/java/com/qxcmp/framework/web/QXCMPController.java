package com.qxcmp.framework.web;

import com.qxcmp.framework.config.SystemConfigService;
import com.qxcmp.framework.config.UserConfigService;
import com.qxcmp.framework.core.QXCMPConfiguration;
import com.qxcmp.framework.user.User;
import com.qxcmp.framework.user.UserService;
import com.qxcmp.framework.web.view.Page;
import org.apache.commons.lang3.StringUtils;
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

    protected HttpServletRequest request;

    protected HttpServletResponse response;

    protected ApplicationContext applicationContext;

    protected QXCMPConfiguration qxcmpConfiguration;

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

    protected Page page() {
        return new Page();
    }

    /**
     * 获取请求的IP地址
     *
     * @return 请求IP地址
     */
    public String getRequestAddress() {
        String ip = request.getHeader("X-Forwarded-For");
        if (StringUtils.isNotBlank(ip) && !"unKnown".equalsIgnoreCase(ip)) {
            //多次反向代理后会有多个ip值，第一个ip才是真实ip
            int index = ip.indexOf(",");
            if (index != -1) {
                return ip.substring(0, index);
            } else {
                return ip;
            }
        }
        ip = request.getHeader("X-Real-IP");
        if (StringUtils.isNotEmpty(ip) && !"unKnown".equalsIgnoreCase(ip)) {
            return ip;
        }
        return request.getRemoteAddr();
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
    public void setQxcmpConfiguration(QXCMPConfiguration qxcmpConfiguration) {
        this.qxcmpConfiguration = qxcmpConfiguration;
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
