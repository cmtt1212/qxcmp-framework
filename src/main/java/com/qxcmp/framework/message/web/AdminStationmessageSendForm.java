package com.qxcmp.framework.message.web;


import com.qxcmp.framework.security.Role;
import com.qxcmp.framework.web.view.annotation.form.Form;
import com.qxcmp.framework.web.view.annotation.form.TextAreaField;
import com.qxcmp.framework.web.view.annotation.form.TextInputField;
import com.qxcmp.framework.web.view.annotation.form.TextSelectionField;
import lombok.Data;

import java.util.Set;

import static com.qxcmp.framework.web.view.support.utils.FormHelper.SELF_ACTION;

@Form(value = "发送站内信", action = SELF_ACTION)
@Data
public class AdminStationmessageSendForm {

    @TextInputField("收件人")
    private String receiver;

    @TextSelectionField(value = "收件组", itemTextIndex = "name", itemValueIndex = "id")
    private Set<Role> group;

    @TextAreaField("内容")
    private String content;

}
