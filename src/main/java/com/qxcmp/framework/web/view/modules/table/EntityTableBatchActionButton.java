package com.qxcmp.framework.web.view.modules.table;

import com.qxcmp.framework.web.view.elements.button.Button;
import com.qxcmp.framework.web.view.modules.form.FormMethod;
import com.qxcmp.framework.web.view.support.AnchorTarget;
import lombok.Getter;
import lombok.Setter;

/**
 * 表单按钮
 * <p>
 * 按钮将以表单的形式提交
 *
 * @author Aaric
 */
@Getter
@Setter
public class EntityTableBatchActionButton extends Button {

    private FormMethod method = FormMethod.POST;

    public EntityTableBatchActionButton(String text) {
        super(text);
    }

    public EntityTableBatchActionButton(String text, String url) {
        super(text, url);
    }

    public EntityTableBatchActionButton(String text, String url, AnchorTarget anchorTarget) {
        super(text, url, anchorTarget);
    }

    @Override
    public String getFragmentFile() {
        return "qxcmp/modules/table";
    }

    @Override
    public String getFragmentName() {
        return "action-button";
    }

    @Override
    public String getClassSuffix() {
        return "batch action " + super.getClassSuffix();
    }

    public EntityTableBatchActionButton setMethod(FormMethod method) {
        this.method = method;
        return this;
    }
}
