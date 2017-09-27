package com.qxcmp.framework.weixin.web;

import com.qxcmp.framework.view.annotation.FormView;
import com.qxcmp.framework.view.annotation.FormViewField;
import com.qxcmp.framework.view.form.InputFiledType;
import lombok.Data;

/**
 * 微信支付模块配置表单
 *
 * @author aaric
 */
@FormView(caption = "微信支付设置")
@Data
public class AdminFinancePaymentWeixinSettingsForm {

    @FormViewField(label = "是否开启微信支付功能", type = InputFiledType.SWITCH, labelOn = "开启", labelOff = "关闭")
    private boolean enable;

    @FormViewField(label = "默认支付方式", type = InputFiledType.SELECT, consumeCandidate = true, candidateTextIndex = "toString()", candidateValueIndex = "toString()")
    private String tradeType;

    @FormViewField(label = "App ID", type = InputFiledType.TEXT, autoFocus = true)
    private String appId;

    @FormViewField(label = "商户号", type = InputFiledType.TEXT, required = false)
    private String mchId;

    @FormViewField(label = "商户密钥", type = InputFiledType.TEXT, required = false)
    private String mchKey;

    @FormViewField(label = "子商户公众账号ID", type = InputFiledType.TEXT, required = false)
    private String subAppId;

    @FormViewField(label = "子商户号", type = InputFiledType.TEXT, required = false)
    private String subMchId;

    @FormViewField(label = "微信支付结果通知Url", type = InputFiledType.TEXT, required = false)
    private String notifyUrl;

    @FormViewField(label = "apiclient_cert.p12的文件的绝对路径", type = InputFiledType.TEXT, required = false)
    private String keyPath;
}
