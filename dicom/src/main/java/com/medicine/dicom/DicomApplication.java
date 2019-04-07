package com.medicine.dicom;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class DicomApplication {

	public static void main(String[] args) {
		SpringApplication.run(DicomApplication.class, args);
	}

}
