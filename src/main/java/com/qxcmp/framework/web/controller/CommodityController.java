package com.qxcmp.framework.web.controller;

import com.qxcmp.framework.domain.*;
import com.qxcmp.framework.user.User;
import com.qxcmp.framework.view.dictionary.DictionaryView;
import com.qxcmp.framework.view.list.ListView;
import com.qxcmp.framework.view.list.ListViewItem;
import com.qxcmp.framework.view.support.PaginationHelper;
import com.qxcmp.framework.web.QXCMPFrontendController;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Optional;

/**
 * 商品相关页面路由
 *
 * @author aaric
 */
@RequestMapping("/mall/item")
@Controller
@RequiredArgsConstructor
public class CommodityController extends QXCMPFrontendController {

    private final CommodityService commodityService;

    private final CommodityOrderService commodityOrderService;

    private final PaginationHelper paginationHelper;

    @GetMapping
    public ModelAndView mall() {
        return builder().setTitle("商城")
                .addFragment("qxcmp/mall-widget", "commodity-list('商品列表', ${commodityList})")
                .addObject(commodityService.findAll())
                .build();
    }

    @GetMapping("/{id}")
    public ModelAndView item(@PathVariable String id) {
        return redirect(String.format("/mall/item/%s.html", id));
    }

    @GetMapping("/{id}.html")
    public ModelAndView item2(@PathVariable String id) {
        return commodityService.findOne(id).map(commodity -> builder().addFragment("qxcmp/mall-widget", "commodity-details").addObject(commodity).build()).orElse(error(HttpStatus.NOT_FOUND, "商品不存在或者已下架").build());
    }

    /**
     * 商品订单页面
     *
     * @return 我的商品订单页面
     */
    @GetMapping("/order")
    public ModelAndView order(@RequestParam(required = false) String type, Pageable pageable) {

        User user = currentUser();

        Page<CommodityOrder> commodityOrders;

        try {
            OrderStatusEnum orderStatus = OrderStatusEnum.valueOf(type);
            commodityOrders = commodityOrderService.findByUserIdAndStatus(user.getId(), orderStatus, new PageRequest(pageable.getPageNumber(), pageable.getPageSize(), Sort.Direction.DESC, "dateCreated"));
        } catch (Exception e) {
            commodityOrders = commodityOrderService.findByUserId(user.getId(), new PageRequest(pageable.getPageNumber(), pageable.getPageSize(), Sort.Direction.DESC, "dateCreated"));
        }

        List<CommodityOrder> commodityOrderList = commodityOrders.getContent();


        return builder().setTitle("我的商品订单")
                .addFragment("qxcmp/mall-widget", "commodity-order-tabs")
                .addListView(listViewBuilder -> commodityOrderList.forEach(commodityOrder -> {
                    Optional<CommodityOrderItem> commodityOrderItemOptional = commodityOrder.getItems().stream().findAny();

                    if (commodityOrderItemOptional.isPresent()) {
                        CommodityOrderItem commodityOrderItem = commodityOrderItemOptional.get();
                        listViewBuilder.item(ListViewItem.builder()
                                .image(commodityOrderItem.getCommodity().getCover())
                                .title(String.format("【%s】%s", commodityOrder.getStatus().getValue(), commodityOrderItem.getCommodity().getTitle()))
                                .description(String.format("共%d件商品 总金额：￥%s", commodityOrderItem.getQuantity(), new DecimalFormat("0.00").format((double) commodityOrder.getActualPayment() / 100)))
                                .link("/mall/item/order/" + commodityOrder.getId())
                                .build());
                    }
                }))
                .addObject(paginationHelper.next(commodityOrders, "/mall/item/order", para -> para.put("type", type)))
                .build();
    }

    /**
     * 订单详情页面
     * <p>
     * 只能查看自己的订单
     *
     * @param id 订单号
     *
     * @return 订单详情页面
     */
    @GetMapping("/order/{id}")
    public ModelAndView orderDetails(@PathVariable String id) {

        Optional<CommodityOrder> commodityOrderOptional = commodityOrderService.findOne(id);

        if (!commodityOrderOptional.isPresent() || !StringUtils.equals(commodityOrderOptional.get().getUserId(), currentUser().getId())) {
            return error(HttpStatus.NOT_FOUND, "订单不存在").build();
        }

        CommodityOrder commodityOrder = commodityOrderOptional.get();

        ListView.ListViewBuilder listViewBuilder = ListView.builder();

        listViewBuilder.item(ListViewItem.builder().title(String.format("%s %s", commodityOrder.getConsigneeName(), commodityOrder.getConsigneePhone())).description(commodityOrder.getAddress()).build());

        commodityOrder.getItems().forEach(commodityOrderItem -> {
            Commodity commodity = commodityOrderItem.getCommodity();

            listViewBuilder.item(ListViewItem.builder()
                    .title(commodity.getTitle())
                    .description(String.format("￥%s x%d", new DecimalFormat("0.00").format(commodityOrderItem.getActualPrice() / 100), commodityOrderItem.getQuantity()))
                    .image(commodity.getCover())
                    .link("/mall/item/" + commodity.getId())
                    .build());
        });

        DictionaryView.DictionaryViewBuilder dictionaryViewBuilder = DictionaryView.builder();

        dictionaryViewBuilder
                .dictionary("订单编号", commodityOrder.getId())
                .dictionary("下单时间", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(commodityOrder.getDateCreated()))
                .dictionary("总金额", new DecimalFormat("0.00").format((double) commodityOrder.getActualPayment() / 100));

        if (commodityOrder.getStatus().equals(OrderStatusEnum.PAYING)) {
            return builder().setTitle("商品订单详情").setResult("订单详情", commodityOrder.getStatus().getValue()).addListView(listViewBuilder.build()).addDictionaryView(dictionaryViewBuilder.build()).setResultNavigation("返回我的订单", "/mall/item/order", "去支付", "/mall/cashier/" + commodityOrder.getId()).build();
        } else {
            return builder().setTitle("商品订单详情").setResult("订单详情", commodityOrder.getStatus().getValue()).addListView(listViewBuilder.build()).addDictionaryView(dictionaryViewBuilder.build()).setResultNavigation("返回我的订单", "/mall/item/order").build();
        }
    }
}
