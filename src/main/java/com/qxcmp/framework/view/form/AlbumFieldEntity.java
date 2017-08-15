package com.qxcmp.framework.view.form;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 图片集字段实体
 *
 * @author aaric
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class AlbumFieldEntity extends FormViewFieldEntity {
    private String albumFieldName;

}
