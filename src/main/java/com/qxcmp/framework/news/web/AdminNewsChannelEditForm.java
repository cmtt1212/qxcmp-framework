package com.qxcmp.framework.news.web;

import com.qxcmp.framework.web.view.annotation.form.Form;

import static com.qxcmp.framework.web.view.support.utils.FormHelper.SELF_ACTION;

@Form(value = "编辑栏目", submitText = "确认修改", action = SELF_ACTION)
public class AdminNewsChannelEditForm extends AdminNewsChannelNewForm {
}
