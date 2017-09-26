package com.qxcmp.framework.web.form;

import com.qxcmp.framework.web.view.annotation.form.Form;
import com.qxcmp.framework.web.view.annotation.form.PasswordField;
import com.qxcmp.framework.web.view.annotation.form.TextInputField;
import lombok.Data;

@Form(submitIcon = "sign in")
@Data
public class LoginForm {

    @TextInputField(value = "用户名", tooltip = "用户名/邮箱/手机", maxLength = 30)
    private String username;

    @PasswordField("登录密码")
    private String password;
}
