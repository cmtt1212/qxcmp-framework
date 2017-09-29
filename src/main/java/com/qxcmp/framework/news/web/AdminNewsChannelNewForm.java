package com.qxcmp.framework.news.web;

import com.google.common.collect.Sets;
import com.qxcmp.framework.user.User;
import com.qxcmp.framework.web.view.annotation.form.*;
import lombok.Data;

import java.util.Set;

@Form(value = "新建栏目", submitText = "确认创建")
@Data
public class AdminNewsChannelNewForm {

    @AvatarField("栏目封面")
    private String cover;

    @TextInputField("栏目名称")
    private String name;

    @TextAreaField(value = "栏目描述", rows = 4)
    private String description;

    @TextSelectionField(value = "所有者", itemValueIndex = "id", itemTextIndex = "username")
    private User owner;

    @TextSelectionField(value = "管理员", itemValueIndex = "id", itemTextIndex = "username")
    private Set<User> admins = Sets.newHashSet();

    @HtmlField("栏目内容")
    private String content;

    private String contentQuill;
}
