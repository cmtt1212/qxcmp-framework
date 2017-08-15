package com.qxcmp.framework.view.nav;

import com.google.common.collect.Sets;
import com.qxcmp.framework.view.component.LinkTarget;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Set;

/**
 * 导航栏项目组件
 *
 * @author aaric
 */
@Data
@AllArgsConstructor
public class NavigationItem {

    /**
     * 标题
     */
    private String title;

    /**
     * 图片，如果图片为空使用图标
     */
    private String image;

    /**
     * 图标，如果图标为空则不显示图片信息
     */
    private String icon;

    /**
     * 超链接
     */
    private String link;

    /**
     * 超链接打开方式
     */
    private String target = LinkTarget.SELF.getValue();

    /**
     * 项目在导航栏中的顺序
     */
    private int order;

    /**
     * 显示该导航栏所需要的权限
     */
    private Set<String> privileges = Sets.newHashSet();
}
