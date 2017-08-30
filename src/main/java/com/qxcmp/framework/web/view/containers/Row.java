package com.qxcmp.framework.web.view.containers;

import com.google.common.collect.Lists;
import com.qxcmp.framework.web.view.support.ColumnCount;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.List;

@Getter
@Setter
@Component
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class Row extends AbstractGridElement {

    /**
     * 行的列数
     */
    private ColumnCount columnCount = ColumnCount.NONE;

    /**
     * 是否拉伸行
     * <p>
     * 该属性将拉伸行的内容占满列的最大高度
     */
    private boolean stretched;

    /**
     * 是否居中列元素
     * <p>
     * 当列没有占满行时，将居中显示列元素
     */
    private boolean centered;

    /**
     * 列元素
     */
    private List<Col> columns = Lists.newArrayList();

    public Row() {
        super("row");
    }

    @Override
    public String getClassName() {
        final StringBuilder stringBuilder = new StringBuilder(super.getClassName());

        if (StringUtils.isNotBlank(columnCount.getClassName())) {
            stringBuilder.append(" ").append(columnCount.getClassName());
        }

        if (stretched) {
            stringBuilder.append(" stretched");
        }

        if (centered) {
            stringBuilder.append(" centered");
        }

        stringBuilder.append(" row");

        return stringBuilder.toString();
    }
}
