package com.qxcmp.framework.web.form;

import com.qxcmp.framework.view.form.InputFiledType;
import com.qxcmp.framework.view.annotation.FormView;
import com.qxcmp.framework.view.annotation.FormViewField;
import lombok.Data;

import javax.validation.constraints.Size;

/**
 * 交易密码修改表单
 *
 * @author aaric
 */
@FormView(caption = "修改交易密码", action = "/finance/password/change")
@Data
public class FinancePayPasswordChangeForm {

    @Size(min = 6, max = 20, message = "密码长度应该在 {2} 到 {1} 之间")
    @FormViewField(label = "原始密码", type = InputFiledType.PASSWORD)
    private String originPassword;

    @Size(min = 6, max = 20, message = "密码长度应该在 {2} 到 {1} 之间")
    @FormViewField(label = "新的密码", type = InputFiledType.PASSWORD)
    private String newPassword;

    @Size(min = 6, max = 20, message = "密码长度应该在 {2} 到 {1} 之间")
    @FormViewField(label = "再次确认", type = InputFiledType.PASSWORD)
    private String confirmPassword;
}
