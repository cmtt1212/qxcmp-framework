package com.qxcmp.framework.calendar;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Calendar;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest
public class CalendarServiceTest {

    @Autowired
    private CalendarService dateService;

    @Test
    public void testGetDate() throws Exception {
        Calendar calendar = Calendar.getInstance();
        calendar.set(2017, Calendar.OCTOBER, 1);
        assertEquals(CalendarDateType.HOLIDAY, dateService.get(calendar.getTime()).getType());
    }

    @Test
    public void testNormalWorkingDayOf2017() throws Exception {
        assertEquals(CalendarDateType.WORKDAY, dateService.get(2017, 7, 10).getType());
        assertEquals(CalendarDateType.WORKDAY, dateService.get(2017, 4, 5).getType());
        assertEquals(CalendarDateType.WORKDAY, dateService.get(2017, 5, 19).getType());
        assertEquals(CalendarDateType.WORKDAY, dateService.get(2017, 6, 16).getType());
    }

    @Test
    public void testExceptionalWorkingDayOf2017() throws Exception {
        assertEquals(CalendarDateType.WORKDAY, dateService.get(2017, 9, 30).getType());
        assertEquals(CalendarDateType.WORKDAY, dateService.get(2017, 1, 22).getType());
        assertEquals(CalendarDateType.WORKDAY, dateService.get(2017, 2, 4).getType());
        assertEquals(CalendarDateType.WORKDAY, dateService.get(2017, 4, 1).getType());
    }

    @Test
    public void testWeekendOf2017() throws Exception {
        assertEquals(CalendarDateType.WEEKEND, dateService.get(2017, 3, 4).getType());
        assertEquals(CalendarDateType.WEEKEND, dateService.get(2017, 3, 5).getType());
    }

    @Test
    public void testHolidayOf2017() throws Exception {
        assertEquals(CalendarDateType.HOLIDAY, dateService.get(2017, 1, 1).getType());
        assertEquals(CalendarDateType.HOLIDAY, dateService.get(2017, 4, 2).getType());
    }
}