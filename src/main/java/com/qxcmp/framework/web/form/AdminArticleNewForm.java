package com.qxcmp.framework.web.form;

import com.qxcmp.framework.article.Channel;
import com.qxcmp.framework.core.validation.Image;
import com.qxcmp.framework.view.annotation.FormView;
import com.qxcmp.framework.view.annotation.FormViewField;
import com.qxcmp.framework.view.form.InputFiledType;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * 新建文章表单
 *
 * @author aaric
 */
@FormView(caption = "新建文章", enctype = "multipart/form-data")
@Data
public class AdminArticleNewForm {

    @FormViewField(label = "文章封面", type = InputFiledType.FILE, required = false)
    @Image
    private MultipartFile coverFile;

    @FormViewField(label = "文章封面链接", type = InputFiledType.TEXT, required = false)
    private String cover;

    @FormViewField(label = "文章所属栏目", type = InputFiledType.SELECT, consumeCandidate = true, candidateValueIndex = "id", candidateTextIndex = "name")
    private List<Channel> channelList;

    @FormViewField(label = "文章标题", type = InputFiledType.TEXT, maxLength = 80)
    private String title;

    @FormViewField(label = "文章作者", type = InputFiledType.TEXT, maxLength = 10)
    private String author;

    @FormViewField(label = "文章内容", type = InputFiledType.HTML)
    private String quillContent;

    private String htmlContent;

    @FormViewField(label = "文章摘要", type = InputFiledType.TEXTAREA, maxLength = 140)
    private String digest;
}
