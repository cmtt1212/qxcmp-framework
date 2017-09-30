package com.qxcmp.framework.mall;


import lombok.Data;
import org.assertj.core.util.Sets;

import javax.persistence.*;
import java.util.Set;

/**
 * 平台商品
 *
 * @author aaric
 */
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
    private String title;

    /**
     * 商品子标题
     */
    private String subTitle;

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
     * 商品是否下架，如果商品下架以后，将不会出现在商品池中
     */
    private boolean disabled;
}
