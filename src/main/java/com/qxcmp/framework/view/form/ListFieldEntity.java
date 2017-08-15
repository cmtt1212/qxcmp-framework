package com.qxcmp.framework.view.form;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.ArrayList;
import java.util.List;

/**
 * 列表框实体 该实体会生成多个文本
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class ListFieldEntity extends FormViewFieldEntity {

    private List<String> listField = new ArrayList<>();

    private List<String> listFieldLabel = new ArrayList<>();

    private boolean rawType;
}
