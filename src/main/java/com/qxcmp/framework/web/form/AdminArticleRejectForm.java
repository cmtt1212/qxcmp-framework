package com.qxcmp.framework.web.form;

import com.qxcmp.framework.view.annotation.FormView;
import com.qxcmp.framework.view.annotation.FormViewField;
import com.qxcmp.framework.view.form.InputFiledType;
import lombok.Data;

import javax.validation.constraints.Size;

@FormView(caption = "驳回文章", action = "$SELF", disableSubmitIcon = true, submitTitle = "确认驳回")
@Data
public class AdminArticleRejectForm {

    @FormViewField(label = "驳回理由", type = InputFiledType.TEXT, maxLength = 20)
    @Size(max = 20)
    private String reason;
}
