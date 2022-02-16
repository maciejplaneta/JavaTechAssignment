package com.example.javatechassignment.domain.metadata;

import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.multipart.MultipartFile;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@AllArgsConstructor
class MetadataServiceImpl implements MetadataService {

    private MetadataRepository metadataRepository;

    public Metadata saveMetadata(MultipartFile multipart) {
        Metadata fileToSave = new Metadata(multipart);
        return metadataRepository.save(fileToSave);
    }

    public Optional<Metadata> getMetadata(Long fileId) {
        return metadataRepository.findById(fileId);
    }

    @Override
    public void deleteMetadata(Long fileId) {
        metadataRepository.deleteById(fileId);
    }

    @Override
    public Metadata update(Metadata oldMetadata, MultipartFile newFile) {
        oldMetadata.update(newFile);

        return metadataRepository.save(oldMetadata);
    }

    @Override
    public Page<Metadata> getMetadataInChunks(PageRequest pageRequest) {
        return metadataRepository.findAll(pageRequest);
    }

}
