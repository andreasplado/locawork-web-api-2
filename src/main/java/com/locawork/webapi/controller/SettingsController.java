package com.locawork.webapi.controller;


import com.locawork.webapi.dao.entity.SettingsEntity;
import com.locawork.webapi.dao.entity.UserEntity;
import com.locawork.webapi.model.ResponseModel;
import com.locawork.webapi.model.UserSettings;
import com.locawork.webapi.service.SettingsService;
import com.locawork.webapi.service.UserDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/settings")
public class SettingsController {

    @Autowired
    SettingsService settingsService;

    @Autowired
    UserDataService userDataService;

    @GetMapping
    public ResponseEntity<?> getAll() {
        List<SettingsEntity> settings = settingsService.findAll();

        return ResponseEntity.ok(settings);
    }

    @RequestMapping(value = "/get-user-settings", method = RequestMethod.GET)
    public ResponseEntity<?> getSettings(@RequestParam Integer userId) {
        SettingsEntity settings = settingsService.getUserSettings(userId);
        Optional<UserEntity> user = userDataService.findUserById(userId);
        UserSettings userSettings = new UserSettings();
        userSettings.setUserId(settings.getUserId());
        userSettings.setAskPermissionsBeforeDeletingAJob(settings.getAskPermissionsBeforeDeletingAJob());
        userSettings.setCreatedAt(settings.getCreatedAt());
        userSettings.setCurrency(settings.getCurrency());
        userSettings.setViewByDefault(settings.getViewByDefault());
        userSettings.setShowInformationOnStartup(settings.getShowInformationOnStartup());
        userSettings.setEmail(user.get().getEmail());
        userSettings.setFullname(user.get().getFullname());
        userSettings.setUpdatedAt(user.get().getUpdatedAt());
        userSettings.setContact(user.get().getContact());
        userSettings.setRadius(settings.getRadius());
        userSettings.setRole(user.get().getRole());
        userSettings.setBiometric(settings.isBiometric());

        return ResponseEntity.ok(userSettings);
    }

    @RequestMapping(value = "/set-initial-settings", method = RequestMethod.POST)
    public ResponseEntity<?> setInitialSettings(@RequestBody SettingsEntity settingsEntity) {
        ResponseModel responseModel = new ResponseModel();

        if (!settingsService.exists(settingsEntity.getUserId())) {
            settingsService.save(settingsEntity);

            return ResponseEntity.ok(settingsEntity);
        } else {
            responseModel.setMessage("These settings for user with id: " + settingsEntity.getUserId() + " already exist!");

            return ResponseEntity.ok(responseModel);
        }
    }


    @RequestMapping(value = "/update-radius", method = RequestMethod.PUT)
    public ResponseEntity<?> updateRadius(@RequestParam Integer userId, @RequestParam Double radius) {
        ResponseModel responseModel = new ResponseModel();
        settingsService.updateRadius(userId, radius);
        if(userDataService.exists(userId)){
            responseModel.setMessage("You updated");
            responseModel.setValid(true);
            responseModel.setRadius(radius);
            return ResponseEntity.ok(responseModel);
        }else {
            responseModel.setMessage("You failed");
            responseModel.setValid(false);
            responseModel.setRadius(radius);
            return ResponseEntity.ok(responseModel);
        }
    }

    @RequestMapping(value = "/update-view-by-default", method = RequestMethod.PUT)
    public ResponseEntity<?> updateViewByDefault(@RequestParam Integer userId, @RequestParam String value) {
        ResponseModel responseModel = new ResponseModel();

        settingsService.updateViewByDefault(userId, value);
        responseModel.setMessage("You updated view by default!");

        return ResponseEntity.ok(responseModel);
    }

    @RequestMapping(value = "/update-ask-permissions-before-deleting-a-job", method = RequestMethod.PUT)
    public ResponseEntity<?> updateAskPermissionsBeforeDeletingAJob(@RequestParam Integer userId, @RequestParam Boolean value) {
        ResponseModel responseModel = new ResponseModel();

        settingsService.updateAskPermissionBeforeDeletingAJob(userId, value);
        responseModel.setMessage("You updated view by default!");

        return ResponseEntity.ok(responseModel);
    }

    @RequestMapping(value = "/update-biometric", method = RequestMethod.PUT)
    public ResponseEntity<?> updateBiometric(@RequestParam Integer userId, @RequestParam Boolean value) {
        ResponseModel responseModel = new ResponseModel();

        settingsService.updateBiometric(userId, value);
        responseModel.setMessage("You updated your biometric!");

        return ResponseEntity.ok(responseModel);
    }

    @RequestMapping(value = "/update-show-information-on-startup", method = RequestMethod.PUT)
    public ResponseEntity<?> updateShowInformationOnStartup(@RequestParam Integer userId, @RequestParam Boolean value) {
        ResponseModel responseModel = new ResponseModel();

        settingsService.updateShowInformationOnStartup(userId, value);
        responseModel.setMessage("You updated view by default!");

        return ResponseEntity.ok(responseModel);
    }

    @Scheduled(cron = "0 1 1 * * ?")
    public void myScheduledMethod() {
        settingsService.removeAllPersonsWhoAreNotMemberAnyMore();
    }
}
