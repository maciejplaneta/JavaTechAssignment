package com.example.javatechassignment.domain.usecases.responses;

import com.example.javatechassignment.domain.metadata.Metadata;

import lombok.Value;

@Value
public class ReplaceFileResponse extends BaseFileActionResponse {

    String originalFileName;

    public ReplaceFileResponse(Metadata updatedMetadata) {
        this.fileId = updatedMetadata.getId();
        this.originalFileName = updatedMetadata.getOriginalName();
        this.fileName = updatedMetadata.getCurrentName();
        this.fileSize = updatedMetadata.getSize();
        this.fileExtension = updatedMetadata.getExtension();
    }
}
