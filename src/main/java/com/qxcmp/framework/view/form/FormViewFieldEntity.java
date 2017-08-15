package com.qxcmp.framework.view.form;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * 表单字段实体，包含了字段渲染的所有信息，通过子类确定渲染类型
 *
 * @author aaric
 */
@Data
public abstract class FormViewFieldEntity {

    private String field;

    private String className;

    private String label;

    private String description;

    private String placeholder;

    private String accept;

    private String alt;

    private float step;

    private String max;

    private String min;

    private int maxLength;

    private boolean autoFocus;

    private boolean readOnly;

    private boolean required;

    private boolean multiple;

    private boolean disabled;

    private boolean candidateI18n;

    private List candidates = new ArrayList();

    private String candidateValueIndex;

    private String candidateTextIndex;

    private int order;
}
