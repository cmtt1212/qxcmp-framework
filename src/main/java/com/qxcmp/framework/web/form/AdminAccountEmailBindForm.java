package com.qxcmp.framework.web.form;

import com.qxcmp.framework.view.annotation.FormView;
import com.qxcmp.framework.view.annotation.FormViewField;
import com.qxcmp.framework.view.form.InputFiledType;
import com.qxcmp.framework.web.view.annotation.form.Form;
import lombok.Data;

/**
 * 邮箱绑定确认表单
 *
 * @author aaric
 */
@FormView(disableCaption = true, disableSubmitIcon = true, submitTitle = "确认绑定")
@Data
public class AdminAccountEmailBindForm {
    @FormViewField(label = "验证码", type = InputFiledType.TEXT, placeholder = "请输入邮箱绑定验证码", autoFocus = true)
    private String captcha;
}
