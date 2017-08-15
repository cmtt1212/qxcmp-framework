package com.qxcmp.framework.domain;


import com.google.common.collect.Lists;
import com.qxcmp.framework.view.annotation.TableView;
import com.qxcmp.framework.view.annotation.TableViewAction;
import com.qxcmp.framework.view.annotation.TableViewField;
import lombok.Data;

import javax.persistence.*;
import java.util.List;

import static com.qxcmp.framework.core.QXCMPConfiguration.QXCMP_BACKEND_URL;

/**
 * 平台商品
 *
 * @author aaric
 */
@TableView(caption = "商品列表", actionUrlPrefix = QXCMP_BACKEND_URL + "/mall/commodity/",
        findAction = @TableViewAction(urlPrefix = "/mall/item/", urlSuffix = ".html", urlTarget = "_blank"),
        removeAction = @TableViewAction(disabled = true))
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
    @TableViewField(title = "封面", isImage = true)
    private String cover;

    /**
     * 商品标题
     */
    @TableViewField(title = "名称")
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
    @TableViewField(title = "销售价")
    private int sellPrice;

    /**
     * 该商品是否支持点数兑换
     */
    private boolean enablePoints;

    /**
     * 商品兑换需要的点数
     * <p>
     * 当平台开启积分兑换功能，并且该商品支持积分兑换的时候，可以使用该积分来兑换商品
     */
    private int points;

    /**
     * 商品描述信息
     */
    private String description;

    /**
     * 商品当前库存
     */
    @TableViewField(title = "库存")
    private int inventory;

    /**
     * 商品销量
     */
    @TableViewField(title = "销量")
    private int salesVolume;

    /**
     * 商品是否下架，如果商品下架以后，将不会出现在商品池中
     */
    @TableViewField(title = "是否下架")
    private boolean soldOut;

    /**
     * 商品介绍相册
     */
    @ElementCollection
    private List<String> albums = Lists.newArrayList();

    /**
     * 商品图文详情
     * <p>
     * 当前平台商品尽支持图片描述
     */
    @ElementCollection
    private List<String> details = Lists.newArrayList();
}
