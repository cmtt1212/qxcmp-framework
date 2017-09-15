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
import com.qxcmp.framework.web.view.AbstractPage;
import com.qxcmp.framework.web.view.elements.grid.Col;
import com.qxcmp.framework.web.view.elements.grid.VerticallyDividedGrid;
import com.qxcmp.framework.web.view.elements.message.ErrorMessage;
import com.qxcmp.framework.web.view.modules.form.AbstractForm;
import com.qxcmp.framework.web.view.support.Alignment;
import com.qxcmp.framework.web.view.support.ColumnCount;
import com.qxcmp.framework.web.view.support.utils.FormHelper;
import com.qxcmp.framework.web.view.views.Overview;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.BindingResult;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Objects;
import java.util.Optional;

public abstract class AbstractQXCMPController {

    protected HttpServletRequest request;

    protected HttpServletResponse response;

    protected ApplicationContext applicationContext;

    protected QXCMPConfiguration qxcmpConfiguration;

    protected UserService userService;

    protected UserConfigService userConfigService;

    protected SystemConfigService systemConfigService;

    private FormHelper formHelper;

    private CaptchaService captchaService;

    protected abstract AbstractPage page();

    protected AbstractPage overviewPage(Overview overview) {
        return page().addComponent(new VerticallyDividedGrid().setTextContainer().setAlignment(Alignment.CENTER).setVerticallyPadded().setColumnCount(ColumnCount.ONE).addItem(new Col().addComponent(overview)));
    }

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

    protected AbstractForm convertToForm(Object object) {
        return formHelper.convert(object);
    }

    protected ErrorMessage convertToErrorMessage(BindingResult bindingResult, Object object) {
        return formHelper.convertToErrorMessage(bindingResult, object);
    }


    /**
     * 验证验证码是否有效，如果无效将错误信息放入 {@code BindingResult} 中
     *
     * @param captcha       用户输入的验证码
     * @param bindingResult 错误绑定
     */
    public void verifyCaptcha(String captcha, BindingResult bindingResult) {
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

    @Autowired
    public void setFormHelper(FormHelper formHelper) {
        this.formHelper = formHelper;
    }

    @Autowired
    public void setCaptchaService(CaptchaService captchaService) {
        this.captchaService = captchaService;
    }
}
