package com.example.javatechassignment.files.metadata;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
interface MetadataRepository extends JpaRepository<Metadata, Long> {
}
