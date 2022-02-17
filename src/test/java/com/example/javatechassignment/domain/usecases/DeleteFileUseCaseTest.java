package com.example.javatechassignment.domain.usecases;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doThrow;

import java.io.FileNotFoundException;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import com.example.javatechassignment.domain.exceptions.DeletingFileException;
import com.example.javatechassignment.domain.metadata.Metadata;
import com.example.javatechassignment.domain.usecases.responses.DeleteFileResponse;

class DeleteFileUseCaseTest extends AbstractFileOperationUseCaseTest {

    private final DeleteFileUseCase deleteFileUseCase = new DeleteFileUseCase(metadataService, storageService);

    @Test
    void shouldReturnProperResponseIfDeletedSuccessfully() throws FileNotFoundException {
        // given
        Metadata metadata = prepareMetadata();
        given(metadataService.getMetadata(anyLong())).willReturn(Optional.of(metadata));
        given(storageService.deleteFile(metadata)).willReturn(true);

        // when
        Optional<DeleteFileResponse> response = deleteFileUseCase.deleteFile(1L);

        // then
        assertThat(response).isPresent();
        assertResponseHasCorrectFieldValues(response.get(), metadata);
    }

    @Test
    void shouldThrowCustomExceptionIfFileIsNotPresentInStorage() throws FileNotFoundException {
        // given
        Metadata metadata = prepareMetadata();
        given(metadataService.getMetadata(anyLong())).willReturn(Optional.of(metadata));
        doThrow(FileNotFoundException.class).when(storageService).deleteFile(metadata);

        // expected
        assertThatThrownBy(() -> deleteFileUseCase.deleteFile(1L)).isInstanceOf(DeletingFileException.class);
    }

    @Test
    void shouldReturnEmptyOptionalIfMetadataNotPresent() {
        // given
        given(metadataService.getMetadata(anyLong())).willReturn(Optional.empty());

        // when
        Optional<DeleteFileResponse> response = deleteFileUseCase.deleteFile(1L);

        // then
        assertThat(response).isEmpty();
    }

    private void assertResponseHasCorrectFieldValues(DeleteFileResponse response, Metadata fileMetadata) {
        assertThat(response.getFileId()).isEqualTo(fileMetadata.getId());
        assertThat(response.getFileExtension()).isEqualTo(fileMetadata.getExtension());
        assertThat(response.getFileName()).isEqualTo(fileMetadata.getCurrentName());
        assertThat(response.getFileSize()).isEqualTo(fileMetadata.getSize());
    }
}
