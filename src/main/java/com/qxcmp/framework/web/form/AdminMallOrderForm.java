package com.qxcmp.framework.web.form;

import com.qxcmp.framework.view.annotation.FormView;
import com.qxcmp.framework.view.annotation.FormViewField;
import com.qxcmp.framework.view.form.InputFiledType;
import lombok.Data;

/**
 * 订单管理表单
 *
 * @author aaric
 */
@FormView(caption = "订单处理")
@Data
public class AdminMallOrderForm {

    @FormViewField(label = "订单号", type = InputFiledType.LABEL)
    private String id;

    @FormViewField(label = "下单日期", type = InputFiledType.LABEL)
    private String date;

    @FormViewField(label = "订单状态", type = InputFiledType.LABEL)
    private String status;

    @FormViewField(label = "送货地址", type = InputFiledType.LABEL)
    private String address;

    @FormViewField(label = "订单总金额", type = InputFiledType.LABEL)
    private String payment;

    @FormViewField(label = "是否完成该订单", type = InputFiledType.SWITCH, labelOn = "确认完成", labelOff = "未完成")
    private boolean finished;
}
