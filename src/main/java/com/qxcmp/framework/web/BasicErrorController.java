package com.qxcmp.framework.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.web.ErrorAttributes;
import org.springframework.boot.autoconfigure.web.ErrorController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * 平台错误页面路由抽象类
 * <p>
 * 需要实现自己的错误页面路由，以添加自己的页面内容
 *
 * @author aaric
 */
public abstract class BasicErrorController extends QXCMPBackendController implements ErrorController {

    private ErrorAttributes errorAttributes;

    @RequestMapping("/error")
    public ModelAndView handleError(HttpServletRequest request) {
        Map<String, Object> errors = errorAttributes.getErrorAttributes(new ServletRequestAttributes(request), true);

        return getErrorPage(request, errors);
    }

    protected abstract ModelAndView getErrorPage(HttpServletRequest request, Map<String, Object> errors);

    @Override
    public String getErrorPath() {
        return "/error";
    }

    @Autowired
    public void setErrorAttributes(ErrorAttributes errorAttributes) {
        this.errorAttributes = errorAttributes;
    }
}
