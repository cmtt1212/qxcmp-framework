package com.qxcmp.framework.validation;

import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class PortValidatorTest {

    @Test
    public void testCorrectPort() throws Exception {
        assertTrue(new PortValidator().isValid("1", null));
        assertTrue(new PortValidator().isValid("10", null));
        assertTrue(new PortValidator().isValid("100", null));
        assertTrue(new PortValidator().isValid("1000", null));
        assertTrue(new PortValidator().isValid("10000", null));
    }

    @Test
    public void testEmpty() throws Exception {
        assertTrue(new PortValidator().isValid(null, null));
        assertTrue(new PortValidator().isValid("", null));
    }

    @Test
    public void testOutOfRange() throws Exception {
        assertFalse(new PortValidator().isValid("100000", null));
        assertFalse(new PortValidator().isValid("-1", null));
        assertFalse(new PortValidator().isValid("-10", null));
        assertFalse(new PortValidator().isValid("-100", null));
        assertFalse(new PortValidator().isValid("-1000", null));
        assertFalse(new PortValidator().isValid("-10000", null));
    }

    @Test
    public void testInvalidString() throws Exception {
        assertFalse(new PortValidator().isValid("abc", null));
    }
}