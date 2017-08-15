package com.qxcmp.framework.web.form;

import com.qxcmp.framework.view.annotation.FormView;
import com.qxcmp.framework.view.annotation.FormViewField;
import com.qxcmp.framework.view.form.InputFiledType;
import lombok.Data;

import javax.validation.constraints.Size;

@FormView(caption = "发布文章", action = "$SELF", disableSubmitIcon = true, submitTitle = "确认发布")
@Data
public class AdminArticlePublishForm {

    @FormViewField(label = "发布备注", type = InputFiledType.TEXT, maxLength = 20)
    @Size(max = 20)
    private String reason;
}
