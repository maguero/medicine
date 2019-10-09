package com.medicine.dicom.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import javax.persistence.*;
import java.util.List;

@Entity
public class Study {

    @Id
    private String id;
    private String name;
    private String patient;
    @OneToMany(mappedBy = "study", cascade = CascadeType.ALL)
    private List<Series> series;
    @Version
    private int version;

    public Study() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Series> getSeries() {
        return series;
    }

    public void setSeries(List<Series> series) {
        this.series = series;
    }

    public String getPatient() {
        return patient;
    }

    public void setPatient(String patient) {
        this.patient = patient;
    }

    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }

    public static class Builder {

        private String id;
        private String name;
        private String patient;
        @JsonManagedReference
        @OneToMany(mappedBy = "study", cascade = CascadeType.ALL)
        private List<Series> series;

        public Builder(List<Series> series) {
            this.series = series;
        }

        public Builder setId(String id) {
            this.id = id;
            return this;
        }

        public Builder setName(String name) {
            this.name = name;
            return this;
        }

        public Builder setPatient(String patient) {
            this.patient = patient;
            return this;
        }

        public Study build() {
            Study study = new Study();
            study.setId(this.id);
            study.setName(this.name);
            study.setPatient(this.patient);
            study.setSeries(this.series);
            return study;
        }
    }
}
