package com.qxcmp.framework.mall;

import com.google.common.collect.Lists;
import com.qxcmp.framework.view.annotation.TableView;
import com.qxcmp.framework.view.annotation.TableViewAction;
import com.qxcmp.framework.view.annotation.TableViewField;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.List;

import static com.qxcmp.framework.core.QXCMPConfiguration.QXCMP_BACKEND_URL;

/**
 * 商品订单
 *
 * @author aaric
 */
@Entity
@Table
@Data
@TableView(caption = "商品订单", actionUrlPrefix = QXCMP_BACKEND_URL + "/mall/order/",
        createAction = @TableViewAction(disabled = true),
        removeAction = @TableViewAction(disabled = true))
public class CommodityOrder {

    /**
     * 订单号，由平台自动生成
     */
    @Id
    @TableViewField(title = "ID")
    private String id;

    /**
     * 订单所属用户ID
     */
    @NotNull
    private String userId;

    /**
     * 下单时间
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm")
    @TableViewField(title = "下单时间")
    private Date dateCreated;

    /**
     * 订单状态
     */
    @TableViewField(title = "订单状态", fieldSuffix = ".value")
    private OrderStatusEnum status;

    /**
     * 订单总金额
     */
    @TableViewField(title = "总金额(分)")
    private int actualPayment;

    /**
     * 订单项目
     */
    @OneToMany(mappedBy = "commodityOrder", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private List<CommodityOrderItem> items = Lists.newArrayList();

    /**
     * 收货人姓名
     */
    private String consigneeName;

    /**
     * 送货地址
     */
    private String address;

    /**
     * 收货人手机
     */
    private String consigneePhone;

    /**
     * 收货人邮箱
     */
    private String consigneeEmail;
}
