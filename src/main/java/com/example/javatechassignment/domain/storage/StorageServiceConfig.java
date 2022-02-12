package com.example.javatechassignment.domain.storage;

import org.springframework.context.annotation.Bean;

public class StorageServiceConfig {

    @Bean
    public StorageService storageService() {
        return new StorageServiceImpl();
    }
}
