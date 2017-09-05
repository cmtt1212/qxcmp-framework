package com.qxcmp.framework.web.view.modules.dropdown;

import com.qxcmp.framework.web.view.AbstractComponent;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.RandomStringUtils;

@Getter
@Setter
public abstract class AbstractDropdown extends AbstractComponent {

    /**
     * 下拉框ID
     * <p>
     * 用于 JS 初始化
     */
    private String id = "dropdown-" + RandomStringUtils.randomAlphanumeric(10);

    /**
     * JS 配置
     */
    private DropdownConfig dropdownConfig;

    /**
     * 是否为加载状态
     */
    private boolean loading;

    /**
     * 是否为错误状态
     */
    private boolean error;

    /**
     * 是否为禁用状态
     */
    private boolean disabled;

    /**
     * 是否显示滚动条
     */
    private boolean scrolling;

    /**
     * 是否移除最小宽度
     * <p>
     * 将根据内容的宽度自动调整
     */
    private boolean compact;

    /**
     * 是否占满父容器
     */
    private boolean fluid;

    @Override
    public String getFragmentFile() {
        return "qxcmp/modules/dropdown";
    }

}
