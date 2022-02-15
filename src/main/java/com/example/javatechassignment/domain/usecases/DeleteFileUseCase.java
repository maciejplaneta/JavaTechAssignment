package com.example.javatechassignment.domain.usecases;

import java.io.FileNotFoundException;
import java.util.Optional;
import com.example.javatechassignment.domain.exceptions.DeletingFileException;
import com.example.javatechassignment.domain.metadata.Metadata;
import com.example.javatechassignment.domain.metadata.MetadataService;
import com.example.javatechassignment.domain.storage.StorageService;
import com.example.javatechassignment.domain.usecases.responses.DeleteFileResponse;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@AllArgsConstructor
public class DeleteFileUseCase {

    private final MetadataService metadataService;
    private final StorageService storageService;

    public Optional<DeleteFileResponse> deleteFile(Long fileId) {
        log.info("Deleting file of ID: {}", fileId);
        Optional<Metadata> metadata = metadataService.getMetadata(fileId);

        return metadata.map(this::tryToDelete);
    }

    private DeleteFileResponse tryToDelete(Metadata metadata) {
        boolean deleteSucceed;

        try {
            deleteSucceed = storageService.deleteFile(metadata);
            if (deleteSucceed) {
                metadataService.deleteMetadata(metadata.getId());
            }
        } catch(FileNotFoundException e) {
            throw new DeletingFileException(e);
        }

        return new DeleteFileResponse(metadata);
    }
}
