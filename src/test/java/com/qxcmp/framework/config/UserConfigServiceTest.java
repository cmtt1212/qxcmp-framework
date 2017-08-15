package com.qxcmp.framework.config;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Optional;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UserConfigServiceTest {

    @Autowired
    private UserConfigService userConfigService;

    @Test
    public void testCreateAndUpdate() throws Exception {
        Optional<UserConfig> userConfig = userConfigService.create("12345", "name1", "value1");

        assertTrue(userConfig.isPresent());
        assertEquals("value1", userConfigService.getString("12345", "name1").orElse(""));

        userConfig = userConfigService.create("12345", "name1", "value2");
        assertFalse(userConfig.isPresent());

        userConfig = userConfigService.update("12345", "name0", "value");
        assertFalse(userConfig.isPresent());

        userConfig = userConfigService.update("12345", "name1", "value3");
        assertTrue(userConfig.isPresent());
        assertEquals("value3", userConfig.get().getValue());

        assertEquals("value3", userConfigService.getString("12345", "name1").orElse(""));
    }
}