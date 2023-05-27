package com.locawork.webapi.respository;

import com.locawork.webapi.dao.entity.JobCategoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<JobCategoryEntity, Integer> {
}
