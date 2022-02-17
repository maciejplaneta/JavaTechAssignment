package com.example.javatechassignment.domain.usecases;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doThrow;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockMultipartFile;
import com.example.javatechassignment.domain.exceptions.DifferentExtensionsException;
import com.example.javatechassignment.domain.exceptions.ReplacingFileException;
import com.example.javatechassignment.domain.metadata.Metadata;
import com.example.javatechassignment.domain.usecases.responses.ReplaceFileResponse;

class ReplaceFileUseCaseTest extends AbstractFileOperationUseCaseTest {

    private static final String DUMMY_CONTENT_TYPE = "content-type";
    private static final String REQUEST_PARAM_NAME = "file";

    private final ReplaceFileUseCase replaceFileUseCase = new ReplaceFileUseCase(metadataService, storageService);

    @Test
    public void shouldReturnProperResponseOnSuccessfulReplacement() {
        // given
        String newFileName = FILE_NAME + DOT_SEPARATOR + TXT_EXTENSION;
        byte[] newContent = new byte[256];
        MockMultipartFile newFile = new MockMultipartFile(REQUEST_PARAM_NAME, newFileName, DUMMY_CONTENT_TYPE,
              newContent);
        Metadata oldFileMetadata = prepareMetadata();
        given(metadataService.getMetadata(anyLong())).willReturn(Optional.of(oldFileMetadata));

        Metadata updatedMetadata = updateMetadata(oldFileMetadata, newFileName, newContent.length);
        given(metadataService.update(oldFileMetadata, newFile)).willReturn(updatedMetadata);

        // when
        Optional<ReplaceFileResponse> response = replaceFileUseCase.replaceFile(1L, newFile);

        // then
        assertThat(response).isNotEmpty();
        assertResponseHasCorrectFieldValues(response.get(), updatedMetadata);
    }

    @Test
    public void shouldThrowCustomExceptionIfFileIsNotPresentInStorage() throws IOException {
        // given
        String newFileName = FILE_NAME + DOT_SEPARATOR + TXT_EXTENSION;
        byte[] newContent = new byte[256];
        MockMultipartFile newFile = new MockMultipartFile(REQUEST_PARAM_NAME, newFileName, DUMMY_CONTENT_TYPE,
              newContent);
        Metadata oldFileMetadata = prepareMetadata();
        given(metadataService.getMetadata(anyLong())).willReturn(Optional.of(oldFileMetadata));

        doThrow(FileNotFoundException.class).when(storageService).replaceFile(oldFileMetadata, newFile);

        // expected
        assertThatThrownBy(() -> replaceFileUseCase.replaceFile(1L, newFile))
              .isInstanceOf(ReplacingFileException.class);
    }

    @Test
    public void shouldReturnEmptyOptionalIfFileMetadataAreNotPresent() {
        // given
        String newFileName = FILE_NAME + DOT_SEPARATOR + TXT_EXTENSION;
        MockMultipartFile dummyFile = new MockMultipartFile(REQUEST_PARAM_NAME, newFileName, DUMMY_CONTENT_TYPE,
              new byte[128]);
        given(metadataService.getMetadata(anyLong())).willReturn(Optional.empty());

        // when
        Optional<ReplaceFileResponse> response = replaceFileUseCase.replaceFile(1L, dummyFile);

        // then
        assertThat(response).isEmpty();
    }

    @Test
    public void shouldThrowCustomExceptionAfExtensionsAreNotMatching() {
        // given
        String newFileName = FILE_NAME + DOT_SEPARATOR + JPG_EXTENSION;
        MockMultipartFile newFileWithDifferentExtension = new MockMultipartFile(REQUEST_PARAM_NAME, newFileName,
              DUMMY_CONTENT_TYPE, new byte[128]);
        Metadata oldFileMetadata = prepareMetadata();
        oldFileMetadata.setExtension(TXT_EXTENSION);

        given(metadataService.getMetadata(anyLong())).willReturn(Optional.of(oldFileMetadata));

        // expected
        assertThatThrownBy(() -> replaceFileUseCase.replaceFile(1L, newFileWithDifferentExtension))
              .isInstanceOf(DifferentExtensionsException.class);
    }

    private void assertResponseHasCorrectFieldValues(ReplaceFileResponse response, Metadata fileMetadata) {
        assertThat(response.getFileId()).isEqualTo(fileMetadata.getId());
        assertThat(response.getFileExtension()).isEqualTo(fileMetadata.getExtension());
        assertThat(response.getFileName()).isEqualTo(fileMetadata.getCurrentName());
        assertThat(response.getFileSize()).isEqualTo(fileMetadata.getSize());
    }

    private Metadata updateMetadata(Metadata oldFileMetadata, String newFileName, int newFileSize) {
        Metadata updatedMetadata = prepareMetadata();

        updatedMetadata.setId(oldFileMetadata.getId());
        updatedMetadata.setCurrentName(newFileName);
        updatedMetadata.setSize((long) newFileSize);

        return updatedMetadata;
    }

}
