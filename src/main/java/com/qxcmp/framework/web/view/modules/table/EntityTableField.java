package com.qxcmp.framework.web.view.modules.table;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EntityTableField {

    private String field;

    private String title;

    private String description;

    private int order;

    private String fieldSuffix;

    private boolean enableUrl;

    private String urlPrefix;

    private String urlEntityIndex;

    private String urlSuffix;
}
