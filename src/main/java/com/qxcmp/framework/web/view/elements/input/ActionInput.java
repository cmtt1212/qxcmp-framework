package com.qxcmp.framework.web.view.elements.input;

import com.qxcmp.framework.web.view.AbstractComponent;
import com.qxcmp.framework.web.view.elements.button.AbstractButton;
import com.qxcmp.framework.web.view.modules.dropdown.Dropdown;
import lombok.Getter;
import lombok.Setter;

/**
 * 带按钮的输入框
 *
 * @author Aaric
 */
@Getter
@Setter
public class ActionInput extends AbstractComponent {

    /**
     * 按钮
     */
    private AbstractButton button;

    /**
     * 如果不是按钮，还可以设置为下拉框
     */
    private Dropdown dropdown;

    /**
     * 按钮是否在左边显示
     */
    private boolean leftAction;

    @Override
    public String getFragmentFile() {
        return "qxcmp/elements/input";
    }

    @Override
    public String getFragmentName() {
        return "action";
    }
}
