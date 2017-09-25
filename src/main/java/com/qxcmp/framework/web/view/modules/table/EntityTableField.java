package com.qxcmp.framework.web.view.modules.table;

import lombok.Getter;
import lombok.Setter;

import java.lang.reflect.Field;

@Getter
@Setter
public class EntityTableField {

    private Field field;

    private String title;

    private String description;

    private int order;

    private String fieldSuffix;

    private int maxCollectionCount;

    private String collectionEntityIndex;

    private boolean enableUrl;

    private String urlPrefix;

    private String urlEntityIndex;

    private String urlSuffix;
}
