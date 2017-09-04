package com.qxcmp.framework.web.view.modules.dropdown;

import com.qxcmp.framework.web.view.AbstractComponent;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.RandomStringUtils;

@Getter
@Setter
public abstract class AbstractDropdown extends AbstractComponent {

    /**
     * 每个下拉框拥有一个10位数字的随机ID
     */
    private String id = RandomStringUtils.randomAlphanumeric(10);

    /**
     * 是否显示滚动条
     */
    private boolean scrolling;

    /**
     * 是否为紧凑样式
     */
    private boolean compact;

    /**
     * 是否占满父容器
     */
    private boolean fluid;

    /**
     * 是否为内敛显示
     */
    private boolean inline;

    /**
     * 是否为加载状态
     */
    private boolean loading;

    /**
     * 是否为错误状态
     */
    private boolean error;

    /**
     * JS 配置
     */
    private DropdownConfig dropdownConfig;

    @Override
    public String getFragmentFile() {
        return "qxcmp/modules/dropdown";
    }

    @Override
    public String getClassName() {
        final StringBuilder stringBuilder = new StringBuilder("ui");

        if (scrolling) {
            stringBuilder.append(" scrolling");
        }

        if (compact) {
            stringBuilder.append(" compact");
        }

        if (fluid) {
            stringBuilder.append(" fluid");
        }

        if (inline) {
            stringBuilder.append(" inline");
        }

        if (loading) {
            stringBuilder.append(" loading");
        }

        if (error) {
            stringBuilder.append(" error");
        }

        return stringBuilder.toString();
    }
}
