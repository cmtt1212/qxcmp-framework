package com.qxcmp.framework.view.support;

import com.google.common.base.CaseFormat;
import com.qxcmp.framework.view.annotation.ListViewField;
import com.qxcmp.framework.view.list.ListView;
import com.qxcmp.framework.view.list.ListViewItem;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import java.lang.reflect.Field;
import java.util.Map;
import java.util.Objects;
import java.util.function.Consumer;

/**
 * 列表视图生成工具类
 * <p>
 * 用于快速生成列表视图渲染对象
 *
 * @author aaric
 */
@Component
@RequiredArgsConstructor
public class ListViewHelper {

    private final PaginationHelper paginationHelper;

    /**
     * 生成一个列表视图渲染对象
     *
     * @param listName                  列表名称
     * @param tClass                    列表实体类型
     * @param tPage                     分页信息
     * @param paginationQueryParameters 分页查询参数
     * @param <T>                       列表元素类型
     *
     * @return 列表视图渲染对象
     */
    public <T> ListView nextList(String listName, Class<T> tClass, Page<T> tPage, Consumer<Map<String, String>> paginationQueryParameters) {
        ListView.ListViewBuilder listViewBuilder = ListView.builder();

        com.qxcmp.framework.view.annotation.ListView listView = tClass.getAnnotation(com.qxcmp.framework.view.annotation.ListView.class);

        if (Objects.isNull(listView)) {
            throw new IllegalStateException("No ListView Definition in" + tClass.getName());
        }

        tPage.getContent().forEach(t -> {
            ListViewItem.ListViewItemBuilder itemBuilder = ListViewItem.builder();

            for (Field field : tClass.getDeclaredFields()) {
                field.setAccessible(true);
                ListViewField listViewField = field.getAnnotation(ListViewField.class);

                if (Objects.nonNull(listViewField) && (StringUtils.isBlank(listViewField.name()) || listViewField.name().equalsIgnoreCase(listName))) {
                    try {
                        switch (listViewField.type()) {
                            case TITLE:
                                itemBuilder.title(field.get(t).toString());
                                break;
                            case SUBTITLE:
                                itemBuilder.subTitle(field.get(t).toString());
                                break;
                            case DESCRIPTION:
                                itemBuilder.description(field.get(t).toString());
                                break;
                            case EXTRA:
                                itemBuilder.extraContent(field.get(t).toString());
                                break;
                            case IMAGE:
                                itemBuilder.image(field.get(t).toString());
                                break;
                        }
                    } catch (IllegalAccessException ignored) {

                    }
                }

                if (field.getName().equalsIgnoreCase(listView.entityIndex())) {
                    try {
                        itemBuilder.link(listView.baseUrl() + "/" + field.get(t).toString() + listView.urlSuffix());
                    } catch (IllegalAccessException ignored) {
                    }
                }

            }

            listViewBuilder.item(itemBuilder.build());
        });

        if (StringUtils.isNotBlank(listView.baseUrl())) {
            listViewBuilder.pagination(paginationHelper.next(tPage, listView.baseUrl(), paginationQueryParameters));
        } else {
            listViewBuilder.pagination(paginationHelper.next(tPage, String.format("/%s", CaseFormat.UPPER_CAMEL.to(CaseFormat.LOWER_CAMEL, tClass.getSimpleName())), paginationQueryParameters));
        }

        return listViewBuilder.build();
    }
}
