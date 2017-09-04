package com.qxcmp.framework.web.view.elements.header;

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
public class AbstractHeaderTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void testPage1() throws Exception {
        mockMvc.perform(get("/test/elements/header/1")).andExpect(status().isOk()).andExpect(content().string(containsString("ui header")));
    }


    @Test
    public void testPage2() throws Exception {
        mockMvc.perform(get("/test/elements/header/2")).andExpect(status().isOk()).andExpect(content().string(containsString("ui small header")));
    }


    @Test
    public void testPage3() throws Exception {
        mockMvc.perform(get("/test/elements/header/3")).andExpect(status().isOk()).andExpect(content().string(containsString("ui icon header")));
    }


    @Test
    public void testPage4() throws Exception {
        mockMvc.perform(get("/test/elements/header/4")).andExpect(status().isOk()).andExpect(content().string(containsString("ui sub header")));
    }
}