package com.locawork.webapi.service;

import com.locawork.webapi.dao.entity.JobCategoryEntity;

import java.util.List;
import java.util.Optional;

public interface ICategoryService {
    List<JobCategoryEntity> findAll();
    JobCategoryEntity save (JobCategoryEntity jobEntity);
    JobCategoryEntity update(JobCategoryEntity jobEntity);
    void delete(Integer id);
    Optional<JobCategoryEntity> findById(Integer id);
    boolean exists(Integer id);
}
