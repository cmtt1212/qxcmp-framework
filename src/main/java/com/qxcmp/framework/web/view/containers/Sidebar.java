package com.qxcmp.framework.web.view.containers;

import com.google.common.collect.Lists;
import com.qxcmp.framework.web.view.AbstractComponent;
import com.qxcmp.framework.web.view.support.Direction;
import com.qxcmp.framework.web.view.support.Width;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class Sidebar extends AbstractComponent {

    private boolean visible;

    private boolean dimmed;

    private Direction direction = Direction.NONE;

    private Width width = Width.NONE;

    private AbstractComponent sidebarContent;

    private List<AbstractComponent> components = Lists.newArrayList();

    private String context;

    private boolean closable = true;

    private boolean dimPage = true;

    @Override
    public String getFragmentFile() {
        return "qxcmp/containers/sidebar";
    }
}
