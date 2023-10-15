package com.locawork.webapi.respository;

import com.locawork.webapi.dao.entity.JobCategoryEntity;
import com.locawork.webapi.dao.entity.JobEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CategoryRepository extends JpaRepository<JobCategoryEntity, Integer> {
    @Query(value="SELECT c.* from categories c " +
            "WHERE c.id=?1", nativeQuery = true)
    List<JobEntity> findAllCategoriesByJobId(Integer jobId);

}
