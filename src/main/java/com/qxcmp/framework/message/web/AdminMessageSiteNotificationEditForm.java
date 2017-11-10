package com.qxcmp.framework.message.web;

import com.qxcmp.framework.web.view.annotation.form.Form;

import static com.qxcmp.framework.web.view.support.utils.FormHelper.SELF_ACTION;

@Form(value = "编辑网站通知", action = SELF_ACTION)
public class AdminMessageSiteNotificationEditForm extends AdminMessageSiteNotificationNewForm {
}
