package com.qxcmp.framework.core.web;

import com.qxcmp.framework.web.view.annotation.form.*;
import lombok.Data;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
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

    @Min(1)
    @Max(36)
    @NumberField(value = "字体大小", min = 1, max = 36)
    private int fontSize;
}
