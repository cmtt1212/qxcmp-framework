package com.qxcmp.framework.web.controller;

import com.qxcmp.framework.domain.CommodityOrder;
import com.qxcmp.framework.domain.CommodityOrderService;
import com.qxcmp.framework.exception.FinanceException;
import com.qxcmp.framework.exception.NoBalanceException;
import com.qxcmp.framework.user.User;
import com.qxcmp.framework.web.QXCMPFrontendController2;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.text.DecimalFormat;

/**
 * 收银台页面相关路由
 *
 * @author aaric
 */
@RequestMapping("/mall/cashier")
@Controller
@RequiredArgsConstructor
public class CashierController extends QXCMPFrontendController2 {

    private final CommodityOrderService commodityOrderService;

    /**
     * 支付商品订单
     *
     * @param id 商品订单号
     *
     * @return
     */
    @GetMapping("/{id}")
    public ModelAndView cash(@PathVariable String id) {
        User user = currentUser();

        if (StringUtils.isBlank(user.getPayPassword())) {
            return builder().setResult("您还没有设置独立支付密码", "请先设置独立支付密码").setResultNavigation("立即设置", "/finance/password").build();
        }

        CommodityOrder commodityOrder = commodityOrderService.findOne(id).orElseThrow(() -> new RuntimeException("订单不存在"));

        return builder().setTitle("收银台")
                .setResult("收银台", String.format("需支付：%s元", new DecimalFormat("0.00").format((double) commodityOrder.getActualPayment() / 100)))
                .addFragment("qxcmp/mall-widget", "cashier")
                .addObject("orderId", commodityOrder.getId()).build();
    }

    /**
     * 处理订单
     *
     * @param id
     * @param password
     *
     * @return
     */
    @PostMapping("")
    public ModelAndView processOrder(@RequestParam String id, @RequestParam String password) {
        User user = currentUser();

        if (!new BCryptPasswordEncoder().matches(password, user.getPayPassword())) {
            return builder().setResult("支付密码错误", "您的支付密码输入错误，请重试").setResultNavigation("重新输入", "/mall/cashier/" + id).build();
        }

        try {
            commodityOrderService.pay(id);
            return builder().setResult("支付成功", "").setResultNavigation("我的订单", "/mall/item/order").build();
        } catch (NoBalanceException e) {
            return builder().setResult("余额不足", "您的账户余额不足，请充值以后再支付").setResultNavigation("立即充值", "/finance/deposit").build();
        } catch (FinanceException e) {
            return error(HttpStatus.BAD_GATEWAY, "无法支付订单：" + e.getMessage()).build();
        }
    }
}
