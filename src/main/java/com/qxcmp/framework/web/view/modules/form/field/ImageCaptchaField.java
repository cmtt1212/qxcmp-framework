package com.qxcmp.framework.web.view.modules.form.field;

public class ImageCaptchaField extends AbstractCaptchaField {

    @Override
    public String getFragmentName() {
        return "field-captcha-image";
    }

    @Override
    public String getClassSuffix() {
        return "image " + super.getClassSuffix();
    }
}
