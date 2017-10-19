package com.qxcmp.framework.mall.web;

import com.qxcmp.framework.web.view.annotation.form.Form;
import com.qxcmp.framework.web.view.annotation.form.TextInputField;
import lombok.Data;

import static com.qxcmp.framework.web.view.support.utils.FormHelper.SELF_ACTION;

@Form(value = "编辑商品", submitText = "确认编辑", action = SELF_ACTION)
@Data
public class AdminMallUserStoreCommodityEditForm extends AdminMallUserStoreCommodityNewForm {

    @TextInputField(value = "关联商品编号", section = "商品分类")
    private String parentId;
}
