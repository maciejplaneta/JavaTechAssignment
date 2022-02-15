package com.example.javatechassignment.domain.usecases.responses;

import com.example.javatechassignment.domain.metadata.Metadata;

import lombok.Value;

@Value
public class StoreFileResponse extends BaseFileActionResponse {

    public StoreFileResponse(Metadata metadata) {
        this.fileId = metadata.getId();
        this.fileName = metadata.getCurrentName();
        this.fileSize = metadata.getSize();
        this.fileExtension = metadata.getExtension();
    }
}
