package com.qxcmp.framework.web.view.modules.dropdown;

import com.google.common.collect.Lists;
import com.qxcmp.framework.web.view.modules.dropdown.item.AbstractSelectionItem;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 选择下拉框
 * <p>
 * 用于在表单中使用
 *
 * @author Aaric
 */
@Getter
@Setter
@Component
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class SelectDropdown extends AbstractDropdown {

    /**
     * 下拉选择框 name - 用于表单提交
     */
    private String name;

    /**
     * 默认文本
     */
    private String placeholder;

    /**
     * 是否开启搜索
     */
    private boolean search;

    /**
     * 是否支持多选，可以与搜索结合使用
     */
    private boolean multiple;

    /**
     * 下拉选项
     */
    private List<AbstractSelectionItem> items = Lists.newArrayList();

    @Override
    public String getFragmentName() {
        return "select";
    }

    @Override
    public String getClassName() {
        final StringBuilder stringBuilder = new StringBuilder(super.getClassName());

        if (search) {
            stringBuilder.append(" search");
        }

        if (multiple) {
            stringBuilder.append(" multiple");
        }

        stringBuilder.append(" selection dropdown");

        return stringBuilder.toString();
    }
}
