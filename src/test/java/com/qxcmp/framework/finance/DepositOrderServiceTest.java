package com.qxcmp.framework.finance;

import com.qxcmp.framework.domain.*;
import com.qxcmp.framework.exception.OrderExpiredException;
import com.qxcmp.framework.exception.OrderStatusException;
import com.qxcmp.framework.user.User;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class DepositOrderServiceTest extends BaseFinanceTest {

    @Autowired
    private DepositOrderService depositOrderService;

    @Autowired
    private WalletService walletService;

    @Test
    public void testOrderCreate() throws Exception {
        Optional<DepositOrder> depositOrderOptional = depositOrderService.create(() -> {
            DepositOrder depositOrder = depositOrderService.next();
            depositOrder.setFee(100_00);
            return depositOrder;
        });

        assertTrue(depositOrderOptional.isPresent());

        assertEquals(10000, depositOrderOptional.get().getFee());

        assertTrue(depositOrderService.findOne(depositOrderOptional.get().getId()).isPresent());
    }

    @Test
    public void testNormalOrder() throws Exception {
        User user = getOrCreateUser("user1");

        Optional<DepositOrder> depositOrder = depositOrderService.create(() -> {
            DepositOrder order = depositOrderService.next();
            order.setUserId(user.getId());
            order.setFee(100);
            return order;
        });

        assertTrue(depositOrder.isPresent());

        depositOrderService.process(depositOrder.get().getId());

        Optional<Wallet> walletOptional = walletService.getByUserId(user.getId());
        assertTrue(walletOptional.isPresent());

        assertTrue(walletOptional.get().getBalance() == 100);
    }

    @Test(expected = OrderStatusException.class)
    public void testProcessNullOrder() throws Exception {
        depositOrderService.process(null);
    }

    @Test(expected = OrderStatusException.class)
    public void testNegativeFee() throws Exception {
        Optional<DepositOrder> depositOrder = depositOrderService.create(() -> {
            DepositOrder order = depositOrderService.next();
            order.setFee(-100_00);
            return order;
        });

        assertTrue(depositOrder.isPresent());

        depositOrderService.process(depositOrder.get().getId());

    }

    @Test(expected = OrderStatusException.class)
    public void testIllegalStatus() throws Exception {
        Optional<DepositOrder> depositOrder = depositOrderService.create(() -> {
            DepositOrder order = depositOrderService.next();
            order.setFee(100_00);
            return order;
        });

        assertTrue(depositOrder.isPresent());

        depositOrderService.update(depositOrder.get().getId(), order -> order.setStatus(OrderStatusEnum.CANCELLING));

        depositOrderService.process(depositOrder.get().getId());
    }

    @Test(expected = OrderExpiredException.class)
    public void testExpire() throws Exception {
        Optional<DepositOrder> depositOrder = depositOrderService.create(() -> {
            DepositOrder order = depositOrderService.next();
            order.setFee(100_00);
            return order;
        });

        assertTrue(depositOrder.isPresent());

        depositOrderService.update(depositOrder.get().getId(), order -> order.setTimeEnd(new Date(0)));

        depositOrderService.process(depositOrder.get().getId());
    }
}