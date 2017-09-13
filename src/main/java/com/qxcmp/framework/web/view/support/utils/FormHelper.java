package com.qxcmp.framework.web.view.support.utils;

import com.qxcmp.framework.web.view.modules.form.Form;
import org.springframework.stereotype.Component;

@Component
public class FormHelper {

    /**
     * 将一个对象转换为表单
     *
     * @param object 对象
     * @return 转换后的表单
     */
    public Form convert(Object object) {
        return new Form();
    }
}
