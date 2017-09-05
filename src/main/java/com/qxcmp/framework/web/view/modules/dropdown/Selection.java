package com.qxcmp.framework.web.view.modules.dropdown;

import lombok.Getter;
import lombok.Setter;

/**
 * 选择下拉框
 * <p>
 * 用于在表单中使用
 *
 * @author Aaric
 */
@Getter
@Setter
public class Selection extends AbstractDropdown {

    /**
     * 下拉选择框 name - 用于表单提交
     */
    private String name;

    /**
     * 默认文本
     */
    private String placeholder;

    /**
     * 是否开启搜索
     */
    private boolean search;

    /**
     * 是否支持多选，可以与搜索结合使用
     */
    private boolean multiple;

    /**
     * 选择框选项菜单
     */
    private SelectionMenu menu;

}
