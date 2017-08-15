package com.qxcmp.framework.web.form;

import com.qxcmp.framework.view.form.InputFiledType;
import com.qxcmp.framework.view.annotation.FormView;
import com.qxcmp.framework.view.annotation.FormViewField;
import lombok.Data;

/**
 * 兑换码使用表单
 *
 * @author aaric
 */
@FormView(caption = "使用兑换码", submitTitle = "立即使用", disableSubmitIcon = true)
@Data
public class RedeemForm {

    @FormViewField(label = "兑换码", placeholder = "请输入兑换码", type = InputFiledType.TEXT)
    private String id;

    /**
     * 使用兑换码回调URL
     */
    @FormViewField(type = InputFiledType.HIDDEN)
    private String callbackUrl;
}
