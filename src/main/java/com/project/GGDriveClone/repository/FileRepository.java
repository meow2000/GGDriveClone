package com.project.GGDriveClone.repository;

import com.project.GGDriveClone.entity.FileEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FileRepository extends JpaRepository<FileEntity, Long> {
}
