package com.example.javatechassignment.domain.storage;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Date;
import org.springframework.http.HttpStatus;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class StorageDirectoryAccessor {

    private static StorageDirectoryAccessor instance;

    private final SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy_HH-mm-ss");
    private final String path;

    private StorageDirectoryAccessor() {
        path = generateStorageDirectoryPath();
        try {
            log.info("Creating storage directory: " + path);
            Files.createDirectories(Paths.get(path));
        } catch(IOException e) {
            log.error("Could not create directory: " + path);
            System.exit(HttpStatus.NOT_FOUND.value());
        }
    }

    public static boolean createStorageDirectory() {
        boolean isAlreadyCreated = true;
        if (instance == null) {
            isAlreadyCreated = false;
            instance = new StorageDirectoryAccessor();
        }

        return isAlreadyCreated;
    }

    public static String storagePath() {
        if (instance == null) {
            instance = new StorageDirectoryAccessor();
        }
        return instance.path;
    }

    private String generateStorageDirectoryPath() {
        StringBuilder sb = new StringBuilder();
        sb.append(System.getProperty("java.io.tmpdir"))
          .append("\\upload")
          .append("\\")
          .append(dateFormat.format(new Date()));
        return sb.toString();
    }
}
