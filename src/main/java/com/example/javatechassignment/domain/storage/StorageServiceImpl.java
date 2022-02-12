package com.example.javatechassignment.domain.storage;

import static com.example.javatechassignment.domain.storage.StorageDirectoryAccessor.storagePath;

import java.io.File;
import java.io.IOException;
import org.springframework.web.multipart.MultipartFile;
import com.example.javatechassignment.domain.metadata.Metadata;

class StorageServiceImpl implements StorageService {

    @Override
    public void storeFile(MultipartFile multipart, Metadata fileMetadata) throws IOException {
        File newFile = new File(storagePath(), fileMetadata.getId() + "_" + multipart.getOriginalFilename());
        multipart.transferTo(newFile);
    }

    @Override
    public File getFile(Metadata metadata) {
        return new File(storagePath(), metadata.getId() + "_" + metadata.getCurrentName());
    }
}
