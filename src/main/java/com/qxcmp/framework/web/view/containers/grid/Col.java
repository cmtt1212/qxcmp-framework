package com.qxcmp.framework.web.view.containers.grid;

import com.google.common.collect.Lists;
import com.qxcmp.framework.web.view.Component;
import com.qxcmp.framework.web.view.support.Floated;
import com.qxcmp.framework.web.view.support.Wide;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * 网格列组件
 *
 * @author Aaric
 */
@Getter
@Setter
public class Col extends AbstractGridItem {

    /**
     * 默认宽度
     */
    private Wide generalWide = Wide.NONE;

    /**
     * 在电脑端的宽度
     */
    private Wide computerWide = Wide.NONE;

    /**
     * 在平板端的宽度
     */
    private Wide tabletWide = Wide.NONE;

    /**
     * 在移动端的宽度
     */
    private Wide mobileWide = Wide.NONE;

    /**
     * 浮动类型
     */
    private Floated floating = Floated.NONE;

    /**
     * 容器内容
     */
    private List<Component> components = Lists.newArrayList();

    public Col() {
        super();
    }

    public Col(Wide generalWide) {
        this();
        this.generalWide = generalWide;
    }

    public Col addComponent(Component component) {
        components.add(component);
        return this;
    }

    @Override
    public String getFragmentName() {
        return "col";
    }

    @Override
    public String getClassContent() {
        final StringBuilder stringBuilder = new StringBuilder(super.getClassContent());

        stringBuilder.append(generalWide.toString()).append(computerWide.toString()).append(tabletWide.toString()).append(mobileWide.toString()).append(floating.toString());

        return stringBuilder.toString();
    }

    @Override
    public String getClassSuffix() {
        return "column";
    }
}
