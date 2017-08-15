package com.qxcmp.framework.web.form;


import com.qxcmp.framework.view.annotation.FormView;
import com.qxcmp.framework.view.annotation.FormViewField;
import com.qxcmp.framework.view.form.InputFiledType;
import lombok.Data;

/**
 * 购物车收货人表单
 *
 * @author aaric
 */
@FormView(caption = "设置收货人")
@Data
public class MallCartOrderConsigneeForm {

    @FormViewField(label = "请选择收货人信息", type = InputFiledType.SELECT, consumeCandidate = true, candidateValueIndex = "id", candidateTextIndex = "name")
    private String consigneeId;
}
