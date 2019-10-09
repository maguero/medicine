package com.medicine.dicom.controller;

import com.google.gson.Gson;
import com.medicine.dicom.model.Image;
import com.medicine.dicom.model.Series;
import com.medicine.dicom.persistence.SeriesRepository;
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
@WebMvcTest(SeriesController.class)
public class SeriesControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private SeriesRepository seriesRepository;

    @Test
    public void shouldReturnEmptySeriesList() throws Exception {
        List<Series> expectedSeriesList = new ArrayList<Series>();

        when(seriesRepository.findAll()).thenReturn(expectedSeriesList);
        this.mockMvc.perform(get("/series/"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string("[]"));
    }

    @Test
    public void shouldReturnSeriesList() throws Exception {
        List<Image> expectedImageList = new ArrayList<Image>();
        expectedImageList.add(new Image.Builder().setId("AAB0001").setNotes("some text").build());
        expectedImageList.add(new Image.Builder().setId("AAB0002").setNotes("some extra text").build());

        List<Series> expectedSeriesList = new ArrayList<Series>();
        expectedSeriesList.add(new Series.Builder().setId("SS001").setModality("CX").setImages(expectedImageList).build());

        when(seriesRepository.findAll()).thenReturn(expectedSeriesList);
        this.mockMvc.perform(get("/series/"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.[0].id").value("SS001"));
        // TODO fix this assert -> .andExpect(content().json(new Gson().toJson(expectedSeriesList)));
        verify(seriesRepository, times(1)).findAll();
    }

    @Test
    public void shouldReturnASeries() throws Exception {
        List<Image> expectedImageList = new ArrayList<Image>();
        expectedImageList.add(new Image.Builder().setId("AAB0001").setNotes("some text").build());
        expectedImageList.add(new Image.Builder().setId("AAB0002").setNotes("some extra text").build());

        String seriesId = "SS001";
        Series expectedSeries = new Series.Builder().setId(seriesId).build();

        when(seriesRepository.findById(seriesId)).thenReturn(Optional.of(expectedSeries));
        this.mockMvc.perform(get("/series/" + seriesId))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(seriesId));
        // TODO fix this assert -> .andExpect(content().json(new Gson().toJson(expectedSeries)));
        verify(seriesRepository, times(1)).findById(seriesId);
    }

    @Test
    public void shouldNotReturnASeries() throws Exception {
        String notExistingSeriesId = "NotExistingId";

        when(seriesRepository.findById(notExistingSeriesId)).thenReturn(Optional.empty());
        this.mockMvc.perform(get("/series/" + notExistingSeriesId))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").doesNotExist());
        verify(seriesRepository, times(1)).findById(notExistingSeriesId);
    }

    @Test
    public void shouldSaveASeries() throws Exception {
        List<Image> expectedImageList = new ArrayList<Image>();
        expectedImageList.add(new Image.Builder().setId("AAB0001").setNotes("some text").build());
        expectedImageList.add(new Image.Builder().setId("AAB0002").setNotes("some extra text").build());

        String seriesId = "SS001";
        Series expectedSeries = new Series.Builder().setId(seriesId).setModality("CX").setImages(expectedImageList).build();
        String seriesJSON = new Gson().toJson(expectedSeries);

        when(seriesRepository.save(expectedSeries)).thenReturn(expectedSeries);
        this.mockMvc.perform(post("/series/")
                .contentType(MediaType.APPLICATION_JSON)
                .content(seriesJSON)
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk());

        // TODO fix this verify -> verify(seriesRepository, times(1)).save(expectedSeries);
    }

    @Test
    public void shouldUpdateASeries() throws Exception {
        List<Image> expectedImageList = new ArrayList<Image>();
        expectedImageList.add(new Image.Builder().setId("AAB0001").setNotes("some text").build());
        expectedImageList.add(new Image.Builder().setId("AAB0002").setNotes("some extra text").build());

        String seriesId = "SS001";
        Series expectedSeries = new Series.Builder().setId(seriesId).setModality("CX").setImages(expectedImageList).build();
        String seriesJSON = new Gson().toJson(expectedSeries);

        when(seriesRepository.save(expectedSeries)).thenReturn(expectedSeries);
        this.mockMvc.perform(put("/series/" + seriesId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(seriesJSON))
                .andDo(print())
                .andExpect(status().isOk());

        // verify(seriesRepository, times(1)).save(expectedSeries);
    }

    @Test
    public void shouldDeleteASeries() throws Exception {
        String seriesId = "SS001";

        doNothing().when(seriesRepository).deleteById(seriesId);
        this.mockMvc.perform(delete("/series/" + seriesId))
                .andDo(print())
                .andExpect(status().isOk());

        verify(seriesRepository, times(1)).deleteById(seriesId);
    }

}
