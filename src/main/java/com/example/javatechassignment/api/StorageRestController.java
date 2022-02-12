package com.example.javatechassignment.api;

import static java.lang.String.format;
import static org.springframework.http.HttpHeaders.CONTENT_DISPOSITION;
import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.NO_CONTENT;
import static org.springframework.http.HttpStatus.OK;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import com.example.javatechassignment.domain.storage.exceptions.DeletingFileException;
import com.example.javatechassignment.domain.storage.exceptions.ReadingFileException;
import com.example.javatechassignment.domain.storage.exceptions.StoringFileException;
import com.example.javatechassignment.domain.usecases.DeleteFileUseCase;
import com.example.javatechassignment.domain.usecases.GetFileUseCase;
import com.example.javatechassignment.domain.usecases.StoreFileUseCase;
import com.example.javatechassignment.domain.usecases.responses.DeleteFileResponse;
import com.example.javatechassignment.domain.usecases.responses.StoreFileResponse;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping(value = "/storage")
@AllArgsConstructor
class StorageRestController {

    private StoreFileUseCase storeFileUseCase;
    private GetFileUseCase getFileUseCase;
    private DeleteFileUseCase deleteFileUseCase;

    @PostMapping
    public ResponseEntity<StoreFileResponse> storeFile(@RequestParam("file") MultipartFile file) {
        return storeFileUseCase
              .store(file)
              .map(response -> ResponseEntity.status(CREATED).body(response))
              .orElseThrow(StoringFileException::new);
    }

    @GetMapping(value = "/{fileId}")
    public ResponseEntity<byte[]> getStoredFile(@PathVariable("fileId") Long fileId) {
        return getFileUseCase
              .get(fileId)
              .map(response -> ResponseEntity
                    .status(OK)
                    .header(CONTENT_DISPOSITION, format("attachment; filename=%s", response.getCurrentFileName()))
                    .body(response.getContent()))
              .orElseThrow(ReadingFileException::new);
    }

    @DeleteMapping(value = "/{fileId}")
    public ResponseEntity<DeleteFileResponse> deleteFile(@PathVariable("fileId") Long fileId) {
        return deleteFileUseCase
              .delete(fileId)
              .map(response -> ResponseEntity
                    .status(NO_CONTENT)
                    .body(response))
              .orElseThrow(DeletingFileException::new);
    }

}
