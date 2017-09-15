package com.qxcmp.framework.core.support;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class IDGeneratorTest {

    @Test
    public void testNumber() throws Exception {
        for (int i = 0; i < 10; i++) {
            String id = IDGenerator.order();
            for (char c : id.toCharArray()) {
                assertTrue(Character.isDigit(c));
            }
            assertEquals(23, id.length());
        }
    }

    @Test
    public void testUUID() throws Exception {
        for (int i = 0; i < 10; i++) {
            String id = IDGenerator.next();
            for (char c : id.toCharArray()) {
                assertTrue(Character.isAlphabetic(c) || Character.isDigit(c));
            }
            assertEquals(32, id.length());
        }
    }

    @Test
    public void testSha256() throws Exception {
        for (int i = 0; i < 10; i++) {
            String id = IDGenerator.sha384();
            for (char c : id.toCharArray()) {
                assertTrue(Character.isAlphabetic(c) || Character.isDigit(c));
            }
            assertEquals(96, id.length());
        }
    }
}