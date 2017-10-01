package com.qxcmp.framework.mall.web;

import com.google.common.collect.Sets;
import com.qxcmp.framework.web.view.annotation.form.*;
import lombok.Data;

import java.util.Set;

@Form(value = "添加商品", submitText = "确认添加")
@Data
public class AdminMallUserStoreCommodityNewForm {

    @AvatarField("商品封面")
    private String cover;

    @AlbumField(value = "商品相册", maxCount = 8)
    private Set<String> albums = Sets.newLinkedHashSet();

    @AlbumField(value = "商品详情", maxCount = 20)
    private Set<String> details = Sets.newLinkedHashSet();

    @TextInputField(value = "标题", required = true, autoFocus = true)
    private String title;

    @TextInputField("子标题")
    private String subTitle;

    @NumberField(value = "商品原价", tooltip = "单位：分")
    private int originPrice;

    @NumberField(value = "商品售价", tooltip = "单位：分")
    private int sellPrice;

    @NumberField(value = "商品库存", tooltip = "库存为零以后商品将不能销售")
    private int inventory;

    @BooleanField("是否下架")
    private boolean disabled;
}
