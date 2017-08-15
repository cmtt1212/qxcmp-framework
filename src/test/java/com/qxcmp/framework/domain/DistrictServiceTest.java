package com.qxcmp.framework.domain;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest
public class DistrictServiceTest {

    @Autowired
    private DistrictService districtService;

    @Test
    public void testGetProvince() throws Exception {
        assertEquals(35, districtService.getAllProvince().size());
    }

    @Test
    public void testGetById() throws Exception {
        assertEquals(1, districtService.getInferiors("110000").size());
        assertEquals(16, districtService.getInferiors("110100").size());
        assertEquals(21, districtService.getInferiors("510000").size());
        assertEquals(21, districtService.getInferiors("510100").size());
    }
}