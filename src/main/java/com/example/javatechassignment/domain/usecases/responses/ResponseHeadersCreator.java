package com.example.javatechassignment.domain.usecases.responses;

import static com.example.javatechassignment.domain.usecases.responses.ResponseHeader.CURRENT_NAME;
import static com.example.javatechassignment.domain.usecases.responses.ResponseHeader.EXTENSION;
import static com.example.javatechassignment.domain.usecases.responses.ResponseHeader.ID;
import static com.example.javatechassignment.domain.usecases.responses.ResponseHeader.ORIGINAL_NAME;
import static com.example.javatechassignment.domain.usecases.responses.ResponseHeader.SIZE;

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
        headers.add(ID.name(), String.valueOf(metadata.getId()));
        headers.add(CURRENT_NAME.name(), String.valueOf(metadata.getCurrentName()));
        headers.add(ORIGINAL_NAME.name(), String.valueOf(metadata.getOriginalName()));
        headers.add(SIZE.name(), String.valueOf(metadata.getSize()));
        headers.add(EXTENSION.name(), String.valueOf(metadata.getExtension()));

        return headers;
    }
}
