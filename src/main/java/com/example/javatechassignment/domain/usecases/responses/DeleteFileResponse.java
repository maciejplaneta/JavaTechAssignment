package com.example.javatechassignment.domain.usecases.responses;

import com.example.javatechassignment.domain.metadata.Metadata;

import lombok.Value;

@Value
public class DeleteFileResponse {

    Long fileId;
    String currentFileName;
    String originalFileName;
    String fileExtension;

    public DeleteFileResponse(Metadata metadata) {
        this.fileId = metadata.getId();
        this.currentFileName = metadata.getCurrentName();
        this.originalFileName = metadata.getOriginalName();
        this.fileExtension = metadata.getExtension();
    }
}
