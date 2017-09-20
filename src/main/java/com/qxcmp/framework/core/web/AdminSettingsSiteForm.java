package com.qxcmp.framework.core.web;

import com.qxcmp.framework.web.view.annotation.form.*;
import com.qxcmp.framework.web.view.modules.form.field.BooleanFieldStyle;
import lombok.Data;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.Size;
import java.util.List;

/**
 * 网站信息配置表单
 *
 * @author aaric
 */
@Form("网站配置")
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

    @BooleanField(value = "开启水印", section = "水印设置", style = BooleanFieldStyle.TOGGLE)
    private boolean watermarkEnabled;

    @Size(max = 20)
    @TextInputField(value = "水印名称", maxLength = 20, section = "水印设置")
    private String watermarkName;

    @TextSelectionField(value = "水印位置", section = "水印设置")
    private String watermarkPosition;

    @Min(1)
    @Max(36)
    @NumberField(value = "字体大小", min = 1, max = 36, section = "水印设置")
    private int watermarkFontSize;
}
