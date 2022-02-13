package com.example.javatechassignment;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import com.example.javatechassignment.domain.storage.StorageDirectoryAccessor;
import com.example.javatechassignment.domain.usecases.UseCasesConfiguration;

@Import({UseCasesConfiguration.class})
@SpringBootApplication
@EnableJpaRepositories
public class JavaTechAssignmentApplication {

    public static void main(String[] args) {
        SpringApplication.run(JavaTechAssignmentApplication.class, args);
        StorageDirectoryAccessor.createStorageDirectory();
    }

}
