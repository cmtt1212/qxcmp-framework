package com.qxcmp.framework.web.view;

import lombok.Builder;
import lombok.Data;
import lombok.Singular;

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

    /**
     * 页面组件，框架会依次渲染各个组件
     */
    @Singular
    private List<Component> components;
}
