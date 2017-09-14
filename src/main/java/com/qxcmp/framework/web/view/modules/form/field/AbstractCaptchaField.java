package com.qxcmp.framework.web.view.modules.form.field;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public abstract class AbstractCaptchaField extends BaseHTMLField {

    /**
     * 获取验证码的地址
     */
    private String captchaUrl;

    @Override
    public String getClassSuffix() {
        return "captcha " + super.getClassSuffix();
    }
}
