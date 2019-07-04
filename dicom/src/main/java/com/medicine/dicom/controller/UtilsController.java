package com.medicine.dicom.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/utils")
public class UtilsController {

    public static final String APPLICATION_VERSION = "Version: 0.1 BETA";

    @RequestMapping(value = "/version", method = RequestMethod.GET)
    public String version() {
        return this.APPLICATION_VERSION;
    }
}