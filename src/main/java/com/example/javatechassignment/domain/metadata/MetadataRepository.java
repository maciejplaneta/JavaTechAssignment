package com.example.javatechassignment.domain.metadata;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
interface MetadataRepository extends PagingAndSortingRepository<Metadata, Long> {
}
