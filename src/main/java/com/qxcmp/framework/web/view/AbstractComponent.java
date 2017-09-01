package com.qxcmp.framework.web.view;

/**
 * 页面组件基本类
 * <p>
 * 每个组件最终会被渲染为一个Html元素
 *
 * @author aaric
 */
public abstract class AbstractComponent implements Component {

    /**
     * 该组件对应的渲染模板文件名称
     */
    private String fragmentFile;

    /**
     * 该组件对应的渲染模板片段名称
     */
    private String fragmentName;

    public AbstractComponent(String fragmentFile) {
        this(fragmentFile, DEFAULT_FRAGMENT_NAME);
    }

    public AbstractComponent(String fragmentFile, String fragmentName) {
        this.fragmentFile = fragmentFile;
        this.fragmentName = fragmentName;
    }

    @Override
    public String getFragmentFile() {
        return fragmentFile;
    }

    @Override
    public String getFragmentName() {
        return fragmentName;
    }
}
