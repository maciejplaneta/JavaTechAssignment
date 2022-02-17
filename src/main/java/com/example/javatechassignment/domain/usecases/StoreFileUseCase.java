package com.example.javatechassignment.domain.usecases;

import java.io.IOException;
import org.springframework.web.multipart.MultipartFile;
import com.example.javatechassignment.domain.exceptions.StoringFileException;
import com.example.javatechassignment.domain.metadata.Metadata;
import com.example.javatechassignment.domain.metadata.MetadataService;
import com.example.javatechassignment.domain.storage.StorageService;
import com.example.javatechassignment.domain.usecases.responses.StoreFileResponse;
import com.example.javatechassignment.domain.validation.ExtensionValidator;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@AllArgsConstructor
public class StoreFileUseCase {

    private final MetadataService metadataService;
    private final StorageService storageService;
    private final ExtensionValidator extensionValidator = new ExtensionValidator();

    public StoreFileResponse storeFile(MultipartFile file) {
        extensionValidator.checkIfExtensionIsSupported(file);

        log.info("Storing file {}", file.getOriginalFilename());
        Metadata saveMetadata = metadataService.saveMetadata(file);
        return tryToStoreFile(file, saveMetadata);
    }

    private StoreFileResponse tryToStoreFile(MultipartFile file, Metadata saveMetadata) {
        try {
            storageService.storeFile(file, saveMetadata);
            return new StoreFileResponse(saveMetadata);
        } catch(IOException e) {
            log.error("File could not be stored due to: ", e);
            metadataService.deleteMetadata(saveMetadata.getId());
            throw new StoringFileException(e);
        }
    }
}
