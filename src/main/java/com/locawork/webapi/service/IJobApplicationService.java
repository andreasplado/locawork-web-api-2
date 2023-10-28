package com.locawork.webapi.service;

import com.locawork.webapi.dao.entity.JobApplicationEntity;
import com.locawork.webapi.dto.JobApplicationDTO;
import com.locawork.webapi.dto.MyApplicationDTO;

import java.util.List;
import java.util.Optional;

public interface IJobApplicationService {

    List<JobApplicationEntity> findAll();
    List<JobApplicationDTO> findCandidates(int userId);
    List<MyApplicationDTO> findMyApplications(int userId);
    List<JobApplicationEntity> existsJobByUserId(Integer userId, Integer jobId);
    void deleteUserJobApplications(int userId);
    void update(int applyerId, int userId);
    List<JobApplicationEntity> findApprovedJobApplications(int userId);
    JobApplicationEntity save(JobApplicationEntity jobEntity);
    JobApplicationEntity update(JobApplicationEntity jobEntity);
    void deleteJobApplication(Integer id);
    void deleteAll();
    Optional<JobApplicationEntity> findById(Integer id);
    boolean exists(Integer id);
    JobApplicationDTO findJobApplication(Integer id);

    void deleteAllByJobId(Integer id);

    List<JobApplicationDTO> findCandidatesWithFilter(Integer userId, String filter);

    void cantGoToJob(Integer jobId, Integer applyerId, String reason);
}
