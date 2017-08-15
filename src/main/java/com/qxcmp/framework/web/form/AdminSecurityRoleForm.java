package com.qxcmp.framework.web.form;

import com.qxcmp.framework.security.Privilege;
import com.qxcmp.framework.view.annotation.FormView;
import com.qxcmp.framework.view.annotation.FormViewField;
import com.qxcmp.framework.view.form.InputFiledType;
import lombok.Data;
import org.hibernate.validator.constraints.NotEmpty;

import java.util.ArrayList;
import java.util.List;

/**
 * 角色创建和编辑表单对象
 *
 * @author aaric
 */
@FormView(caption = "角色编辑")
@Data
public class AdminSecurityRoleForm {

    @FormViewField(type = InputFiledType.HIDDEN)
    private Long id;

    @NotEmpty
    @FormViewField(type = InputFiledType.TEXT, label = "角色名称")
    private String name;

    @FormViewField(type = InputFiledType.TEXT, label = "角色描述")
    private String description;

    @FormViewField(consumeCandidate = true, candidateTextIndex = "name", label = "角色权限")
    private List<Privilege> privilegeList = new ArrayList<>();
}
