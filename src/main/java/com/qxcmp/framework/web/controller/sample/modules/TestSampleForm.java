package com.qxcmp.framework.web.controller.sample.modules;

import com.qxcmp.framework.web.view.annotation.form.Form;
import com.qxcmp.framework.web.view.annotation.form.TextInputField;
import lombok.Data;

@Form
@Data
public class TestSampleForm {


    @TextInputField(value = "用户名", section = "基本资料")
    private String username;

    @TextInputField(value = "昵称", section = "基本资料")
    private String nickname;

    @TextInputField(value = "个性签名", section = "补充资料")
    private String signature;
}
