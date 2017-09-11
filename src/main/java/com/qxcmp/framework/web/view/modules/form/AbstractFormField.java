package com.qxcmp.framework.web.view.modules.form;

import com.qxcmp.framework.web.view.AbstractComponent;
import com.qxcmp.framework.web.view.support.Wide;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public abstract class AbstractFormField extends AbstractComponent implements FormItem {

    /**
     * 是否为必选项
     * <p>
     * 会在标签上加上红星
     */
    private boolean required;

    /**
     * 是否为错误状态
     */
    private boolean error;

    /**
     * 错误消息，当为错误状态时自动显示
     */
    private String errorMessage;

    /**
     * 是否为禁用状态
     */
    private boolean disabled;

    /**
     * 是否为内联
     * <p>
     * 内联字段的标签会显示在左边，默认时显示在上面
     */
    private boolean inline;

    /**
     * 字段宽度
     */
    private Wide wide = Wide.NONE;

    @Override
    public String getFragmentFile() {
        return "qxcmp/modules/form";
    }

    @Override
    public String getClassContent() {
        final StringBuilder stringBuilder = new StringBuilder();

        if (required) {
            stringBuilder.append(" required");
        }

        if (error) {
            stringBuilder.append(" error");
        }

        if (disabled) {
            stringBuilder.append(" disabled");
        }

        if (inline) {
            stringBuilder.append(" inline");
        }

        return stringBuilder.append(wide).toString();
    }

    @Override
    public String getClassSuffix() {
        return "field";
    }

    public AbstractFormField setRequired() {
        setRequired(true);
        return this;
    }

    public AbstractFormField setError() {
        setError(true);
        return this;
    }

    public AbstractFormField setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
        return this;
    }

    public AbstractFormField setDisabled() {
        setDisabled(true);
        return this;
    }

    public AbstractFormField setInline() {
        setInline(true);
        return this;
    }
}
