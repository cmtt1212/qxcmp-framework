package com.qxcmp.framework.core.web;

import com.qxcmp.framework.view.annotation.FormViewField;
import com.qxcmp.framework.view.form.InputFiledType;
import com.qxcmp.framework.web.view.annotation.form.BooleanField;
import com.qxcmp.framework.web.view.annotation.form.Form;
import com.qxcmp.framework.web.view.annotation.form.TextInputField;
import com.qxcmp.framework.web.view.annotation.form.TextSelectionField;
import lombok.Data;

import javax.validation.constraints.Max;
import javax.validation.constraints.Size;

/**
 * 网站信息配置表单
 *
 * @author aaric
 */
@Form("水印设置")
@Data
public class AdminSettingsWatermarkForm {

    @BooleanField("开启水印")
    private boolean enable;

    @Size(max = 20)
    @TextInputField(value = "水印名称", maxLength = 20)
    private String name;

    @TextSelectionField("水印位置")
    private String position;

    @Max(36)
    @TextInputField("字体大小")
    private int fontSize;
}
