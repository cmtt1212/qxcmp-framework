package com.qxcmp.framework.web.form;

import com.qxcmp.framework.core.validation.Phone;
import com.qxcmp.framework.view.annotation.FormView;
import com.qxcmp.framework.view.annotation.FormViewField;
import com.qxcmp.framework.view.form.CaptchaType;
import com.qxcmp.framework.view.form.InputFiledType;
import lombok.Data;

/**
 * 手机号绑定、解绑表单
 *
 * @author aaric
 */
@FormView(caption = "手机绑定", disableSubmitIcon = true, submitTitle = "确认绑定")
@Data
public class AdminAccountPhoneForm {

    @FormViewField(label = "手机号码", type = InputFiledType.TEXT, autoFocus = true, placeholder = "请输入您要绑定的手机号码")
    @Phone
    private String phone;

    @FormViewField(label = "短信验证码", type = InputFiledType.CAPTCHA, captchaType = CaptchaType.PHONE, placeholder = "请输入短信验证码")
    private String captcha;
}
