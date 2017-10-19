package com.qxcmp.framework.account;

import com.qxcmp.framework.web.view.annotation.form.FileUploadField;
import com.qxcmp.framework.web.view.annotation.form.Form;
import lombok.Data;

import java.util.List;

@Form("测试表单")
@Data
public class LoginTestForm {

    @FileUploadField(value = "全景图片", maxCount = 5)
    private List<String> file;
}
