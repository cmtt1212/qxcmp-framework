package com.qxcmp.core.validation;

import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class PhoneValidatorTest {

    @Test
    public void testCorectPhone() throws Exception {
        assertTrue(new PhoneValidator().isValid("13610000000", null));
        assertTrue(new PhoneValidator().isValid("185-0392-8374", null));
        assertTrue(new PhoneValidator().isValid("185 0392 8374", null));
    }

    @Test
    public void testEmptyPhone() throws Exception {
        assertTrue(new PhoneValidator().isValid(null, null));
        assertTrue(new PhoneValidator().isValid("", null));
    }

    @Test
    public void testInvalidPhone() throws Exception {
        assertFalse(new PhoneValidator().isValid("1386475827", null));
        assertFalse(new PhoneValidator().isValid("138647582713", null));
    }
}