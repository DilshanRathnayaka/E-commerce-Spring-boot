package com.dtech.Ecommerce.fileService.repository;
import com.dtech.Ecommerce.fileService.model.FileMetadata;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
/**
 * Author: Nimesh Dilshan
 * User:nimesh_r
 * Date:1/6/2025
 * Time:12:23 PM
 */
public interface FileRepository extends JpaRepository<FileMetadata, Long> {
    Optional<FileMetadata> findByUuid(String uuid);
}
