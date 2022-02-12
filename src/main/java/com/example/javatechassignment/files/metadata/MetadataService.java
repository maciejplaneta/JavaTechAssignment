package com.example.javatechassignment.files.metadata;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.StreamCorruptedException;
import org.springframework.web.multipart.MultipartFile;
import com.example.javatechassignment.files.storage.responses.GetFileResponse;
import com.example.javatechassignment.files.storage.responses.StoreFileResponse;

public interface MetadataService {

    StoreFileResponse storeFile(MultipartFile multipart) throws IOException;

    GetFileResponse getFile(Long fileId) throws FileNotFoundException, StreamCorruptedException;
}
