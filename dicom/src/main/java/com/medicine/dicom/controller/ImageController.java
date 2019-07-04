package com.medicine.dicom.controller;

import com.medicine.dicom.model.Image;
import com.medicine.dicom.persistence.ImageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(value = "/images")
public class ImageController {

    @Autowired
    private ImageRepository repository;

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public List<Image> getAll() {
        return repository.findAll();
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public Optional<Image> getById(@PathVariable("id") String id) {
        return repository.findById(id);
    }

    @RequestMapping(value = "/", method = RequestMethod.POST)
    public Image saveImage(@RequestBody Image image) {
        return repository.save(image);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public Image updateImage(@PathVariable("id") String id, @RequestBody Image image) {
        image.setId(id);
        return repository.save(image);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public void deleteImage(@PathVariable("id") String id) {
        repository.deleteById(id);
    }
}
