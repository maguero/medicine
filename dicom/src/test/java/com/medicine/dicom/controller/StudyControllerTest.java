package com.medicine.dicom.controller;

import com.google.gson.Gson;
import com.medicine.dicom.model.Image;
import com.medicine.dicom.model.Series;
import com.medicine.dicom.model.Study;
import com.medicine.dicom.persistence.StudyRepository;
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
@WebMvcTest(StudyController.class)
public class StudyControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private StudyRepository studyRepository;

    @Test
    public void shouldReturnEmptyStudyList() throws Exception {
        List<Study> expectedStudyList = new ArrayList<Study>();

        when(studyRepository.findAll()).thenReturn(expectedStudyList);
        this.mockMvc.perform(get("/studies/"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string("[]"));
    }

    @Test
    public void shouldReturnStudyList() throws Exception {
        List<Image> expectedImageList = new ArrayList<Image>();
        expectedImageList.add(new Image.Builder().setId("AAB0001").setNotes("some text").build());
        expectedImageList.add(new Image.Builder().setId("AAB0002").setNotes("some extra text").build());

        List<Series> expectedSeriesList = new ArrayList<Series>();
        expectedSeriesList.add(new Series.Builder().setId("SS001").setModality("CX").setImages(expectedImageList).build());

        List<Study> expectedStudyList = new ArrayList<Study>();
        expectedStudyList.add(new Study.Builder(expectedSeriesList).setId("ST001").setName("StudyName").setPatient("P001").build());

        when(studyRepository.findAll()).thenReturn(expectedStudyList);
        this.mockMvc.perform(get("/studies/"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.[0].id").value("ST001"));
        // TODO fix this assert -> .andExpect(content().json(new Gson().toJson(expectedStudyList)));
        verify(studyRepository, times(1)).findAll();
    }

    @Test
    public void shouldReturnAStudy() throws Exception {
        List<Image> expectedImageList = new ArrayList<Image>();
        expectedImageList.add(new Image.Builder().setId("AAB0001").setNotes("some text").build());
        expectedImageList.add(new Image.Builder().setId("AAB0002").setNotes("some extra text").build());

        List<Series> expectedSeriesList = new ArrayList<Series>();
        expectedSeriesList.add(new Series.Builder().setId("SS001").setModality("CX").setImages(expectedImageList).build());

        String studyId = "ST001";
        Study expectedStudy = new Study.Builder(expectedSeriesList).setId(studyId).setName("StudyName").setPatient("P001").build();

        when(studyRepository.findById(studyId)).thenReturn(Optional.of(expectedStudy));
        this.mockMvc.perform(get("/studies/" + studyId))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(studyId));
        // TODO fix this assert -> .andExpect(content().json(new Gson().toJson(expectedStudy)));
        verify(studyRepository, times(1)).findById(studyId);
    }

    @Test
    public void shouldNotReturnAStudy() throws Exception {
        String notExistingStudyId = "NotExistingId";

        when(studyRepository.findById(notExistingStudyId)).thenReturn(Optional.empty());
        this.mockMvc.perform(get("/studies/" + notExistingStudyId))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").doesNotExist());
        verify(studyRepository, times(1)).findById(notExistingStudyId);
    }

    @Test
    public void shouldSaveAStudy() throws Exception {
        List<Image> expectedImageList = new ArrayList<Image>();
        expectedImageList.add(new Image.Builder().setId("AAB0001").setNotes("some text").build());
        expectedImageList.add(new Image.Builder().setId("AAB0002").setNotes("some extra text").build());

        List<Series> expectedSeriesList = new ArrayList<Series>();
        expectedSeriesList.add(new Series.Builder().setId("SS001").setModality("CX").setImages(expectedImageList).build());

        String studyId = "ST001";
        Study expectedStudy = new Study.Builder(expectedSeriesList).setId(studyId).setName("StudyName").setPatient("P001").build();
        String studyJSON = new Gson().toJson(expectedStudy);

        when(studyRepository.save(expectedStudy)).thenReturn(expectedStudy);
        this.mockMvc.perform(post("/studies/")
                .contentType(MediaType.APPLICATION_JSON)
                .content(studyJSON)
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk());

        // TODO fix this verify -> verify(studyRepository, times(1)).save(expectedStudy);
    }

    @Test
    public void shouldUpdateAStudy() throws Exception {
        List<Image> expectedImageList = new ArrayList<Image>();
        expectedImageList.add(new Image.Builder().setId("AAB0001").setNotes("some text").build());
        expectedImageList.add(new Image.Builder().setId("AAB0002").setNotes("some extra text").build());

        List<Series> expectedSeriesList = new ArrayList<Series>();
        expectedSeriesList.add(new Series.Builder().setId("SS001").setModality("CX").setImages(expectedImageList).build());

        String studyId = "ST001";
        Study expectedStudy = new Study.Builder(expectedSeriesList).setId(studyId).setName("StudyName").setPatient("P001").build();
        String studyJSON = new Gson().toJson(expectedStudy);

        when(studyRepository.save(expectedStudy)).thenReturn(expectedStudy);
        this.mockMvc.perform(put("/studies/" + studyId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(studyJSON))
                .andDo(print())
                .andExpect(status().isOk());

        // verify(studyRepository, times(1)).save(expectedStudy);
    }

    @Test
    public void shouldDeleteAStudy() throws Exception {
        String studyId = "ST001";

        doNothing().when(studyRepository).deleteById(studyId);
        this.mockMvc.perform(delete("/studies/" + studyId))
                .andDo(print())
                .andExpect(status().isOk());

        verify(studyRepository, times(1)).deleteById(studyId);
    }

}
