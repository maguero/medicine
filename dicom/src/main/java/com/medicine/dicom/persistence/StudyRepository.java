package com.medicine.dicom.persistence;

import com.medicine.dicom.model.Study;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StudyRepository extends JpaRepository<Study, String> {
}
