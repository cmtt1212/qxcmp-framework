package com.qxcmp.framework.web.view.modules.form.field;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * 头像上传表单
 *
 * @author Aaric
 */
@Getter
@Setter
public class AvatarField extends AbstractFormField {

    /**
     * 支持的图片类型
     */
    private List<String> supportTypes;

    /**
     * 最大图片大小(KB)
     */
    private long maxSize;

    @Override
    public String getFragmentName() {
        return "field-avatar";
    }

    @Override
    public String getClassSuffix() {
        return "avatar " + super.getClassSuffix();
    }
}
