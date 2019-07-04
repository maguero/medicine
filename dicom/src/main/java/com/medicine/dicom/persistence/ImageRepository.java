package com.medicine.dicom.persistence;

import com.medicine.dicom.model.Image;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ImageRepository extends MongoRepository<Image, String> {

}
