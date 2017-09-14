package com.qxcmp.framework.web.controller;

import com.qxcmp.framework.core.QXCMPConfiguration;
import com.qxcmp.framework.view.ModelAndViewBuilder;
import com.qxcmp.framework.view.support.FrontendBuilderEvent;
import com.qxcmp.framework.web.BasicErrorController;
import com.qxcmp.framework.web.QXCMPFrontendController2;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * 平台后端错误页面路由
 * <p>
 * 用户处理平台后端遇到的所有错误消息，并生成错误消息页面
 *
 * @author aaric
 */
@Controller
public class QXCMPErrorController extends BasicErrorController {
    @Override
    protected ModelAndView getErrorPage(HttpServletRequest request, Map<String, Object> errors) {

        HttpStatus status = HttpStatus.valueOf(Integer.parseInt(errors.get("status").toString()));
        String path = errors.get("path").toString();
        String message = errors.get("message").toString();

        if (path.startsWith(QXCMPConfiguration.QXCMP_BACKEND_URL)) {
            return error(status, message).build();
        } else {
            ModelAndViewBuilder modelAndViewBuilder = builder(QXCMPFrontendController2.DEFAULT_FRONTEND_PAGE).setResult(status.toString(), status.getReasonPhrase(), message);
            applicationContext.publishEvent(new FrontendBuilderEvent(request, response, modelAndViewBuilder));
            return modelAndViewBuilder.build();
        }
    }
}
