package com.qxcmp.framework.web.view.annotation.table;

import java.lang.annotation.*;

@Documented
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface TableFields {
    TableField[] value();
}
