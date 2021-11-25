package com.project.GGDriveClone.repository;

import com.project.GGDriveClone.entity.UserEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<UserEntity, Long> {
    UserEntity findUserEntityByName(String username);

    Page<UserEntity> findAll(Specification<UserEntity> specs, Pageable pageable);

}


