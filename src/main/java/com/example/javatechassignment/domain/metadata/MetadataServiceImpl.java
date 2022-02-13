package com.example.javatechassignment.domain.metadata;

import java.util.Optional;
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
        oldMetadata.setCurrentName(newFile.getOriginalFilename());
        oldMetadata.setSize(newFile.getSize());

        return metadataRepository.save(oldMetadata);

//        return oldFile.map(metadata -> {
//            metadata.setCurrentName(newFile.getOriginalFilename());
//            metadata.setSize(newFile.getSize());
//            return Optional.of(metadataRepository.save(metadata));
//        }).orElse(Optional.empty());
    }

}
