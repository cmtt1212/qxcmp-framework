package com.qxcmp.framework.web.view.elements.grid;

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
public class AbstractGridTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void testPage1() throws Exception {
        mockMvc.perform(get("/test/containers/grid/1")).andExpect(status().isOk()).andExpect(content().string(containsString("ui grid")));
    }

    @Test
    public void testPage2() throws Exception {
        mockMvc.perform(get("/test/containers/grid/2")).andExpect(status().isOk()).andExpect(content().string(containsString("ui divided grid")));
    }

    @Test
    public void testPage3() throws Exception {
        mockMvc.perform(get("/test/containers/grid/3")).andExpect(status().isOk()).andExpect(content().string(containsString("ui vertically divided grid")));
    }

    @Test
    public void testPage4() throws Exception {
        mockMvc.perform(get("/test/containers/grid/4")).andExpect(status().isOk()).andExpect(content().string(containsString("ui celled grid")));
    }

    @Test
    public void testPage5() throws Exception {
        mockMvc.perform(get("/test/containers/grid/5")).andExpect(status().isOk()).andExpect(content().string(containsString("ui internally celled grid")));
    }
}