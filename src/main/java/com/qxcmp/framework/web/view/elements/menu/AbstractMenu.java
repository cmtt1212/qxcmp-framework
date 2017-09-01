package com.qxcmp.framework.web.view.elements.menu;

import com.qxcmp.framework.web.view.AbstractComponent;
import com.qxcmp.framework.web.view.support.Direction;
import com.qxcmp.framework.web.view.support.ItemCount;
import com.qxcmp.framework.web.view.support.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public abstract class AbstractMenu extends AbstractComponent {

    /**
     * 是否为紧凑样式
     */
    private boolean compact;

    /**
     * 显示指定菜单项数量
     * <p>
     * 若使用该属性，所有菜单项将平分菜单宽度
     */
    private ItemCount itemCount = ItemCount.NONE;


    /**
     * 是否为附着菜单
     */
    private boolean attached;

    /**
     * 附着方向，当为附着菜单的时候生效，方向仅支持 TOP, BOTTOM, NONE
     */
    private Direction attachDirection = Direction.NONE;

    /**
     * 菜单大小
     */
    private Size size = Size.NONE;

    public AbstractMenu() {
        super("qxcmp/elements/menu");
    }
}
