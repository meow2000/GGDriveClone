package com.project.GGDriveClone.service;

import com.project.GGDriveClone.entity.FileEntity;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;

@Service
public class FileService {
    public FileEntity addFile(Long size, String name, String type, String path){
        FileEntity fileEntity = new FileEntity(size, name, type, path);
        fileEntity.setCreatedTime(new Timestamp(System.currentTimeMillis()));
        fileEntity.setUpdatedTime(new Timestamp(System.currentTimeMillis()));
        return fileEntity;
    }
}
