package com.qxcmp.framework.web.view.modules.table;

import com.qxcmp.framework.web.view.modules.form.FormMethod;
import com.qxcmp.framework.web.view.support.AnchorTarget;
import com.qxcmp.framework.web.view.support.Color;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public abstract class AbstractEntityTableAction {

    private String title;

    private String action;

    private FormMethod method;

    private AnchorTarget target;

    private Color color = Color.NONE;

    private boolean primary;

    private boolean secondary;

    private boolean inverted;

    private boolean basic;
}
