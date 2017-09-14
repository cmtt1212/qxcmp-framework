package com.qxcmp.framework.view.form2;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

/**
 * 文本输入框渲染对象
 *
 * @author Aaric
 */
@Getter
@Setter
public class TextInputField extends FormViewField {

    /**
     * 占位符内容
     */
    private String placeholder;

    /**
     * 最长字符
     */
    private String manLength;

    /**
     * 是否自动获取焦点
     */
    private boolean autoFocus;

    /**
     * 是否为只读
     */
    private boolean readOnly;

    /**
     * 是否为必填项
     */
    private boolean required;

    /**
     * 是否为禁用
     */
    private boolean disabled;

}
