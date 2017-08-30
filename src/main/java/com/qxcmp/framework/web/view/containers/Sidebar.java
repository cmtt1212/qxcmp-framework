package com.qxcmp.framework.web.view.containers;

import com.google.common.collect.Lists;
import com.qxcmp.framework.web.view.QXCMPComponent;
import com.qxcmp.framework.web.view.support.Direction;
import com.qxcmp.framework.web.view.support.Width;
import lombok.Data;
import lombok.EqualsAndHashCode;
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
public class Sidebar extends QXCMPComponent {

    public Sidebar() {
        super("qxcmp/containers/sidebar");
    }

    private boolean visible;

    private boolean dimmed;

    private Direction direction = Direction.NONE;

    private Width width = Width.NONE;

    private QXCMPComponent sidebarContent;

    private List<QXCMPComponent> components = Lists.newArrayList();

    private String context;

    private boolean closable = true;

    private boolean dimPage = true;
}
