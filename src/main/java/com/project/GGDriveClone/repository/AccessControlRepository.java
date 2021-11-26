package com.project.GGDriveClone.repository;


import com.project.GGDriveClone.entity.AccessControlEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AccessControlRepository extends JpaRepository<AccessControlEntity, Long> {

    List<AccessControlEntity> findAllByUid(Long uid);

    List<AccessControlEntity> findAllByOid(Long oid);
}
