package com.example.javatechassignment.domain.metadata;

import java.util.Optional;
import org.springframework.web.multipart.MultipartFile;

public interface MetadataService {

    Metadata saveMetadata(MultipartFile multipart);

    Optional<Metadata> getMetadata(Long fileId);

    void deleteMetadata(Long fileId);

    Metadata update(Metadata oldMetadata, MultipartFile newFile);
}
