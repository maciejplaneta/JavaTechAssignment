package com.example.javatechassignment.domain.usecases.responses;

import com.example.javatechassignment.domain.metadata.Metadata;

import lombok.Value;

@Value
public class ReplaceFileResponse {

    Long fileId;
    String currentFileName;
    String originalFileName;
    Long currentSize;

    public ReplaceFileResponse(Metadata updatedMetadata) {
        fileId = updatedMetadata.getId();
        currentFileName = updatedMetadata.getCurrentName();
        originalFileName = updatedMetadata.getOriginalName();
        currentSize = updatedMetadata.getSize();
    }
}
