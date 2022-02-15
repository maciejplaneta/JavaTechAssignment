package com.example.javatechassignment.domain.usecases.responses;

import com.example.javatechassignment.domain.metadata.Metadata;

import lombok.Value;

@Value
public class DeleteFileResponse extends BaseFileActionResponse {

    public DeleteFileResponse(Metadata metadata) {
        this.fileId = metadata.getId();
        this.fileName = metadata.getCurrentName();
        this.fileExtension = metadata.getExtension();
        this.fileSize = metadata.getSize();
    }
}
