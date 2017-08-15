package com.qxcmp.framework.web.form;

import com.qxcmp.framework.view.form.InputFiledType;
import com.qxcmp.framework.view.annotation.FormView;
import com.qxcmp.framework.view.annotation.FormViewField;
import lombok.Data;

import javax.validation.constraints.Size;

/**
 * 交易密码设置表单
 *
 * @author aaric
 */
@FormView(caption = "设置交易密码", action = "/finance/password/new")
@Data
public class FinancePayPasswordCreateForm {

    @Size(min = 6, max = 20, message = "密码长度应该在 {2} 到 {1} 之间")
    @FormViewField(label = "交易密码", type = InputFiledType.PASSWORD)
    private String password;

    @Size(min = 6, max = 20, message = "密码长度应该在 {2} 到 {1} 之间")
    @FormViewField(label = "再次确认", type = InputFiledType.PASSWORD)
    private String confirmPassword;
}
