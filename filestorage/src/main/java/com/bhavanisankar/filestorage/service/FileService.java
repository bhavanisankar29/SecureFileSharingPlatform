package com.bhavanisankar.filestorage.service;

import com.bhavanisankar.filestorage.model.FileEntity;
import com.bhavanisankar.filestorage.model.User;
import com.bhavanisankar.filestorage.repository.FileRepository;
import com.bhavanisankar.filestorage.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class FileService {

    // IMPORTANT: Saves files to a folder named 'uploads' in your project root
    private final String UPLOAD_DIR = "uploads/";

    @Autowired
    private FileRepository fileRepository;

    @Autowired
    private UserRepository userRepository;

    public FileService() {
        // Create upload directory if it doesn't exist
        File directory = new File(UPLOAD_DIR);
        if (!directory.exists()) {
            directory.mkdirs();
        }
    }

    public void uploadFile(MultipartFile file, String username) throws IOException {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));

        // Create user specific folder
        String userFolderPath = UPLOAD_DIR + user.getId() + "/";
        File userFolder = new File(userFolderPath);
        if (!userFolder.exists()) {
            userFolder.mkdirs();
        }

        // Save file to file system
        String fileName = file.getOriginalFilename();
        Path filePath = Paths.get(userFolderPath + fileName);
        Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);

        // Save metadata to DB
        FileEntity fileEntity = new FileEntity();
        fileEntity.setFilename(fileName);
        fileEntity.setContentType(file.getContentType());
        fileEntity.setSize(file.getSize());
        fileEntity.setFilePath(filePath.toString());
        fileEntity.setUploadTime(LocalDateTime.now());
        fileEntity.setUser(user);

        fileRepository.save(fileEntity);
    }

    public List<FileEntity> getFilesByUser(String username) {
        User user = userRepository.findByUsername(username).orElseThrow();
        return fileRepository.findAllByUser(user);
    }

    public FileEntity getFile(Long fileId, String username) {
        FileEntity file = fileRepository.findById(fileId)
                .orElseThrow(() -> new RuntimeException("File not found"));
        
        if (!file.getUser().getUsername().equals(username)) {
            throw new RuntimeException("Unauthorized access");
        }
        return file;
    }

    public void deleteFile(Long fileId, String username) {
        FileEntity file = getFile(fileId, username);
        
        // Delete from filesystem
        File f = new File(file.getFilePath());
        if (f.exists()) {
            f.delete();
        }
        
        // Delete from DB
        fileRepository.delete(file);
    }
}