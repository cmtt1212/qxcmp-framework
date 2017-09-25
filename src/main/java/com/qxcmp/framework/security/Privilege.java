package com.qxcmp.framework.security;

import com.qxcmp.framework.view.annotation.FormView;
import com.qxcmp.framework.view.annotation.FormViewField;
import com.qxcmp.framework.view.annotation.ListView;
import com.qxcmp.framework.view.annotation.ListViewField;
import com.qxcmp.framework.view.form.InputFiledType;
import com.qxcmp.framework.view.list.ListViewFieldTypeEnum;
import com.qxcmp.framework.web.view.annotation.table.EntityTable;
import com.qxcmp.framework.web.view.annotation.table.RowAction;
import com.qxcmp.framework.web.view.annotation.table.TableField;
import com.qxcmp.framework.web.view.modules.form.FormMethod;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import static com.qxcmp.framework.core.QXCMPConfiguration.QXCMP_BACKEND_URL;

/**
 * 权限实体定义
 *
 * @author aaric
 */
@EntityTable(value = "权限列表", action = QXCMP_BACKEND_URL + "/security/privilege",
        rowActions = {@RowAction(value = "激活", action = "enable", method = FormMethod.POST), @RowAction(value = "禁用", action = "disable", method = FormMethod.POST)})
@Entity
@Table
@FormView(caption = "权限修改", action = QXCMP_BACKEND_URL + "/security/privilege")
@ListView(baseUrl = QXCMP_BACKEND_URL + "/security/privilege", urlSuffix = "/edit")
@Data
public class Privilege {

    /**
     * 权限主键，有框架自动生成
     */
    @Id
    @GeneratedValue
    @FormViewField(type = InputFiledType.HIDDEN)
    private Long id;

    /**
     * 权限名称
     */
    @FormViewField(type = InputFiledType.LABEL, label = "权限名称")
    @ListViewField(type = ListViewFieldTypeEnum.TITLE)
    @TableField("权限名称")
    private String name;

    /**
     * 权限描述信息
     */
    @FormViewField(type = InputFiledType.LABEL, label = "权限描述")
    @ListViewField(type = ListViewFieldTypeEnum.DESCRIPTION)
    @TableField("权限描述")
    private String description;

    /**
     * 权限是否禁用，若权限禁用以后，该权限关联的所有操作将会失效
     */
    @FormViewField(type = InputFiledType.SWITCH, label = "是否禁用", labelOn = "禁用", labelOff = "启用")
    private boolean disabled;

    public Privilege() {
    }

    public Privilege(String name, String description) {
        this.name = name;
        this.description = description;
    }
}
