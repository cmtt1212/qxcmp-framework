package com.qxcmp.framework.web.form;

import com.qxcmp.framework.view.annotation.FormView;
import com.qxcmp.framework.view.annotation.FormViewField;
import com.qxcmp.framework.view.form.InputFiledType;
import lombok.Data;

import javax.validation.constraints.Size;

/**
 * 密码修改表单
 *
 * @author aaric
 */
@FormView(caption = "密码修改", showDialog = false)
@Data
public class AdminAccountPasswordForm {

    @FormViewField(type = InputFiledType.LABEL, label = "用户名")
    private String username;

    @Size(min = 6, max = 20, message = "密码长度应该在 {2} 到 {1} 之间")
    @FormViewField(type = InputFiledType.PASSWORD, label = "原始密码", maxLength = 20)
    private String oldPassword;

    @Size(min = 6, max = 20, message = "密码长度应该在 {2} 到 {1} 之间")
    @FormViewField(type = InputFiledType.PASSWORD, label = "新密码", maxLength = 20)
    private String newPassword;

    @Size(min = 6, max = 20, message = "密码长度应该在 {2} 到 {1} 之间")
    @FormViewField(type = InputFiledType.PASSWORD, label = "确认密码", maxLength = 20)
    private String passwordConfirm;
}
