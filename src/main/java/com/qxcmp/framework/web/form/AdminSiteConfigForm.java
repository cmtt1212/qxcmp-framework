package com.qxcmp.framework.web.form;

import com.qxcmp.framework.core.validation.Image;
import com.qxcmp.framework.view.annotation.FormView;
import com.qxcmp.framework.view.annotation.FormViewField;
import com.qxcmp.framework.view.form.InputFiledType;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

/**
 * 网站信息配置表单
 *
 * @author aaric
 */
@FormView(caption = "网站信息配置", enctype = "multipart/form-data")
@Data
public class AdminSiteConfigForm {

    @FormViewField(label = "更改网站LOGO", type = InputFiledType.FILE, required = false)
    @Image
    private MultipartFile logoFile;

    @FormViewField(label = "更改网站图标", type = InputFiledType.FILE, required = false)
    @Image
    private MultipartFile faviconFile;

    @FormViewField(label = "网站域名", type = InputFiledType.TEXT, autoFocus = true, required = false)
    private String domain;

    @FormViewField(label = "网站标题", type = InputFiledType.TEXT, maxLength = 20, required = false)
    private String title;

    @FormViewField(label = "网站关键字", type = InputFiledType.TEXT, maxLength = 70, required = false)
    private String keywords;

    @FormViewField(label = "网站描述信息", type = InputFiledType.TEXT, maxLength = 140, required = false)
    private String description;
}
