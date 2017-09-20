package com.qxcmp.framework.message.web;

import com.qxcmp.framework.view.annotation.FormView;
import com.qxcmp.framework.view.annotation.FormViewField;
import com.qxcmp.framework.view.form.InputFiledType;
import lombok.Data;

/**
 * 短信发送测试表单
 *
 * @author aaric
 */
@FormView(caption = "短信服务测试")
@Data
public class AdminMessageSmsTestForm {

    @FormViewField(label = "短信模板Code", type = InputFiledType.TEXT, placeholder = "短信模板Code")
    private String templateCode;

    @FormViewField(type = InputFiledType.TEXT, label = "短信模板参数名称", placeholder = "短信模板参数名称")
    private String parameter;

    @FormViewField(type = InputFiledType.TEXT, label = "短信模板参数内容", placeholder = "短信模板参数内容")
    private String content;

    @FormViewField(type = InputFiledType.TEXTAREA, label = "手机号", placeholder = "短息发送目标手机号，多个手机号使用回车分隔", rows = 10)
    private String phone;
}
