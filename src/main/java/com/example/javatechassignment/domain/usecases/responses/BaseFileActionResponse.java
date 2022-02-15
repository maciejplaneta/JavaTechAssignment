package com.example.javatechassignment.domain.usecases.responses;

import lombok.Getter;

@Getter
abstract class BaseFileActionResponse {
    protected Long fileId;
    protected String fileName;
    protected String fileExtension;
    protected Long fileSize;
}
