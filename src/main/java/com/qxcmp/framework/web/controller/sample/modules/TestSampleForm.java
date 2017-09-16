package com.qxcmp.framework.web.controller.sample.modules;

import com.qxcmp.framework.core.validation.Username;
import com.qxcmp.framework.web.view.annotation.form.Form;
import com.qxcmp.framework.web.view.annotation.form.PhoneCaptchaField;
import com.qxcmp.framework.web.view.annotation.form.TextAreaField;
import com.qxcmp.framework.web.view.annotation.form.TextInputField;
import com.qxcmp.framework.web.view.modules.form.FormMethod;
import lombok.Data;

@Form(action = "/test/sample/form")
@Data
public class TestSampleForm {

    @TextInputField(value = "用户名", section = "基本资料", required = true, tooltip = "用户名/邮箱/手机", maxLength = 30, placeholder = "请输入用户名")
    @Username
    private String username;

    @TextInputField(value = "昵称", section = "基本资料", required = true, placeholder = "请输入昵称")
    private String nickname;

    @TextInputField(value = "手机号", section = "基本资料", required = true, placeholder = "请输入手机号")
    private String phone;

    //@ImageCaptchaField(value = "验证码", section = "基本资料", placeholder = "请输入验证码")
    @PhoneCaptchaField(value = "短信验证码", section = "基本资料", placeholder = "请输入验证码")
    private String captcha;

    @TextAreaField(value = "个性签名", section = "补充资料", rows = 10, maxLength = 140, placeholder = "请输入个性签名")
    private String signature;
}
