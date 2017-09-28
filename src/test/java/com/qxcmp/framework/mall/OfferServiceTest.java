package com.qxcmp.framework.mall;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;
import java.util.Optional;
import java.util.Set;

import static org.junit.Assert.*;

public class OfferServiceTest extends BaseMallTest {

    @Autowired
    private OfferService offerService;

    @Test
    public void testPublish() throws Exception {

        Set<String> ids = offerService.publish(offer -> {
            offer.setName("offerName");
            offer.setType("offerType");
            offer.setVendorID(getOrCreateUser("user1").getId());
            offer.setDateStart(new Date());
            offer.setDateEnd(new Date(System.currentTimeMillis() + 600000));
        }, 1000);

        assertEquals(1000, ids.size());
    }

    @Test
    public void testNormalPick() throws Exception {
        Set<String> ids = offerService.publish(offer -> {
            offer.setName("商品一优惠券");
            offer.setType("特惠活动");
            offer.setVendorID(getOrCreateUser("user1").getId());
            offer.setDateStart(new Date());
            offer.setDateEnd(new Date(System.currentTimeMillis() + 600000));
        }, 10);

        assertEquals(10, ids.size());

        Optional<Offer> pickedOffer = offerService.pick(getOrCreateUser("user2").getId(), ids.stream().findAny().get());

        assertTrue(pickedOffer.isPresent());
        assertEquals(getOrCreateUser("user2").getId(), pickedOffer.get().getUserId());
    }

    @Test
    public void testDuplicatePick() throws Exception {
        Set<String> ids = offerService.publish(offer -> {
            offer.setName("商品二优惠券");
            offer.setType("特惠活动");
            offer.setVendorID(getOrCreateUser("user1").getId());
            offer.setDateStart(new Date());
            offer.setDateEnd(new Date(System.currentTimeMillis() + 600000));
        }, 10);

        String offerId = ids.stream().findAny().get();
        Optional<Offer> pickedOffer = offerService.pick(getOrCreateUser("user2").getId(), offerId);
        assertTrue(pickedOffer.isPresent());
        assertEquals(getOrCreateUser("user2").getId(), pickedOffer.get().getUserId());
        pickedOffer = offerService.pick(getOrCreateUser("user2").getId(), offerId);
        assertFalse(pickedOffer.isPresent());
    }
}