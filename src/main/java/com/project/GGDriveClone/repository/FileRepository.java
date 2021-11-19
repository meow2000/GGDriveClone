package com.project.GGDriveClone.repository;

import com.project.GGDriveClone.entity.FileEntity;
import com.project.GGDriveClone.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FileRepository extends JpaRepository<FileEntity, Long> {

    FileEntity findFileEntityById(long id);

    FileEntity findFileEntityByName(String name);

    List<FileEntity> findFileEntitiesByUserEntitiesIs(UserEntity userEntity);

}
