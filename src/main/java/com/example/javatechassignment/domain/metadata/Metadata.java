package com.example.javatechassignment.domain.metadata;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import org.apache.commons.io.FilenameUtils;
import org.springframework.web.multipart.MultipartFile;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Table(name = "file_metadata")
@NoArgsConstructor
public class Metadata {

    @Id
    @GeneratedValue
    @Column(name = "id")
    private Long id;

    @Column(name = "original_name")
    private String originalName;

    @Column(name = "current_name")
    private String currentName;

    @Column(name = "size")
    private Long size;

    @Column(name = "extension")
    private String extension;

    public Metadata(MultipartFile multipartFile) {
        this.originalName = multipartFile.getOriginalFilename();
        this.currentName = multipartFile.getOriginalFilename();
        this.size = multipartFile.getSize();
        this.extension = FilenameUtils.getExtension(multipartFile.getOriginalFilename());
    }
}
