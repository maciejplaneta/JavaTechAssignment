package com.example.javatechassignment.store_file;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.MalformedURLException;
import java.nio.file.Paths;
import java.util.Optional;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.ContentDisposition;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import com.example.javatechassignment.StorageDirectory;
import com.example.javatechassignment.db_model.StoredFile;
import com.example.javatechassignment.db_model.StoredFilesRepository;

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
    public ResponseEntity<Resource> getStoredFile(@PathVariable("fileId") Long fileId) {

        Optional<StoredFile> fileOptional = filesRepository.findById(fileId);

        return fileOptional.map(storedFile -> {
            File file = new File(StorageDirectory.path(), fileId + "_" + storedFile.getCurrentName());
            Resource resource;
            try {
                resource = new UrlResource(Paths.get(file.getAbsolutePath()).toUri());
            } catch(MalformedURLException e) {
                e.printStackTrace();
                return new ResponseEntity<Resource>(HttpStatus.BAD_REQUEST);
            }

            HttpHeaders headers = new HttpHeaders();
            headers.setContentDisposition(ContentDisposition
                  .attachment()
                  .filename(storedFile.getId() + " _ " + storedFile.getCurrentName())
                  .build());

            return new ResponseEntity<Resource>(resource, headers, HttpStatus.OK);
        }).orElse(new ResponseEntity<Resource>(HttpStatus.NOT_FOUND));
    }

    @Value
    @AllArgsConstructor
    private class StoreFileResponse {
        Long fileId;
        String fileName;
    }
}
