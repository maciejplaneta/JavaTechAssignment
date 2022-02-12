package com.example.javatechassignment.domain.storage;

import java.io.File;
import java.io.IOException;
import org.springframework.web.multipart.MultipartFile;
import com.example.javatechassignment.domain.metadata.Metadata;

public interface StorageService {

    void storeFile(MultipartFile file, Metadata fileMetadata) throws IOException;

    File getFile(Metadata metadata);
}
