package com.qxcmp.framework.web.form;

import com.qxcmp.framework.core.validation.Image;
import com.qxcmp.framework.view.form.InputFiledType;
import com.qxcmp.framework.view.annotation.FormView;
import com.qxcmp.framework.view.annotation.FormViewField;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import static com.qxcmp.framework.core.QXCMPConfiguration.QXCMP_BACKEND_URL;

/**
 * 广告表单
 * <p>
 * 用于创建和编辑广告
 *
 * @author aaric
 */
@FormView(caption = "广告详情", action = QXCMP_BACKEND_URL + "/ad", enctype = "multipart/form-data")
@Data
public class AdminAdForm {

    @FormViewField(type = InputFiledType.HIDDEN)
    private Long id;

    @FormViewField(type = InputFiledType.FILE, required = false, label = "广告图片")
    @Image
    private MultipartFile portrait;

    @FormViewField(label = "广告图片链接", type = InputFiledType.TEXT, required = false)
    private String image;

    @FormViewField(label = "广告类型", type = InputFiledType.SELECT, consumeCandidate = true, candidateTextIndex = "toString()", candidateValueIndex = "toString()")
    private String type;

    @FormViewField(label = "广告名称", type = InputFiledType.TEXT, required = false)
    private String title;

    @FormViewField(label = "广告链接", type = InputFiledType.TEXT, required = false)
    private String link;

    @FormViewField(label = "顺序，越小越优先", type = InputFiledType.NUMBER, required = false)
    private int adOrder;
}
