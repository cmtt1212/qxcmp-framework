package com.qxcmp.framework.security;

import com.qxcmp.framework.web.view.annotation.table.EntityTable;
import com.qxcmp.framework.web.view.annotation.table.RowAction;
import com.qxcmp.framework.web.view.annotation.table.TableField;
import com.qxcmp.framework.web.view.modules.form.FormMethod;
import com.qxcmp.framework.web.view.support.Color;
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
        rowActions = {
                @RowAction(value = "激活", action = "enable", method = FormMethod.POST, color = Color.GREEN),
                @RowAction(value = "禁用", action = "disable", method = FormMethod.POST, color = Color.RED)
        })
@Entity
@Table
@Data
public class Privilege {

    /**
     * 权限主键，有框架自动生成
     */
    @Id
    @GeneratedValue
    private Long id;

    /**
     * 权限名称
     */
    @TableField("权限名称")
    private String name;

    /**
     * 权限描述信息
     */
    @TableField("权限描述")
    private String description;

    /**
     * 权限是否禁用，若权限禁用以后，该权限关联的所有操作将会失效
     */
    private boolean disabled;

    public Privilege() {
    }

    public Privilege(String name, String description) {
        this.name = name;
        this.description = description;
    }
}
