package com.project.GGDriveClone.repository;

import com.project.GGDriveClone.entity.UserEntity;
import com.project.GGDriveClone.entity.plans.PlanEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface UserRepository extends JpaRepository<UserEntity, Long> {
    UserEntity findUserEntityByName(String username);

    PlanEntity findPlanEntityById(int id);

    UserEntity findUserEntityById(Long uid);

    Page<UserEntity> findAll(Specification<UserEntity> specs, Pageable pageable);

    @Query("SELECT u FROM UserEntity u WHERE u.email = ?1")
    public UserEntity findByEmail(String email);

    @Query("SELECT u FROM UserEntity u WHERE u.verificationCode = ?1")
    public UserEntity findByVerificationCode(String code);


}


