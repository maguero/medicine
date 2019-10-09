package com.medicine.dicom.controller;

import com.google.gson.Gson;
import com.medicine.dicom.model.Image;
import com.medicine.dicom.persistence.ImageRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

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
        expectedImageList.add(new Image.Builder().setId("AAB0001").setNotes("some text").build());
        expectedImageList.add(new Image.Builder().setId("AAB0002").setNotes("some extra text").build());

        when(imageRepository.findAll()).thenReturn(expectedImageList);
        this.mockMvc.perform(get("/images/"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json(new Gson().toJson(expectedImageList)));
        verify(imageRepository, times(1)).findAll();
    }

    @Test
    public void shouldReturnAnImage() throws Exception {
        String imageId = "AAB0001";
        Image expectedImage = new Image.Builder().setId(imageId).setNotes("notes").setMetadata("metadata").build();

        when(imageRepository.findById(imageId)).thenReturn(Optional.of(expectedImage));
        this.mockMvc.perform(get("/images/" + imageId))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json(new Gson().toJson(expectedImage)));
        verify(imageRepository, times(1)).findById(imageId);
    }

    @Test
    public void shouldNotReturnAnImage() throws Exception {
        String imageId = "NotExistingId";

        when(imageRepository.findById(imageId)).thenReturn(Optional.empty());
        this.mockMvc.perform(get("/images/" + imageId))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").doesNotExist());
        verify(imageRepository, times(1)).findById(imageId);
    }

    @Test
    public void shouldSaveAnImage() throws Exception {
        String imageId = "AAB0001";
        Image expectedImage = new Image.Builder().setId(imageId).setNotes("notes").setMetadata("metadata").build();
        String jsonImage = new Gson().toJson(expectedImage);

        when(imageRepository.save(expectedImage)).thenReturn(expectedImage);
        this.mockMvc.perform(post("/images/")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonImage)
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk());

        // verify(imageRepository, times(1)).save(expectedImage);
    }

    @Test
    public void shouldUpdateAnImage() throws Exception {
        String imageId = "AAB0001";
        Image expectedImage = new Image.Builder().setId(imageId).setNotes("notes").setMetadata("metadata").build();
        String jsonImage = new Gson().toJson(expectedImage);

        when(imageRepository.save(expectedImage)).thenReturn(expectedImage);
        this.mockMvc.perform(put("/images/" + imageId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonImage))
                .andDo(print())
                .andExpect(status().isOk());

        // verify(imageRepository, times(1)).save(expectedImage);
    }

    @Test
    public void shouldDeleteAnImage() throws Exception {
        String imageId = "AAB0001";

        doNothing().when(imageRepository).deleteById(imageId);
        this.mockMvc.perform(delete("/images/" + imageId))
                .andDo(print())
                .andExpect(status().isOk());

        verify(imageRepository, times(1)).deleteById(imageId);
    }

}
