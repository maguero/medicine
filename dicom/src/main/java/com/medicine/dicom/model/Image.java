package com.medicine.dicom.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class Image {

    @Id
    private String id;
    private String notes;

    public Image(String id, String notes) {
        this.id = id;
        this.notes = notes;
    }

    @Override
    public String toString() {
        return super.toString();
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
}
