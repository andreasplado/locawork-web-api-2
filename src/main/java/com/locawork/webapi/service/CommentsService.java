package com.locawork.webapi.service;

import com.locawork.webapi.dao.entity.CommentsEntity;
import com.locawork.webapi.dao.entity.JobEntity;
import com.locawork.webapi.respository.CategoryRepository;
import com.locawork.webapi.respository.CommentsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CommentsService implements ICommentsService {

    @Autowired
    private CommentsRepository repository;

    @Override
    public List<CommentsEntity> findAll() {
        return repository.findAll();
    }

    @Override
    public CommentsEntity save(CommentsEntity commentsEntity) {
        return repository.save(commentsEntity);
    }

    @Override
    public CommentsEntity update(CommentsEntity commentsEntity) {
        if(repository.existsById(commentsEntity.getId())){
            repository.save(commentsEntity);
        }
        return commentsEntity;
    }

    @Override
    public void delete(Integer id) {
        if(repository.existsById(id)){
            repository.deleteById(id);
        }
    }

    @Override
    public Optional<CommentsEntity> findById(Integer id) {
        return repository.findById(id);
    }

    @Override
    public boolean exists(Integer id) {
        return repository.existsById(id);
    }

    @Override
    public List<JobEntity> findAllByJobId(Integer jobId) {
        return repository.findAllByJobId(jobId);
    }
}
