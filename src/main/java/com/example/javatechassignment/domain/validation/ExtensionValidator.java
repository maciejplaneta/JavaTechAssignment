package com.example.javatechassignment.domain.validation;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import com.example.javatechassignment.domain.exceptions.ReplacingFileException;
import com.example.javatechassignment.domain.exceptions.UnsupportedExtensionException;

public class ExtensionValidator {

    public static final List<String> SUPPORTED_FILE_FORMATS = Stream
          .of(SupportedFileExtension.values())
          .map(Enum::name)
          .map(String::toLowerCase)
          .collect(Collectors.toList());

    public void checkIfExtensionIsSupported(MultipartFile file) {
        String fileExtension = FilenameUtils.getExtension(file.getOriginalFilename());

        if(!isSupported(fileExtension)) {
            throw new UnsupportedExtensionException(String.format("File extension '%s' is unsupported", fileExtension));
        }
    }

    public void checkIfFileExtensionsMatch(String firstExtension, String secondExtension) {
        if(!StringUtils.equals(firstExtension, secondExtension)) {
            throw new ReplacingFileException("Files with different extensions can't be replaced");
        }
    }

    public boolean isSupported(String fileFormat) {
        return SUPPORTED_FILE_FORMATS.contains(fileFormat);
    }
}
