package com.qxcmp.framework.security;

import com.qxcmp.framework.view.annotation.TableView;
import com.qxcmp.framework.view.annotation.TableViewAction;
import com.qxcmp.framework.view.annotation.TableViewField;
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
@Entity
@Table
@TableView(caption = "角色列表", sortAction = @TableViewAction(disabled = true), actionUrlPrefix = QXCMP_BACKEND_URL + "/security/role/", findAction = @TableViewAction(disabled = true))
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
    @TableViewField(title = "角色名称")
    private String name;

    /**
     * 角色描述
     */
    @TableViewField(title = "角色描述")
    private String description;

    /**
     * 角色拥有权限集合
     */
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable
    @TableViewField(title = "拥有权限", isCollection = true, collectionEntityIndex = "name")
    private Set<Privilege> privileges = new HashSet<>();
}
