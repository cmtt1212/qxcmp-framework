package com.qxcmp.framework.account;

import com.qxcmp.framework.view.annotation.FormView;
import com.qxcmp.framework.view.annotation.FormViewField;
import com.qxcmp.framework.view.form.InputFiledType;
import lombok.Data;

/**
 * 账户激活表单
 *
 * @author aaric
 */
@FormView(caption = "激活账户", disableSubmitIcon = true)
@Data
public class AccountActivateForm {

    @FormViewField(label = "用户名", type = InputFiledType.TEXT, placeholder = "请输入要激活账户的用户名")
    private String username;

    @FormViewField(label = "验证码", type = InputFiledType.CAPTCHA, placeholder = "请输入验证码")
    private String captcha;
}
