package com.locawork.webapi.controller;

import com.locawork.webapi.dao.entity.JobApplicationEntity;
import com.locawork.webapi.dto.JobApplicationDTO;
import com.locawork.webapi.dto.MyApplicationDTO;
import com.locawork.webapi.model.ResponseModel;
import com.locawork.webapi.service.JobApplicationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/jobapplications")
public class JobApplicationController {

    private static String KEY_JOB_APPLICATIONS = "job_applications";

    Logger logger = LoggerFactory.getLogger(JobApplicationController.class);

    @Autowired
    private JobApplicationService jobApplicationService;

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<?> create(@RequestBody JobApplicationEntity jobApplicationEntity) {
        List<JobApplicationEntity> jobApplicationEntities = jobApplicationService.existsJobByUserId(jobApplicationEntity.getUserId(), jobApplicationEntity.getJob());
        ResponseModel responseModel = new ResponseModel();
        if (jobApplicationEntities.size() == 0) {
            jobApplicationService.save(jobApplicationEntity);
            responseModel.setMessage("You successfully applied to the job");
        } else {
            responseModel.setMessage("You already applied to job!");
        }


        return ResponseEntity.ok(responseModel);
    }

    @RequestMapping(value = "/candidates", method = RequestMethod.GET)
    public ResponseEntity<HashMap<String, Object>> getCandidates(@RequestParam Integer userId) {
        List<JobApplicationDTO> jobApplications = jobApplicationService.findCandidates(userId);
        HashMap<String, Object> combined = new HashMap<>();
        combined.put(KEY_JOB_APPLICATIONS, jobApplications);

        if (combined.isEmpty()) {
            ResponseModel responseModel = new ResponseModel();
            responseModel.setMessage("You have no jobs found!");
        }

        return ResponseEntity.ok(combined);
    }

    @RequestMapping(value = "/candidates-filtered", method = RequestMethod.GET)
    public ResponseEntity<HashMap<String, Object>> getCandidates(@RequestParam Integer userId, @RequestParam String filter) {
        List<JobApplicationDTO> jobApplications = jobApplicationService.findCandidatesWithFilter(userId, filter);
        HashMap<String, Object> combined = new HashMap<>();
        combined.put(KEY_JOB_APPLICATIONS, jobApplications);

        if (combined.isEmpty()) {
            ResponseModel responseModel = new ResponseModel();
            responseModel.setMessage("You have no jobs found!");
        }

        return ResponseEntity.ok(combined);
    }

    @RequestMapping(value = "/my-applications", method = RequestMethod.GET)
    public ResponseEntity<HashMap<String, Object>> getMyApplications(@RequestParam Integer userId) {
        List<MyApplicationDTO> jobApplications = jobApplicationService.findMyApplications(userId);
        HashMap<String, Object> combined = new HashMap<>();
        combined.put(KEY_JOB_APPLICATIONS, jobApplications);

        if (combined.isEmpty()) {
            ResponseModel responseModel = new ResponseModel();
            responseModel.setMessage("You have no jobs found!");
        }

        return ResponseEntity.ok(combined);
    }

    @RequestMapping(value = "/cancel-application", method = RequestMethod.DELETE)
    public ResponseEntity<?> deleteApplication(@RequestParam Integer applicationId) {
        JobApplicationDTO jobApplicationEntity = jobApplicationService.findJobApplication(applicationId);
        jobApplicationService.deleteJobApplication(applicationId);


        return ResponseEntity.ok(jobApplicationEntity);
    }

    @RequestMapping(value = "/apply", method = RequestMethod.POST)
    public ResponseEntity<ResponseModel> apply(@RequestParam Integer userId, @RequestParam Integer applyerId) {
        jobApplicationService.deleteUserJobApplications(userId);
        jobApplicationService.update(userId, applyerId);
        ResponseModel responseModel = new ResponseModel();
        responseModel.setMessage("You applied to job!");

        return ResponseEntity.ok(responseModel);
    }

    @RequestMapping(value = "/get-approved-job-applications", method = RequestMethod.GET)
    public ResponseEntity<HashMap<String, Object>> getApprovedJobApplications(@RequestParam int userId) {
        List<JobApplicationEntity> jobApplications = jobApplicationService.findApprovedJobApplications(userId);
        HashMap<String, Object> combined = new HashMap<>();
        combined.put(KEY_JOB_APPLICATIONS, jobApplications);

        if (combined.isEmpty()) {
            ResponseModel responseModel = new ResponseModel();
            responseModel.setMessage("You have no jobs found!");
        }

        return ResponseEntity.ok(combined);
    }

    @RequestMapping(value = "{id}", method = RequestMethod.DELETE)
    public ResponseEntity<?> delete(@PathVariable("id") Integer id) {

        if (!jobApplicationService.exists(id)) {
            ResponseModel responseModel = new ResponseModel();
            responseModel.setMessage("You have no jobs found!");

            return ResponseEntity.ok(responseModel);
        } else {
            jobApplicationService.deleteJobApplication(id);

            ResponseModel responseModel = new ResponseModel();
            responseModel.setMessage("You deleted job successfully!");

            return ResponseEntity.ok(responseModel);
        }
    }
}