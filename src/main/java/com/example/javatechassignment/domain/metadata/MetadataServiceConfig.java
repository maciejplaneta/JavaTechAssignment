package com.example.javatechassignment.domain.metadata;

import org.springframework.context.annotation.Bean;

public class MetadataServiceConfig {

    @Bean
    private MetadataService metadataService(MetadataRepository repository) {
        return new MetadataServiceImpl(repository);
    }
}
