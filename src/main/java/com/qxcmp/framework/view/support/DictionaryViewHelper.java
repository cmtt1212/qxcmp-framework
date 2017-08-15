package com.qxcmp.framework.view.support;

import com.qxcmp.framework.view.annotation.DictionaryViewField;
import com.qxcmp.framework.view.dictionary.DictionaryView;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import java.lang.reflect.Field;
import java.util.Objects;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;

/**
 * 字典视图帮助类
 * <p>
 * 用于根据注解动态生成字典视图
 *
 * @author aaric
 */
@Component
public class DictionaryViewHelper {

    /**
     * 根据传入对象生成字典视图
     *
     * @param name   字典视图名称
     * @param object 目标对象
     *
     * @return 与对象绑定的字典视图
     */
    public DictionaryView next(String name, Object object) {
        checkArgument(Objects.nonNull(name));
        checkArgument(Objects.nonNull(object));

        com.qxcmp.framework.view.annotation.DictionaryView dictionaryView = getDictionaryView(name, object);

        checkNotNull(dictionaryView, "No DictionaryView definition in " + object.getClass().getName());

        DictionaryView.DictionaryViewBuilder dictionaryViewBuilder = DictionaryView.builder();

        dictionaryViewBuilder.title(dictionaryView.title());

        configDictionaryView(name, object, dictionaryViewBuilder);

        return dictionaryViewBuilder.build();
    }

    /**
     * 获取对象的字典视图定义
     *
     * @param name   字典视图名称
     * @param object 目标对象
     *
     * @return 如果找不到返回空
     */
    private com.qxcmp.framework.view.annotation.DictionaryView getDictionaryView(String name, Object object) {
        for (com.qxcmp.framework.view.annotation.DictionaryView dictionaryView : object.getClass().getDeclaredAnnotationsByType(com.qxcmp.framework.view.annotation.DictionaryView.class)) {
            if (dictionaryView.name().equals(name)) {
                return dictionaryView;
            }
        }

        return null;
    }

    private void configDictionaryView(String name, Object object, DictionaryView.DictionaryViewBuilder dictionaryViewBuilder) {
        for (Field field : object.getClass().getDeclaredFields()) {

            field.setAccessible(true);

            for (DictionaryViewField dictionaryViewField : field.getDeclaredAnnotationsByType(DictionaryViewField.class)) {
                if (StringUtils.isBlank(dictionaryViewField.name()) || StringUtils.equals(dictionaryViewField.name(), name)) {

                    String key = dictionaryViewField.value();
                    String value;
                    try {
                        value = field.get(object).toString();
                    } catch (IllegalAccessException e) {
                        value = "";
                    }

                    dictionaryViewBuilder.dictionary(key, value);
                }
            }
        }
    }
}
