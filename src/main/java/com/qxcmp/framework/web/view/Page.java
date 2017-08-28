package com.qxcmp.framework.web.view;

import lombok.Builder;
import lombok.Data;
import lombok.Singular;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

/**
 * 页面对象
 * <p>
 * 包含了渲染页面所有的信息
 *
 * @author aaric
 */
@Data
@Builder
public class Page {

    private static final String DEFAULT_PAGE_VIEW = "qxcmp";

    @Builder.Default
    private ModelAndView modelAndView = new ModelAndView(DEFAULT_PAGE_VIEW);

    /**
     * 页面组件，框架会依次渲染各个组件
     */
    @Singular
    private List<Component> components;

    public ModelAndView build() {
        modelAndView.addObject(this);
        return modelAndView;
    }
}
