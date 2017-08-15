package com.qxcmp.framework.view.form;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 验证码输入框实体
 *
 * @author aaric
 * @see FormViewFieldEntity
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class CaptchaFieldEntity extends FormViewFieldEntity {

    private String url;

    private CaptchaType captchaType = CaptchaType.IMAGE;
}
