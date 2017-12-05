package com.qxcmp.core.support;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class TimeDurationHelperTest {
    private TimeDurationHelper timeConvertHelper = new TimeDurationHelper();

    @Test
    public void convert() throws Exception {
        assertEquals("1秒", timeConvertHelper.convert(1000));
        assertEquals("1分1秒", timeConvertHelper.convert(61000));
        assertEquals("3分1秒", timeConvertHelper.convert(181000));
        assertEquals("1小时3分1秒", timeConvertHelper.convert(3781000));
        assertEquals("2天1小时3分1秒", timeConvertHelper.convert(176581000));
    }
}