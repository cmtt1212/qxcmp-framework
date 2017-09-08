package com.qxcmp.framework.web.view.modules.sidebar;

import com.google.common.collect.Lists;
import com.qxcmp.framework.web.view.AbstractComponent;
import com.qxcmp.framework.web.view.Component;
import com.qxcmp.framework.web.view.support.Direction;
import com.qxcmp.framework.web.view.support.Wide;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.RandomStringUtils;

import java.util.Collection;
import java.util.List;

/**
 * 侧边栏抽象类
 *
 * @author Aaric
 */
@Getter
@Setter
public abstract class AbstractSidebar extends AbstractComponent {

    /**
     * ID
     * <p>
     * 用于 JS 初始化
     */
    private String id = "sidebar-" + RandomStringUtils.randomAlphanumeric(10);

    /**
     * JS 配置
     */
    private SidebarConfig config;

    /**
     * 是否附着事件到指定元素上面
     * <p>
     * 如果该值不为空则为要附着的 selector
     */
    private String attachEventsSelector = "";

    /**
     * 要附着的事件名称
     */
    private String attachEventsName = "show";

    /**
     * 是否初始可见
     */
    private boolean visible;

    /**
     * 方位
     */
    private Direction direction = Direction.LEFT;

    /**
     * 宽度
     */
    private Wide wide = Wide.NONE;

    /**
     * 侧边栏内容
     */
    private List<Component> sideContent = Lists.newArrayList();

    /**
     * 主要区域内容
     */
    private List<Component> contents = Lists.newArrayList();

    public AbstractSidebar addSideContent(Component component) {
        sideContent.add(component);
        return this;
    }

    public AbstractSidebar addSideContents(Collection<? extends Component> components) {
        sideContent.addAll(components);
        return this;
    }

    public AbstractSidebar addContent(Component component) {
        contents.add(component);
        return this;
    }

    public AbstractSidebar addContents(Collection<? extends Component> components) {
        contents.addAll(components);
        return this;
    }

    @Override
    public String getFragmentFile() {
        return "qxcmp/modules/sidebar";
    }

    @Override
    public String getClassPrefix() {
        return "ui";
    }

    @Override
    public String getClassContent() {
        final StringBuilder stringBuilder = new StringBuilder();

        if (visible) {
            stringBuilder.append(" visible");
        }

        return stringBuilder.append(direction).append(wide).toString();
    }

    @Override
    public String getClassSuffix() {
        return "sidebar";
    }

    public AbstractSidebar setConfig(SidebarConfig config) {
        this.config = config;
        return this;
    }

    public AbstractSidebar setAttachEventsSelector(String selector) {
        attachEventsSelector = selector;
        return this;
    }

    public AbstractSidebar setAttachEventsName(String attachEventsName) {
        this.attachEventsName = attachEventsName;
        return this;
    }

    public AbstractSidebar setVisible() {
        setVisible(true);
        return this;
    }

    public AbstractSidebar setDirection(Direction direction) {
        this.direction = direction;
        return this;
    }

    public AbstractSidebar setWide(Wide wide) {
        this.wide = wide;
        return this;
    }
}
