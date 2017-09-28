package com.qxcmp.framework.mall;

import com.qxcmp.framework.core.entity.AbstractEntityService;
import com.qxcmp.framework.exception.FinanceException;
import com.qxcmp.framework.exception.NoBalanceException;
import com.qxcmp.framework.exception.OrderStatusException;
import com.qxcmp.framework.core.support.IDGenerator;
import com.qxcmp.framework.finance.Wallet;
import com.qxcmp.framework.finance.WalletService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.context.ApplicationContext;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Optional;
import java.util.function.Supplier;

/**
 * 商品订单服务
 * <p>
 * 支持以下服务： <ol> <li>商品订单创建</li> <li>商品订单处理</li> <li>商品订单查询</li> </ol>
 *
 * @author aaric
 */
@Service
public class CommodityOrderService extends AbstractEntityService<CommodityOrder, String, CommodityOrderRepository> {

    private final ApplicationContext applicationContext;

    private final WalletService walletService;

    private final ShoppingCartService shoppingCartService;

    private final ShoppingCartItemService shoppingCartItemService;

    private final CommodityService commodityService;

    private final CommodityOrderItemService commodityOrderItemService;

    public CommodityOrderService(CommodityOrderRepository repository, ApplicationContext applicationContext, WalletService walletService, ShoppingCartService shoppingCartService, ShoppingCartItemService shoppingCartItemService, CommodityService commodityService, CommodityOrderItemService commodityOrderItemService) {
        super(repository);
        this.applicationContext = applicationContext;
        this.walletService = walletService;
        this.shoppingCartService = shoppingCartService;
        this.shoppingCartItemService = shoppingCartItemService;
        this.commodityService = commodityService;
        this.commodityOrderItemService = commodityOrderItemService;
    }

    /**
     * 商品统一下单接口
     * <p>
     * 购物车项目ID必须在用户自己的购物车中，如果不在则忽略该项目
     *
     * @param userId      用户ID
     * @param payment     订单总金额
     * @param address     收货地址
     * @param cartItemIds 购物车项目ID
     *
     * @return 创建好的商品订单
     */
    public Optional<CommodityOrder> order(String userId, BigDecimal payment, String address, Long... cartItemIds) {

        if (cartItemIds.length == 0) {
            return Optional.empty();
        }

        return Optional.empty();
    }

    /**
     * 商品订单统一支付接口
     * <p>
     * 调用该接口对商品订单进行支付，并把订单状态标记为已付款
     * <p>
     * 订单支付成功以后会发送订单完成事件
     *
     * @param orderId 订单号
     *
     * @return 支付后的订单
     *
     * @throws FinanceException 如果用户余额不足，或者其他异常，抛出该异常
     */
    public Optional<CommodityOrder> pay(String orderId) throws FinanceException {
        Optional<CommodityOrder> commodityOrderOptional = findOne(orderId);

        if (!commodityOrderOptional.isPresent()) {
            throw new OrderStatusException("Order not exist");
        }

        CommodityOrder commodityOrder = commodityOrderOptional.get();

        if (!commodityOrder.getStatus().equals(OrderStatusEnum.PAYING)) {
            throw new OrderStatusException("Order status is not paying");
        }

        Optional<Wallet> walletOptional = walletService.getByUserId(commodityOrder.getUserId());

        if (!walletOptional.isPresent()) {
            throw new OrderStatusException("User not exist");
        }

        Wallet wallet = walletOptional.get();

        int orderPrice = commodityOrder.getActualPayment();

        if (wallet.getBalance() < orderPrice) {
            throw new NoBalanceException("No balance");
        }

        walletService.update(wallet.getId(), w -> w.setBalance(w.getBalance() - orderPrice));

        Optional<CommodityOrder> updatedCommodity = update(commodityOrder.getId(), order -> {
            order.setStatus(OrderStatusEnum.PAYED);
            order.setActualPayment(orderPrice);
        });


        /*
        * 发送商品销售事件
        * */
        updatedCommodity.ifPresent(order -> order.getItems().forEach(commodityOrderItem -> applicationContext.publishEvent(new CommoditySellEvent(commodityOrder.getUserId(), commodityOrderItem.getCommodity(), (long) commodityOrderItem.getQuantity()))));

        return updatedCommodity;
    }

    /**
     * 查询用户所有订单
     *
     * @param userId   用户ID
     * @param pageable 分页信息
     *
     * @return 查询结果
     */
    public Page<CommodityOrder> findByUserId(String userId, Pageable pageable) {
        return repository.findByUserId(userId, pageable);
    }


    /**
     * 根据订单状态查询订单记录
     *
     * @param userId   用户ID
     * @param status   订单状态
     * @param pageable 分页信息
     *
     * @return 查询结果
     */
    public Page<CommodityOrder> findByUserIdAndStatus(String userId, OrderStatusEnum status, Pageable pageable) {
        return repository.findByUserIdAndStatus(userId, status, pageable);
    }

    @Override
    public <S extends CommodityOrder> Optional<S> create(Supplier<S> supplier) {
        S entity = supplier.get();

        if (StringUtils.isNotEmpty(entity.getId())) {
            return Optional.empty();
        }

        entity.setId(IDGenerator.order());
        entity.setDateCreated(new Date());
        entity.setStatus(OrderStatusEnum.PAYING);

        return super.create(() -> entity);
    }

    @Override
    protected <S extends CommodityOrder> String getEntityId(S entity) {
        return entity.getId();
    }
}
