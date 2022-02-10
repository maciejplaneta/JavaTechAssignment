package com.example.javatechassignment.files.storage;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Optional;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ContentDisposition;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import com.example.javatechassignment.files.metadata.StoredFile;
import com.example.javatechassignment.files.metadata.StoredFilesRepository;

import lombok.AllArgsConstructor;
import lombok.Value;

@RestController
@RequestMapping(value = "/file")
class FileStorageController {

    @Autowired
    private StoredFilesRepository filesRepository;

    @PostMapping
    public ResponseEntity<StoreFileResponse> storeFile(@RequestParam("file") MultipartFile file) throws IOException {
        StoredFile storedFile = new StoredFile();

        storedFile.setOriginalName(file.getOriginalFilename());
        storedFile.setCurrentName(file.getOriginalFilename());
        storedFile.setSize(file.getSize());
        storedFile.setExtension(FilenameUtils.getExtension(file.getOriginalFilename()));
        StoredFile saved = filesRepository.save(storedFile);

        File newFile = new File(StorageDirectory.path(), saved.getId() + "_" + file.getOriginalFilename());

        try(OutputStream os = new FileOutputStream(newFile)) {
            os.write(file.getBytes());
        }

        return ResponseEntity.ok().body(new StoreFileResponse(saved.getId(), newFile.getName()));
    }

    @GetMapping(value = "/{fileId}")
    public ResponseEntity<byte[]> getStoredFile(@PathVariable("fileId") Long fileId) {
        Optional<StoredFile> fileOptional = filesRepository.findById(fileId);

        return fileOptional.map(storedFile -> createGetFileResponse(fileId, storedFile))
                           .orElse(ResponseEntity.notFound().build());
    }

    private ResponseEntity<byte[]> createGetFileResponse(Long fileId, StoredFile storedFile) {
        File file = new File(StorageDirectory.path(), fileId + "_" + storedFile.getCurrentName());
        try {
            byte[] fileBytes = FileUtils.readFileToByteArray(file);
            HttpHeaders headers = new HttpHeaders();
            headers.setContentDisposition(ContentDisposition.attachment().filename(file.getName()).build());
            return ResponseEntity.ok().headers(headers).body(fileBytes);
        } catch(IOException e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().build();
        }
    }

    @Value
    @AllArgsConstructor
    private class StoreFileResponse {
        Long fileId;
        String fileName;
    }
}
