package com.qxcmp.framework.core.validation;

import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class UsernameValidatorTest {

    @Test
    public void testCorrect() throws Exception {
        assertTrue(new UsernameValidator().isValid("username", null));
        assertTrue(new UsernameValidator().isValid("_username", null));
        assertTrue(new UsernameValidator().isValid("user007", null));
        assertTrue(new UsernameValidator().isValid("_user007", null));
    }

    @Test
    public void testEmptyUsername() throws Exception {
        assertTrue(new UsernameValidator().isValid(null, null));
        assertTrue(new UsernameValidator().isValid("", null));
    }

    @Test
    public void testInvalidUsername() throws Exception {
        assertFalse(new UsernameValidator().isValid("007user", null));
        assertFalse(new UsernameValidator().isValid("^username", null));
    }

    @Test
    public void testSpaceChar() throws Exception {
        assertFalse(new UsernameValidator().isValid("user name", null));
        assertFalse(new UsernameValidator().isValid("_user name", null));
        assertFalse(new UsernameValidator().isValid("_user 007", null));
        assertFalse(new UsernameValidator().isValid("username ", null));
    }
}