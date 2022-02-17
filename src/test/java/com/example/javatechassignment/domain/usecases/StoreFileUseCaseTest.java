package com.example.javatechassignment.domain.usecases;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doThrow;

import java.io.IOException;
import java.util.stream.Stream;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.mock.web.MockMultipartFile;
import com.example.javatechassignment.domain.exceptions.StoringFileException;
import com.example.javatechassignment.domain.exceptions.UnsupportedExtensionException;
import com.example.javatechassignment.domain.metadata.Metadata;
import com.example.javatechassignment.domain.usecases.responses.StoreFileResponse;
import com.example.javatechassignment.domain.validation.SupportedFileExtension;

class StoreFileUseCaseTest extends AbstractFileOperationUseCaseTest {

    private static final String DUMMY_CONTENT_TYPE = "content-type";
    private static final String REQUEST_PARAM_NAME = "file";

    private final StoreFileUseCase storeFileUseCase = new StoreFileUseCase(metadataService, storageService);

    @ParameterizedTest
    @MethodSource("supportedFileExtensions")
    public void shouldReturnProperStoreResponseOnSuccess(String fileExtension) {
        // given
        String fileName = FILE_NAME + DOT_SEPARATOR + fileExtension;
        MockMultipartFile file = new MockMultipartFile(REQUEST_PARAM_NAME, fileName, DUMMY_CONTENT_TYPE, new byte[128]);
        Metadata fileMetadata = prepareMetadata(file);
        given(metadataService.saveMetadata(file)).willReturn(fileMetadata);

        // when
        StoreFileResponse response = storeFileUseCase.storeFile(file);

        // then
        assertResponseHasCorrectFieldValues(response, fileMetadata);
    }

    @Test
    public void shouldThrowCustomExceptionOnUnsupportedFileExtension() {
        // given
        String fileName = FILE_NAME + DOT_SEPARATOR + UNSUPPORTED_EXTENSION;
        MockMultipartFile file = new MockMultipartFile(REQUEST_PARAM_NAME, fileName, DUMMY_CONTENT_TYPE, new byte[128]);

        // expect
        assertThatThrownBy(() -> storeFileUseCase.storeFile(file))
              .isInstanceOf(UnsupportedExtensionException.class);
    }

    @Test
    public void shouldThrowCustomExceptionOnSavingFileToStorage() throws IOException {
        // given
        String fileName = FILE_NAME + DOT_SEPARATOR + TXT_EXTENSION;
        MockMultipartFile file = new MockMultipartFile(REQUEST_PARAM_NAME, fileName, DUMMY_CONTENT_TYPE, new byte[128]);
        Metadata fileMetadata = prepareMetadata(file);
        given(metadataService.saveMetadata(file)).willReturn(fileMetadata);
        doThrow(IOException.class).when(storageService).storeFile(file, fileMetadata);

        // expect
        assertThatThrownBy(() -> storeFileUseCase.storeFile(file))
              .isInstanceOf(StoringFileException.class);
    }

    private void assertResponseHasCorrectFieldValues(StoreFileResponse response, Metadata fileMetadata) {
        assertThat(response).isNotNull();
        assertThat(response.getFileId()).isEqualTo(fileMetadata.getId());
        assertThat(response.getFileExtension()).isEqualTo(fileMetadata.getExtension());
        assertThat(response.getFileName()).isEqualTo(fileMetadata.getCurrentName());
        assertThat(response.getFileSize()).isEqualTo(fileMetadata.getSize());
    }

    public static Stream<Arguments> supportedFileExtensions() {
        return Stream.of(SupportedFileExtension.values())
                     .map(fileExtension -> Arguments.of(fileExtension.name().toLowerCase()));
    }

    /*
        Test cases:
        1 - store file:
            a) Happy path
            b) With unsupported extension
            c) File could not be stored
        2 - get file:
            a) Happy path
            b) Metadata present && File not present
            c) Metadata not present && File not present
            d) Could not read file - check if metadata is deleted
        3 - replace file:
            a) Happy path
            b) Metadata present && File not present
            c) Metadata not present && File not present
            d) Not matching extensions
        4 - delete file:
            a) Happy path
            b) Metadata present && File not present
            c) Metadata not present && File not present
     */
}
