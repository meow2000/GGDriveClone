package com.project.GGDriveClone.service;

import com.project.GGDriveClone.entity.AccessControlEntity;
import com.project.GGDriveClone.repository.AccessControlRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.List;

@Service
public class AccessControlService {

    @Autowired
    AccessControlRepository accessControlRepository;

    public AccessControlEntity addAccessControlEntity(AccessControlEntity accessControlEntity){
        accessControlEntity.setCreatedTime(new Timestamp(System.currentTimeMillis()));
        accessControlEntity.setUpdatedTime(new Timestamp(System.currentTimeMillis()));
        return accessControlRepository.save(accessControlEntity);
    }

    public List<AccessControlEntity> findAllByUid(Long uid){
        return accessControlRepository.findAllByUid(uid);
    }

    public List<AccessControlEntity> findAllByOid(Long oid){
        return  accessControlRepository.findAllByOid(oid);
    }
}
