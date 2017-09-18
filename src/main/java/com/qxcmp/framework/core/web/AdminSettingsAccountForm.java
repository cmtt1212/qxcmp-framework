package com.qxcmp.framework.core.web;

import com.qxcmp.framework.web.view.annotation.form.BooleanField;
import com.qxcmp.framework.web.view.annotation.form.Form;
import com.qxcmp.framework.web.view.modules.form.field.BooleanFieldStyle;
import lombok.Data;

/**
 * 账户注册配置表单
 *
 * @author aaric
 */
@Form("账户注册配置")
@Data
public class AdminSettingsAccountForm {

    @BooleanField(value = "用户名注册", style = BooleanFieldStyle.TOGGLE)
    private boolean enableUsername;

    @BooleanField(value = "邮箱注册", style = BooleanFieldStyle.TOGGLE)
    private boolean enableEmail;

    @BooleanField(value = "手机号注册", style = BooleanFieldStyle.TOGGLE)
    private boolean enablePhone;

    @BooleanField(value = "邀请码注册", style = BooleanFieldStyle.TOGGLE)
    private boolean enableInvite;
}
