package com.example.javatechassignment.files.metadata;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Data
@Entity
@Table(name = "stored_file")
public class StoredFile {

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

}
