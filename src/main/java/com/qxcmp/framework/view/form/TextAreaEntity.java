package com.qxcmp.framework.view.form;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 文本域实体
 *
 * @author aaric
 * @see FormViewFieldEntity
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class TextAreaEntity extends FormViewFieldEntity {

    private int rows;
}
