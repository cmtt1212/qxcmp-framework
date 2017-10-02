package com.qxcmp.framework.web.view.components.mall;

import com.qxcmp.framework.mall.ShoppingCartItem;
import com.qxcmp.framework.web.view.AbstractComponent;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class ShoppingCart extends AbstractComponent {

    private List<ShoppingCartItem> items;

    private double totalPrice;

    private int selectCount;

    public ShoppingCart(List<ShoppingCartItem> items, long totalPrice, int selectCount) {
        this.items = items;
        this.totalPrice = totalPrice;
        this.selectCount = selectCount;
    }

    @Override
    public String getFragmentFile() {
        return "qxcmp/components/mall/shopping-cart";
    }

    @Override
    public String getFragmentName() {
        return "shopping-cart";
    }
}
