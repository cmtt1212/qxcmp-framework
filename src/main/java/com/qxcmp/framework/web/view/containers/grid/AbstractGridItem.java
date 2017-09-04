package com.qxcmp.framework.web.view.containers.grid;

import com.qxcmp.framework.web.view.AbstractComponent;
import com.qxcmp.framework.web.view.support.Alignment;
import com.qxcmp.framework.web.view.support.Color;
import com.qxcmp.framework.web.view.support.VerticalAlignment;
import lombok.Getter;
import lombok.Setter;

/**
 * 网格内容抽象类
 * <p>
 * 实现该类的组件可以作为网格的内容
 *
 * @author Aaric
 */
@Getter
@Setter
public abstract class AbstractGridItem extends AbstractComponent {

    /**
     * 颜色
     */
    private Color color = Color.NONE;

    /**
     * 对齐方式
     */
    protected Alignment alignment = Alignment.NONE;

    /**
     * 垂直对齐方式
     */
    protected VerticalAlignment verticalAlignment = VerticalAlignment.NONE;

    @Override
    public String getFragmentFile() {
        return "qxcmp/containers/grid";
    }

    @Override
    public String getClassContent() {
        final StringBuilder stringBuilder = new StringBuilder();

        stringBuilder.append(color.toString()).append(alignment.toString()).append(verticalAlignment.toString());

        return stringBuilder.toString();
    }
}
