package com.locawork.webapi.service;

import com.locawork.webapi.dao.entity.JobApplicationEntity;
import com.locawork.webapi.dto.JobApplicationDTO;
import com.locawork.webapi.dto.MyApplicationDTO;
import com.locawork.webapi.respository.JobApplicationRepository;
import com.locawork.webapi.respository.UserDataRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class JobApplicationService implements IJobApplicationService {

    @Autowired
    private JobApplicationRepository jobApplicationRepository;

    @Autowired
    private UserDataRepository userDataRepository;

    @Override
    public List<JobApplicationEntity> findAll() {
        return jobApplicationRepository.findAll();
    }

    @Override
    public List<JobApplicationDTO> findCandidates(int applyerId) {
        return jobApplicationRepository.findCandidates(applyerId);
    }

    @Override
    public List<MyApplicationDTO> findMyApplications(int userId) {
        return jobApplicationRepository.findMyApplications(userId);
    }

    @Override
    public List<JobApplicationEntity> existsJobByUserId(Integer userId, Integer jobId) {
        return jobApplicationRepository.existJobByUserId(userId, jobId);
    }

    @Override
    public void deleteUserJobApplications(int userId) {
        jobApplicationRepository.deleteUserJobApplication(userId);
    }

    @Override
    public void update(int applyerId, int userId) {
        jobApplicationRepository.updateJob(applyerId, userId);
    }


    @Override
    public List<JobApplicationEntity> findApprovedJobApplications(int userId) {
        return jobApplicationRepository.getApprovedJobApplications(userId);
    }


    @Override
    public JobApplicationEntity save(JobApplicationEntity jobEntity) {
        return jobApplicationRepository.save(jobEntity);
    }

    @Override
    public JobApplicationEntity update(JobApplicationEntity jobApplicationEntity) {
        if(jobApplicationRepository.existsById(jobApplicationEntity.getId())){
            jobApplicationRepository.save(jobApplicationEntity);
        }
        return jobApplicationEntity;
    }


    @Override
    public void deleteJobApplication(Integer id) {
        jobApplicationRepository.deleteJobApplication(id);
    }

    @Override
    public void deleteAll() {
        jobApplicationRepository.deleteAll();
    }

    @Override
    public Optional<JobApplicationEntity> findById(Integer id) {
        return jobApplicationRepository.findById(id);
    }

    @Override
    public boolean exists(Integer id) {
        return jobApplicationRepository.existsById(id);
    }

    @Override
    public JobApplicationDTO findJobApplication(Integer id) {
        return jobApplicationRepository.findJobApplicationById(id);
    }

    @Override
    public void deleteAllByJobId(Integer id) {
        jobApplicationRepository.deleteAllByJobId(id);
    }

    @Override
    public List<JobApplicationDTO> findCandidatesWithFilter(Integer userId, String filter) {
        return jobApplicationRepository.findCandidatesWithFilter(userId, filter);
    }

    @Override
    public void cantGoToJob(Integer jobId, Integer userId, String reason) {
        System.out.println("userId: " + userId);
        System.out.println("jobId: " + jobId);
        jobApplicationRepository.cantGoToWork(reason, userId, jobId);
    }

}