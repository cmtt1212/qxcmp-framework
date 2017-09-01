package com.qxcmp.framework.web.view;

/**
 * UI 组件接口
 *
 * @author aaric
 */
public interface Component {

    String DEFAULT_FRAGMENT_NAME = "default";

    /**
     * @return 获取组件对应的渲染模板文件名称
     */
    String getFragmentFile();

    /**
     * @return 获取组件对应的渲染模板片段名称
     */
    default String getFragmentName() {
        return DEFAULT_FRAGMENT_NAME;
    }

    /**
     * @return 组件 CSS class 前缀
     */
    default String getClassPrefix() {
        return "";
    }

    /**
     * @return 组件 CSS class 内容
     */
    default String getClassContent() {
        return "";
    }

    /**
     * @return 组件 CSS class 后缀
     */
    default String getClassSuffix() {
        return "";
    }

    /**
     * @return 该组件经过计算后的组件 CSS class 名称
     */
    default String getClassName() {
        return (getClassPrefix() + " " + getClassContent() + " " + getClassSuffix()).trim().replaceAll("\\s+", "\\s");
    }

}
