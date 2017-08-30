package com.qxcmp.framework.web.view.containers;

import com.google.common.collect.Lists;
import com.qxcmp.framework.web.view.QXCMPComponent;
import com.qxcmp.framework.web.view.support.Floating;
import com.qxcmp.framework.web.view.support.Wide;
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
public class Col extends AbstractGridElement {

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
    private Floating floating = Floating.NONE;

    /**
     * 容器内容
     */
    private List<QXCMPComponent> components = Lists.newArrayList();

    public Col() {
        super("col");
    }

    @Override
    public String getClassName() {
        final StringBuilder stringBuilder = new StringBuilder(super.getClassName());

        if (StringUtils.isNotBlank(generalWide.getClassName())) {
            stringBuilder.append(" ").append(generalWide.getClassName());
        }

        if (StringUtils.isNotBlank(computerWide.getClassName())) {
            stringBuilder.append(" ").append(computerWide.getClassName()).append(" computer");
        }

        if (StringUtils.isNotBlank(tabletWide.getClassName())) {
            stringBuilder.append(" ").append(tabletWide.getClassName()).append(" tablet");
        }

        if (StringUtils.isNotBlank(mobileWide.getClassName())) {
            stringBuilder.append(" ").append(mobileWide.getClassName()).append(" mobile");
        }

        if (StringUtils.isNotBlank(floating.getClassName())) {
            stringBuilder.append(" ").append(floating.getClassName());
        }

        stringBuilder.append(" column");

        return stringBuilder.toString();
    }
}
