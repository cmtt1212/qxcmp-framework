package com.qxcmp.framework.web.view.modules;

import com.qxcmp.framework.web.view.Component;
import com.qxcmp.framework.web.view.support.Direction;
import com.qxcmp.framework.web.view.support.Width;
import lombok.Builder;
import lombok.Data;
import lombok.Singular;

import java.util.List;

@Data
@Builder
public class Sidebar implements Component {

    private boolean visible;

    private boolean dimmed;

    @Builder.Default
    private Direction direction = Direction.NONE;

    @Builder.Default
    private Width width = Width.NONE;

    private Component sidebarContent;

    @Singular
    private List<Component> components;

    private String context;

    @Builder.Default
    private boolean closable = true;

    @Builder.Default
    private boolean dimPage = true;

    @Override
    public String getFragmentFile() {
        return "qxcmp/modules/sidebar";
    }
}
