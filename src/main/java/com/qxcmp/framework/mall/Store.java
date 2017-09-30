package com.qxcmp.framework.mall;

import com.google.common.collect.Sets;
import com.qxcmp.framework.user.User;
import com.qxcmp.framework.web.view.annotation.table.EntityTable;
import com.qxcmp.framework.web.view.annotation.table.RowAction;
import com.qxcmp.framework.web.view.annotation.table.TableAction;
import com.qxcmp.framework.web.view.annotation.table.TableField;
import lombok.Data;

import javax.persistence.*;
import java.util.Set;

import static com.qxcmp.framework.core.QXCMPConfiguration.QXCMP_BACKEND_URL;

/**
 * 商城店铺实体
 *
 * @author Aaric
 */
@EntityTable(value = "我的店铺", name = "userStore")
@EntityTable(value = "店铺管理", name = "store", action = QXCMP_BACKEND_URL + "/mall/store",
        tableActions = @TableAction(value = "创建店铺", action = "new", primary = true),
        rowActions = {
                @RowAction(value = "编辑", action = "edit")
        })
@Entity
@Table
@Data
public class Store {

    @Id
    private String id;

    @TableField(value = "封面", image = true)
    private String cover;

    @TableField("店铺名称")
    private String name;

    @ManyToOne
    @TableField(value = "所有者", fieldSuffix = ".username")
    private User owner;

    @ManyToMany(fetch = FetchType.EAGER)
    @TableField(value = "管理员", collectionEntityIndex = "username")
    private Set<User> admins = Sets.newHashSet();
}
