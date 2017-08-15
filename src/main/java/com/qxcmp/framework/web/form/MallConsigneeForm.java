package com.qxcmp.framework.web.form;


import com.qxcmp.framework.view.annotation.FormView;
import com.qxcmp.framework.view.annotation.FormViewField;
import com.qxcmp.framework.view.form.InputFiledType;
import lombok.Data;

/**
 * 收货人表单
 *
 * @author aaric
 */
@FormView(caption = "收货地址")
@Data
public class MallConsigneeForm {

    @FormViewField(type = InputFiledType.HIDDEN)
    private String id;

    @FormViewField(label = "收货人姓名", type = InputFiledType.TEXT)
    private String consigneeName;

    @FormViewField(label = "详细地址", type = InputFiledType.TEXT, placeholder = "街道、楼牌号等")
    private String address;

    @FormViewField(label = "手机号码", type = InputFiledType.TEXT)
    private String telephone;

    @FormViewField(label = "邮箱", type = InputFiledType.TEXT, required = false)
    private String email;

    @FormViewField(label = "标签", type = InputFiledType.TEXT, required = false)
    private String name;
}
