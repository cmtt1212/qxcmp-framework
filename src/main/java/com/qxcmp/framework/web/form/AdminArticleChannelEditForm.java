package com.qxcmp.framework.web.form;

import com.google.common.collect.Sets;
import com.qxcmp.framework.core.QXCMPConfiguration;
import com.qxcmp.framework.user.User;
import com.qxcmp.framework.core.validation.Image;
import com.qxcmp.framework.view.annotation.FormView;
import com.qxcmp.framework.view.annotation.FormViewField;
import com.qxcmp.framework.view.form.InputFiledType;
import lombok.Data;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.web.multipart.MultipartFile;

import java.util.Set;

/**
 * 栏目编辑表单
 *
 * @author aaric
 */
@FormView(caption = "编辑栏目", action = QXCMPConfiguration.QXCMP_BACKEND_URL + "/news/channel", enctype = "multipart/form-data")
@Data
public class AdminArticleChannelEditForm {

    @FormViewField(type = InputFiledType.HIDDEN)
    private long id;

    @FormViewField(label = "栏目封面", type = InputFiledType.FILE, required = false)
    @Image
    private MultipartFile coverFile;

    @FormViewField(label = "栏目封面链接", type = InputFiledType.TEXT, required = false)
    private String cover;

    @FormViewField(label = "栏目名称", type = InputFiledType.TEXT)
    @NotEmpty
    private String name;

    @FormViewField(label = "栏目别名，唯一，可以通过该别名可以访问此栏目", type = InputFiledType.TEXT)
    private String alias;

    @FormViewField(label = "栏目分类", type = InputFiledType.SELECT, consumeCandidate = true, candidateValueIndex = "toString()", candidateTextIndex = "toString()")
    private Set<String> catalogs;

    @FormViewField(label = "栏目描述", type = InputFiledType.TEXTAREA, maxLength = 140)
    private String description;

    @FormViewField(label = "栏目所有者", type = InputFiledType.SELECT, consumeCandidate = true, candidateTextIndex = "username", candidateValueIndex = "id")
    private User owner;

    @FormViewField(label = "栏目管理员", type = InputFiledType.TEXT, consumeCandidate = true, candidateTextIndex = "username", candidateValueIndex = "id")
    private Set<User> admins = Sets.newHashSet();

    @FormViewField(label = "栏目页面", type = InputFiledType.HTML)
    private String quillContent;

    private String htmlContent;
}
