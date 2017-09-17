package com.qxcmp.framework.core.web;

import com.qxcmp.framework.web.view.annotation.form.AvatarField;
import com.qxcmp.framework.web.view.annotation.form.Form;
import com.qxcmp.framework.web.view.annotation.form.TextInputField;
import lombok.Data;

/**
 * 网站信息配置表单
 *
 * @author aaric
 */
@Form
@Data
public class AdminSettingsSiteForm {

    @AvatarField(value = "网站LOGO", section = "主要配置")
    private String logo;

    @AvatarField(value = "网站图标", section = "主要配置")
    private String favicon;

    @TextInputField(value = "网站域名", autoFocus = true, section = "主要配置")
    private String domain;

    @TextInputField(value = "网站标题", section = "SEO配置")
    private String title;

    @TextInputField(value = "网站关键字", section = "SEO配置")
    private String keywords;

    @TextInputField(value = "网站描述信息", section = "SEO配置")
    private String description;
}
