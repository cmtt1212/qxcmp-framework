package com.qxcmp.framework.web.form;

import com.qxcmp.framework.validation.Image;
import com.qxcmp.framework.validation.Username;
import com.qxcmp.framework.view.annotation.FormView;
import com.qxcmp.framework.view.annotation.FormViewField;
import com.qxcmp.framework.view.form.InputFiledType;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.Size;

/**
 * 我的资料表单
 *
 * @author aaric
 */
@FormView(caption = "我的资料", submitTitle = "保存我的资料", enctype = "multipart/form-data", showDialog = false)
@Data
public class AdminAccountProfileForm {

    @FormViewField(type = InputFiledType.FILE, required = false, label = "更换头像")
    @Image
    private MultipartFile portrait;

    @Size(min = 6, max = 20, message = "{Size.username}")
    @Username
    @FormViewField(type = InputFiledType.TEXT, label = "用户名", placeholder = "用户名只能由字母、数字、下划线组成", maxLength = 20)
    private String username;

    @FormViewField(type = InputFiledType.TEXT, label = "昵称", maxLength = 20, required = false)
    @Size(max = 20)
    private String nickname;

    @FormViewField(label = "个性签名", type = InputFiledType.TEXTAREA, maxLength = 140, required = false, placeholder = "输入您的个性签名")
    @Size(max = 140)
    private String personalizedSignature;
}
