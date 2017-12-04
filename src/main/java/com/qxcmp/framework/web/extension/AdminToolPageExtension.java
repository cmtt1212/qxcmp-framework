package com.qxcmp.framework.web.extension;

import com.qxcmp.framework.core.extension.Extension;

/**
 * 后台工具页面扩展
 *
 * @author Aaric
 */
public interface AdminToolPageExtension extends Extension {

    /**
     * 工具栏图标
     *
     * @return 工具栏图标
     */
    String getIcon();

    /**
     * 工具栏标题
     *
     * @return 工具栏标题
     */
    String getTitle();

    /**
     * 工具栏超链接
     *
     * @return 工具栏超链接
     */
    String getUrl();
}
