package com.qxcmp.framework.site;

import org.springframework.stereotype.Service;

/**
 * 网站相关服务
 *
 * @author Aaric
 */
public interface SiteService {

    /**
     * @return 获取网站名称
     */
    String getTitle();

    /**
     * @return 获取网站域名
     */
    String getDomain();

    /**
     * @return 获取网站 LOGO 链接
     */
    String getLogo();

    /**
     * @return 获取网站图标链接
     */
    String getFavicon();

    /**
     * @return 获取网站关键字
     */
    String getKeywords();

    /**
     * @return 获取网站描述
     */
    String getDescription();
}
