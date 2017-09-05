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
     * 下拉框文本
     */
    private String text;

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

    public Dropdown(String text) {
        this.text = text;
    }

    @Override
    public String getFragmentName() {
        return "dropdown";
    }

    @Override
    public String getClassContent() {
        final StringBuilder stringBuilder = new StringBuilder(super.getClassContent());

        if (inline) {
            stringBuilder.append(" inline");
        }

        if (floating) {
            stringBuilder.append(" floating");
        }

        stringBuilder.append(pointing);

        return stringBuilder.toString();
    }

    public Dropdown setMenu(DropdownMenu menu) {
        this.menu = menu;
        return this;
    }
}
