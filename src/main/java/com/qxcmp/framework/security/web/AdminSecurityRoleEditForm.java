package com.qxcmp.framework.security.web;

import com.qxcmp.framework.web.view.annotation.form.Form;

import static com.qxcmp.framework.web.view.support.utils.FormHelper.SELF_ACTION;

@Form(value = "编辑角色", action = SELF_ACTION)
public class AdminSecurityRoleEditForm extends AdminSecurityRoleNewForm {
}
