package com.example.javatechassignment.api;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.NO_CONTENT;
import static org.springframework.http.HttpStatus.OK;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import com.example.javatechassignment.domain.usecases.DeleteFileUseCase;
import com.example.javatechassignment.domain.usecases.GetFileUseCase;
import com.example.javatechassignment.domain.usecases.ReplaceFileUseCase;
import com.example.javatechassignment.domain.usecases.StoreFileUseCase;
import com.example.javatechassignment.domain.usecases.responses.DeleteFileResponse;
import com.example.javatechassignment.domain.usecases.responses.ReplaceFileResponse;
import com.example.javatechassignment.domain.usecases.responses.StoreFileResponse;

import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import io.swagger.v3.oas.annotations.Operation;
import lombok.AllArgsConstructor;

@RestController
@RequestMapping(value = "/storage")
@AllArgsConstructor
class StorageRestController {

    private final StoreFileUseCase storeFileUseCase;
    private final GetFileUseCase getFileUseCase;
    private final ReplaceFileUseCase replaceFileUseCase;
    private final DeleteFileUseCase deleteFileUseCase;

    @Operation(summary = "Store file")
    @ApiResponses(value = {
          @ApiResponse(code = 200, message = "File was retrieved successfully"),
          @ApiResponse(code = 415, message = "Unsupported file extension"),
          @ApiResponse(code = 500, message = "Server error has occurred - file could not be stored")
    })
    @PostMapping
    public ResponseEntity<StoreFileResponse> storeFile(@RequestParam("file") MultipartFile file) {
        return ResponseEntity.status(CREATED).body(storeFileUseCase.storeFile(file));
    }

    @Operation(summary = "Get file of given ID")
    @ApiResponses(value = {
          @ApiResponse(code = 200, message = "File was retrieved successfully"),
          @ApiResponse(code = 204, message = "File with given ID is not present in storage"),
          @ApiResponse(code = 500, message = "Server error has occurred - file could not be retrieved")
    })
    @GetMapping(value = "/{fileId}")
    public ResponseEntity<byte[]> getStoredFile(@PathVariable("fileId") Long fileId) {
        return getFileUseCase
              .getFile(fileId)
              .map(response -> ResponseEntity
                    .status(OK)
                    .headers(response.getHeaders())
                    .body(response.getContent()))
              .orElseGet(() -> ResponseEntity.status(NO_CONTENT).build());
    }

    @Operation(summary = "Replace file of given ID with another - they must have the same extension")
    @ApiResponses(value = {
          @ApiResponse(code = 200, message = "File was replaced successfully"),
          @ApiResponse(code = 204, message = "File with given ID is not present in storage"),
          @ApiResponse(code = 416, message = "New and old file have different extension"),
          @ApiResponse(code = 500, message = "Server error has occurred - file could not be replaced")
    })
    @PutMapping(value = "/{fileId}")
    public ResponseEntity<ReplaceFileResponse> replaceFile(@PathVariable("fileId") Long fileId,
          @RequestParam("file") MultipartFile newFile) {
        return replaceFileUseCase
              .replaceFile(fileId, newFile)
              .map(response -> ResponseEntity.status(OK).body(response))
              .orElseGet(() -> ResponseEntity.status(NO_CONTENT).build());
    }

    @Operation(summary = "Get paged metadata")
    @ApiResponses(value = {
          @ApiResponse(code = 200, message = "File was deleted successfully"),
          @ApiResponse(code = 204, message = "File with given ID is not present in storage"),
          @ApiResponse(code = 500, message = "Server error has occurred - file could not be deleted")
    })
    @DeleteMapping(value = "/{fileId}")
    public ResponseEntity<DeleteFileResponse> deleteFile(@PathVariable("fileId") Long fileId) {
        return deleteFileUseCase
              .deleteFile(fileId)
              .map(response -> ResponseEntity.status(OK).body(response))
              .orElseGet(() -> ResponseEntity.status(NO_CONTENT).build());
    }

}
