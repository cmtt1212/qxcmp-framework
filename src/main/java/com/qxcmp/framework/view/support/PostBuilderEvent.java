package com.qxcmp.framework.view.support;

import com.qxcmp.framework.view.ModelAndViewBuilder;
import lombok.AllArgsConstructor;
import lombok.Data;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 每当一个页面生成的时候会触发该事件
 *
 * @author aaric
 */
@AllArgsConstructor
@Data
public class PostBuilderEvent {

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
