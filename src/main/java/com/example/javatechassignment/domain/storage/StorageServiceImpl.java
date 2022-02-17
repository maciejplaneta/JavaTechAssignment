package com.example.javatechassignment.domain.storage;

import static com.example.javatechassignment.domain.storage.StorageDirectoryAccessor.storagePath;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import org.springframework.web.multipart.MultipartFile;
import com.example.javatechassignment.domain.metadata.Metadata;

class StorageServiceImpl implements StorageService {

    @Override
    public void storeFile(MultipartFile multipart, Metadata fileMetadata) throws IOException {
        if (storageDirectoryDoesNotExist()) {
            throw new FileNotFoundException("Storage directory does not exist");
        }
        File newFile = new File(storagePath(), fileMetadata.getId() + "_" + multipart.getOriginalFilename());
        multipart.transferTo(newFile);
    }

    @Override
    public File getFile(Metadata metadata) throws FileNotFoundException {
        File file = new File(storagePath(), metadata.getFullFileName());
        if (file.exists()) {
            return file;
        } else {
            throw new FileNotFoundException(String.format("File '%s' not found", metadata.getCurrentName()));
        }
    }

    @Override
    public boolean deleteFile(Metadata metadata) throws FileNotFoundException {
        File file = getFile(metadata);
        if (file.exists()) {
            return file.delete();
        } else {
            throw new FileNotFoundException(String.format("File '%s' not found", metadata.getCurrentName()));
        }
    }

    @Override
    public void replaceFile(Metadata oldMetadata, MultipartFile newFile) throws IOException {
        deleteFile(oldMetadata);
        storeFile(newFile, oldMetadata);
    }

    private boolean storageDirectoryDoesNotExist() {
        File storageDirectory = new File(storagePath());
        return !storageDirectory.exists() || !storageDirectory.isDirectory();
    }
}
