package com.dtech.Ecommerce.fileService.service.Impl;

import com.dtech.Ecommerce.common.Document;
import com.dtech.Ecommerce.fileService.dto.CommonResponse;
import com.dtech.Ecommerce.fileService.model.FileMetadata;
import com.dtech.Ecommerce.fileService.repository.FileRepository;
import com.dtech.Ecommerce.fileService.service.FileService;
import com.dtech.Ecommerce.product.model.DocumentData;
import com.dtech.Ecommerce.product.repository.DocumentDataRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.UUID;

/**
 * Author: Nimesh Dilshan
 * User:nimesh_r
 * Date:1/6/2025
 * Time:12:24 PM
 */

@Service

public class FileServiceImpl implements FileService {

    @Value("${file.upload-dir}")
    private String uploadDir;

    @Autowired
    private FileRepository fileRepository;
    private final Path uploadDirectory;
    @Autowired
    private DocumentDataRepo documentDataRepo;

    public FileServiceImpl(@Value("${file.upload-dir}") String uploadDir) {
        this.uploadDirectory = Paths.get(uploadDir).toAbsolutePath().normalize();

        // Ensure the directory exists
        try {
            Files.createDirectories(this.uploadDirectory);
        } catch (Exception e) {
            throw new RuntimeException("Could not create upload directory!", e);
        }
    }

    public Path getUploadDirectory() {
        return this.uploadDirectory;
    }

    @Override
    public CommonResponse uploadFiles(MultipartFile file) throws IOException {
        try {
            return saveFile(file);
        } catch (IOException e) {
            throw new RuntimeException("Failed to upload file: " + file.getOriginalFilename(), e);
        }
    }

    private CommonResponse saveFile(MultipartFile file) throws IOException {
        CommonResponse cr = new CommonResponse();
        String uuid = UUID.randomUUID().toString();
        String originalFileName = file.getOriginalFilename();
        String storedFileName = uuid + "_" + originalFileName;
        Path filePath = Paths.get(uploadDir, storedFileName);

        // Save file locally
        Files.write(filePath, file.getBytes());

        // Save metadata in the database
        FileMetadata metadata = new FileMetadata();
        metadata.setUuid(uuid);
        metadata.setOriginalFileName(originalFileName);
        metadata.setStoredFileName(storedFileName);
        metadata.setFilePath(filePath.toString());
        metadata.setUploadTime(LocalDateTime.now());
        fileRepository.saveAndFlush(metadata);
        cr.setMessage(uuid);
        cr.setFileName(originalFileName);
        return cr;
    }

    @Override
    public byte[] downloadFile(String uuid) throws IOException {
        FileMetadata metadata = fileRepository.findByUuid(uuid)
                .orElseThrow(() -> new RuntimeException("File not found with UUID: " + uuid));

        Path filePath = Paths.get(metadata.getFilePath());
        return Files.readAllBytes(filePath);
    }

    @Override
    public String deleteFile(String uuid) {
        fileRepository.findByUuid(uuid)
                .ifPresent(metadata -> {
                    try {
                        Files.delete(Paths.get(metadata.getFilePath()));
                        fileRepository.delete(metadata);
                        DocumentData doc = documentDataRepo.findByDocUUID(uuid);
                        if(doc != null){
                            documentDataRepo.delete(doc);
                        }

                    } catch (IOException e) {
                        throw new RuntimeException("Failed to delete file with UUID: " + uuid, e);
                    }
                });
        return "File deleted successfully";
    }
}
