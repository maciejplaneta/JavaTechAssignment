package com.example.javatechassignment.domain.metadata;

import java.util.Optional;
import org.springframework.web.multipart.MultipartFile;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@AllArgsConstructor
class MetadataServiceImpl implements MetadataService {

    private MetadataRepository storageRepository;

    public Metadata saveMetadata(MultipartFile multipart) {
        Metadata fileToSave = new Metadata(multipart);
        return storageRepository.save(fileToSave);
    }

    public Optional<Metadata> getMetadata(Long fileId) {
        return storageRepository.findById(fileId);
    }

    @Override
    public void deleteMetadata(Long fileId) {
        storageRepository.deleteById(fileId);
    }

}
