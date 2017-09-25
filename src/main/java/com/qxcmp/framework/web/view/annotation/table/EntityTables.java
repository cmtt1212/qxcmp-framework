package com.qxcmp.framework.web.view.annotation.table;

import java.lang.annotation.*;

@Documented
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface EntityTables {
    EntityTable[] value();
}
