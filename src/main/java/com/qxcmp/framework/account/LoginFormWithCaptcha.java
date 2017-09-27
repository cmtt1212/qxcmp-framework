package com.qxcmp.framework.account;

import com.qxcmp.framework.web.view.annotation.form.Form;
import com.qxcmp.framework.web.view.annotation.form.ImageCaptchaField;
import com.qxcmp.framework.web.view.annotation.form.PasswordField;
import com.qxcmp.framework.web.view.annotation.form.TextInputField;
import lombok.Data;

@Form(submitIcon = "sign in", action = "/login")
@Data
public class LoginFormWithCaptcha {

    @TextInputField(value = "用户名", tooltip = "用户名/邮箱/手机", maxLength = 30)
    private String username;

    @PasswordField("登录密码")
    private String password;

    @ImageCaptchaField("验证码")
    private String captcha;

}
