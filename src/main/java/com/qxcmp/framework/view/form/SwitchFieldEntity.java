package com.qxcmp.framework.view.form;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 布尔值开关实体
 *
 * @author aaric
 * @see FormViewFieldEntity
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class SwitchFieldEntity extends FormViewFieldEntity {

    private String labelOn;

    private String labelOff;
}
