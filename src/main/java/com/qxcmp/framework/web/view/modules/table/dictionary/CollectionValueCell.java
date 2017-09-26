package com.qxcmp.framework.web.view.modules.table.dictionary;

import com.qxcmp.framework.web.view.elements.label.Label;
import com.qxcmp.framework.web.view.elements.label.Labels;
import com.qxcmp.framework.web.view.modules.table.AbstractTableCell;
import com.qxcmp.framework.web.view.modules.table.TableData;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanWrapperImpl;

import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Getter
@Setter
public class CollectionValueCell extends AbstractDictionaryValueCell {

    private String entityIndex;

    public CollectionValueCell(Collection<?> object) {
        super(object);
    }

    public CollectionValueCell(Collection<?> object, String entityIndex) {
        super(object);
        this.entityIndex = entityIndex;
    }

    @SuppressWarnings("unchecked")
    @Override
    public AbstractTableCell parse() {
        final TableData tableData = new TableData();
        final Labels labels = new Labels();

        List list = (List) ((Collection) object).stream().collect(Collectors.toList());

        list.forEach(item -> {
            final BeanWrapperImpl beanWrapper = new BeanWrapperImpl(item);

            if (StringUtils.isNotBlank(entityIndex)) {
                Object itemValue = beanWrapper.getPropertyValue(entityIndex);
                labels.addLabel(new Label(Objects.nonNull(itemValue) ? itemValue.toString() : ""));
            } else {
                labels.addLabel(new Label(item.toString()));
            }
        });

        tableData.setComponent(labels);
        return tableData;
    }
}
