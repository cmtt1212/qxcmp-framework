package com.qxcmp.framework.view.form2;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Multimap;
import lombok.Data;
import org.springframework.http.HttpMethod;

/**
 * 表单渲染对象
 *
 * @author Aaric
 */
@Data
public class FormView {

    /**
     * 表单标题
     */
    private String caption;

    /**
     * 表单提交Url
     */
    private String action;

    /**
     * 表单提交方法
     */
    private EnctypeEnum enctype = EnctypeEnum.TEXT;

    /**
     * 表单数据编码方式
     */
    private HttpMethod method;

    /**
     * 表单提交按钮文本
     */
    private String submitTitle;

    /**
     * 表单字段信息
     */
    private Multimap<String, FormViewField> fields = ArrayListMultimap.create();
}
