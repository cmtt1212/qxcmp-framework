package com.qxcmp.framework.account.phone;

import com.qxcmp.framework.domain.Code;
import com.qxcmp.framework.validation.Phone;
import com.qxcmp.framework.view.annotation.FormView;
import com.qxcmp.framework.view.annotation.FormViewField;
import com.qxcmp.framework.view.form.CaptchaType;
import com.qxcmp.framework.view.form.InputFiledType;
import lombok.Data;

/**
 * 手机账户密码找回平台统一表单
 * <p>
 * 用户在使用手机号找回密码的时候需要填写的信息
 * <p>
 * 找回流程如下： <ol> <li>用户填写手机号，并点击发送短信验证码按钮</li> <li>验证手机号是否已经注册</li> <li>发送短信验证码到指定手机号</li> <li>用户输入短信验证码</li>
 * <li>如果验证码正确则生成一个密码重置链接并跳转到该链接{@link Code}</li> </ol>
 *
 * @author aaric
 */
@FormView(caption = "找回密码", submitTitle = "立即找回", disableSubmitIcon = true, showDialog = false)
@Data
public class AccountPhoneResetForm {

    @FormViewField(label = "手机号码", type = InputFiledType.TEXT, autoFocus = true, placeholder = "请输入您账户绑定的手机号码")
    @Phone
    private String phone;

    @FormViewField(label = "短信验证码", type = InputFiledType.CAPTCHA, captchaType = CaptchaType.PHONE, placeholder = "请输入短信验证码")
    private String captcha;
}
