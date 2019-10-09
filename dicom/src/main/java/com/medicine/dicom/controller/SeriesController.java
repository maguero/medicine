package com.medicine.dicom.controller;

import com.medicine.dicom.model.Series;
import com.medicine.dicom.persistence.SeriesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(value = "/series")
public class SeriesController {

    @Autowired
    private SeriesRepository repository;

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public List<Series> getAll() {
        return repository.findAll();
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public Optional<Series> getById(@PathVariable("id") String id) {
        return repository.findById(id);
    }

    @RequestMapping(value = "/", method = RequestMethod.POST)
    public Series saveSerie(@RequestBody Series series) {
        if (repository.existsById(series.getId())) {
            throw new ResponseStatusException(
                    HttpStatus.CONFLICT, "Serie #" + series.getId() + " already exists.");
        }
        return repository.save(series);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public Series updateSerie(@PathVariable("id") String id, @RequestBody Series series) {
        series.setId(id);
        return repository.save(series);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public void deleteSerie(@PathVariable("id") String id) {
        repository.deleteById(id);
    }
}
