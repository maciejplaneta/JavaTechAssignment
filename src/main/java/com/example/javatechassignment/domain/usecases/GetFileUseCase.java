package com.example.javatechassignment.domain.usecases;

import java.io.File;
import java.io.IOException;
import java.util.Optional;
import org.apache.commons.io.FileUtils;
import com.example.javatechassignment.domain.metadata.Metadata;
import com.example.javatechassignment.domain.metadata.MetadataService;
import com.example.javatechassignment.domain.responses.GetFileResponse;
import com.example.javatechassignment.domain.storage.StorageService;
import com.example.javatechassignment.domain.storage.exceptions.StoringFileException;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@AllArgsConstructor
public class GetFileUseCase {

    private final MetadataService metadataService;
    private final StorageService storageService;

    public Optional<GetFileResponse> get(Long fileId) {
        log.info("Reading file of ID: {}", fileId);
        Optional<Metadata> metadata = metadataService.getMetadata(fileId);
        return metadata.flatMap(this::tryToReadFile);
    }

    private Optional<GetFileResponse> tryToReadFile(Metadata metadata) throws StoringFileException {
        try {
            File file = storageService.getFile(metadata);
            return Optional.of(new GetFileResponse(metadata, FileUtils.readFileToByteArray(file)));
        } catch(IOException e) {
            log.info("Reading file failed due to: ", e);
            return Optional.empty();
        }
    }
}
