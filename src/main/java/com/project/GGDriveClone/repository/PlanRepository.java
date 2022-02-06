package com.project.GGDriveClone.repository;

import com.project.GGDriveClone.entity.PlanEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface PlanRepository extends JpaRepository<PlanEntity, Long> {
    PlanEntity findPlanEntityById(Long pid);

    List<PlanEntity> findAll();
}
