package com.qxcmp.framework.web.view.elements.input;

import com.qxcmp.framework.web.view.AbstractComponent;
import com.qxcmp.framework.web.view.elements.label.AbstractLabel;

/**
 * 带标签的输入框
 *
 * @author Aaric
 */
public class LabeledInput extends AbstractComponent {

    /**
     * 输入框
     */
    private AbstractInput input;

    /**
     * 标签
     */
    private AbstractLabel label;

    /**
     * 额外标签
     */
    private AbstractLabel extraLabel;

    /**
     * 标签是否靠右显示
     */
    private boolean rightLabel;

    @Override
    public String getFragmentFile() {
        return "qxcmp/elements/input";
    }

    @Override
    public String getFragmentName() {
        return "labeled";
    }
}
