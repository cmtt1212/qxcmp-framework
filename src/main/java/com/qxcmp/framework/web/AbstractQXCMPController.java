package com.qxcmp.framework.web;

import com.google.common.collect.Maps;
import com.google.gson.Gson;
import com.qxcmp.framework.audit.Action;
import com.qxcmp.framework.audit.ActionExecutor;
import com.qxcmp.framework.config.SiteService;
import com.qxcmp.framework.config.SystemConfigService;
import com.qxcmp.framework.config.UserConfigService;
import com.qxcmp.framework.core.entity.EntityService;
import com.qxcmp.framework.domain.Captcha;
import com.qxcmp.framework.domain.CaptchaService;
import com.qxcmp.framework.exception.CaptchaExpiredException;
import com.qxcmp.framework.exception.CaptchaIncorrectException;
import com.qxcmp.framework.user.User;
import com.qxcmp.framework.user.UserService;
import com.qxcmp.framework.web.model.RestfulResponse;
import com.qxcmp.framework.web.page.AbstractPage;
import com.qxcmp.framework.web.support.QXCMPPageResolver;
import com.qxcmp.framework.web.view.annotation.form.Form;
import com.qxcmp.framework.web.view.elements.grid.Col;
import com.qxcmp.framework.web.view.elements.grid.Grid;
import com.qxcmp.framework.web.view.elements.header.IconHeader;
import com.qxcmp.framework.web.view.elements.html.P;
import com.qxcmp.framework.web.view.elements.icon.Icon;
import com.qxcmp.framework.web.view.elements.message.ErrorMessage;
import com.qxcmp.framework.web.view.modules.form.AbstractForm;
import com.qxcmp.framework.web.view.modules.table.EntityTable;
import com.qxcmp.framework.web.view.modules.table.Table;
import com.qxcmp.framework.web.view.support.Alignment;
import com.qxcmp.framework.web.view.support.Color;
import com.qxcmp.framework.web.view.support.utils.FormHelper;
import com.qxcmp.framework.web.view.support.utils.TableHelper;
import com.qxcmp.framework.web.view.support.utils.ViewHelper;
import com.qxcmp.framework.web.view.views.Overview;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.BindingResult;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

/**
 * 平台页面路由基类
 * <p>
 * 负责解析页面类型并提供视图渲染和表单提交等一些基本支持
 *
 * @author Aaric
 */
public abstract class AbstractQXCMPController {

    protected HttpServletRequest request;
    protected HttpServletResponse response;
    protected ApplicationContext applicationContext;
    protected SiteService siteService;
    protected UserService userService;
    protected UserConfigService userConfigService;
    protected SystemConfigService systemConfigService;
    protected ViewHelper viewHelper;
    protected QXCMPPageResolver pageResolver;

    private FormHelper formHelper;
    private TableHelper tableHelper;
    private CaptchaService captchaService;
    private ActionExecutor actionExecutor;

    /**
     * 根据请求获取一个页面
     *
     * @return 由页面解析器解析出来的页面
     *
     * @see QXCMPPageResolver
     */
    protected AbstractPage page() {
        return pageResolver.resolve(request, response);
    }

    /**
     * 获取一个概览视图页面
     *
     * @param overview 概览组件
     *
     * @return 概览视图页面
     *
     * @see Overview
     */
    protected AbstractPage overviewPage(Overview overview) {
        return page().addComponent(new Grid().setTextContainer().setAlignment(Alignment.CENTER).setVerticallyPadded().addItem(new Col().addComponent(overview)));
    }

    /**
     * 获取一个重定向页面
     *
     * @param url 重定向链接
     *
     * @return 重定向页面
     */
    protected ModelAndView redirect(String url) {
        return new ModelAndView("redirect:" + url);
    }

    protected AbstractForm convertToForm(Object object) {
        return formHelper.convert(object);
    }

    protected ErrorMessage convertToErrorMessage(BindingResult bindingResult, Object object) {
        return formHelper.convertToErrorMessage(bindingResult, object);
    }

    protected EntityTable convertToTable(Pageable pageable, EntityService entityService) {
        return convertToTable("", pageable, entityService);
    }

    protected EntityTable convertToTable(String tableName, Pageable pageable, EntityService entityService) {
        return convertToTable(tableName, "", pageable, entityService);
    }

    @SuppressWarnings("unchecked")
    protected EntityTable convertToTable(String tableName, String action, Pageable pageable, EntityService entityService) {
        return tableHelper.convert(tableName, action, entityService.type(), entityService.findAll(pageable));
    }

    protected <T> EntityTable convertToTable(Class<T> tClass, Page<T> tPage) {
        return convertToTable("", tClass, tPage);
    }

    protected <T> EntityTable convertToTable(String tableName, Class<T> tClass, Page<T> tPage) {
        return tableHelper.convert(tableName, tClass, tPage);
    }

    protected Table convertToTable(Map<String, Object> dictionary) {
        return tableHelper.convert(dictionary);
    }

    protected Table convertToTable(Consumer<Map<String, Object>> consumer) {
        Map<String, Object> dictionary = Maps.newLinkedHashMap();
        consumer.accept(dictionary);
        return convertToTable(dictionary);
    }

