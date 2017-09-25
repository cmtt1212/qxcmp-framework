package com.qxcmp.framework.security.web;

import com.google.common.collect.Sets;
import com.qxcmp.framework.security.Privilege;
import com.qxcmp.framework.web.view.annotation.form.Form;
import com.qxcmp.framework.web.view.annotation.form.TextInputField;
import com.qxcmp.framework.web.view.annotation.form.TextSelectionField;
import lombok.Data;

import java.util.Set;

import static com.qxcmp.framework.web.view.support.utils.FormHelper.SELF_ACTION;

@Form(value = "编辑角色", action = SELF_ACTION)
@Data
public class AdminSecurityRoleEditForm {

    @TextInputField(value = "角色名称", required = true, autoFocus = true)
    private String name;

    @TextInputField("角色描述")
    private String description;

    @TextSelectionField(value = "角色权限", itemTextIndex = "name", itemValueIndex = "id")
    private Set<Privilege> privileges = Sets.newHashSet();
}
