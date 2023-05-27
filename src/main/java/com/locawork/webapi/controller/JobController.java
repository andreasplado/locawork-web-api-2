package com.locawork.webapi.controller;

import com.locawork.webapi.dao.entity.JobCategoryEntity;
import com.locawork.webapi.dao.entity.JobEntity;
import com.locawork.webapi.dto.JobApplicationDTO;
import com.locawork.webapi.model.MainData;
import com.locawork.webapi.model.ResponseModel;
import com.locawork.webapi.service.JobApplicationService;
import com.locawork.webapi.service.JobCategoryService;
import com.locawork.webapi.service.JobService;
import com.locawork.webapi.service.UserDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/jobs")
public class JobController {

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

    @GetMapping
    public ResponseEntity<HashMap<String, Object>> getAll() {
        List<JobEntity> jobs = jobService.findAll();
        List<JobCategoryEntity> categories = jobCategoryService.findAll();
        HashMap<String, Object> combined = new HashMap<>();

        combined.put(KEY_JOBS, jobs);
        combined.put(KEY_CATEGORIES, categories);

        if (combined.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        return new ResponseEntity<>(combined, HttpStatus.OK);
    }


    @RequestMapping(value = "/start-work", method = RequestMethod.POST)
    public ResponseEntity<ResponseModel> apply(@RequestParam Integer applyerId) {
        jobService.applyToJob(applyerId);
        ResponseModel responseModel = new ResponseModel();
        responseModel.setMessage("You started work!");

        return ResponseEntity.ok(responseModel);
    }

    @RequestMapping(value = "/get-available-jobs", method = RequestMethod.GET)
    public ResponseEntity<?> getUserOffers(@RequestParam Double latitude, @RequestParam Double longitude, @RequestParam Double distance, @RequestParam Integer userId) {
        List<JobEntity> jobs = jobService.findAvailableJobsWithUserToken(latitude, longitude, distance, userId);
        return ResponseEntity.ok(jobs);
    }

    @RequestMapping(value = "/getalljobsbylocation", method = RequestMethod.GET)
    public ResponseEntity<?> getAllJobsByLocation(@RequestParam Double latitude, @RequestParam Double longitude, @RequestParam Double distance) {
        List<JobEntity> jobs = jobService.findAllNearestJobs(latitude, longitude, distance);

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