package com.project.GGDriveClone.repository;

import com.project.GGDriveClone.entity.PlanEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PlanRepository extends JpaRepository<PlanEntity, Long> {
    PlanEntity findPlanEntityById(Long pid);
}
