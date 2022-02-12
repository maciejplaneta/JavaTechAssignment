package com.example.javatechassignment.files.storage;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.StreamCorruptedException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import com.example.javatechassignment.files.metadata.MetadataService;
import com.example.javatechassignment.files.storage.responses.StoreFileResponse;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping(value = "/file")
@AllArgsConstructor
class FileStorageRestController {

    private MetadataService storageService;

    @PostMapping
    public ResponseEntity<StoreFileResponse> storeFile(@RequestParam("file") MultipartFile file) {
        ResponseEntity<StoreFileResponse> response;
        try {
            log.info("Attempting to store file: {}", file.getOriginalFilename());
            response = ResponseEntity.status(HttpStatus.CREATED).body(storageService.storeFile(file));
            log.info("Attempt succeed");
        } catch(IOException e) {
            log.error("Attempt failed due to: ", e);
            response = ResponseEntity.badRequest().build();
        }

        return response;
    }

    @GetMapping(value = "/{fileId}")
    public ResponseEntity<byte[]> getStoredFile(@PathVariable("fileId") Long fileId) {
        ResponseEntity<byte[]> response;
        try {
            log.info("Attempting to get file of ID {}", fileId);
            response = ResponseEntity.ok(storageService.getFile(fileId).getContent());
            log.info("Attempt succeed");
        } catch(StreamCorruptedException e) {
            log.error("Could not get requested file of ID {} due to: ", fileId, e);
            response = ResponseEntity.unprocessableEntity().build();
        } catch(FileNotFoundException e) {
            log.error("Could not get requested file of ID {} due to: ", fileId, e);
            response = ResponseEntity.notFound().build();
        }

        return response;
    }

}
