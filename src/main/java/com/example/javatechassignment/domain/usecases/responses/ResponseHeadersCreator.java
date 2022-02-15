package com.example.javatechassignment.domain.usecases.responses;

import org.springframework.http.ContentDisposition;
import org.springframework.http.HttpHeaders;
import com.example.javatechassignment.domain.metadata.Metadata;

class GetResponseHeadersCreator {

    GetResponseHeadersCreator(Metadata metadata) {
        this.metadata = metadata;
    }

    private final Metadata metadata;

    HttpHeaders createGetFileResponseHeaders() {
        HttpHeaders headers = new HttpHeaders();

        headers.setContentDisposition(ContentDisposition.attachment().filename(metadata.getCurrentName()).build());
        headers.add("ID", String.valueOf(metadata.getId()));
        headers.add("CURRENT_NAME", String.valueOf(metadata.getCurrentName()));
        headers.add("ORIGINAL_NAME", String.valueOf(metadata.getOriginalName()));
        headers.add("SIZE", String.valueOf(metadata.getSize()));
        headers.add("EXTENSION", String.valueOf(metadata.getExtension()));

        return headers;
    }
}
