package com.qxcmp.framework.mall;


import com.google.common.collect.Sets;
import com.qxcmp.framework.user.User;
import com.qxcmp.framework.web.view.annotation.table.EntityTable;
import com.qxcmp.framework.web.view.annotation.table.RowAction;
import com.qxcmp.framework.web.view.annotation.table.TableAction;
import com.qxcmp.framework.web.view.annotation.table.TableField;
import lombok.Data;

import javax.persistence.*;
import java.util.Date;
import java.util.Set;

import static com.qxcmp.framework.core.QXCMPConfiguration.QXCMP_BACKEND_URL;

/**
 * 平台商品
 *
 * @author aaric
 */
@EntityTable(value = "商品管理", name = "userStoreCommodity", action = QXCMP_BACKEND_URL + "/mall/user/store/commodity",
        tableActions = @TableAction(value = "添加商品", action = "new", primary = true),
        rowActions = {
                @RowAction(value = "编辑", action = "edit")
        })
@Entity
@Table
@Data
public class Commodity {

    /**
     * 商品主键，由框架自动生成
     */
    @Id
    @GeneratedValue
    private Long id;

    /**
     * 商品封面
     */
    @TableField(value = "封面", image = true, order = Integer.MIN_VALUE)
    private String cover;

    /**
     * 商品介绍相册
     */
    @ElementCollection(fetch = FetchType.EAGER)
    private Set<String> albums = Sets.newLinkedHashSet();

    /**
     * 商品图文详情
     * <p>
     * 当前平台商品尽支持图片描述
     */
    @ElementCollection(fetch = FetchType.EAGER)
    private Set<String> details = Sets.newLinkedHashSet();

    /**
     * 商品所属店铺
     */
    @ManyToOne
    private Store store;

    /**
     * 商品标题
     */
    @TableField("标题")
    private String title;

    /**
     * 商品子标题
     */
    private String subTitle;

    /**
     * 商品分类
     * <p>
     * 如，电脑、手机等
     */
    @ElementCollection(fetch = FetchType.EAGER)
    private Set<String> catalogs = Sets.newLinkedHashSet();

    /**
     * 商品原始价格(分) - 用于界面显示
     */
    private int originPrice;

    /**
     * 商品结算价格(分) - 用于结算订单
     */
    private int sellPrice;

    /**
     * 商品当前库存
     */
    private int inventory;

    /**
     * 销量
     */
    @TableField("销量")
    private int sales;

    /**
     * 商品是否下架，如果商品下架以后，将不会出现在商品池中
     */
    private boolean disabled;

    @ManyToOne(fetch = FetchType.EAGER)
    private User userModified;

    private Date dateCreated;

    private Date dateModified;

    private Date dateDisabled;
}