    /**
     * 获取当前请求对应的认证用户
     *
     * @return 当前认证用户
     */
    protected Optional<User> currentUser() {
        return Optional.ofNullable(userService.currentUser());
    }

    /**
     * 刷新当前用户实体
     */
    protected void refreshUser() {
        currentUser().ifPresent(currentUser -> userService.update(currentUser.getId(), user -> {
            Authentication authentication = new UsernamePasswordAuthenticationToken(user, user.getPassword(), user.getAuthorities());
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }));
    }

    /**
     * 验证验证码是否有效，如果无效将错误信息放入 {@code BindingResult} 中
     *
     * @param captcha       用户输入的验证码
     * @param bindingResult 错误绑定
     */
    protected void verifyCaptcha(String captcha, BindingResult bindingResult) {
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
    protected String getRequestAddress() {
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

    protected ModelAndView submitForm(Object form, Action action) {
        return submitForm(form, action, (stringObjectMap, overview) -> {
        });
    }

    protected ModelAndView submitForm(Object form, Action action, BiConsumer<Map<String, Object>, Overview> biConsumer) {
        return submitForm("", form, action, biConsumer);
    }

    /**
     * 提交一个表单并执行相应操作
     * <p>
     * 该操作会被记录到审计日志中
     *
     * @param title      操作名称
     * @param form       要提交的表单
     * @param action     要执行的操作
     * @param biConsumer 返回的结果页面
     *
     * @return 提交后的页面
     */
    protected ModelAndView submitForm(String title, Object form, Action action, BiConsumer<Map<String, Object>, Overview> biConsumer) {
        Form annotation = form.getClass().getAnnotation(Form.class);

        if (Objects.nonNull(annotation) && StringUtils.isNotBlank(annotation.value())) {
            title = annotation.value();
        }

        if (StringUtils.isBlank(title)) {
            title = request.getRequestURL().toString();
        }

        return actionExecutor.execute(title, request.getRequestURL().toString(), getRequestContent(request), currentUser().orElse(null), action)
                .map(auditLog -> {
                    Overview overview = null;
                    switch (auditLog.getStatus()) {
                        case SUCCESS:
                            overview = new Overview(new IconHeader(auditLog.getTitle(), new Icon("info circle")).setSubTitle("操作成功"));
                            break;
                        case FAILURE:
                            overview = new Overview(new IconHeader(auditLog.getTitle(), new Icon("warning circle").setColor(Color.RED)).setSubTitle("操作失败")).addComponent(new P(auditLog.getComments()));
                            break;
                    }


                    biConsumer.accept(auditLog.getActionContext(), overview);

                    if (overview.getLinks().isEmpty()) {
                        overview.addLink("返回", request.getRequestURL().toString());
                    }

                    return overviewPage(overview).build();
                }).orElse(overviewPage(new Overview(new IconHeader("保存操作结果失败", new Icon("warning circle"))).addLink("返回", request.getRequestURL().toString())).build());
    }

    /**
     * 执行一个操作并记录到审计日志中
     *
     * @param title  操作名称
     * @param action 要执行的操作
     *
     * @return 操作结果实体
     */
    protected RestfulResponse audit(String title, Action action) {
        return actionExecutor.execute(title, request.getRequestURL().toString(), getRequestContent(request), currentUser().orElse(null), action).map(auditLog -> {

            switch (auditLog.getStatus()) {
                case SUCCESS:
                    return new RestfulResponse(HttpStatus.OK.value(), "", auditLog.getTitle(), auditLog.getComments());
                case FAILURE:
                    return new RestfulResponse(HttpStatus.BAD_GATEWAY.value(), "", auditLog.getTitle(), auditLog.getComments());
            }

            return new RestfulResponse(HttpStatus.NOT_ACCEPTABLE.value(), "", auditLog.getTitle(), auditLog.getComments());

        }).orElse(new RestfulResponse(HttpStatus.BAD_GATEWAY.value(), "", "Can't save audit log"));
    }

    private String getRequestContent(HttpServletRequest request) {
        if (request.getMethod().equalsIgnoreCase("get")) {
            return request.getQueryString();
        } else if (request.getMethod().equalsIgnoreCase("post")) {
            return new Gson().toJson(request.getParameterMap());
        } else {
            return "Unknown request method: " + request.toString();
        }
    }

    @Autowired
    public void setActionExecutor(ActionExecutor actionExecutor) {
        this.actionExecutor = actionExecutor;
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
    public void setSiteService(SiteService siteService) {
        this.siteService = siteService;
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
    public void setViewHelper(ViewHelper viewHelper) {
        this.viewHelper = viewHelper;
    }

    @Autowired
    public void setFormHelper(FormHelper formHelper) {
        this.formHelper = formHelper;
    }

    @Autowired
    public void setTableHelper(TableHelper tableHelper) {
        this.tableHelper = tableHelper;
    }

    @Autowired
    public void setCaptchaService(CaptchaService captchaService) {
        this.captchaService = captchaService;
    }

    @Autowired
    public void setPageResolver(QXCMPPageResolver pageResolver) {
        this.pageResolver = pageResolver;
    }
}
