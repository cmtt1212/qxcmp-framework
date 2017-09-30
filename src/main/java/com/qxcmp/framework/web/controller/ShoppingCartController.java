package com.qxcmp.framework.web.controller;


import com.qxcmp.framework.exception.ShoppingCartServiceException;
import com.qxcmp.framework.mall.*;
import com.qxcmp.framework.user.User;
import com.qxcmp.framework.view.list.ListViewItem;
import com.qxcmp.framework.web.QXCMPFrontendController2;
import com.qxcmp.framework.web.form.MallCartOrderConsigneeForm;
import com.qxcmp.framework.web.form.MallItemForm;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.text.DecimalFormat;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * 购物车相关页面路由
 *
 * @author aaric
 */
@RequiredArgsConstructor
public class ShoppingCartController extends QXCMPFrontendController2 {

    private final ShoppingCartService shoppingCartService;

    private final ShoppingCartItemService shoppingCartItemService;

    private final ConsigneeService consigneeService;

    private final CommodityOrderService commodityOrderService;

    @GetMapping
    public ModelAndView myCart() {
        User user = currentUser();

        List<ShoppingCartItem> items = shoppingCartItemService.findByUser(user.getId());

        List<ShoppingCartItem> selectedItems = items.stream().filter(ShoppingCartItem::isSelected).collect(Collectors.toList());

        if (items.isEmpty()) {
            return builder().setResult("我的购物车", "您还没有添加任何商品哟").setResultNavigation("寻找商品", "/mall/item").build();
        }

        return builder().setTitle("我的购物车")
                .setResult("我的购物车", "")
                .addFragment("qxcmp/mall-widget", "shopping-cart")
                .addObject(items)
                .addObject("count", getCommodityCount(selectedItems))
                .addObject("price", new DecimalFormat("0.00").format(getOrderPrice(selectedItems) / 100))
                .build();
    }

    /**
     * 购物车结算页面
     *
     * @return 购物车结算页面
     */
    @GetMapping("/order")
    public ModelAndView order() throws ShoppingCartServiceException {

        User user = currentUser();

        List<ShoppingCartItem> shoppingCartItems = shoppingCartItemService.findSelectedByUser(user.getId());

        if (shoppingCartItems.isEmpty()) {
            return builder().setResult("无法结算", "您还没有选择任何商品，请先选择要购买的商品").setResultNavigation("马上选择", "/mall/cart").build();
        }

        List<ListViewItem> shoppingCartItemView = shoppingCartItems.stream().map(shoppingCartItem -> ListViewItem.builder()
                .title(shoppingCartItem.getCommodity().getTitle())
                .description(String.format("￥%s x%d", new DecimalFormat("0.00").format(shoppingCartItem.getCommodity().getSellPrice() / 100), shoppingCartItem.getQuantity()))
                .image(shoppingCartItem.getCommodity().getCover())
                .build()).collect(Collectors.toList());

        ShoppingCart shoppingCart = shoppingCartService.findByUserId(user.getId());

        ListViewItem consigneeItem = consigneeService.findOne(shoppingCart.getConsigneeId()).map(consignee ->
                ListViewItem.builder().title(String.format("%s %s", consignee.getName(), consignee.getTelephone())).description(consignee.getAddress()).link("/mall/cart/order/consignee").build())
                .orElseGet(() -> {
                    if (consigneeService.findByUser(user.getId()).isEmpty()) {
                        return ListViewItem.builder().title("暂无收货地址信息").description("请点击添加收货地址").link("/mall/consignee/new").build();
                    } else {
                        return ListViewItem.builder().title("您还没有设置收货地址").description("请点击设置收货地址").link("/mall/cart/order/consignee").build();
                    }
                });

        return builder().setTitle("购物车结算")
                .setResult("确认订单", "")
                .addListView(listViewBuilder -> {
                    listViewBuilder.item(consigneeItem);
                    shoppingCartItemView.forEach(listViewBuilder::item);
                })
                .setResultNavigation(String.format("立即下单(￥%s)", new DecimalFormat("0.00").format(getOrderPrice(shoppingCartItems) / 100)), "/mall/cart/order/confirm")
                .build();
    }

