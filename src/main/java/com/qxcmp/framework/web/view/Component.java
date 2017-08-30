package com.qxcmp.framework.web.view;

/**
 * 页面组件基本类
 * <p>
 * 每个组件最终会被渲染为一个Html元素
 *
 * @author aaric
 */
public interface Component {

    /**
     * @return 该组件对应的渲染模板文件名称
     */
    String getFragmentFile();

    /**
     * @return 该组件对应的渲染模板片段名称
     */
    default String getFragmentName() {
        return "default";
    }

    /**
     * @return 该组件的CSS名称
     */
    default String getClassName() {
        return "";
    }

    /**
     * @return 该组件的style属性
     */
    default String getStyle() {
        return "";
    }
}
