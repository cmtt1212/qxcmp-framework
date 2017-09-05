package com.qxcmp.framework.web.view.modules.dropdown;

import com.qxcmp.framework.web.view.support.DropdownPointing;
import lombok.Getter;
import lombok.Setter;

/**
 * 外表为按钮的下拉框
 * <p>
 * 用于在一般情况下使用
 *
 * @author Aaric
 */
@Getter
@Setter
public class Dropdown extends AbstractDropdown {


    /**
     * 是否为内敛显示
     */
    private boolean inline;

    /**
     * 下拉框指向
     */
    private DropdownPointing pointing = DropdownPointing.NONE;

    /**
     * 是否现在元素和下拉框之间的间距
     */
    private boolean floating;

    /**
     * 下拉框菜单
     */
    private DropdownMenu menu;

}
