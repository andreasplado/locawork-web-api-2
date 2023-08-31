package com.locawork.webapi.controller;

import com.locawork.webapi.dao.entity.CommentsEntity;
import com.locawork.webapi.dao.entity.JobCategoryEntity;
import com.locawork.webapi.dao.entity.JobEntity;
import com.locawork.webapi.dto.JobApplicationDTO;
import com.locawork.webapi.model.MainData;
import com.locawork.webapi.model.ResponseModel;
import com.locawork.webapi.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/comments")
public class CommentsController {

    private static String KEY_JOBS = "jobs";
    private static String KEY_CATEGORIES = "categories";
    private static String KEY_FIREBASE_TOKEN = "firebasetoken";

    @Autowired
    private UserDataService userservice;

    @Autowired
    private JobService jobService;

    @Autowired
    private JobApplicationService jobApplicationService;

    @Autowired
    private JobCategoryService jobCategoryService;

    @Autowired
    private CommentsService commentsService;

    @GetMapping
        public ResponseEntity<List<CommentsEntity>> getAll() {
        List<CommentsEntity>  commentsEntities = commentsService.findAll();

        if (commentsEntities.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        return new ResponseEntity<>(commentsEntities, HttpStatus.OK);
    }


    @RequestMapping(value = "/post-comment", method = RequestMethod.POST)
    public ResponseEntity<CommentsEntity> apply(@RequestBody CommentsEntity commentsEntity) {
        commentsService.save(commentsEntity);

        return ResponseEntity.ok(commentsEntity);
    }

    @RequestMapping(value = "/get-all-comments", method = RequestMethod.GET)
    public ResponseEntity<?> getAllComments() {
        List<CommentsEntity> commentsEntities = commentsService.findAll();
        return ResponseEntity.ok(commentsEntities);
    }

    @RequestMapping(value = "/get-job-comments", method = RequestMethod.GET)
    public ResponseEntity<?> getJobComments(@RequestParam Integer jobId) {
        List<JobEntity> jobs = commentsService.findAllByJobId(jobId);

        return ResponseEntity.ok(jobs);
    }

    @RequestMapping(value = "{id}", method = RequestMethod.GET)
    public ResponseEntity<?> get(@PathVariable("id") Integer id) {

        Optional<JobEntity> job = jobService.findById(id);
        return ResponseEntity.ok(job);
    }

    @RequestMapping(value = "/getjobsbyaccount", method = RequestMethod.GET)
    public ResponseEntity<?> getAll(@RequestParam Integer userId) {
        List<JobEntity> jobs = jobService.findAllPostedJobs(userId);


        return ResponseEntity.ok(jobs);
    }


    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<?> create(@RequestBody JobEntity job) {
        jobService.save(job);
        return ResponseEntity.ok(job);
    }

    @RequestMapping(value = "{id}", method = RequestMethod.PUT)
    public ResponseEntity<?> update(@PathVariable Integer id, @RequestBody JobEntity jobEntity) {

        if (jobService.exists(id)) {
            jobService.update(jobEntity);
            return ResponseEntity.ok(jobEntity);
        } else {
            ResponseModel responseModel = new ResponseModel();
            responseModel.setMessage("You have no jobs found!");
            return ResponseEntity.ok(responseModel);
        }
    }

    @RequestMapping(value = "registerjob/{id}", method = RequestMethod.PUT)
    public ResponseEntity<?> registerjob(@PathVariable("id") Integer id, @RequestBody JobEntity jobEntity) {

        if (jobService.exists(id)) {
            jobService.update(jobEntity);

            return ResponseEntity.ok(jobEntity);
        } else {
            ResponseModel responseModel = new ResponseModel();
            responseModel.setMessage("You have no jobs found!");

            return ResponseEntity.ok(responseModel);
        }
    }

    @RequestMapping(value = "{id}", method = RequestMethod.DELETE)
    public ResponseEntity<?> delete(@PathVariable("id") Integer id) {

        if (!jobService.exists(id)) {
            ResponseModel responseModel = new ResponseModel();
            responseModel.setMessage("You have no jobs found!");

            return ResponseEntity.ok(responseModel);
        }else {
            JobEntity jobEntity = jobService.findSingleById(id);
            jobService.delete(id);


            return ResponseEntity.ok(jobEntity);
        }
    }

    @RequestMapping(value = "/does-exists", method = RequestMethod.GET)
    public ResponseEntity<Boolean> getMyApplications(@RequestParam Integer id) {
        boolean doesExists = jobService.exists(id);
        return ResponseEntity.ok(doesExists);
    }

    @RequestMapping(value = "/get-my-upcoming-work", method = RequestMethod.GET)
    public ResponseEntity<?> getAppliedJobsByGooogleAccount(@RequestParam Integer userId) {
        List<JobEntity> jobs = jobService.findUpcomingWork(userId);

        if (jobs.isEmpty()) {
            ResponseModel responseModel = new ResponseModel();
            responseModel.setMessage("You have no jobs found!");
        }

        return ResponseEntity.ok(jobs);
    }

    @RequestMapping(value = "/getmydonework", method = RequestMethod.GET)
    public ResponseEntity<?> getMyDoneWork(@RequestParam Integer userId) {
        List<JobEntity> jobs = jobService.findMyDoneWork(userId);

        if (jobs.isEmpty()) {
            ResponseModel responseModel = new ResponseModel();
            responseModel.setMessage("You have no jobs found!");
        }

        return ResponseEntity.ok(jobs);
    }

    @RequestMapping(value = "/get-main-data", method = RequestMethod.GET)
    public ResponseEntity<MainData> getMainData(@RequestParam Integer userId) {
        List<JobEntity> applyedJobs = jobService.findUpcomingWork(userId);
        List<JobApplicationDTO> myCandidates = jobApplicationService.findCandidates(userId);

        MainData mainData = new MainData();
        mainData.setMyUpcomingWorkNumber(applyedJobs.size());
        mainData.setMyCandidatesNumber(myCandidates.size());

        return ResponseEntity.ok(mainData);
    }

    @RequestMapping(value = "deleteall", method = RequestMethod.DELETE)
    public ResponseEntity<Void> deleteaLL() {
        jobService.deleteAll();
        return new ResponseEntity<>(HttpStatus.OK);
    }
}