package com.qxcmp.framework.mall;

import com.google.common.collect.Sets;
import com.qxcmp.framework.user.User;
import lombok.Data;

import javax.persistence.*;
import java.util.Set;

/**
 * 商城店铺实体
 *
 * @author Aaric
 */
@Entity
@Table
@Data
public class Store {

    @Id
    private String id;

    private String cover;

    private String name;

    @ManyToOne
    private User owner;

    @ManyToMany(fetch = FetchType.EAGER)
    private Set<User> admins = Sets.newHashSet();
}
