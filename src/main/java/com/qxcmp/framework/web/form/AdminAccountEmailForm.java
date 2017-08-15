package com.qxcmp.framework.web.form;

import com.qxcmp.framework.view.annotation.FormView;
import com.qxcmp.framework.view.annotation.FormViewField;
import com.qxcmp.framework.view.form.InputFiledType;
import lombok.Data;
import org.hibernate.validator.constraints.Email;

/**
 * 邮箱绑定、解绑表单
 *
 * @author aaric
 */
@FormView(caption = "邮箱绑定", disableSubmitIcon = true, submitTitle = "发送绑定验证码")
@Data
public class AdminAccountEmailForm {

    @FormViewField(type = InputFiledType.TEXT, label = "邮箱", placeholder = "输入您要绑定的邮箱", autoFocus = true)
    @Email
    private String email;

    @FormViewField(label = "验证码", type = InputFiledType.CAPTCHA, placeholder = "请输入验证码")
    private String captcha;
}
