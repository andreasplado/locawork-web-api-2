package com.locawork.webapi.controller;

import com.locawork.webapi.dao.entity.JobCategoryEntity;
import com.locawork.webapi.dao.entity.JobEntity;
import com.locawork.webapi.data.EndTimeDTO;
import com.locawork.webapi.dto.JobApplicationDTO;
import com.locawork.webapi.data.StartTimeDTO;
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


    @RequestMapping(value = "/all-jobs", method = RequestMethod.GET)
    public ResponseEntity<List<JobEntity>> getAll() {
        List<JobEntity> jobs = jobService.findAll();

        return new ResponseEntity<>(jobs, HttpStatus.OK);
    }


    @RequestMapping(value = "/get-all-jobs-with-categories", method = RequestMethod.GET)
    public ResponseEntity<HashMap<String, Object>> getAllWithCategories() {
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
    public ResponseEntity<ResponseModel> startWork(@RequestBody StartTimeDTO startTimeDTO) {
        jobService.startWork(startTimeDTO.getStartTime(), startTimeDTO.getApplyerId(), startTimeDTO.getJobId());
        ResponseModel responseModel = new ResponseModel();
        responseModel.setMessage("You started work!");

        return ResponseEntity.ok(responseModel);
    }

    @RequestMapping(value = "/end-work", method = RequestMethod.POST)
    public ResponseEntity<ResponseModel> endWork(@RequestBody EndTimeDTO endTimeDTO) {
        jobService.endWork(endTimeDTO.getEndTime(), endTimeDTO.getApplyerId(), endTimeDTO.getJobId());
        ResponseModel responseModel = new ResponseModel();
        responseModel.setMessage("You ended work!");

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

    @RequestMapping(value = "/get-not-chosen-candidate", method = RequestMethod.GET)
    public ResponseEntity<?> getNotChosenCandidate(@RequestParam Integer userId) {
        List<JobEntity> jobs = jobService.getNotChosenCandidateJobs(userId);

        return ResponseEntity.ok(jobs);
    }
    @RequestMapping(value = "/get-done-posted-jobs", method = RequestMethod.GET)
    public ResponseEntity<?> getPostedDoneJobs(@RequestParam Integer userId) {
        List<JobEntity> jobs = jobService.findDonePostedJobs(userId);

        return ResponseEntity.ok(jobs);
    }

    @RequestMapping(value = "/get-work-in-progresss-jobs", method = RequestMethod.GET)
    public ResponseEntity<?> getWorkInProgressJobs(@RequestParam Integer userId) {
        List<JobEntity> jobs = jobService.findWorkInProgressPostedJobs(userId);

        return ResponseEntity.ok(jobs);
    }

    @RequestMapping(value = "/get-all-posted-jobs", method = RequestMethod.GET)
    public ResponseEntity<?> getAllPostedJobs(@RequestParam Integer userId) {
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


    @RequestMapping(value = "registerjob/cant-go{id}", method = RequestMethod.PUT)
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

    @RequestMapping(value = "/getmydonework/today", method = RequestMethod.GET)
    public ResponseEntity<?> getMyDoneWorkToday(@RequestParam Integer userId) {
        List<JobEntity> jobs = jobService.findMyDoneWorkToday(userId);

        if (jobs.isEmpty()) {
            ResponseModel responseModel = new ResponseModel();
            responseModel.setMessage("You have no jobs found!");
        }

        return ResponseEntity.ok(jobs);
    }

    @RequestMapping(value = "/getmydonework/this-week", method = RequestMethod.GET)
    public ResponseEntity<?> getMyDoneWorkThisWeek(@RequestParam Integer userId) {
        List<JobEntity> jobs = jobService.findMyDoneWorkThisWeek(userId);

        if (jobs.isEmpty()) {
            ResponseModel responseModel = new ResponseModel();
            responseModel.setMessage("You have no jobs found!");
        }

        return ResponseEntity.ok(jobs);
    }

    @RequestMapping(value = "/getmydonework/this-month", method = RequestMethod.GET)
    public ResponseEntity<?> getMyDoneWorkThisMonth(@RequestParam Integer userId) {
        List<JobEntity> jobs = jobService.findMyDoneWorkThisMonth(userId);

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

    @RequestMapping(value = "/cant-go-to-work", method = RequestMethod.PUT)
    public ResponseEntity<?> cantGoToWork(@RequestParam Integer jobId, @RequestParam String reason) {
        jobService.cantGoToJob(jobId, reason);
        return ResponseEntity.ok(jobService.findById(jobId));
    }
}