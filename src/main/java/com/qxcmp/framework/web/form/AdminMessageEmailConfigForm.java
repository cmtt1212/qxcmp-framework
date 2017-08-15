package com.qxcmp.framework.web.form;

import com.qxcmp.framework.validation.Port;
import com.qxcmp.framework.view.annotation.FormView;
import com.qxcmp.framework.view.annotation.FormViewField;
import com.qxcmp.framework.view.form.InputFiledType;
import lombok.Data;

import javax.validation.constraints.Size;

/**
 * 邮件配置表单
 *
 * @author aaric
 */
@FormView(caption = "邮箱服务配置")
@Data
public class AdminMessageEmailConfigForm {

    @FormViewField(type = InputFiledType.TEXT, label = "主机名", placeholder = "邮箱主机名，如smtp.sina.com")
    private String host;

    @FormViewField(type = InputFiledType.NUMBER, label = "邮箱服务端口", placeholder = "默认为25")
    @Port
    private String port;

    @FormViewField(type = InputFiledType.TEXT, label = "邮件服务用户名", placeholder = "邮件服务用户名")
    private String username;

    @FormViewField(type = InputFiledType.TEXT, label = "邮箱服务密码", placeholder = "您的邮箱服务密码", required = false)
    private String password;

    @FormViewField(type = InputFiledType.TEXT, label = "账户激活邮件主题", placeholder = "账户激活邮件主题", maxLength = 140)
    @Size(max = 140)
    private String activateSubject;

    @FormViewField(type = InputFiledType.TEXTAREA, label = "账户激活邮件模板（使用${link}）来代替激活链接", placeholder = "账户激活邮件内容", maxLength = 255)
    @Size(max = 255)
    private String activateContent;

    @FormViewField(type = InputFiledType.TEXT, label = "密码重置邮件主题", placeholder = "密码重置邮件主题", maxLength = 140)
    @Size(max = 140)
    private String resetSubject;

    @FormViewField(type = InputFiledType.TEXTAREA, label = "密码重置邮件模板（使用${link}）来代替密码重置邮件", placeholder = "密码重置邮件模板", maxLength = 255)
    @Size(max = 255)
    private String resetContent;

    @FormViewField(type = InputFiledType.TEXT, label = "账户绑定邮件主题", placeholder = "账户绑定邮件主题", maxLength = 140)
    @Size(max = 140)
    private String bindingSubject;

    @FormViewField(type = InputFiledType.TEXTAREA, label = "账户绑定邮件模板（使用${captcha}）来代替密码重置邮件", placeholder = "账户绑定邮件模板", maxLength = 255)
    @Size(max = 255)
    private String bindingContent;
}
