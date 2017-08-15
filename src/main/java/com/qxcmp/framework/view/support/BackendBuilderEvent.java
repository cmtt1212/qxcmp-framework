package com.qxcmp.framework.view.support;

import com.qxcmp.framework.view.ModelAndViewBuilder;
import lombok.AllArgsConstructor;
import lombok.Data;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 每当访问一个后端页面的时候会生成该事件
 * <p>
 * 用于自定义后端页面
 *
 * @author aaric
 */
@AllArgsConstructor
@Data
public class BackendBuilderEvent {

    /**
     * 用户请求
     */
    private HttpServletRequest request;

    /**
     * 用户响应
     */
    private HttpServletResponse response;

    /**
     * 模型视图生成器
     */
    private ModelAndViewBuilder modelAndViewBuilder;
}
