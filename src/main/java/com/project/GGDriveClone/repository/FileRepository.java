package com.project.GGDriveClone.repository;

import com.project.GGDriveClone.entity.FileEntity;
import com.project.GGDriveClone.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FileRepository extends JpaRepository<FileEntity, Long> {

    FileEntity findFileById(Long id);

    List<FileEntity> findFilesByUidAndIsDeletedTrue(Long uid);

    List<FileEntity> findFilesByUidAndIsDeletedFalse(Long uid);

    List<FileEntity> findFileEntitiesByUserEntitiesIs(UserEntity userEntity);

}
