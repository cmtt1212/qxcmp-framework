package com.qxcmp.framework.mall.web;

import com.qxcmp.framework.mall.Commodity;
import com.qxcmp.framework.mall.Consignee;
import com.qxcmp.framework.mall.ShoppingCartItem;
import com.qxcmp.framework.web.view.Component;
import com.qxcmp.framework.web.view.components.mall.CommodityActionBar;
import com.qxcmp.framework.web.view.components.mall.CommodityPrice;
import com.qxcmp.framework.web.view.components.mall.ShoppingCart;
import com.qxcmp.framework.web.view.components.mall.ShoppingCartOrderActionBar;
import com.qxcmp.framework.web.view.elements.button.Button;
import com.qxcmp.framework.web.view.elements.container.Container;
import com.qxcmp.framework.web.view.elements.divider.HorizontalDivider;
import com.qxcmp.framework.web.view.elements.grid.Col;
import com.qxcmp.framework.web.view.elements.grid.Grid;
import com.qxcmp.framework.web.view.elements.grid.Row;
import com.qxcmp.framework.web.view.elements.header.*;
import com.qxcmp.framework.web.view.elements.icon.Icon;
import com.qxcmp.framework.web.view.elements.image.Image;
import com.qxcmp.framework.web.view.elements.image.Images;
import com.qxcmp.framework.web.view.elements.segment.Segment;
import com.qxcmp.framework.web.view.support.Alignment;
import com.qxcmp.framework.web.view.support.Color;
import com.qxcmp.framework.web.view.support.Size;
import com.qxcmp.framework.web.view.views.MobileList;
import com.qxcmp.framework.web.view.views.MobileListItem;
import com.qxcmp.framework.web.view.views.Overview;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * 商城前端页面组件生成帮助类
 *
 * @author Aaric
 */
@org.springframework.stereotype.Component
public class MallPageHelper {

    /**
     * 商品详情移动端页面组件
     *
     * @param commodity
     * @return
     */
    public Component nextMobileCommodityDetails(Commodity commodity) {

        final Images details = new Images();

        commodity.getDetails().forEach(s -> details.addImage(new Image(s).setFluid()));

        return new Grid()
                .addItem(new Row().addCol(new Col().addComponent(new Image(commodity.getCover()).setFluid().setBordered())))
                .addItem(new Row().addCol(new Col().addComponent(new Container()
                        .addComponent(new PageHeader(HeaderType.H4, commodity.getTitle()))
                        .addComponent(new ContentHeader(commodity.getSubTitle(), Size.TINY).setColor(Color.RED))
                        .addComponent(new CommodityPrice(commodity))
                ))).addItem(new Row().addCol(new Col().addComponent(new Segment().addComponent(new HorizontalDivider("商品信息")).addComponent(details))))
                .addItem(new Row().addCol(new Col().addComponent(new CommodityActionBar(commodity))));
    }

    public Component nextMobileShoppingCartComponent(List<ShoppingCartItem> items) {

        List<ShoppingCartItem> selectedItems = items.stream().filter(ShoppingCartItem::isSelected).collect(Collectors.toList());

        if (items.isEmpty()) {
            return new Grid().setVerticallyPadded().setContainer()
                    .addItem(new Row().addCol(new Col().addComponent(new Overview(new IconHeader("购物车空空的", new Icon("opencart"))).addLink("去逛逛", "/mall").setAlignment(Alignment.CENTER))));
        }

        return new Grid().setVerticallyPadded().addItem(new Row().addCol(new Col().addComponent(new ShoppingCart(items, getTotalItemPrice(selectedItems), getTotalItemCount(selectedItems)))));
    }

    public Component nextMobileShoppingCartOrder(List<ShoppingCartItem> items, Consignee consignee) {

        if (items.isEmpty()) {
            return new Grid().setVerticallyPadded().setContainer()
                    .addItem(new Row().addCol(new Col().addComponent(new Overview(new IconHeader("购物车空空的", new Icon("opencart"))).addLink("去逛逛", "/mall").setAlignment(Alignment.CENTER))));
        }

        List<ShoppingCartItem> selectedItems = items.stream().filter(ShoppingCartItem::isSelected).collect(Collectors.toList());

        if (selectedItems.isEmpty()) {
            return new Grid().setVerticallyPadded().setContainer()
                    .addItem(new Row().addCol(new Col().addComponent(new Overview(new IconHeader("你还没有选择商品哦", new Icon("opencart"))).addLink("返回选择", "/mall/cart").setAlignment(Alignment.CENTER))));
        }

        AbstractHeader contentHeader;

        if (Objects.nonNull(consignee)) {
            contentHeader = new ContentHeader(String.format("%s %s", consignee.getConsigneeName(), consignee.getTelephone()), Size.SMALL).setSubTitle(consignee.getAddress());
        } else {
            contentHeader = new ContentHeader("你还未设置收货地址", Size.SMALL).setSubTitle("点击设置收货地址");
        }

        Images images = new Images().setSize(Size.TINY);

        selectedItems.forEach(shoppingCartItem -> images.addImage(new Image(shoppingCartItem.getCommodity().getCover())));

        return new Grid().setVerticallyPadded()
                .addItem(new Row().addCol(new Col()
                        .addComponent(new HorizontalDivider("结算订单"))
                        .addComponent(new MobileList().addItem(new MobileListItem(contentHeader, "/mall/consignee")))
                        .addComponent(new MobileList().addItem(new MobileListItem(images, "/mall/cart/order/details", String.format("共%d件", getTotalItemCount(selectedItems)))))))
                .addItem(new Row().addCol(new Col().addComponent(new ShoppingCartOrderActionBar(getTotalItemPrice(selectedItems)))));
    }

    public Component nextMobileConsignee(List<Consignee> consignees) {
        MobileList mobileList = new MobileList();

        consignees.forEach(consignee -> mobileList.addItem(new MobileListItem(new ContentHeader(String.format("%s %s", consignee.getConsigneeName(), consignee.getTelephone()), Size.SMALL).setSubTitle(consignee.getAddress()), "/mall/consignee/select?id=" + consignee.getId())))
        ;

        return new Grid().setVerticallyPadded()
                .addItem(new Row().addCol(new Col()
                        .addComponent(new HorizontalDivider("收货地址"))
                        .addComponent(mobileList)
                        .addComponent(new Container().addComponent(new Button("新建收货地址", "/mall/consignee/new").setFluid()))
                ));
    }

    private int getTotalItemPrice(List<ShoppingCartItem> selectedItems) {
        return selectedItems.stream().map(shoppingCartItem -> shoppingCartItem.getCommodity().getSellPrice() * shoppingCartItem.getQuantity()).reduce(0, (sum, price) -> sum + price);
    }

    private int getTotalItemCount(List<ShoppingCartItem> selectedItems) {
        return selectedItems.stream().map(ShoppingCartItem::getQuantity).reduce(0, (sum, quantity) -> sum + quantity);
    }
}
