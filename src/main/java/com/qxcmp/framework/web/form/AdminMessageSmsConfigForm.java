package com.qxcmp.framework.web.form;

import com.qxcmp.framework.view.annotation.FormView;
import com.qxcmp.framework.view.annotation.FormViewField;
import com.qxcmp.framework.view.form.InputFiledType;
import lombok.Data;

/**
 * 短信配置表单
 * Created by y27chen on 4/6/2017.
 */
@FormView(caption = "短信服务配置")
@Data
public class AdminMessageSmsConfigForm {

    @FormViewField(type = InputFiledType.TEXT, label = "AccessKey", placeholder = "从阿里云短信服务控制台中获取")
    private String accessKey;

    @FormViewField(type = InputFiledType.TEXT, label = "AccessSecret", placeholder = "从阿里云短信服务控制台中获取")
    private String accessSecret;

    @FormViewField(type = InputFiledType.TEXT, label = "EndPoint", placeholder = "从阿里云短信服务控制台中获取")
    private String endPoint;

    @FormViewField(type = InputFiledType.TEXT, label = "主题名称", placeholder = "从阿里云短信服务控制台中获取")
    private String topicRef;

    @FormViewField(type = InputFiledType.TEXT, label = "短信签名ID", placeholder = "从阿里云短信服务控制台中获取")
    private String sign;

    @FormViewField(type = InputFiledType.TEXT, label = "验证码短信模板CODE", placeholder = "从阿里云短信服务控制台中获取")
    private String captchaTemplate;
}
