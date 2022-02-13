package com.example.javatechassignment.domain.usecases;

import java.io.IOException;
import java.util.Optional;
import org.springframework.web.multipart.MultipartFile;
import com.example.javatechassignment.domain.metadata.Metadata;
import com.example.javatechassignment.domain.metadata.MetadataService;
import com.example.javatechassignment.domain.storage.StorageService;
import com.example.javatechassignment.domain.storage.exceptions.ReplacingFileException;
import com.example.javatechassignment.domain.usecases.responses.ReplaceFileResponse;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@AllArgsConstructor
public class ReplaceFileUseCase {

    private final MetadataService metadataService;
    private final StorageService storageService;

    public Optional<ReplaceFileResponse> replaceFile(Long fileId, MultipartFile newFile) {
        log.info("Trying to replace file of ID: {}", fileId);

        Optional<Metadata> oldMetadata = metadataService.getMetadata(fileId);
        return oldMetadata.map(metadata -> tryToReplaceFile(metadata, newFile));
    }

    private ReplaceFileResponse tryToReplaceFile(Metadata oldMetadata, MultipartFile newFile) {
        try {
            storageService.replaceFile(oldMetadata, newFile);
            return new ReplaceFileResponse(metadataService.update(oldMetadata, newFile));
        } catch(IOException e) {
            throw new ReplacingFileException(e);
        }
    }
}
