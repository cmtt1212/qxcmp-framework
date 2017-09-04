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
public class AbstractSegmentTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void testPage1() throws Exception {
        mockMvc.perform(get("/test/elements/segment/1")).andExpect(status().isOk()).andExpect(content().string(containsString("ui segment")));
    }

    @Test
    public void testPage2() throws Exception {
        mockMvc.perform(get("/test/elements/segment/2")).andExpect(status().isOk()).andExpect(content().string(containsString("ui basic segment")));
    }

    @Test
    public void testPage3() throws Exception {
        mockMvc.perform(get("/test/elements/segment/3")).andExpect(status().isOk()).andExpect(content().string(containsString("ui circular segment")));
    }

    @Test
    public void testPage4() throws Exception {
        mockMvc.perform(get("/test/elements/segment/4")).andExpect(status().isOk()).andExpect(content().string(containsString("ui piled segment")));
    }

    @Test
    public void testPage5() throws Exception {
        mockMvc.perform(get("/test/elements/segment/5")).andExpect(status().isOk()).andExpect(content().string(containsString("ui raised segment")));
    }

    @Test
    public void testPage6() throws Exception {
        mockMvc.perform(get("/test/elements/segment/6")).andExpect(status().isOk()).andExpect(content().string(containsString("ui vertical segment")));
    }

    @Test
    public void testPage7() throws Exception {
        mockMvc.perform(get("/test/elements/segment/7")).andExpect(status().isOk()).andExpect(content().string(containsString("ui stacked segment")));
    }

    @Test
    public void testPage8() throws Exception {
        mockMvc.perform(get("/test/elements/segment/8")).andExpect(status().isOk()).andExpect(content().string(containsString("ui stacked tall segment")));
    }
}