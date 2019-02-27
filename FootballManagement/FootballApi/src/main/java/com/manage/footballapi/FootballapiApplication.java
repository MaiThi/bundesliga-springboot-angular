package com.manage.footballapi;

import com.manage.footballapi.API.Controller.UploadController;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import java.io.File;

@SpringBootApplication
@EnableJpaAuditing
public class FootballapiApplication {

    public static void main(String[] args) {
        SpringApplication.run(FootballapiApplication.class, args);
    }

}

