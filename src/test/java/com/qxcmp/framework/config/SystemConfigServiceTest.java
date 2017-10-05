package com.qxcmp.framework.config;

import com.google.common.collect.ImmutableList;
import com.qxcmp.framework.user.UserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SystemConfigServiceTest {

    @Autowired
    private SystemConfigService systemConfigService;

    @MockBean
    private UserService userService;

    @Test
    public void testGetList() throws Exception {
        String data = "data1\n data2\n data3 \ndata4";

        Optional<SystemConfig> systemConfig = systemConfigService.create("testConfig", data);
        assertEquals(true, systemConfig.isPresent());

        assertEquals(4, systemConfigService.getList("testConfig").size());
        assertEquals("data1", systemConfigService.getList("testConfig").get(0));
        assertEquals("data2", systemConfigService.getList("testConfig").get(1));
        assertEquals("data3", systemConfigService.getList("testConfig").get(2));
        assertEquals("data4", systemConfigService.getList("testConfig").get(3));

        List<String> updateDataList = ImmutableList.of("update1", " update2", " update3", "update4 ");

        systemConfigService.create("testConfig1", "update1\nupdate2\nupdate3\nupdate4");
        systemConfigService.update("testConfig", updateDataList);

        assertEquals(false, systemConfigService.getList("testConfig1").isEmpty());
        assertEquals(4, systemConfigService.getList("testConfig").size());
        assertEquals("update1", systemConfigService.getList("testConfig").get(0));
        assertEquals("update2", systemConfigService.getList("testConfig").get(1));
        assertEquals("update3", systemConfigService.getList("testConfig").get(2));
        assertEquals("update4", systemConfigService.getList("testConfig").get(3));
        assertEquals("update1\nupdate2\nupdate3\nupdate4", systemConfigService.getString("testConfig").orElse(""));
    }
}