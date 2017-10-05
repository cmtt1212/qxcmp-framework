package com.qxcmp.framework.web.view.html;

import com.qxcmp.framework.web.view.AbstractComponent;

/**
 * 自定义样式表
 *
 * @author Aaric
 */
public class Stylesheet extends AbstractComponent {

    private String fragmentFile;

    private String fragmentName;

    public Stylesheet(String fragmentFile, String fragmentName) {
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
