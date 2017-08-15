package com.qxcmp.framework.web.form;

import com.qxcmp.framework.view.annotation.FormView;
import com.qxcmp.framework.view.annotation.FormViewField;
import com.qxcmp.framework.view.form.InputFiledType;
import lombok.Data;

import static com.qxcmp.framework.core.QXCMPConfiguration.QXCMP_BACKEND_URL;

/**
 * 蜘蛛元数据配置表单
 *
 * @author aaric
 */
@FormView(caption = "蜘蛛配置", action = QXCMP_BACKEND_URL + "/spider/config")
@Data
public class SpiderDefinitionForm {

    /**
     * 蜘蛛所属组
     */
    @FormViewField(label = "蜘蛛所属组", type = InputFiledType.LABEL)
    private String group;

    /**
     * 蜘蛛名称
     */
    @FormViewField(label = "蜘蛛名称", type = InputFiledType.LABEL)
    private String name;

    /**
     * 蜘蛛线程数
     */
    @FormViewField(label = "蜘蛛线程数", type = InputFiledType.NUMBER, max = "50")
    private Integer thread;

    /**
     * 蜘蛛执行顺序
     */
    @FormViewField(label = "蜘蛛优先级，越低越优先", type = InputFiledType.NUMBER, min = "1")
    private Integer order;

    /**
     * 蜘蛛是否禁用
     */
    @FormViewField(label = "蜘蛛是否禁用", type = InputFiledType.SWITCH)
    private Boolean disabled;
}
