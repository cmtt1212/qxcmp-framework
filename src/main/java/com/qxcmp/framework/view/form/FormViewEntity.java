package com.qxcmp.framework.view.form;

import lombok.Data;
import org.springframework.http.HttpMethod;

import java.util.ArrayList;
import java.util.List;

/**
 * 表单视图实体定义，用于前端渲染，包含了渲染表单的所有信息
 *
 * @author aaric
 * @see FormViewFieldEntity
 */
@Data
public class FormViewEntity {

    private String caption;

    private String description;

    private String className;

    private String action;

    private String enctype;

    private HttpMethod method;

    private String submitTitle;

    private String submitIcon;

    private String dialogTitle;

    private String dialogDescription;

    private boolean disableSubmitTitle;

    private boolean disableSubmitIcon;

    private boolean disableCaption;

    private boolean showDialog;

    private List<FormViewFieldEntity> fields = new ArrayList<>();
}
