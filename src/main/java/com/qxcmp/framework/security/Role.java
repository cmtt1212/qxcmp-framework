package com.qxcmp.framework.security;

import com.qxcmp.framework.web.view.annotation.table.EntityTable;
import com.qxcmp.framework.web.view.annotation.table.RowAction;
import com.qxcmp.framework.web.view.annotation.table.TableAction;
import com.qxcmp.framework.web.view.annotation.table.TableField;
import com.qxcmp.framework.web.view.modules.form.FormMethod;
import lombok.Data;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

import static com.qxcmp.framework.core.QXCMPConfiguration.QXCMP_BACKEND_URL;

/**
 * 角色实体定义
 *
 * @author aaric
 */
@EntityTable(value = "角色列表", action = QXCMP_BACKEND_URL + "/security/role",
        tableActions = {@TableAction(value = "新建", action = "new")},
        rowActions = {@RowAction(value = "编辑", action = "edit"), @RowAction(value = "删除", action = "remove", method = FormMethod.POST)}
)
@Entity
@Table
@Data
public class Role {

    /**
     * 角色主键，有框架自动生成
     */
    @Id
    @GeneratedValue
    private Long id;

    /**
     * 角色名称
     */
    @TableField("角色名称")
    private String name;

    /**
     * 角色描述
     */
    @TableField("角色描述")
    private String description;

    /**
     * 角色拥有权限集合
     */
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable
    @TableField(value = "拥有权限", collectionEntityIndex = "name", enableUrl = true, urlPrefix = QXCMP_BACKEND_URL + "/security/privilege", urlEntityIndex = "id")
    private Set<Privilege> privileges = new HashSet<>();
}
