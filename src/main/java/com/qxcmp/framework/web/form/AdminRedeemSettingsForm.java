package com.qxcmp.framework.web.form;

import com.qxcmp.framework.view.form.InputFiledType;
import com.qxcmp.framework.view.annotation.FormView;
import com.qxcmp.framework.view.annotation.FormViewField;
import lombok.Data;

/**
 * 兑换码设置表单
 *
 * @author aaric
 */
@FormView(caption = "兑换码设置")
@Data
public class AdminRedeemSettingsForm {

    @FormViewField(label = "是否开启兑换功能（禁用以后所有兑换码的使用将无效）", type = InputFiledType.SWITCH, labelOn = "开启", labelOff = "关闭")
    private boolean enable;

    @FormViewField(label = "兑换码有效时间（单位：秒）", type = InputFiledType.NUMBER)
    private Integer expireDuration;

    @FormViewField(label = "兑换码业务类型（每一个业务占用一行）", type = InputFiledType.TEXTAREA, rows = 5)
    private String type;
}
