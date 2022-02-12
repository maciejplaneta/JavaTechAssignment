package com.example.javatechassignment.files.metadata;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.StreamCorruptedException;
import java.util.Optional;
import org.apache.commons.io.FileUtils;
import org.springframework.web.multipart.MultipartFile;
import com.example.javatechassignment.files.storage.StorageDirectory;
import com.example.javatechassignment.files.storage.responses.GetFileResponse;
import com.example.javatechassignment.files.storage.responses.StoreFileResponse;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@AllArgsConstructor
class MetadataServiceImpl implements MetadataService {

    private MetadataRepository storageRepository;

    public StoreFileResponse storeFile(MultipartFile multipart) throws IOException {
        Metadata fileToSave = new Metadata(multipart);
        Metadata savedMetadata = storageRepository.save(fileToSave);

        File newFile = new File(StorageDirectory.path(), savedMetadata.getId() + "_" + multipart.getOriginalFilename());
        multipart.transferTo(newFile);

        return new StoreFileResponse(savedMetadata);
    }

    public GetFileResponse getFile(Long fileId) throws FileNotFoundException, StreamCorruptedException {
        Optional<Metadata> metadataOptional = storageRepository.findById(fileId);

        Metadata foundMetadata = metadataOptional.orElseThrow(FileNotFoundException::new);

        File file = new File(StorageDirectory.path(), fileId + "_" + foundMetadata.getCurrentName());
        byte[] fileContent = tryToReadFile(file);

        return new GetFileResponse(foundMetadata, fileContent);
    }

    private byte[] tryToReadFile(File file) throws StreamCorruptedException {
        try {
            log.debug("Reading file {}", file.getName());
            return FileUtils.readFileToByteArray(file);
        } catch(IOException e) {
            log.debug("Reading file failed due to: ", e);
            throw new StreamCorruptedException(e.getMessage());
        }
    }

}
