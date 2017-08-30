package com.qxcmp.framework.web.view;

import lombok.Data;

/**
 * 页面组件基本类
 * <p>
 * 每个组件最终会被渲染为一个Html元素
 *
 * @author aaric
 */
@Data
public abstract class QXCMPComponent {

    /**
     * 该组件对应的渲染模板文件名称
     */
    private String fragmentFile;

    /**
     * 该组件对应的渲染模板片段名称
     */
    private String fragmentName;

    public QXCMPComponent(String fragmentFile) {
        this(fragmentFile, "default");
    }

    public QXCMPComponent(String fragmentFile, String fragmentName) {
        this.fragmentFile = fragmentFile;
        this.fragmentName = fragmentName;
    }

    /**
     * @return 该组件经过计算后的CSS ClassName
     */
    public String getClassName() {
        return "";
    }

    /**
     * @return 该组件经过计算后的CSS Style
     */
    public String getStyle() {
        return "";
    }
}
