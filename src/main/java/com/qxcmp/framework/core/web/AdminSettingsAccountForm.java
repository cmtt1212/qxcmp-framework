package com.qxcmp.framework.core.web;

import com.qxcmp.framework.web.view.annotation.form.BooleanField;
import com.qxcmp.framework.web.view.annotation.form.Form;
import lombok.Data;

/**
 * 账户注册配置表单
 *
 * @author aaric
 */
@Form
@Data
public class AdminSettingsAccountForm {

    @BooleanField("用户名注册")
    private boolean enableUsername;

    @BooleanField("邮箱注册")
    private boolean enableEmail;

    @BooleanField("手机号注册")
    private boolean enablePhone;

    @BooleanField("邀请码注册")
    private boolean enableInvite;
}
