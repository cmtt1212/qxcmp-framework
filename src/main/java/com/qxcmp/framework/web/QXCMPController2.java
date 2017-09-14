package com.qxcmp.framework.web;

import com.qxcmp.framework.config.SystemConfigService;
import com.qxcmp.framework.config.UserConfigService;
import com.qxcmp.framework.core.QXCMPConfiguration;
import com.qxcmp.framework.domain.Captcha;
import com.qxcmp.framework.domain.CaptchaExpiredException;
import com.qxcmp.framework.domain.CaptchaIncorrectException;
import com.qxcmp.framework.domain.CaptchaService;
import com.qxcmp.framework.user.User;
import com.qxcmp.framework.user.UserService;
import com.qxcmp.framework.view.ModelAndViewBuilder;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.BindingResult;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Objects;

/**
 * 页面路由器基础类
 * <p>
 * 提供框架基本支持
 *
 * @author aaric
 * @see ModelAndViewBuilder
 */
public abstract class QXCMPController2 {

    public static final String FORM_OBJECT = ModelAndViewBuilder.FORM_OBJECT;

    /**
     * 请求对象
     */
    protected HttpServletRequest request;

    /**
     * 响应对象
     */
    protected HttpServletResponse response;

    /**
     * Spring IoC 容器
     */
    protected ApplicationContext applicationContext;

    /**
     * 平台配置
     */
    protected QXCMPConfiguration qxcmpConfiguration;

    /**
     * 平台配置服务
     */
    protected SystemConfigService systemConfigService;

    /**
     * 用户配置服务
     */
    protected UserConfigService userConfigService;

    /**
     * 用户服务
     */
    protected UserService userService;

    /**
     * 验证码服务
     */
    private CaptchaService captchaService;

    /**
     * 获取当前认证用户
     *
     * @return 当前认证的用户，如果用户未认证则返回{@code null}
     */
    protected User currentUser() {
        return userService.currentUser();
    }

    /**
     * 刷新当前用户登录信息
     *
     * @return 当前认证用户
     */
    protected User refreshUser() {
        User currentUser = currentUser();
        if (Objects.nonNull(currentUser)) {
            return userService.update(currentUser.getId(), user -> {
                Authentication authentication = new UsernamePasswordAuthenticationToken(user, user.getPassword(), user.getAuthorities());
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }).orElse(null);
        } else {
            return null;
        }
    }

    /**
     * 页面重定向
     *
     * @param redirectUrl 重定向位置
     *
     * @return 页面重定向
     */
    protected ModelAndView redirect(String redirectUrl) {
        return builder("redirect:" + redirectUrl).build();
    }


    /**
     * 获取一个模型视图生成器
     *
     * @param viewName 视图名称
     *
     * @return 模型视图生成器
     */
    protected ModelAndViewBuilder builder(String viewName) {
        ModelAndViewBuilder modelAndViewBuilder = applicationContext.getBean(ModelAndViewBuilder.class, request, response);
        modelAndViewBuilder.setViewName(viewName);
        return modelAndViewBuilder;
    }

    /**
     * 获取一个错误页面
     *
     * @param status  HTTP错误代码
     * @param message 错误信息
     *
     * @return 错误页面模型视图生成器
     */
    protected abstract ModelAndViewBuilder error(HttpStatus status, String message);

    /**
     * 验证验证码是否有效，如果无效将错误信息放入 {@code BindingResult} 中
     *
     * @param captcha       用户输入的验证码
     * @param bindingResult 错误绑定
     */
    public void validateCaptcha(String captcha, BindingResult bindingResult) {
        if (Objects.isNull(request.getSession().getAttribute(CaptchaService.CAPTCHA_SESSION_ATTR))) {
            bindingResult.rejectValue("captcha", "Captcha.null");
        } else {
            try {
                Captcha c = (Captcha) request.getSession().getAttribute(CaptchaService.CAPTCHA_SESSION_ATTR);
                captchaService.verify(c, captcha);
            } catch (CaptchaExpiredException e) {
                bindingResult.rejectValue("captcha", "Captcha.expired");
            } catch (CaptchaIncorrectException e) {
                bindingResult.rejectValue("captcha", "Captcha.incorrect");
            }
        }
    }

    /**
     * 获取请求的IP地址
     *
     * @return 请求IP地址
     */
    public String getRequestIPAddress() {
        String ip = request.getHeader("X-Forwarded-For");
        if (StringUtils.isNotEmpty(ip) && !"unKnown".equalsIgnoreCase(ip)) {
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
    public void setSystemConfigService(SystemConfigService systemConfigService) {
        this.systemConfigService = systemConfigService;
    }

    @Autowired
    public void setUserConfigService(UserConfigService userConfigService) {
        this.userConfigService = userConfigService;
    }

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    @Autowired
    public void setCaptchaService(CaptchaService captchaService) {
        this.captchaService = captchaService;
    }
}
