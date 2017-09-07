package com.qxcmp.framework.web.view.modules.table;

import com.qxcmp.framework.web.view.support.Color;
import com.qxcmp.framework.web.view.support.ColumnCount;
import com.qxcmp.framework.web.view.support.Device;
import com.qxcmp.framework.web.view.support.Size;
import lombok.Getter;
import lombok.Setter;

/**
 * 数据表格抽象类
 *
 * @author Aaric
 */
@Getter
@Setter
public abstract class AbstractTable extends AbstractTableComponent {

    /**
     * 表头
     */
    private AbstractTableHeader header;

    /**
     * 表格内容
     */
    private AbstractTableBody body;

    /**
     * 表尾
     */
    private AbstractTableFooter footer;

    /**
     * 是否在单元格之间显示分隔符
     */
    private boolean celled;

    /**
     * 是否为基本表格
     * <p>
     * 该属性为移除表头和表尾的背景色
     */
    private boolean basic;

    /**
     * 是否为纯净表格
     * <p>
     * 该属性在基本表格的基础上再移除表格的外边框
     */
    private boolean veryBasic;

    /**
     * 是否为单行模式
     * <p>
     * 该模式下的单元格不会换行，始终保持单行
     */
    private boolean singleLine;

    /**
     * 是否为固定表格
     * <p>
     * 该属性将会使用 table-layout: fixed 作为数据表格布局
     * <p>
     * 如果和单行模式结合使用，显示不下的数据将以...表示
     */
    private boolean fixed;

    /**
     * 自动堆叠的设备类型
     * <p>
     * 数据表格默认不开启自动堆叠，如需要指定自动堆叠，使用此属性
     */
    private Device stackDevice = Device.NONE;

    /**
     * 是否开始行高亮
     */
    private boolean selectable;

    /**
     * 是否显示单双行的条纹
     */
    private boolean striped;

    /**
     * 是否翻转颜色
     */
    private boolean inverted;

    /**
     * 是否为崩塌
     * <p>
     * 该属性会让单元格只占用实际宽度
     */
    private boolean collapsing;

    /**
     * 是否增加单元格的内边距
     */
    private boolean padded;

    /**
     * 是否更多的增加单元格的内边距
     */
    private boolean veryPadded;

    /**
     * 是否减少单元格的内边距
     */
    private boolean compact;

    /**
     * 是否更多的减少单元格的内边距
     */
    private boolean veryCompact;

    /**
     * 显示指定列的数量
     * <p>
     * 每一列都为等宽
     */
    protected ColumnCount columnCount = ColumnCount.NONE;

    /**
     * 颜色
     */
    private Color color = Color.NONE;

    /**
     * 大小
     */
    private Size size = Size.NONE;
}
