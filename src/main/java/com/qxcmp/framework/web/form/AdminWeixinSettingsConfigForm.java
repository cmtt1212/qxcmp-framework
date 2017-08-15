package com.qxcmp.framework.web.form;

import com.qxcmp.framework.view.annotation.FormView;
import com.qxcmp.framework.view.annotation.FormViewField;
import com.qxcmp.framework.view.form.InputFiledType;
import lombok.Data;

/**
 * 微信模块配置表单
 *
 * @author aaric
 */
@FormView(caption = "微信公众平台设置")
@Data
public class AdminWeixinSettingsConfigForm {

    @FormViewField(label = "App ID", type = InputFiledType.TEXT, autoFocus = true)
    private String appId;

    @FormViewField(label = "App Secret", type = InputFiledType.TEXT)
    private String secret;

    @FormViewField(label = "Token", type = InputFiledType.TEXT)
    private String token;

    @FormViewField(label = "AES Key", type = InputFiledType.TEXT, required = false)
    private String aesKey;

    @FormViewField(label = "网页授权回调URL", type = InputFiledType.TEXT, required = false)
    private String oauth2Url;

    @FormViewField(label = "是否开启调试模式", type = InputFiledType.SWITCH, labelOn = "开启", labelOff = "禁用")
    private boolean debug;

    @FormViewField(label = "用户关注欢迎语", type = InputFiledType.TEXTAREA, required = false, maxLength = 255)
    private String subscribeMessage;
}
