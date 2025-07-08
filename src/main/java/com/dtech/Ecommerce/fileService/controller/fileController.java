package com.dtech.Ecommerce.fileService.controller;

import com.dtech.Ecommerce.fileService.dto.CommonResponse;
import com.dtech.Ecommerce.fileService.service.FileService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

/**
 * Author: Nimesh Dilshan
 * User:nimesh_r
 * Date:1/6/2025
 * Time:11:52 AM
 */

@RestController
@RequestMapping("/api/files")
@RequiredArgsConstructor
public class fileController {

    private final FileService fileService;

    @PostMapping("/upload")
    public ResponseEntity<?> uploadFiles(@RequestParam("files") MultipartFile files) {
        try {;
            return new ResponseEntity<>(fileService.uploadFiles(files), HttpStatus.OK);
        } catch (IOException e) {
            return ResponseEntity.status(500).body(null);
        }
    }

    @GetMapping("/download/{uuid}")
    public ResponseEntity<byte[]> downloadFile(@PathVariable String uuid) {
        try {
            byte[] fileBytes = fileService.downloadFile(uuid);

            return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + uuid + "\"")
                    .contentType(MediaType.APPLICATION_OCTET_STREAM)
                    .body(fileBytes);
        } catch (IOException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/delete/{uuid}")
    public ResponseEntity<?> deleteFile(@PathVariable String uuid) {
      return new ResponseEntity<>(fileService.deleteFile(uuid), HttpStatus.OK);
    }
}
