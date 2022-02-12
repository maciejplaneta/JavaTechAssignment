package com.example.javatechassignment.domain.usecases.responses;

import com.example.javatechassignment.domain.metadata.Metadata;

import lombok.Value;

@Value
public class StoreFileResponse {
    Long id;
    String name;
    Long size;
    String extension;

    public StoreFileResponse(Metadata fileMetadata) {
        this.id = fileMetadata.getId();
        this.name = fileMetadata.getCurrentName();
        this.size = fileMetadata.getSize();
        this.extension = fileMetadata.getExtension();
    }
}
