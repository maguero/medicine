package com.medicine.dicom.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import javax.persistence.*;
import java.util.List;

@Entity
public class Series {

    @Id
    private String Id;
    private String modality;
    @JsonManagedReference
    @OneToMany(mappedBy = "series", cascade = CascadeType.ALL)
    private List<Image> images;
    @JsonBackReference
    @ManyToOne
    private Study study;
    @Version
    private int version;

    public Series() {
    }

    public String getId() {
        return Id;
    }

    public void setId(String id) {
        Id = id;
    }

    public String getModality() {
        return modality;
    }

    public void setModality(String modality) {
        this.modality = modality;
    }

    public List<Image> getImages() {
        return images;
    }

    public void setImages(List<Image> images) {
        this.images = images;
    }

    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }

    public Study getStudy() {
        return study;
    }

    public void setStudy(Study study) {
        this.study = study;
    }

    public static class Builder {

        private String id;
        private String modality;
        private List<Image> images;
        private Study study;

        public Builder setId(String id) {
            this.id = id;
            return this;
        }

        public Builder setModality(String modality) {
            this.modality = modality;
            return this;
        }

        public Builder setImages(List<Image> images) {
            this.images = images;
            return this;
        }

        public Builder setStudy(Study study) {
            this.study = study;
            return this;
        }

        public Series build() {
            Series series = new Series();
            series.setId(this.id);
            series.setModality(this.modality);
            series.setImages(this.images);
            series.setStudy(this.study);
            return series;
        }
    }
}
