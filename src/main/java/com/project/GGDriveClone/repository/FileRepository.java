package com.project.GGDriveClone.repository;

import com.project.GGDriveClone.entity.FileEntity;
import com.project.GGDriveClone.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FileRepository extends JpaRepository<FileEntity, Long> {

    FileEntity findFileEntityById(long id);

    FileEntity findFileEntityByName(String name);

    List<FileEntity> findFileEntitiesByUserEntitiesIs(UserEntity userEntity);

}
