package com.qxcmp.framework.web.view.html;

import com.qxcmp.framework.web.view.AbstractComponent;

/**
 * 自定义脚本
 * <p>
 * 该脚本会添加在 html -> head 元素内
 *
 * @author Aaric
 */
public class JavaScript extends AbstractComponent {

    private String fragmentFile;

    private String fragmentName;

    public JavaScript(String fragmentFile, String fragmentName) {
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
