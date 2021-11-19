package com.project.GGDriveClone.repository;

import com.project.GGDriveClone.entity.FileEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FileRepository extends JpaRepository<FileEntity, Long> {

    FileEntity findFileEntityById(long id);

    FileEntity findFileEntityBy(String name);

//    List<FileEntity> findAllBy();
}
