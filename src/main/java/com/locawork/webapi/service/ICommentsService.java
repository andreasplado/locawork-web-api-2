package com.locawork.webapi.service;

import com.locawork.webapi.dao.entity.CommentsEntity;
import com.locawork.webapi.dao.entity.JobEntity;
import com.locawork.webapi.dao.entity.UserEntity;
import com.locawork.webapi.respository.CommentsRepository;

import java.util.List;
import java.util.Optional;

public interface ICommentsService {

    List<CommentsEntity> findAll();
    CommentsEntity save(CommentsEntity commentsEntity);
    CommentsEntity update(CommentsEntity commentsEntity);
    void delete(Integer id);
    Optional<CommentsEntity> findById(Integer id);
    boolean exists(Integer id);

    List<JobEntity> findAllByJobId(Integer jobId);

}
