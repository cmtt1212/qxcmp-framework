package com.qxcmp.framework.view.form2;

import lombok.Data;

/**
 * 表单字段渲染基本类
 *
 * @author Aaric
 */
@Data
public abstract class FormViewField {

    /**
     * 对应表单对象字段名称
     */
    private String field;

    /**
     * 字段标题
     */
    private String title;

    /**
     * 字段描述
     */
    private String description;

}
