package com.qxcmp.framework.web.view.modules.form.field;

import lombok.Getter;
import lombok.Setter;

/**
 * 文本输入框
 *
 * @author Aaric
 */
@Getter
@Setter
public class TextInputField extends AbstractTextField {

    public TextInputField(String name, String value, String label) {
        super(name, value, label);
    }

    @Override
    public String getFragmentName() {
        return "field-text-input";
    }

    @Override
    public String getClassSuffix() {
        return "text input " + super.getClassSuffix();
    }
}
