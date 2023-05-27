package com.locawork.webapi.service;

import com.locawork.webapi.dao.entity.JobEntity;

import java.util.List;
import java.util.Optional;

public interface IJobService {

    List<JobEntity> findAll();
    void applyToJob(int applyerId);
    List<JobEntity> findMyDoneWork(int userId);
    List<JobEntity> findAvailableJobsWithUserToken(Double longitude, Double latitude, Double distance, Integer userId);
    List<JobEntity> findUpcomingWork(Integer userId);
    List<JobEntity> findAllPostedJobs(Integer userId);
    List<JobEntity> findAllNearestJobs(Double latitude, Double longitude, Double distance);
    JobEntity save (JobEntity jobEntity);
    JobEntity update(JobEntity jobEntity);
    void delete(Integer id);
    void deleteAll();
    Optional<JobEntity> findById(Integer id);
    JobEntity findSingleById(Integer id);
    boolean exists(Integer id);
}
