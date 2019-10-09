package com.medicine.dicom.model;

import com.fasterxml.jackson.annotation.JsonBackReference;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Version;

@Entity
public class Image {

    @Id
    private String id;
    private String notes;
    private String metadata;
    @JsonBackReference
    @ManyToOne
    private Series series;
    @Version
    private int version;

    public Image() {
    }

    @Override
    public String toString() {
        return "Image #" + id + " - " + notes;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public String getMetadata() {
        return metadata;
    }

    public void setMetadata(String metadata) {
        this.metadata = metadata;
    }

    public Series getSeries() {
        return series;
    }

    public void setSeries(Series series) {
        this.series = series;
    }

    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }

    public static class Builder {

        private String id;
        private String notes;
        private String metadata;
        private Series series;

        public Builder setId(String id) {
            this.id = id;
            return this;
        }

        public Builder setNotes(String notes) {
            this.notes = notes;
            return this;
        }

        public Builder setMetadata(String metadata) {
            this.metadata = metadata;
            return this;
        }

        public Builder setSeries(Series series) {
            this.series = series;
            return this;
        }

        public Image build() {
            Image image = new Image();
            image.setId(this.id);
            image.setNotes(this.notes);
            image.setMetadata(this.metadata);
            image.setSeries(this.series);
            return image;
        }
    }
}
