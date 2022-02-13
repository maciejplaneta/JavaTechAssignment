package com.example.javatechassignment.domain.validation;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.apache.commons.io.FilenameUtils;
import org.springframework.web.multipart.MultipartFile;

public class FormatValidator {

    public static final List<String> SUPPORTED_FILE_FORMATS = Stream
          .of(SupportedFileFormat.values())
          .map(Enum::name)
          .map(String::toLowerCase)
          .collect(Collectors.toList());

    public void validateExtension(MultipartFile file) {
        String fileExtension = FilenameUtils.getExtension(file.getOriginalFilename());

        if (!isSupported(fileExtension)) {
            throw new UnsupportedFormatException(String.format("File format '%s' is unsupported", fileExtension));
        }
    }

    public boolean isSupported(String fileFormat) {
        return SUPPORTED_FILE_FORMATS.contains(fileFormat);
    }
}
