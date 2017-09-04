package com.qxcmp.framework.web.view.elements.segment;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@AutoConfigureMockMvc
@SpringBootTest
public class AbstractSegmentsTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void testPage1() throws Exception {
        mockMvc.perform(get("/test/elements/segments/1")).andExpect(status().isOk()).andExpect(content().string(containsString("ui segments")));
    }

    @Test
    public void testPage2() throws Exception {
        mockMvc.perform(get("/test/elements/segments/2")).andExpect(status().isOk()).andExpect(content().string(containsString("ui horizontal segments")));
    }

    @Test
    public void testPage3() throws Exception {
        mockMvc.perform(get("/test/elements/segments/3")).andExpect(status().isOk()).andExpect(content().string(containsString("ui piled segments")));
    }

    @Test
    public void testPage4() throws Exception {
        mockMvc.perform(get("/test/elements/segments/4")).andExpect(status().isOk()).andExpect(content().string(containsString("ui raised segments")));
    }

    @Test
    public void testPage5() throws Exception {
        mockMvc.perform(get("/test/elements/segments/5")).andExpect(status().isOk()).andExpect(content().string(containsString("ui stacked segments")));
    }
}