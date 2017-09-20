package com.qxcmp.framework.message.web;

import com.google.common.collect.Lists;
import com.qxcmp.framework.web.view.annotation.form.DynamicField;
import com.qxcmp.framework.web.view.annotation.form.Form;
import com.qxcmp.framework.web.view.annotation.form.TextInputField;
import lombok.Data;

import java.util.List;

/**
 * 短信发送测试表单
 *
 * @author aaric
 */
@Form(value = "短信发送验证", submitText = "发送验证短信")
@Data
public class AdminMessageSmsVerifyForm {

    @TextInputField("短信模板Code")
    private String templateCode;

    @DynamicField(value = "短信参数", itemHeaders = {"变量名", "变量值"}, itemFields = {"key", "value"})
    private List<SmsMessageParameter> parameters = Lists.newArrayList();

    @DynamicField(value = "发送目标", itemHeaders = "手机号")
    private List<String> phones = Lists.newArrayList();
}
