package com.example.javatechassignment.domain.usecases;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.reset;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Random;
import org.apache.commons.io.FileUtils;
import org.junit.jupiter.api.BeforeEach;
import org.mockito.Mockito;
import org.springframework.mock.web.MockMultipartFile;
import com.example.javatechassignment.domain.metadata.Metadata;
import com.example.javatechassignment.domain.metadata.MetadataService;
import com.example.javatechassignment.domain.storage.StorageService;
import com.example.javatechassignment.domain.validation.SupportedFileExtension;

abstract class AbstractFileOperationUseCaseTest {

    protected static final String FILE_NAME = "sample";
    protected static final String DOT_SEPARATOR = ".";
    protected static final String UNSUPPORTED_EXTENSION = "UNSUPPORTED_EXTENSION";
    protected static final String TXT_EXTENSION = SupportedFileExtension.TXT.name().toLowerCase();
    protected static final String JPG_EXTENSION = SupportedFileExtension.JPG.name().toLowerCase();

    protected final StorageService storageService = Mockito.mock(StorageService.class);
    protected final MetadataService metadataService = Mockito.mock(MetadataService.class);

    @BeforeEach
    public void beforeEach() {
        reset(storageService, metadataService);
    }

    protected File prepareProperFile() throws IOException {
        InputStream inputStream = getClass()
              .getClassLoader()
              .getResourceAsStream(FILE_NAME + DOT_SEPARATOR + TXT_EXTENSION);
        assertThat(inputStream).isNotNull();

        File tempFile = File.createTempFile("prefix", null);
        FileUtils.copyInputStreamToFile(inputStream, tempFile);
        return tempFile;
    }

    protected File prepareUnreadableFile() {
        return mock(File.class);
    }

    protected Metadata prepareMetadata() {
        Metadata metadata = new Metadata();
        metadata.setId(new Random().nextLong());
        metadata.setCurrentName(FILE_NAME);
        metadata.setOriginalName(FILE_NAME);
        metadata.setExtension(TXT_EXTENSION);
        metadata.setSize(128L);

        return metadata;
    }

    protected Metadata prepareMetadata(MockMultipartFile file) {
        Metadata fileMetadata = new Metadata(file);
        fileMetadata.setId(new Random().nextLong());
        return fileMetadata;
    }
}
