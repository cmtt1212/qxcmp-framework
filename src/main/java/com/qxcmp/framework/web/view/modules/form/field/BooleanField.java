package com.qxcmp.framework.web.view.modules.form.field;

import lombok.Getter;
import lombok.Setter;

/**
 * 布尔值选择框
 *
 * @author Aaric
 */
@Getter
@Setter
public class BooleanField extends AbstractFormField {

    @Override
    public String getFragmentName() {
        return "field-boolean";
    }
}
