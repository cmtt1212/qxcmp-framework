package com.qxcmp.framework.web.form;

import com.qxcmp.framework.view.annotation.FormView;
import com.qxcmp.framework.view.annotation.FormViewField;
import com.qxcmp.framework.view.form.InputFiledType;
import lombok.Data;

@FormView(caption = "系统会话配置（需要重启）")
@Data
public class AdminSiteSessionForm {

    @FormViewField(label = "会话超时时间（秒）", type = InputFiledType.NUMBER)
    private int sessionTimeout;

    @FormViewField(label = "最大会话数", type = InputFiledType.NUMBER)
    private int maxSessionCount;

    @FormViewField(label = "达到最大会话数是否阻止继续登录", type = InputFiledType.SWITCH, labelOn = "阻止", labelOff = "不阻止")
    private boolean preventLogin;
}
