package com.qxcmp.framework.web.view.modules.dropdown.item;

import com.google.common.collect.Lists;
import com.qxcmp.framework.web.view.support.Direction;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.List;

@Getter
@Setter
@Component
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class MenuDropdownItem extends AbstractTextDropdownItem {

    /**
     * 子菜单打开方向，只支持 LEFT, RIGHT
     */
    private Direction direction = Direction.NONE;

    /**
     * 子选项
     */
    private List<AbstractItem> items = Lists.newArrayList();

    @Override
    public String getFragmentName() {
        return "item-menu";
    }

    @Override
    public String getClassName() {
        final StringBuilder stringBuilder = new StringBuilder(super.getClassName());

        stringBuilder.append(" item");

        return stringBuilder.toString();
    }
}
