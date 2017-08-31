package com.qxcmp.framework.web.view.modules.dropdown.item;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public abstract class AbstractTextDropdownItem extends AbstractItem {

    /**
     * 选项文本
     */
    private String text;

    /**
     * 选项描述，靠右侧显示
     * <p>
     * 当使用该属性且下拉框为按钮形式的时候，需要保证下拉框的宽度足够
     */
    private String description;
}
