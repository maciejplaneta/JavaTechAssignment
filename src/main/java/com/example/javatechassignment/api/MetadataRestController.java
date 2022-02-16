package com.example.javatechassignment.api;

import static org.springframework.http.HttpStatus.NO_CONTENT;

import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.example.javatechassignment.domain.metadata.Metadata;
import com.example.javatechassignment.domain.usecases.GetMetadataUseCase;

import io.swagger.v3.oas.annotations.Operation;
import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/metadata")
@AllArgsConstructor
class MetadataRestController {

    private final GetMetadataUseCase getMetadataUseCase;

    @Operation(summary = "Get metadata of particular file")
    @GetMapping("/{fileId}")
    public ResponseEntity<Metadata> getMetadata(@PathVariable("fileId") Long fileId) {
        return getMetadataUseCase
              .getMetadata(fileId)
              .map(ResponseEntity::ok)
              .orElseGet(() -> ResponseEntity.status(NO_CONTENT).build());
    }

    @Operation(summary = "Get paged metadata")
    @GetMapping
    public ResponseEntity<Page<Metadata>> getMetadataInChunks(@RequestParam("page") Integer page,
          @RequestParam("size") Integer size) {
        return ResponseEntity.ok(getMetadataUseCase.getMetadataInChunks(page, size));
    }
}
