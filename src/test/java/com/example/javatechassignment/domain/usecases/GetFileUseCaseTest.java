package com.example.javatechassignment.domain.usecases;

import static java.lang.String.valueOf;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Map;
import java.util.Optional;
import org.apache.commons.io.FileUtils;
import org.junit.jupiter.api.Test;
import org.springframework.http.ContentDisposition;
import org.springframework.http.HttpHeaders;
import com.example.javatechassignment.domain.exceptions.ReadingFileException;
import com.example.javatechassignment.domain.metadata.Metadata;
import com.example.javatechassignment.domain.usecases.responses.GetFileResponse;
import com.example.javatechassignment.domain.usecases.responses.ResponseHeader;

class GetFileUseCaseTest extends AbstractFileOperationUseCaseTest {

    private final GetFileUseCase getFileUseCase = new GetFileUseCase(metadataService, storageService);

    @Test
    public void shouldReturnProperGetResponseOnSuccess() throws IOException {
        // given
        Metadata metadata = prepareMetadata();
        File file = prepareProperFile();
        given(metadataService.getMetadata(anyLong())).willReturn(Optional.of(metadata));
        given(storageService.getFile(metadata)).willReturn(file);

        // when
        Optional<GetFileResponse> response = getFileUseCase.getFile(1L);

        // then
        assertThat(response).isPresent();
        assertResponseHasCorrectFieldValues(response.get(), metadata);
        assertResponseHasCorrectContent(file, response.get());
        assertResponseHasCorrectHttpHeaders(response.get(), metadata);
    }

    @Test
    public void shouldThrowCustomExceptionWhenFileNotPresentInStorage() throws FileNotFoundException {
        // given
        Metadata metadata = prepareMetadata();
        given(metadataService.getMetadata(anyLong())).willReturn(Optional.of(metadata));
        given(storageService.getFile(metadata)).willThrow(FileNotFoundException.class);

        // expected
        assertThatThrownBy(() -> getFileUseCase.getFile(1L)).isInstanceOf(ReadingFileException.class);
    }

    @Test
    public void shouldReturnEmptyOptionalIfFileMetadataNotPresent() {
        // given
        given(metadataService.getMetadata(anyLong())).willReturn(Optional.empty());

        // when
        Optional<GetFileResponse> response = getFileUseCase.getFile(1L);

        // then
        assertThat(response).isEmpty();
    }

    @Test
    public void shouldThrowCustomExceptionIfFileCouldNotBeRead() throws FileNotFoundException {
        // given
        Metadata metadata = prepareMetadata();
        File unreadableFile = prepareUnreadableFile();
        given(metadataService.getMetadata(anyLong())).willReturn(Optional.of(metadata));
        given(storageService.getFile(metadata)).willReturn(unreadableFile);

        // expected
        assertThatThrownBy(() -> getFileUseCase.getFile(1L)).isInstanceOf(ReadingFileException.class);

    }

    private void assertResponseHasCorrectContent(File file, GetFileResponse response) throws IOException {
        assertThat(response.getContent()).isEqualTo(FileUtils.readFileToByteArray(file));
    }

    private void assertResponseHasCorrectFieldValues(GetFileResponse response, Metadata fileMetadata) {
        assertThat(response.getFileId()).isEqualTo(fileMetadata.getId());
        assertThat(response.getFileExtension()).isEqualTo(fileMetadata.getExtension());
        assertThat(response.getFileName()).isEqualTo(fileMetadata.getCurrentName());
        assertThat(response.getFileSize()).isEqualTo(fileMetadata.getSize());
    }

    private void assertResponseHasCorrectHttpHeaders(GetFileResponse response, Metadata fileMetadata) {
        Map<String, String> headers = response.getHeaders().toSingleValueMap();

        assertThat(headers).containsEntry(ResponseHeader.ID.name(), valueOf(fileMetadata.getId()));
        assertThat(headers).containsEntry(ResponseHeader.CURRENT_NAME.name(), valueOf(fileMetadata.getCurrentName()));
        assertThat(headers).containsEntry(ResponseHeader.ORIGINAL_NAME.name(), valueOf(fileMetadata.getOriginalName()));
        assertThat(headers).containsEntry(ResponseHeader.SIZE.name(), valueOf(fileMetadata.getSize()));
        assertThat(headers).containsEntry(ResponseHeader.EXTENSION.name(), valueOf(fileMetadata.getExtension()));
        assertThat(headers).containsEntry(HttpHeaders.CONTENT_DISPOSITION,
              ContentDisposition.attachment().filename(fileMetadata.getFullFileName()).build().toString());

    }

}
