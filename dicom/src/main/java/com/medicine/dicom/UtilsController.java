package com.medicine.dicom;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UtilsController {

    @RequestMapping(value = "/version", method = RequestMethod.GET)
    public String version() {
        return "Version: 0.1 BETA";
    }
}