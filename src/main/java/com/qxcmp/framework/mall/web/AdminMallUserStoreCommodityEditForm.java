package com.qxcmp.framework.mall.web;

import com.qxcmp.framework.web.view.annotation.form.Form;

import static com.qxcmp.framework.web.view.support.utils.FormHelper.SELF_ACTION;

@Form(value = "编辑商品", submitText = "确认编辑", action = SELF_ACTION)
public class AdminMallUserStoreCommodityEditForm extends AdminMallUserStoreCommodityNewForm {
}
