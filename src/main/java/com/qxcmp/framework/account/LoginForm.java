package com.qxcmp.framework.account;

import com.qxcmp.framework.web.view.annotation.form.DateTimeField;
import com.qxcmp.framework.web.view.annotation.form.Form;
import com.qxcmp.framework.web.view.annotation.form.PasswordField;
import com.qxcmp.framework.web.view.annotation.form.TextInputField;
import com.qxcmp.framework.web.view.modules.form.support.DateTimeFieldType;
import lombok.Data;

import java.util.Date;

@Form(submitIcon = "sign in")
@Data
public class LoginForm {

    @TextInputField(value = "用户名", tooltip = "用户名/邮箱/手机", maxLength = 30)
    private String username;

    @PasswordField("登录密码")
    private String password;

    @DateTimeField(value = "测试日期", placeholder = "请选择日期")
    private Date date1;

    @DateTimeField(value = "测试日期", type = DateTimeFieldType.DATE, placeholder = "请选择日期")
    private Date date2;

    @DateTimeField(value = "测试日期", type = DateTimeFieldType.TIME, placeholder = "请选择日期")
    private Date date3;
}
