package com.qxcmp.framework.link.web;

import com.qxcmp.framework.web.view.annotation.form.Form;

import static com.qxcmp.framework.web.view.support.utils.FormHelper.SELF_ACTION;

@Form(value = "编辑链接", action = SELF_ACTION)
public class AdminLinkEditForm extends AdminLinkNewForm {
}
