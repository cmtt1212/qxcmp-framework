package com.qxcmp.framework.web.view.modules.dropdown;

import com.qxcmp.framework.web.view.QXCMPComponent;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Getter
@Setter
@Component
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public abstract class AbstractDropdown extends QXCMPComponent {

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

    public AbstractDropdown() {
        super("qxcmp/modules/dropdown");
    }
}
