package com.qxcmp.framework.web.form;

import com.google.common.collect.Lists;
import com.qxcmp.framework.config.SystemDictionaryItem;
import com.qxcmp.framework.view.annotation.FormView;
import com.qxcmp.framework.view.annotation.FormViewField;
import com.qxcmp.framework.view.annotation.FormViewListField;
import com.qxcmp.framework.view.form.InputFiledType;
import lombok.Data;

import java.util.List;

/**
 * 系统字典编辑表单
 *
 * @author aaric
 */
@FormView(caption = "编辑系统字典", disableCaption = true)
@Data
public class AdminSiteDictionaryForm {

    @FormViewField(label = "字典名称", type = InputFiledType.HIDDEN)
    private String name;

    @FormViewField(label = "字典项目", type = InputFiledType.LIST, listFiled = @FormViewListField(fields = {"name", "priority"}, labels = {"名称", "顺序"}))
    private List<SystemDictionaryItem> items = Lists.newArrayList();

}
