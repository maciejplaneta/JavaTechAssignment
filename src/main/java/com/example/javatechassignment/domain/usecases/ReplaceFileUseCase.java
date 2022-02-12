package com.example.javatechassignment.domain.usecases;

import java.io.IOException;
import java.util.Optional;
import org.springframework.web.multipart.MultipartFile;
import com.example.javatechassignment.domain.metadata.Metadata;
import com.example.javatechassignment.domain.metadata.MetadataService;
import com.example.javatechassignment.domain.storage.StorageService;
import com.example.javatechassignment.domain.usecases.responses.ReplaceFileResponse;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@AllArgsConstructor
public class ReplaceFileUseCase {

    private final MetadataService metadataService;
    private final StorageService storageService;

    public Optional<ReplaceFileResponse> replace(Long fileId, MultipartFile newFile) {
        log.info("Trying to replace file of ID: {}", fileId);

        return metadataService.getMetadata(fileId)
              .map(metadata -> tryToReplaceFile(metadata, newFile))
              .orElse(Optional.empty());

    }

    private Optional<ReplaceFileResponse> tryToReplaceFile(Metadata metadata, MultipartFile newFile) {
        try {
            storageService.replaceFile(metadata, newFile);
            return metadataService.update(metadata.getId(), newFile).map(ReplaceFileResponse::new);
        } catch(IOException e) {
            log.info("Replacing file failed due to: ", e);
            return Optional.empty();
        }
    }
}
