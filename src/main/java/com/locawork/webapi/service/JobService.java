package com.locawork.webapi.service;

import com.locawork.webapi.dao.entity.JobEntity;
import com.locawork.webapi.respository.JobRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class JobService implements IJobService {

    @Autowired
    private JobRepository repository;

    @Override
    public List<JobEntity> findAll() {
        return repository.findAll();
    }

    @Override
    public void startWork(String startTime, int applyerId, int jobId) {
        repository.startWork(startTime, applyerId, jobId);
    }

    @Override
    public void endWork(String endTime, int applyerId, int jobId) {
        repository.endWork(endTime, applyerId, jobId);
    }

    @Override
    public List<JobEntity> findMyDoneWork(int userId) {
        return repository.findMyDoneWork(userId);
    }

    @Override
    public List<JobEntity> findMyUnddoneWork(int userId) {
        return repository.findNotChosenCandidateJobs(userId);
    }

    @Override
    public List<JobEntity> findMyAllWork(int userId) {
        return repository.findMyDoneWork(userId);
    }

    @Override
    public List<JobEntity> findMyDoneWorkToday(int userId) {
        return repository.findMyDoneWorkToday(userId);
    }

    @Override
    public List<JobEntity> findMyDoneWorkThisWeek(Integer userId) {
        return repository.findMyDoneWorkThisWeek(userId);
    }

    @Override
    public List<JobEntity> findMyDoneWorkThisMonth(Integer userId) {
        return repository.findMyDoneWorkThisMonth(userId);
    }

    @Override
    public void cantGoToJob(Integer jobId, String reason) {
        repository.cantGoToWork(reason, jobId);
    }

    @Override
    public List<JobEntity> findAvailableJobsWithUserToken(Double latitude, Double longitude, Double distance, Integer userId) {
        return repository.findAvailableJobs(latitude, longitude, distance, userId);
    }

    @Override
    public List<JobEntity> findUpcomingWork(Integer userId) {
        return repository.findUpcomingWork(userId);
    }

    @Override
    public List<JobEntity> findAllNearestJobs(Double latitude, Double longitude, Double distance) {
        return repository.findAllNearestJobs(latitude, longitude, distance);
    }

    @Override
    public List<JobEntity> getNotChosenCandidateJobs(Integer userId){
        return repository.findNotChosenCandidateJobs(userId);
    }

    @Override
    public List<JobEntity> findDonePostedJobs(Integer userId) {
        return repository.findMyDonePostedJobs(userId);
    }

    @Override
    public List<JobEntity> findWorkInProgressPostedJobs(Integer userId) {
        return repository.findMyWorkInProgressPostedJobs(userId);
    }

    @Override
    public List<JobEntity> findAllPostedJobs(Integer userId) {
        return repository.findAllPostedJobs(userId);
    }

    @Override
    public JobEntity save(JobEntity jobEntity) {
        return repository.save(jobEntity);
    }


    @Override
    public JobEntity update(JobEntity jobEntity) {
        if(repository.existsById(jobEntity.getId())){
            repository.save(jobEntity);
        }
        return jobEntity;
    }

    @Override
    public void delete(Integer id) {
        repository.deleteById(id);
    }

    @Override
    public void deleteAll() {
        repository.deleteAll();
    }

    @Override
    public Optional<JobEntity> findById(Integer id) {
        return repository.findById(id);
    }

    @Override
    public JobEntity findSingleById(Integer id) {
        return repository.findSingle(id);
    }

    @Override
    public boolean exists(Integer id) {
        return repository.existsById(id);
    }

}