package com.medicine.dicom.persistence;

import com.medicine.dicom.model.Image;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ImageRepository extends JpaRepository<Image, String> {

}
