package com.qxcmp.framework.web.form;

import com.qxcmp.framework.view.form.InputFiledType;
import com.qxcmp.framework.view.annotation.FormView;
import com.qxcmp.framework.view.annotation.FormViewField;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import java.util.Date;

/**
 * 生成兑换码表单
 *
 * @author aaric
 */
@FormView(caption = "生成兑换码", submitTitle = "开始生成", disableSubmitIcon = true)
@Data
public class AdminRedeemGenerateForm {

    @FormViewField(label = "业务类型", type = InputFiledType.SELECT, consumeCandidate = true, candidateValueIndex = "toString()", candidateTextIndex = "toString()")
    private String type;

    @FormViewField(label = "业务数据", type = InputFiledType.TEXT)
    private String content;

    @FormViewField(label = "过期时间", type = InputFiledType.DATETIME)
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date dateExpired;

    @Min(value = 1, message = "生成数量必须大于1")
    @Max(value = 100, message = "一次最多生成100张兑换券")
    @FormViewField(label = "生成数量", type = InputFiledType.TEXT)
    private int quantity;
}
