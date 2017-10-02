package com.qxcmp.framework.web.view.components.mall;

import com.qxcmp.framework.mall.ShoppingCartItem;
import com.qxcmp.framework.web.view.AbstractComponent;
import com.qxcmp.framework.web.view.Component;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class ShoppingCart extends AbstractComponent {

    private List<ShoppingCartItem> items;

    public ShoppingCart(List<ShoppingCartItem> items) {
        this.items = items;
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
