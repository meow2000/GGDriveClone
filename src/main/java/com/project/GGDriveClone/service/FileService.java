package com.project.GGDriveClone.service;

import com.project.GGDriveClone.entity.FileEntity;
import com.project.GGDriveClone.entity.UserEntity;
import com.project.GGDriveClone.repository.FileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

@Service
public class FileService {

    @Autowired
    FileRepository fileRepository;

    public FileEntity addFile(Long uid, Long size, String name, String type, String path) {
        FileEntity fileEntity = new FileEntity(uid, size, name, type, path);
        fileEntity.setCreatedTime(new Timestamp(System.currentTimeMillis()));
        fileEntity.setUpdatedTime(new Timestamp(System.currentTimeMillis()));
        return fileRepository.save(fileEntity);
    }

    public FileEntity moveToTrash(FileEntity fileEntity) {
        fileEntity.setIsDeleted(true);
        fileEntity.setUpdatedTime(new Timestamp(System.currentTimeMillis()));
        return fileRepository.save(fileEntity);
    }

    public void completedDelete(Long oid){
        fileRepository.deleteById(oid);
    }

    public FileEntity findFile(Long id) {
        return fileRepository.findFileById(id);
    }

    public FileEntity findFile(String filename) {
        return fileRepository.findFirstByName(filename);
    }

    public List<FileEntity> findFilesByUser(Long uid) {
        return fileRepository.findFilesByUidAndIsDeletedFalse(uid);
    }

    public List<FileEntity> findFilesDeletedByUser(Long uid) {
        return fileRepository.findFilesByUidAndIsDeletedTrue(uid);
    }

    public List<FileEntity> findFilesSharedByUser(Long uid) {
        UserEntity userEntity = new UserEntity(uid);
        return fileRepository.findFileEntitiesByUserEntitiesIs(userEntity);
    }

    public List<FileEntity> findRecentFile(Long uid) {
        List<FileEntity> list1 = fileRepository.findRecentOwnerFile(uid);
        List<FileEntity> list2 = fileRepository.findRecentSharedFile(uid);
        List<FileEntity> totalList = new ArrayList<>();
        if(list1.size() == 0 && list2.size() ==0){
            return null;
        }
        if(list1.size() >= 2){
            totalList.add(list1.get(0));
            totalList.add(list1.get(1));
        }
        else{
            totalList.add(list1.get(0));
        }
        if(list2.size() >= 2){
            totalList.add(list2.get(0));
            totalList.add(list2.get(1));
        }
        else{
            totalList.add(list2.get(0));
        }
        return totalList;
    }

    public List<FileEntity> findStarFile(Long userId) {
        return fileRepository.findFilesByUidAndIsStarTrue(userId);
    }

    public boolean starFile(Long oid){
        try{
            FileEntity fileEntity = fileRepository.findFileById(oid);
            fileEntity.setStar(true);
            fileRepository.save(fileEntity);
            return true;
        }
        catch(Exception e){
            e.printStackTrace();
            return false;
        }
    }

    public boolean unstarFile(Long oid){
        try{
            FileEntity fileEntity = fileRepository.findFileById(oid);
            fileEntity.setStar(false);
            fileRepository.save(fileEntity);
            return true;
        }
        catch(Exception e){
            e.printStackTrace();
            return false;
        }
    }
}
