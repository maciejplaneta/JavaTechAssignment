package com.example.javatechassignment.files.metadata;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StoredFilesRepository extends JpaRepository<StoredFile, Long> {
}
