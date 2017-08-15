package com.qxcmp.framework.web.form;

import com.qxcmp.framework.view.annotation.FormView;
import com.qxcmp.framework.view.annotation.FormViewField;
import com.qxcmp.framework.view.form.InputFiledType;
import lombok.Data;

/**
 * 账户注册配置表单
 *
 * @author aaric
 */
@FormView(caption = "账户注册配置")
@Data
public class AdminSiteAccountForm {

    @FormViewField(label = "是否开启用户名注册模块", type = InputFiledType.SWITCH, labelOn = "开启", labelOff = "关闭")
    private boolean enableUsername;

    @FormViewField(label = "是否开启邮箱注册模块", type = InputFiledType.SWITCH, labelOn = "开启", labelOff = "关闭")
    private boolean enableEmail;

    @FormViewField(label = "是否开启手机号注册模块", type = InputFiledType.SWITCH, labelOn = "开启", labelOff = "关闭")
    private boolean enablePhone;

    @FormViewField(label = "是否开启邀请码注册模块", type = InputFiledType.SWITCH, labelOn = "开启", labelOff = "关闭")
    private boolean enableInvite;
}
