package com.qxcmp.framework.domain;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest
public class RegionServiceTest {

    @Autowired
    private RegionService regionService;

    @Test
    public void testFindByLevel() throws Exception {
        assertEquals(1, regionService.findByLevel(RegionLevel.STATE).size());
        assertEquals(34, regionService.findByLevel(RegionLevel.PROVINCE).size());
        assertEquals(344, regionService.findByLevel(RegionLevel.CITY).size());
        assertEquals(3130, regionService.findByLevel(RegionLevel.COUNTY).size());
    }

    @Test
    public void testFindSubRegion() throws Exception {
        assertEquals(21, regionService.findInferiors("510000").size());
    }
}