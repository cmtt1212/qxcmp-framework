package com.qxcmp.framework.web.view.containers;

import com.qxcmp.framework.web.view.support.Color;
import com.qxcmp.framework.web.view.support.DeviceVisibility;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.StringUtils;

@Getter
@Setter
public class AbstractGridElement extends AbstractGrid {

    /**
     * 网格容器颜色
     */
    private Color color = Color.NONE;

    /**
     * 设备可见性
     */
    private DeviceVisibility deviceVisibility = DeviceVisibility.NONE;

    public AbstractGridElement(String fragmentName) {
        super(fragmentName);
    }

    @Override
    public String getClassName() {
        final StringBuilder stringBuilder = new StringBuilder(super.getClassName());

        if (StringUtils.isNotBlank(color.getClassName())) {
            stringBuilder.append(" ").append(color.getClassName());
        }

        if (StringUtils.isNotBlank(deviceVisibility.getClassName())) {
            stringBuilder.append(" ").append(deviceVisibility.getClassName());
        }

        return stringBuilder.toString();
    }
}
