package com.qxcmp.framework.web;

import com.google.gson.Gson;
import com.qxcmp.framework.audit.Action;
import com.qxcmp.framework.audit.ActionExecutor;
import com.qxcmp.framework.view.ModelAndViewBuilder;
import com.qxcmp.framework.view.support.BackendBuilderEvent;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;
import java.util.Objects;
import java.util.function.BiConsumer;

/**
 * 后端页面路由基类
 * <p>
 * 平台的所有后端页面路由都应该继承自该类，以获得平台框架提供的支持
 *
 * @author aaric
 * @see com.qxcmp.framework.view.ModelAndViewBuilder
 */
public abstract class QXCMPBackendController2 extends QXCMPController2 {

    /**
     * 平台后端页面视图默认名称
     */
    public static final String DEFAULT_BACKEND_PAGE = "qxcmp";

    /**
     * 操作实行器
     */
    private ActionExecutor actionExecutor;

    /**
     * 获取一个后端模型视图生成器
     * <p>
     * 视图自动包含当前登录用户和用户的侧边导航栏数据
     *
     * @return 后端模型视图生成器
     */
    public ModelAndViewBuilder builder() {
        return builder(DEFAULT_BACKEND_PAGE);
    }

    /**
     * 获取一个后端模型视图生成器
     * <p>
     * 视图自动包含当前登录用户和用户的侧边导航栏数据
     *
     * @param viewName 视图名称
     *
     * @return 后端模型视图生成器
     */
    public ModelAndViewBuilder builder(String viewName) {
        ModelAndViewBuilder modelAndViewBuilder = super.builder(viewName);
        applicationContext.publishEvent(new BackendBuilderEvent(request, response, modelAndViewBuilder));
        return modelAndViewBuilder;
    }

    /**
     * 执行一个操作，保存操作记录并返回一个模型视图生成器
     *
     * @param title  操作名称
     * @param action 要执行的操作
     *
     * @return 后端模型视图生成器
     */
    public ModelAndViewBuilder action(String title, Action action) {
        return action(title, action, (context, modelAndViewBuilder) -> {
        });
    }

    /**
     * 执行一个操作，保存操作记录并返回一个模型视图生成器
     *
     * @param viewName 视图名称
     * @param title    操作名称
     * @param action   要执行的操作
     *
     * @return 后端模型视图生成器
     */
    public ModelAndViewBuilder action(String viewName, String title, Action action) {
        return action(viewName, title, action, (context, modelAndViewBuilder) -> {
        });
    }

    /**
     * 执行一个操作，保存操作记录并返回一个模型视图生成器
     *
     * @param title                         操作名称
     * @param action                        要执行的操作
     * @param modelAndViewBuilderBiConsumer 包含操作上下文对象的函数
     *
     * @return 后端模型视图生成器
     */
    public ModelAndViewBuilder action(String title, Action action, BiConsumer<Map<String, Object>, ModelAndViewBuilder> modelAndViewBuilderBiConsumer) {
        return action(DEFAULT_BACKEND_PAGE, title, action, modelAndViewBuilderBiConsumer);
    }

    /**
     * 执行一个操作，保存操作记录并返回一个模型视图生成器
     *
     * @param viewName                      视图名称
     * @param title                         操作名称
     * @param action                        要执行的操作
     * @param modelAndViewBuilderBiConsumer 包含操作上下文对象的函数
     *
     * @return 后端模型视图生成器
     */
    public ModelAndViewBuilder action(String viewName, String title, Action action, BiConsumer<Map<String, Object>, ModelAndViewBuilder> modelAndViewBuilderBiConsumer) {
        return actionExecutor.execute(title, request.getRequestURL().toString(), getRequestContent(request), currentUser(), action)
                .map(auditLog -> {
                    ModelAndViewBuilder modelAndViewBuilder = builder(viewName)
                            .setTitle(title)
                            .setResult(auditLog.getTitle(), StringUtils.isBlank(auditLog.getComments()) ? auditLog.getTitle() + "成功" : auditLog.getComments());

                    modelAndViewBuilderBiConsumer.accept(auditLog.getActionContext(), modelAndViewBuilder);

                    if (Objects.isNull(modelAndViewBuilder.getObject("resultNavigation"))) {
                        modelAndViewBuilder.setResultNavigation("返回", auditLog.getUrl());
                    }

                    return modelAndViewBuilder;
                })
                .orElse(error(HttpStatus.BAD_GATEWAY, "保存操作结果失败"));
    }

    @Override
    protected ModelAndViewBuilder error(HttpStatus status, String message) {
        return builder().setResult(status.toString(), status.getReasonPhrase(), message);
    }

    /**
     * 根据HTTP请求获取操作内容
     * <p>
     * 如果请求为GET，则返回查询参数
     * <p>
     * 如果请求为POST，则返回参数JSON
     *
     * @param request 操作请求
     *
     * @return 操作的内容
     */
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
}
