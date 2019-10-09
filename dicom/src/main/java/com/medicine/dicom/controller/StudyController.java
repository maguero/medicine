package com.medicine.dicom.controller;

import com.medicine.dicom.model.Study;
import com.medicine.dicom.persistence.StudyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(value = "/studies")
public class StudyController {

    @Autowired
    private StudyRepository repository;

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public List<Study> getAll() {
        return repository.findAll();
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public Optional<Study> getById(@PathVariable("id") String id) {
        return repository.findById(id);
    }

    @RequestMapping(value = "/", method = RequestMethod.POST)
    public Study saveStudy(@RequestBody Study study) {
        if (repository.existsById(study.getId())) {
            throw new ResponseStatusException(
                    HttpStatus.CONFLICT, "Study #" + study.getId() + " already exists.");
        }
        return repository.save(study);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public Study updateStudy(@PathVariable("id") String id, @RequestBody Study study) {
        study.setId(id);
        return repository.save(study);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public void deleteStudy(@PathVariable("id") String id) {
        repository.deleteById(id);
    }
}
