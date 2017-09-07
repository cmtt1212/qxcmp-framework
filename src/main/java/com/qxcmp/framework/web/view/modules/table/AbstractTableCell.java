package com.qxcmp.framework.web.view.modules.table;

import com.qxcmp.framework.web.view.AbstractComponent;
import com.qxcmp.framework.web.view.Component;
import com.qxcmp.framework.web.view.support.Alignment;
import com.qxcmp.framework.web.view.support.VerticalAlignment;
import com.qxcmp.framework.web.view.support.Wide;
import lombok.Getter;
import lombok.Setter;

/**
 * 数据表格单元格抽象类
 *
 * @author Aaric
 */
@Getter
@Setter
public abstract class AbstractTableCell extends AbstractTableComponent {

    /**
     * 单元格内容
     */
    private Component component;

    /**
     * 单元格文本
     * <p>
     * 当单元格未设置内容时，将使用单元格文本作为内容填充
     */
    private String content;

    /**
     * 行跨度
     */
    private int rowSpan;

    /**
     * 列跨度
     */
    private int colSpan;

    /**
     * 是否为正确状态
     */
    private boolean positive;

    /**
     * 是否为不正确状态
     */
    private boolean negative;

    /**
     * 是否为错误状态
     */
    private boolean error;

    /**
     * 是否为警告状态
     */
    private boolean warning;

    /**
     * 是否为激活状态
     */
    private boolean active;

    /**
     * 是否为禁用状态
     */
    private boolean disabled;

    /**
     * 是否为崩塌
     * <p>
     * 该属性会让单元格只占用实际宽度
     */
    private boolean collapsing;

    /**
     * 对齐方式
     */
    private Alignment alignment = Alignment.NONE;

    /**
     * 垂直对齐方式
     */
    private VerticalAlignment verticalAlignment = VerticalAlignment.NONE;

    /**
     * 是否为可选择
     * <p>
     * 在鼠标悬浮的时候高亮显示
     * <p>
     * 如果使用 a 超链接作为单元格内容，则会让整个单元格都可点击
     */
    private boolean selectable;

    /**
     * 单元格宽度
     */
    private Wide wide = Wide.NONE;
}
