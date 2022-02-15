package com.example.javatechassignment.domain.usecases.responses;

import org.springframework.http.HttpHeaders;
import com.example.javatechassignment.domain.metadata.Metadata;

import lombok.Value;

@Value
public class GetFileResponse extends BaseFileActionResponse {
    byte[] content;
    HttpHeaders headers;

    public GetFileResponse(Metadata metadata, byte[] content) {
        this.fileId = metadata.getId();
        this.fileName = metadata.getCurrentName();
        this.fileSize = metadata.getSize();
        this.fileExtension = metadata.getExtension();
        this.content = content;
        this.headers = new GetResponseHeadersCreator(metadata).createGetFileResponseHeaders();
    }

}
