package com.example.javatechassignment.domain.usecases;

import java.io.File;
import java.util.Optional;
import org.apache.commons.io.FileUtils;
import com.example.javatechassignment.domain.metadata.Metadata;
import com.example.javatechassignment.domain.metadata.MetadataService;
import com.example.javatechassignment.domain.storage.StorageService;
import com.example.javatechassignment.domain.storage.exceptions.ReadingFileException;
import com.example.javatechassignment.domain.usecases.responses.GetFileResponse;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@AllArgsConstructor
public class GetFileUseCase {

    private final MetadataService metadataService;
    private final StorageService storageService;

    public Optional<GetFileResponse> get(Long fileId) {
        log.info("Reading file of ID: {}", fileId);
        Optional<Metadata> fileMetadata = metadataService.getMetadata(fileId);

        return fileMetadata.map(this::tryToReadFile);
    }

    private GetFileResponse tryToReadFile(Metadata metadata) {
        try {
            File file = storageService.getFile(metadata);
            return new GetFileResponse(metadata, FileUtils.readFileToByteArray(file));
        } catch(Exception e) {
            throw new ReadingFileException(e);
        }
    }
}
