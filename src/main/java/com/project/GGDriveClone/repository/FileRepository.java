package com.project.GGDriveClone.repository;

import com.project.GGDriveClone.entity.FileEntity;
import com.project.GGDriveClone.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.util.List;

@Repository
public interface FileRepository extends JpaRepository<FileEntity, Long> {

    FileEntity findFileById(Long id);

    List<FileEntity> findFilesByUidAndIsDeletedTrue(Long uid);

    List<FileEntity> findFilesByUidAndIsDeletedFalse(Long uid);

    List<FileEntity> findFileEntitiesByUserEntitiesIs(UserEntity userEntity);

    @Query(value = "select e " +
            "from FileEntity e " +
            "where e.isDeleted = false  " +
            "and (e.uid = ?1)" +
            "order by e.updatedTime desc")
    List<FileEntity> findRecentOwnerFile(Long uid);

    @Query(value = "select e " +
            "from FileEntity e, AccessControlEntity a " +
            "where (e.isDeleted = false) " +
            "and (a.uid = ?1)" +
            "and (a.oid = e.id) " +
            "order by e.updatedTime desc")
    List<FileEntity> findRecentSharedFile(Long uid);

    List<FileEntity> findFilesByUidAndIsStarTrue(Long uid);

}
