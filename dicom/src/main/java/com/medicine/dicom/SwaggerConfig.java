package com.medicine.dicom;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class SwaggerConfig {

    @Bean
    public Docket swaggerconf() {
        return new Docket(DocumentationType.SWAGGER_2)
                .useDefaultResponseMessages(false)
                .apiInfo(apiInfo("1.0-BETA"))
                .select()
                .apis(RequestHandlerSelectors.any())
//                .paths(PathSelectors.ant("/images/**"))
//                .paths(PathSelectors.regex("/version"))
                .paths(PathSelectors.any())
                .build();
    }

    private ApiInfo apiInfo(String version) {
        return new ApiInfoBuilder()
                .title("DICOM REST API")
                .description("API to save and search DICOM images")
                .version(version)
                .contact(new Contact("Matias Aguero", "http://www.linkedin.com/in/mjaguero", "mjaguero.work@gmail.com"))
                .build();
    }

}