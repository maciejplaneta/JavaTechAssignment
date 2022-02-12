package com.example.javatechassignment.domain;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import com.example.javatechassignment.domain.metadata.MetadataService;
import com.example.javatechassignment.domain.metadata.MetadataServiceConfig;
import com.example.javatechassignment.domain.storage.StorageService;
import com.example.javatechassignment.domain.storage.StorageServiceConfig;
import com.example.javatechassignment.domain.usecases.DeleteFileUseCase;
import com.example.javatechassignment.domain.usecases.GetFileUseCase;
import com.example.javatechassignment.domain.usecases.ReplaceFileUseCase;
import com.example.javatechassignment.domain.usecases.StoreFileUseCase;

@Import({MetadataServiceConfig.class, StorageServiceConfig.class})
public class UseCasesConfiguration {

    @Bean
    public StoreFileUseCase storeFileUseCase(MetadataService metadataService, StorageService storageService) {
        return new StoreFileUseCase(metadataService, storageService);
    }

    @Bean
    public GetFileUseCase getFileUseCase(MetadataService metadataService, StorageService storageService) {
        return new GetFileUseCase(metadataService, storageService);
    }

    @Bean
    public DeleteFileUseCase deleteFileUseCase(MetadataService metadataService, StorageService storageService) {
        return new DeleteFileUseCase(metadataService, storageService);
    }

    @Bean
    public ReplaceFileUseCase replaceFileUseCase(MetadataService metadataService, StorageService storageService) {
        return new ReplaceFileUseCase(metadataService, storageService);
    }

}
