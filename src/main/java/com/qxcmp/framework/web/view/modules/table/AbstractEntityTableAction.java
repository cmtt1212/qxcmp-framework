package com.qxcmp.framework.web.view.modules.table;

import com.qxcmp.framework.web.view.modules.form.FormMethod;
import com.qxcmp.framework.web.view.support.AnchorTarget;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public abstract class AbstractEntityTableAction {

    private String title;

    private String action;

    private boolean form;

    private FormMethod method;

    private AnchorTarget target;
}
