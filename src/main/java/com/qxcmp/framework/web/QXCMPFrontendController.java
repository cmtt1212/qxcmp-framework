package com.qxcmp.framework.web;

import com.qxcmp.framework.view.ModelAndViewBuilder;
import com.qxcmp.framework.view.support.FrontendBuilderEvent;
import org.springframework.http.HttpStatus;

/**
 * 前端页面路由基类
 * <p>
 * 平台的所有前端页面路由都应该继承自该类，以获得平台框架提供的支持
 *
 * @author aaric
 */
public abstract class QXCMPFrontendController extends QXCMPController {

    /**
     * 平台前端页面默认视图名称
     */
    public static final String DEFAULT_FRONTEND_PAGE = "qxcmp-frontend";

    public ModelAndViewBuilder builder() {
        return builder(DEFAULT_FRONTEND_PAGE);
    }

    @Override
    protected ModelAndViewBuilder builder(String viewName) {
        ModelAndViewBuilder modelAndViewBuilder = super.builder(viewName);
        applicationContext.publishEvent(new FrontendBuilderEvent(request, response, modelAndViewBuilder));
        return modelAndViewBuilder;
    }

    @Override
    protected ModelAndViewBuilder error(HttpStatus status, String message) {
        return builder().setResult(status.toString(), status.getReasonPhrase(), message);
    }
}
