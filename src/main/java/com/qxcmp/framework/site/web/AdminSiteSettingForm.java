package com.qxcmp.framework.site.web;

import com.qxcmp.framework.core.validation.Image;
import com.qxcmp.framework.web.view.annotation.form.AvatarField;
import com.qxcmp.framework.web.view.annotation.form.Form;
import com.qxcmp.framework.web.view.annotation.form.TextInputField;
import com.qxcmp.framework.web.view.modules.form.FormEnctype;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

/**
 * 网站信息配置表单
 *
 * @author aaric
 */
@Form(enctype = FormEnctype.MULTIPART)
@Data
public class AdminSiteSettingForm {

    @Image
    @AvatarField("网站LOGO")
    private MultipartFile logoFile;

    @Image
    @AvatarField("网站图标")
    private MultipartFile faviconFile;

    @TextInputField(value = "网站域名", autoFocus = true)
    private String domain;

    @TextInputField(value = "网站标题", autoFocus = true)
    private String title;

    @TextInputField(value = "网站关键字", autoFocus = true)
    private String keywords;

    @TextInputField(value = "网站描述信息", autoFocus = true)
    private String description;
}
