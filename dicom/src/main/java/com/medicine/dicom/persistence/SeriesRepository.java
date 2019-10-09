package com.medicine.dicom.persistence;

import com.medicine.dicom.model.Series;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SeriesRepository extends JpaRepository<Series, String> {
}
