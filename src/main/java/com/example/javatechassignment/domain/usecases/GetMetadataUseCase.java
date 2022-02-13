package com.example.javatechassignment.domain.usecases;

import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import com.example.javatechassignment.domain.metadata.Metadata;
import com.example.javatechassignment.domain.metadata.MetadataService;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class GetMetadataUseCase {

    private final MetadataService metadataService;

    public Optional<Metadata> getMetadata(Long fileId) {
        return metadataService.getMetadata(fileId);
    }

    public Page<Metadata> getMetadataInChunks(Integer page, Integer size) {
        return metadataService.getMetadataInChunks(PageRequest.of(page, size));
    }
}
