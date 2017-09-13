package com.qxcmp.framework.web.view.modules.form.field;

/**
 * 密码输入框
 *
 * @author Aaric
 */
public class PasswordField extends TextInputField {
    public PasswordField(String name, String value, String label) {
        super(name, value, label);
    }

    @Override
    public String getFragmentName() {
        return "field-password";
    }

    @Override
    public String getClassSuffix() {
        return "password " + super.getClassSuffix();
    }
}
