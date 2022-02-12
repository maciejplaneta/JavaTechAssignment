package com.example.javatechassignment.files.storage.responses;

import com.example.javatechassignment.files.metadata.Metadata;

import lombok.Value;

@Value
public class StoreFileResponse {
    Long fileId;
    String fileName;

    public StoreFileResponse(Metadata fileMetadata) {
        this.fileId = fileMetadata.getId();
        this.fileName = fileMetadata.getCurrentName();
    }
}
