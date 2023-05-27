package com.locawork.webapi.service;

import com.locawork.webapi.dao.entity.JobCategoryEntity;
import com.locawork.webapi.respository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class JobCategoryService implements ICategoryService {

    @Autowired
    private CategoryRepository repository;

    @Override
    public List<JobCategoryEntity> findAll() {
        return repository.findAll();
    }

    @Override
    public JobCategoryEntity save(JobCategoryEntity jobCategoryEntity) {
        return repository.save(jobCategoryEntity);
    }

    @Override
    public JobCategoryEntity update(JobCategoryEntity jobCategoryEntity) {
        if(repository.existsById(jobCategoryEntity.getId())){
            repository.save(jobCategoryEntity);
        }
        return jobCategoryEntity;
    }

    @Override
    public void delete(Integer id) {
        repository.deleteById(id);
    }

    @Override
    public Optional<JobCategoryEntity> findById(Integer id) {
        return repository.findById(id);
    }

    @Override
    public boolean exists(Integer id) {
        return repository.existsById(id);
    }
}
