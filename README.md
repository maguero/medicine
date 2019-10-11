[![Build Status](https://travis-ci.org/maguero/medicine.svg?branch=master)](https://travis-ci.org/maguero/medicine)
[![Codacy Badge](https://api.codacy.com/project/badge/Grade/3acdaa7cf45c4052b5add4132fb52b77)](https://www.codacy.com/manual/maguero/medicine?utm_source=github.com&amp;utm_medium=referral&amp;utm_content=maguero/medicine&amp;utm_campaign=Badge_Grade)
[![Coverage Status](https://coveralls.io/repos/github/maguero/medicine/badge.svg?branch=master)](https://coveralls.io/github/maguero/medicine?branch=master)
# Medicine
Research project for semantic queries on medical images (DICOM format)

## Techs & Architecture
It's a micro-services architecture developed in Java and run/deployed in Docker containers.
  * [Java 12](https://openjdk.java.net/projects/jdk/12/)
  * [Spring-boot 2.1.4.RELEASE](https://spring.io/guides/gs/spring-boot/)
  * [Maven 3.6.1](https://maven.apache.org/docs/3.6.1/release-notes.html)
  * [Docker](https://www.docker.com/) & [Docker-Compose](https://docs.docker.com/compose/)
  * [MySQL](https://hub.docker.com/_/mysql)
  * Unit test with [Mockito](https://site.mockito.org/)

The architecture is composed by the following micro services mounted on docker containers:
  * **Registry / Discovery**: [Service Discovery (Eureka)](https://spring.io/guides/gs/service-registration-and-discovery/)
  * **Gateway**: [Spring Cloud Gateway](https://spring.io/projects/spring-cloud-gateway)
  * **Dicom API**: 

## API endpoints

  * Eureka dashboard: [http://localhost:8761/](http://localhost:8761/)
  * Dicom Swagger UI: [http://localhost:8080/dicom/swagger-ui.html](http://localhost:8080/dicom/swagger-ui.html)
  * Entities
    * List all images: [http://localhost:8080/dicom/images/](http://localhost:8080/dicom/images/)
    * Get an specific image: [http://localhost:8080/dicom/images/##](http://localhost:8080/dicom/images/##) where "##" is the image ID
    * List all series: [http://localhost:8080/dicom/series/](http://localhost:8080/dicom/series/)
    * Get an specific series: [http://localhost:8080/dicom/series/##](http://localhost:8080/dicom/series/##) where "##" is the series ID
    * List all studies: [http://localhost:8080/dicom/studies/](http://localhost:8080/dicom/studies/)
    * Get an specific study: [http://localhost:8080/dicom/studies/##](http://localhost:8080/dicom/studies/##) where "##" is the study ID

### Inserting a Study

A complete study can be inserted with the help of [Swagger](http://localhost:8080/dicom/swagger-ui.html#/study-controller/saveStudyUsingPOST) by this JSON:
``` JSON
{
  "id": "ST001",
  "name": "X-Ray Angiography",
  "patient": "Jhon Doe",
  "series": [
    {
      "id": "SS001",
      "images": [
        {
          "id": "IMG001",
          "notes": "Medium and high right arm",
          "series": { "id": "SS001" }
        },
        {
          "id": "IMG002",
          "notes": "High right leg",
          "series": { "id": "SS001" }
        }
      ],
      "modality": "XA",
      "study": { "id": "ST001" }
    }
  ]
}
```
Ref: [_A list of Series' Modalities_](https://www.dicomlibrary.com/dicom/modality/)