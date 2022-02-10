package com.example.javatechassignment;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Date;
import org.springframework.http.HttpStatus;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class StorageDirectory {

    private static StorageDirectory instance;

    private final SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy_HH-mm-ss");
    private final String path;

    private StorageDirectory() {
        path = generateStorageDirectoryPath();
        try {
            log.info("Creating storage directory: " + path);
            Files.createDirectories(Paths.get(path));
        } catch(IOException e) {
            log.error("Could not create directory: " + path);
            System.exit(HttpStatus.NOT_FOUND.value());
        }
    }

    public static boolean create() {
        boolean isAlreadyCreated = true;
        if (instance == null) {
            isAlreadyCreated = false;
            instance = new StorageDirectory();
        }

        return isAlreadyCreated;
    }

    public static String path() {
        if (instance == null) {
            instance = new StorageDirectory();
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
