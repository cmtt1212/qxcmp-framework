package com.qxcmp.framework.account.phone;

import com.qxcmp.framework.validation.Username;
import com.qxcmp.framework.view.annotation.FormView;
import com.qxcmp.framework.view.annotation.FormViewField;
import com.qxcmp.framework.view.form.CaptchaType;
import com.qxcmp.framework.view.form.InputFiledType;
import lombok.Data;

import javax.validation.constraints.Size;

/**
 * 手机账户注册平台统一表单
 * <p>
 * 用户在使用手机号注册账户的时候需要填写的信息
 *
 * @author aaric
 */
@FormView(caption = "注册新用户", submitTitle = "立即注册", disableSubmitIcon = true, showDialog = false)
@Data
public class AccountPhoneLogonForm {

    /**
     * 用户名
     * <p>
     * 全局唯一，注册的时候需要检查是否已经存在
     */
    @Size(min = 6, max = 20, message = "{Size.username}")
    @Username
    @FormViewField(type = InputFiledType.TEXT, label = "用户名", placeholder = "用户名只能由字母、数字、下划线组成", maxLength = 20)
    private String username;

    /**
     * 手机号码
     * <p>
     * 全局唯一，注册的时候需要检查手机号码是否已经存在
     */
    @FormViewField(label = "手机号码", type = InputFiledType.TEXT)
    private String phone;

    /**
     * 登录密码
     * <p>
     * 用户登录时候需要输入的密码
     */
    @Size(min = 6, max = 20, message = "{Size.password}")
    @FormViewField(label = "设置密码", type = InputFiledType.PASSWORD)
    private String password;

    /**
     * 确认密码
     * <p>
     * 保证用户两次输入的密码一致
     */
    @FormViewField(label = "确认密码", type = InputFiledType.PASSWORD)
    private String passwordConfirm;

    /**
     * 短信验证码
     * <p>
     * 输入的短信验证码需要与会话中的短信验证码一致
     */
    @FormViewField(label = "短信验证码", type = InputFiledType.CAPTCHA, captchaType = CaptchaType.PHONE)
    private String captcha;
}
