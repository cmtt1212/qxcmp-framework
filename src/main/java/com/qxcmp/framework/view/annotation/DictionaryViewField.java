package com.qxcmp.framework.view.annotation;

import java.lang.annotation.*;

/**
 * 字典视图字典项注解
 * <p>
 * 该注解使用在类的字段上面
 * <p>
 * 标注了该注解的字段会生成到字典视图中
 *
 * @author aaric
 */
@Documented
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Repeatable(DictionaryViewFields.class)
public @interface DictionaryViewField {

    /**
     * 字典项所属的字典视图名称
     * <p>
     * 如果不填则所有字典视图都会生成
     *
     * @return 字典项所属的字典视图名称
     */
    String name() default "";

    /**
     * @return 字典项名称
     */
    String value();

}
