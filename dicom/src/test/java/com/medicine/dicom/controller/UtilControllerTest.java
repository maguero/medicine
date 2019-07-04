package com.medicine.dicom.controller;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.CoreMatchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(UtilsController.class)
public class UtilControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void shouldReturnVersionMessage() throws Exception {
        this.mockMvc.perform(get("/utils/version")).andDo(print()).andExpect(status().isOk())
                .andExpect(content().string(containsString(UtilsController.APPLICATION_VERSION)));
    }

}