    /**
     * 确认下单
     *
     * @return
     */
    @GetMapping("/order/confirm")
    public ModelAndView orderConfirm() {
        try {
            User user = currentUser();

            List<ShoppingCartItem> shoppingCartItems = shoppingCartItemService.findSelectedByUser(user.getId());

            if (shoppingCartItems.isEmpty()) {
                return builder().setResult("无法下单", "您还没有选择任何商品，请先选择要购买的商品").setResultNavigation("马上选择", "/mall/cart").build();
            }

            ShoppingCart shoppingCart = shoppingCartService.findByUserId(user.getId());

            return commodityOrderService.create(() -> {
                final CommodityOrder commodityOrder = commodityOrderService.next();
                commodityOrder.setUserId(user.getId());
                commodityOrder.setActualPayment((int) getOrderPrice(shoppingCartItems));
                commodityOrder.setItems(shoppingCartItems.stream().map(shoppingCartItem -> {
                    CommodityOrderItem commodityOrderItem = new CommodityOrderItem();
                    commodityOrderItem.setCommodity(shoppingCartItem.getCommodity());
                    commodityOrderItem.setActualPrice(shoppingCartItem.getCommodity().getSellPrice());
                    commodityOrderItem.setQuantity(shoppingCartItem.getQuantity());
                    commodityOrderItem.setCommodityOrder(commodityOrder);
                    return commodityOrderItem;
                }).collect(Collectors.toList()));

                Optional<Consignee> consignee = consigneeService.findOne(shoppingCart.getConsigneeId());

                if (consignee.isPresent()) {
                    commodityOrder.setConsigneeName(consignee.get().getConsigneeName());
                    commodityOrder.setConsigneePhone(consignee.get().getTelephone());
                    commodityOrder.setConsigneeEmail(consignee.get().getEmail());
                    commodityOrder.setAddress(consignee.get().getAddress());
                } else {
                    commodityOrder.setConsigneeName("无收货人信息");
                    commodityOrder.setAddress(consigneeService.findOne(shoppingCart.getConsigneeId()).map(Consignee::getAddress).orElse("没有收货地址"));
                }

                return commodityOrder;
            }).map(commodityOrder -> {
                shoppingCartItems.forEach(shoppingCartItem -> shoppingCartItemService.remove(shoppingCartItem.getId()));
                return redirect("/mall/cashier/" + commodityOrder.getId());
            }).orElse(error(HttpStatus.BAD_GATEWAY, "创建订单失败").build());
        } catch (Exception e) {
            return error(HttpStatus.BAD_GATEWAY, "创建订单失败:" + e.getMessage()).build();
        }
    }

    /**
     * 设置购物车收货人
     *
     * @param form
     *
     * @return
     */
    @GetMapping("/order/consignee")
    public ModelAndView orderConsignee(final MallCartOrderConsigneeForm form) throws ShoppingCartServiceException {
        form.setConsigneeId(shoppingCartService.findByUserId(currentUser().getId()).getConsigneeId());
        return builder().setFormView(form, consigneeService.findByUser(currentUser().getId())).setResultNavigation("新建收货人", "/mall/consignee/new").build();
    }

    @PostMapping("/order/consignee")
    public ModelAndView orderConsignee(@Valid @ModelAttribute(FORM_OBJECT) MallCartOrderConsigneeForm form, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return builder().setFormView(form, consigneeService.findByUser(currentUser().getId())).build();
        }

        try {
            shoppingCartService.update(shoppingCartService.findByUserId(currentUser().getId()).getId(), shoppingCart -> shoppingCart.setConsigneeId(form.getConsigneeId()));
            return redirect("/mall/cart/order");
        } catch (Exception e) {
            return error(HttpStatus.BAD_GATEWAY, e.getMessage()).build();
        }
    }

    /**
     * 添加商品到购物车
     *
     * @param id 商品ID
     *
     * @return
     */
    @PostMapping("/item/add")
    public ModelAndView addItem(@RequestParam long id) {
        try {
            shoppingCartItemService.addCommodity(currentUser().getId(), id);
            return builder().setResult("加入购物车成功", "").setResultNavigation("返回", "/mall/item/" + id, "我的购物车", "/mall/cart").build();
        } catch (ShoppingCartServiceException e) {
            return builder().setResult("加入购物车失败", e.getMessage()).build();
        }
    }

    /**
     * 修改购物车项目
     *
     * @param form 表单
     *
     * @return
     */
    @PostMapping("/item")
    public ModelAndView modifyItem(MallItemForm form) {

        shoppingCartItemService.update(form.getId(), shoppingCartItem -> {
            shoppingCartItem.setSelected(form.isSelected());
            shoppingCartItem.setQuantity(form.getQuantity());
        });

        return redirect("/mall/cart");
    }

    /**
     * 计算订单总价
     *
     * @param selectedItems
     *
     * @return
     */
    private double getOrderPrice(List<ShoppingCartItem> selectedItems) {
        return selectedItems.stream().map(shoppingCartItem -> shoppingCartItem.getCommodity().getSellPrice() * shoppingCartItem.getQuantity()).reduce(0, (sum, price) -> sum + price);
    }

    /**
     * 计算订单商品总数
     *
     * @param selectedItems
     *
     * @return
     */
    private int getCommodityCount(List<ShoppingCartItem> selectedItems) {
        return selectedItems.stream().map(ShoppingCartItem::getQuantity).reduce(0, (sum, quantity) -> sum + quantity);
    }
}
