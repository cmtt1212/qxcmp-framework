package com.qxcmp.framework.web.view.modules.form;

import com.google.common.collect.Lists;
import com.qxcmp.framework.web.view.AbstractComponent;
import com.qxcmp.framework.web.view.support.ItemCount;
import lombok.Getter;
import lombok.Setter;

import java.util.Collection;
import java.util.List;

@Getter
@Setter
public abstract class AbstractFormFields extends AbstractComponent implements FormItem {

    /**
     * 字段组字段数
     * <p>
     * 会等分字段宽度
     */
    private ItemCount itemCount = ItemCount.NONE;

    /**
     * 是否为分组字段
     * <p>
     * 一般用于单选或者复选框
     */
    private boolean grouped;

    /**
     * 是否为等宽度
     */
    private boolean equalWidth;

    /**
     * 是否为内联
     * <p>
     * 内联字段的标签会显示在左边，默认时显示在上面
     */
    private boolean inline;

    private List<AbstractFormField> fields = Lists.newArrayList();

    public AbstractFormFields addField(AbstractFormField field) {
        fields.add(field);
        return this;
    }

    public AbstractFormFields addFields(Collection<? extends AbstractFormField> fields) {
        this.fields.addAll(fields);
        return this;
    }

    @Override
    public String getFragmentFile() {
        return "qxcmp/modules/form";
    }

    @Override
    public String getFragmentName() {
        return "fields";
    }

    @Override
    public String getClassContent() {
        final StringBuilder stringBuilder = new StringBuilder();

        if (grouped) {
            stringBuilder.append(" grouped");
        }

        if (equalWidth) {
            stringBuilder.append(" equal width");
        }

        if (inline) {
            stringBuilder.append(" inline");
        }

        return stringBuilder.append(itemCount).toString();
    }

    @Override
    public String getClassSuffix() {
        return "fields";
    }

    public AbstractFormFields setGrouped() {
        setGrouped(true);
        return this;
    }

    public AbstractFormFields setEqualWidth() {
        setEqualWidth(true);
        return this;
    }

    public AbstractFormFields setInline() {
        setInline(true);
        return this;
    }

    public AbstractFormFields setItemCount(ItemCount itemCount) {
        this.itemCount = itemCount;
        return this;
    }
}
