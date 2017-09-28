package com.qxcmp.framework.weixin.web;

import com.qxcmp.framework.web.view.annotation.form.Form;
import com.qxcmp.framework.web.view.annotation.form.TextAreaField;
import lombok.Data;

@Form("公众号菜单")
@Data
public class AdminWeixinMenuForm {

    @TextAreaField(value = "菜单内容", autoFocus = true, rows = 50)
    private String content;
}
