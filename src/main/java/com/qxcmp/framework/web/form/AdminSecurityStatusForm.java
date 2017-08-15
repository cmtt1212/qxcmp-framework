package com.qxcmp.framework.web.form;

import com.qxcmp.framework.view.annotation.FormView;
import com.qxcmp.framework.view.annotation.FormViewField;
import com.qxcmp.framework.view.form.InputFiledType;
import lombok.Data;

/**
 * 用户状态表单
 *
 * @author aaric
 */
@FormView(caption = "用户状态")
@Data
public class AdminSecurityStatusForm {

    @FormViewField(type = InputFiledType.HIDDEN)
    private String id;
}
