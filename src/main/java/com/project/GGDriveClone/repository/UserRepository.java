package com.project.GGDriveClone.repository;

import com.project.GGDriveClone.entity.UserEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface UserRepository extends JpaRepository<UserEntity, Long> {
    UserEntity findUserEntityByName(String username);


    UserEntity findUserEntityById(Long uid);

    UserEntity findUserEntityByIdAndIs_deletedFalse(Long uid);

    Page<UserEntity> findAll(Specification<UserEntity> specs, Pageable pageable);

    UserEntity findUserEntityByEmail(String email);

    @Query("SELECT u FROM UserEntity u WHERE u.email = ?1")
    UserEntity findByEmail(String email);

    @Query("SELECT u FROM UserEntity u WHERE u.verificationCode = ?1")
    UserEntity findByVerificationCode(String code);

    @Query(value = "select u " +
            "from UserEntity u " +
            "where u.is_deleted = false  " +
            "and u.name like concat('%', ?1, '%') " +
            "order by u.updated_time desc")
    List<UserEntity> search(String keyword);
}


