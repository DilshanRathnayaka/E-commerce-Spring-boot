package com.dtech.Ecommerce.fileService.service;

import com.dtech.Ecommerce.fileService.dto.CommonResponse;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

/**
 * Author: Nimesh Dilshan
 * User:nimesh_r
 * Date:1/6/2025
 * Time:12:30 PM
 */
public interface FileService {
    CommonResponse uploadFiles(MultipartFile files) throws IOException;

    byte[] downloadFile(String uuid) throws IOException;

    String deleteFile(String uuid);
}
