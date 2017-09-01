package com.qxcmp.framework.web.view;

/**
 * UI 组件接口
 *
 * @author aaric
 */
public interface Component {

    /**
     * @return 获取组件对应的渲染模板文件名称
     */
    String getFragmentFile();

    /**
     * @return 获取组件对应的渲染模板片段名称
     */
    String getFragmentName();

    /**
     * @return 该组件经过计算后的组件 CSS class 名称
     */
    String getClassName();

}
