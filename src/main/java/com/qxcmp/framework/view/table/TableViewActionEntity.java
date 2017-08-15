package com.qxcmp.framework.view.table;

import lombok.Data;
import org.springframework.http.HttpMethod;

/**
 * 表格视图操作实体定义
 * <p>
 * 该实体包含了表格渲染操作列按钮的信息
 *
 * @author aaric
 * @see TableViewEntity
 */
@Data
public class TableViewActionEntity {

    /**
     * 操作名称
     * <p>
     * 用于显示在操作按钮上
     */
    private String title;

    /**
     * 操作类型
     */
    private ActionType type;

    /**
     * 操作是否禁用
     * <p>
     * 默认为启用
     */
    private boolean disabled;

    /**
     * 操作是否为表单
     * <p>
     * 默认为否
     */
    private boolean form;

    /**
     * 操作表单提交方法
     * <p>
     * 当操作为表单的时候生效，默认为 {@link HttpMethod#POST}
     */
    private HttpMethod formMethod;

    private String urlTarget;

    /**
     * 操作目标链接前缀
     */
    private String urlPrefix;

    /**
     * 操作目标链接后缀
     */
    private String urlSuffix;

    /**
     * 操作目标链接实体索引
     */
    private String urlEntityIndex;

    /**
     * 是否禁用实体索引
     * <p>
     * 默认的目标链接为 {@code urlPrefix} + {@code urlEntityIndex} + {@code urlSuffix}
     * <p>
     * 禁用以后为 {@code urlPrefix} + {@code urlSuffix}
     */
    private boolean disableUrlEntityIndex;

    /**
     * 是否显示确认框，当操作为表单的时候有效
     */
    private boolean showDialog;

    /**
     * 确认框标题
     */
    private String dialogTitle;

    /**
     * 确认框描述
     */
    private String dialogDescription;

    /**
     * 表格操作类型枚举
     */
    public enum ActionType {

        /**
         * 表级操作
         */
        TABLE,

        /**
         * 行级操作
         */
        ROW
    }
}
