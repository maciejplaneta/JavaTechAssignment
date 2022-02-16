package com.example.javatechassignment.domain.usecases;

import java.io.IOException;
import java.util.Optional;
import org.apache.commons.io.FilenameUtils;
import org.springframework.web.multipart.MultipartFile;
import com.example.javatechassignment.domain.exceptions.ReplacingFileException;
import com.example.javatechassignment.domain.metadata.Metadata;
import com.example.javatechassignment.domain.metadata.MetadataService;
import com.example.javatechassignment.domain.storage.StorageService;
import com.example.javatechassignment.domain.usecases.responses.ReplaceFileResponse;
import com.example.javatechassignment.domain.validation.ExtensionValidator;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@AllArgsConstructor
public class ReplaceFileUseCase {

    private final MetadataService metadataService;
    private final StorageService storageService;
    private final ExtensionValidator extensionValidator = new ExtensionValidator();

    public Optional<ReplaceFileResponse> replaceFile(Long fileId, MultipartFile newFile) {
        extensionValidator.checkIfExtensionIsSupported(newFile);

        log.info("Trying to replace file of ID: {}", fileId);
        Optional<Metadata> oldMetadata = metadataService.getMetadata(fileId);
        return oldMetadata.map(metadata -> tryToReplaceFile(metadata, newFile));
    }

    private ReplaceFileResponse tryToReplaceFile(Metadata oldMetadata, MultipartFile newFile) {
        try {
            extensionValidator.checkIfFileExtensionsMatch(oldMetadata.getExtension(),
                  FilenameUtils.getExtension(newFile.getOriginalFilename()));

            storageService.replaceFile(oldMetadata, newFile);
            Metadata updatedMetadata = metadataService.update(oldMetadata, newFile);
            return new ReplaceFileResponse(updatedMetadata);
        } catch(IOException e) {
            throw new ReplacingFileException(e);
        }
    }
}
