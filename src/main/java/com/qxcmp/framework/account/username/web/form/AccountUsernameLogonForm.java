package com.qxcmp.framework.account.username.web.form;

import com.qxcmp.framework.validation.Username;
import com.qxcmp.framework.view.annotation.FormView;
import com.qxcmp.framework.view.annotation.FormViewField;
import com.qxcmp.framework.view.form.InputFiledType;
import lombok.Data;

import javax.validation.constraints.Size;

/**
 * 用户名账户注册平台统一表单
 * <p>
 * 用户在使用用户名账户注册的时候需要填写的信息
 *
 * @author aaric
 */
@FormView(caption = "注册新用户", submitTitle = "立即注册", disableSubmitIcon = true, showDialog = false)
@Data
public class AccountUsernameLogonForm {

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
     * 登录密码
     * <p>
     * 用户登录时候需要输入的密码
     */
    @Size(min = 6, max = 20, message = "{Size.password}")
    @FormViewField(type = InputFiledType.PASSWORD, label = "设置密码", placeholder = "请使用至少两种以上的字符组合", maxLength = 20)
    private String password;

    /**
     * 确认密码
     * <p>
     * 保证用户两次输入的密码一致
     */
    @FormViewField(type = InputFiledType.PASSWORD, label = "确认密码", placeholder = "请再次输入您的登录密码", maxLength = 20)
    private String passwordConfirm;

    /**
     * 会话验证码
     * <p>
     * 用户输入的会话验证码，需要和验证码图片中的信息一致
     */
    @FormViewField(type = InputFiledType.CAPTCHA, label = "验证码", placeholder = "请输入验证码")
    private String captcha;
}
