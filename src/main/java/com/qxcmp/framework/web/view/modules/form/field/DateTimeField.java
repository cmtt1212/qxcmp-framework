package com.qxcmp.framework.web.view.modules.form.field;

import com.qxcmp.framework.web.view.modules.form.support.DateTimeFieldType;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DateTimeField extends BaseHTMLField {

    private DateTimeFieldType type = DateTimeFieldType.DATETIME;

    @Override
    public String getFragmentName() {
        return "field-date-time";
    }
}
