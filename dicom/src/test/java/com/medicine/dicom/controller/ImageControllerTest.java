package com.medicine.dicom.controller;

import com.google.gson.Gson;
import com.medicine.dicom.model.Image;
import com.medicine.dicom.persistence.ImageRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(ImageController.class)
public class ImageControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ImageRepository imageRepository;

    @Test
    public void shouldReturnEmptyImageList() throws Exception {
        List<Image> expectedImageList = new ArrayList<Image>();

        when(imageRepository.findAll()).thenReturn(expectedImageList);
        this.mockMvc.perform(get("/images/"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string("[]"));
    }

    @Test
    public void shouldReturnImageList() throws Exception {
        List<Image> expectedImageList = new ArrayList<Image>();
        expectedImageList.add(new Image("1","some text"));
        expectedImageList.add(new Image("12","some extra text"));

        when(imageRepository.findAll()).thenReturn(expectedImageList);
        this.mockMvc.perform(get("/images/"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json(new Gson().toJson(expectedImageList)));
        verify(imageRepository,times(1)).findAll();
    }

}
