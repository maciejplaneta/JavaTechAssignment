package com.example.javatechassignment;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories
public class JavaTechAssignmentApplication {

    public static void main(String[] args) {
        SpringApplication.run(JavaTechAssignmentApplication.class, args);
        StorageDirectory.create();
    }

}
