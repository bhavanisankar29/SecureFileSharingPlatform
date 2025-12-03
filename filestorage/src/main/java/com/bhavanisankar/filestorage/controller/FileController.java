package com.bhavanisankar.filestorage.controller;

import com.bhavanisankar.filestorage.model.FileEntity;
import com.bhavanisankar.filestorage.service.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

@RestController
@RequestMapping("/files")
public class FileController {

    @Autowired
    private FileService fileService;

    @PostMapping("/upload")
    public ResponseEntity<String> uploadFile(@RequestParam MultipartFile file, Authentication authentication) {
        try {
            fileService.uploadFile(file, authentication.getName());
            return ResponseEntity.ok("File uploaded successfully");
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error uploading file: " + e.getMessage());
        }
    }

    @GetMapping("/list")
    public List<FileEntity> listFiles(Authentication authentication) {
        return fileService.getFilesByUser(authentication.getName());
    }

    @GetMapping("/download/{id}")
    public ResponseEntity<Resource> downloadFile(@PathVariable Long id, Authentication authentication) {
        try {
            FileEntity fileEntity = fileService.getFile(id, authentication.getName());
            Path path = Paths.get(fileEntity.getFilePath());
            Resource resource = new UrlResource(path.toUri());

            if (resource.exists() || resource.isReadable()) {
                return ResponseEntity.ok()
                        .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + fileEntity.getFilename() + "\"")
                        .body(resource);
            } else {
                throw new RuntimeException("Could not read the file!");
            }
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteFile(@PathVariable Long id, Authentication authentication) {
        try {
            fileService.deleteFile(id, authentication.getName());
            return ResponseEntity.ok("File deleted");
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error deleting file");
        }
    }
}