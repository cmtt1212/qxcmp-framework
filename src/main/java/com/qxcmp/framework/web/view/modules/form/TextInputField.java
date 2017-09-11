package com.qxcmp.framework.web.view.modules.form;

import lombok.Getter;
import lombok.Setter;

/**
 * 文本输入框
 *
 * @author Aaric
 */
@Getter
@Setter
public class TextInputField extends AbstractFormField {
    public TextInputField(String name, String label) {
        super(name, label);
    }

    @Override
    public String getFragmentName() {
        return "field-text-input";
    }
}
