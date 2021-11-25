package com.project.GGDriveClone.repository;

import com.project.GGDriveClone.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<UserEntity, Long> {
    UserEntity findUserEntityByName(String username);
    UserEntity findUserEntityById(Long id);
}
