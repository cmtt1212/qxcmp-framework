package com.qxcmp.framework.web.form;

import com.qxcmp.framework.security.Role;
import com.qxcmp.framework.view.annotation.FormView;
import com.qxcmp.framework.view.annotation.FormViewField;
import com.qxcmp.framework.view.form.InputFiledType;
import lombok.Data;

import java.util.HashSet;
import java.util.Set;

/**
 * 用户角色表单
 *
 * @author aaric
 */
@FormView(caption = "用户角色")
@Data
public class AdminSecurityUserForm {

    @FormViewField(type = InputFiledType.HIDDEN)
    private String id;

    @FormViewField(type = InputFiledType.LABEL, label = "用户名")
    private String username;

    @FormViewField(type = InputFiledType.SWITCH, label = "账户是否未过期")
    private boolean accountNonExpired;

    @FormViewField(type = InputFiledType.SWITCH, label = "账户是否未锁定")
    private boolean accountNonLocked;

    @FormViewField(type = InputFiledType.SWITCH, label = "密码是否未过期")
    private boolean credentialsNonExpired;

    @FormViewField(type = InputFiledType.SWITCH, label = "账户是否启用")
    private boolean enabled;

    @FormViewField(consumeCandidate = true, candidateTextIndex = "name", label = "拥有角色")
    private Set<Role> roles = new HashSet<>();
}
