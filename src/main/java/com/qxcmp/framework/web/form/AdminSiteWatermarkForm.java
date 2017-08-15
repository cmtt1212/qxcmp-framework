package com.qxcmp.framework.web.form;

import com.qxcmp.framework.view.annotation.FormView;
import com.qxcmp.framework.view.annotation.FormViewField;
import com.qxcmp.framework.view.form.InputFiledType;
import lombok.Data;

import javax.validation.constraints.Max;
import javax.validation.constraints.Size;

/**
 * 水印设置表单
 *
 * @author aaric
 */
@FormView(caption = "水印设置")
@Data
public class AdminSiteWatermarkForm {

    @FormViewField(label = "是否开启水印功能", type = InputFiledType.SWITCH, labelOn = "开启", labelOff = "关闭")
    private boolean enable;

    @FormViewField(label = "水印名称", type = InputFiledType.TEXT, maxLength = 20)
    @Size(max = 20)
    private String name;

    @FormViewField(label = "水印名称", type = InputFiledType.SELECT, consumeCandidate = true, candidateTextIndex = "toString()", candidateValueIndex = "toString()")
    private String position;

    @FormViewField(label = "水印字体大小", type = InputFiledType.NUMBER, max = "36")
    @Max(36)
    private int fontSize;

}
