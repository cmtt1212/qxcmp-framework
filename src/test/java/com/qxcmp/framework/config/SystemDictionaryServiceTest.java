package com.qxcmp.framework.config;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SystemDictionaryServiceTest {

    @Autowired
    private SystemDictionaryService systemDictionaryService;

    @Test
    public void testGet() throws Exception {
        assertEquals(2, systemDictionaryService.get("性别").size());
        assertEquals(56, systemDictionaryService.get("民族").size());
    }
}