package com.example.javatechassignment.domain.metadata;

import java.util.Optional;
import org.springframework.web.multipart.MultipartFile;

public interface MetadataService {

    Metadata saveMetadata(MultipartFile multipart);

    Optional<Metadata> getMetadata(Long fileId);

    void deleteMetadata(Long fileId);

    Optional<Metadata> update(Long fileId, MultipartFile newFile);
}
