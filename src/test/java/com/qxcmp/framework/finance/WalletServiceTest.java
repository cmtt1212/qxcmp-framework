package com.qxcmp.framework.finance;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Optional;

import static org.junit.Assert.*;

public class WalletServiceTest extends BaseFinanceTest {

    @Autowired
    private WalletService walletService;

    @Test
    public void testGetByNotExistUser() throws Exception {
        Optional<Wallet> walletOptional = walletService.getByUserId("dummyUser");
        assertFalse(walletOptional.isPresent());
    }

    @Test
    public void testGetWithExistUser() throws Exception {
        Optional<Wallet> walletOptional = walletService.getByUserId(getOrCreateUser("user1").getId());
        assertTrue(walletOptional.isPresent());
    }

    @Test
    public void testGetMultipleTime() throws Exception {
        Optional<Wallet> w1 = walletService.getByUserId(getOrCreateUser("user1").getId());
        Optional<Wallet> w2 = walletService.getByUserId(getOrCreateUser("user1").getId());
        assertTrue(w1.isPresent());
        assertTrue(w2.isPresent());
        assertEquals(w1.get().getId(), w2.get().getId());
    }

}