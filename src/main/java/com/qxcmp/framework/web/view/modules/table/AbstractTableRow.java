package com.qxcmp.framework.web.view.modules.table;

import com.google.common.collect.Lists;
import com.qxcmp.framework.web.view.AbstractComponent;
import com.qxcmp.framework.web.view.support.Alignment;
import com.qxcmp.framework.web.view.support.VerticalAlignment;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * 数据表格行组件抽象类
 *
 * @author Aaric
 */
@Getter
@Setter
public abstract class AbstractTableRow extends AbstractTableComponent {

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
     * 对齐方式
     */
    private Alignment alignment = Alignment.NONE;

    /**
     * 垂直对齐方式
     */
    private VerticalAlignment verticalAlignment = VerticalAlignment.NONE;

    /**
     * 行单元格
     */
    private List<AbstractTableCell> cells = Lists.newArrayList();
}
