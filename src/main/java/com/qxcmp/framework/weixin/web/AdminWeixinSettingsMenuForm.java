package com.qxcmp.framework.weixin.web;

import com.qxcmp.framework.view.annotation.FormView;
import com.qxcmp.framework.view.annotation.FormViewField;
import com.qxcmp.framework.view.form.InputFiledType;
import lombok.Data;

/**
 * 微信公众平台自定义菜单管理表单
 *
 * @author aaric
 */
@FormView(caption = "自定义菜单")
@Data
public class AdminWeixinSettingsMenuForm {

    @FormViewField(label = "菜单内容", type = InputFiledType.TEXTAREA, autoFocus = true)
    private String content;
}
