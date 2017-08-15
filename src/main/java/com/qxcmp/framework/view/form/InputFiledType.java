package com.qxcmp.framework.view.form;

/**
 * 输入框类型
 *
 * @author aaric
 */
public enum InputFiledType {
    AUTO("auto"),

    /**
     * 验证码输入框
     */
    CAPTCHA("captcha"),

    /**
     * 文件上传框
     */
    FILE("file"),

    /**
     * 隐藏输入框
     */
    HIDDEN("hidden"),

    /**
     * 只读标签
     */
    LABEL("label"),

    /**
     * 数字输入框
     */
    NUMBER("number"),

    /**
     * 密码输入框
     */
    PASSWORD("password"),

    /**
     * 选择框
     */
    SELECT("select"),

    /**
     * 日期选择框
     */
    DATE("date"),

    /**
     * 时间选择框
     */
    TIME("time"),

    /**
     * 日期和时间选择框
     */
    DATETIME("datetime"),

    /**
     * 布尔值开关
     */
    SWITCH("switch"),

    /**
     * 文本框
     */
    TEXT("text"),

    /**
     * 文本域
     */
    TEXTAREA("textarea"),

    /**
     * 动态列表
     */
    LIST("list"),

    /**
     * 图片集
     */
    ALBUM("album"),

    /**
     * HTML编辑器
     */
    HTML("html"),

    /**
     * 行政区选择组件
     */
    DISTRICT("district");

    private String name;

    InputFiledType(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
