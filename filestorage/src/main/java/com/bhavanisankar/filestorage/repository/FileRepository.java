package com.bhavanisankar.filestorage.repository;


import com.bhavanisankar.filestorage.model.FileEntity;
import com.bhavanisankar.filestorage.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface FileRepository extends JpaRepository<FileEntity, Long> {
    List<FileEntity> findAllByUser(User user);
}