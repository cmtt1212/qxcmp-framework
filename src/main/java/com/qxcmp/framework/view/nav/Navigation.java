package com.qxcmp.framework.view.nav;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

/**
 * 导航栏视图组件
 *
 * @author aaric
 * @see NavigationService
 */
@Data
@AllArgsConstructor
public class Navigation {

    /**
     * 导航栏名称
     */
    private String name;

    /**
     * 导航栏类型
     */
    private Type type = Type.NORMAL;

    /**
     * 导航栏顺序
     */
    private int order;

    /**
     * 导航栏项目
     */
    private List<NavigationItem> items;

    public enum Type {

        /**
         * 一般导航栏，用于页面内部导航使用
         */
        NORMAL,

        /**
         * 侧边导航栏，用于后端页面使用
         */
        SIDEBAR,

        /**
         * 盒子导航栏
         */
        BOX,

        /**
         * 用户操作导航栏，用于后端页面使用
         */
        ACTION,

        /**
         * 移动端导航栏
         */
        MOBILE
    }
}
