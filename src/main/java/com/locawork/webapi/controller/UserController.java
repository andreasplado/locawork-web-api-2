package com.locawork.webapi.controller;

import com.locawork.webapi.dao.entity.SettingsEntity;
import com.locawork.webapi.dao.entity.UserEntity;
import com.locawork.webapi.data.Note;
import com.locawork.webapi.model.ResponseModel;
import com.locawork.webapi.service.SettingsService;
import com.locawork.webapi.service.UserDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.provider.token.store.InMemoryTokenStore;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserDataService userDataService;

    @Autowired
    private SettingsService settingsService;

    @GetMapping
    public ResponseEntity<?> getAll() {
        List<UserEntity> userEntities = userDataService.findAll();

        if (userEntities != null && userEntities.isEmpty()) {
            ResponseModel responseModel = new ResponseModel();
            responseModel.setMessage("Users not found!");
            return ResponseEntity.ok(responseModel);
        }

        return ResponseEntity.ok(userEntities);
    }

    @PostMapping("/logout")
    public ResponseEntity<?> logout(@RequestParam String userId){
        Note note = new Note();
        note.setMessage("You logged out successfully");
        new InMemoryTokenStore().findTokensByClientId(userId).clear();
        return ResponseEntity.ok(note);
    }

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<?> create(@RequestBody UserEntity userEntity) {
        if(!userDataService.existByEmail(userEntity.getEmail())){
            userDataService.save(userEntity);
        }

        userEntity = userDataService.findByEmail(userEntity.getEmail());

        return ResponseEntity.ok(userEntity);
    }

    @PostMapping("/signup")
    public ResponseEntity<?> signUp(@RequestBody UserEntity user)
    {
        if(!userDataService.existByEmail(user.getEmail())){
            user.setPassword(user.getPassword());
            userDataService.save(user);

            int userId = userDataService.findId(user.getEmail());
            SettingsEntity settingsEntity = new SettingsEntity();
            settingsEntity.setBiometric(false);
            settingsEntity.setCreatedAt(new Date());
            settingsEntity.setCurrency("euro");
            settingsEntity.setUserId(userId);
            settingsEntity.setMemberRole("admin");
            settingsEntity.setRadius(0.0);
            settingsEntity.setViewByDefault("available");
            settingsEntity.setAskPermissionsBeforeDeletingAJob(true);
            settingsService.save(settingsEntity);
        }else{
            Note note = new Note();
            note.setMessage("User with this email already exists!");
            return ResponseEntity.ok(note);
        }
        user = userDataService.findByEmail(user.getEmail());

        return ResponseEntity.ok(user);
    }

    @RequestMapping(value = "{id}", method = RequestMethod.PUT)
    public ResponseEntity<?> update(@PathVariable Integer id, @RequestBody UserEntity userEntity) {

        if (!userDataService.exists(id)) {
            ResponseModel responseModel = new ResponseModel();
            responseModel.setMessage("User already exists!");
            return ResponseEntity.ok(responseModel);
        }
        userDataService.update(userEntity);

        return ResponseEntity.ok(userEntity);
    }


    @RequestMapping(value = "/update-role", method = RequestMethod.PUT)
    public ResponseEntity<?> updateRole(@RequestParam String userRole, @RequestParam Integer id) {
        if (!userDataService.exists(id)) {
            return ResponseEntity.ok("");
        }

        userDataService.updateUserRole(userRole, id);

        return ResponseEntity.ok(userRole);
    }

    @RequestMapping(value = "/update-firebase-token", method = RequestMethod.POST)
    public ResponseEntity<?> updateUserFirebaseToken(@RequestParam String firebaseToken, @RequestParam Integer userId) {
        userDataService.updateUserFirebaseToken(firebaseToken, userId);

        return ResponseEntity.ok(firebaseToken);
    }

    @RequestMapping(value = "/get-offerer-firebase-token", method = RequestMethod.GET)
    public ResponseEntity<?> updateUserFirebaseToken(@RequestParam Integer offererId) {
        String token = userDataService.getUserFirebaseToken(offererId);

        Note note = new Note();
        note.setMessage(token);

        return ResponseEntity.ok(note);
    }

    @RequestMapping(value = "{id}", method = RequestMethod.DELETE)
    public ResponseEntity<?> delete(@PathVariable("id") Integer id) {
        Optional<UserEntity> userEntity = userDataService.findById(id);

        if (!userEntity.isPresent()) {
            ResponseModel responseModel = new ResponseModel();
            responseModel.setMessage("User not found!");
            return ResponseEntity.ok(responseModel);
        }

        userDataService.delete(id);
        return ResponseEntity.ok(userEntity);
    }
    @RequestMapping(value = "/does-exists", method = RequestMethod.GET)
        public ResponseEntity<Boolean> getMyApplications(@RequestParam String email) {
        boolean userExists = userDataService.existByEmail(email);
        return ResponseEntity.ok(userExists);
    }

    @RequestMapping(value = "/member-role", method = RequestMethod.GET)
    public ResponseEntity<String> isMember(@RequestParam int userId) {
        String memberRole = userDataService.memberRole(userId);

        return ResponseEntity.ok(memberRole);
    }

    @RequestMapping(value = "/set-role", method = RequestMethod.GET)
    public ResponseEntity<String> setRole(@RequestParam int userId) {
        String memberRole = userDataService.memberRole(userId);

        return ResponseEntity.ok(memberRole);
    }

    @RequestMapping(value = "/get-user-firebase-token", method = RequestMethod.GET)
    public ResponseEntity<String> setRole(@RequestParam Integer userId) {
        String firebaseToken = userDataService.getUserFirebaseToken(userId);

        return ResponseEntity.ok(firebaseToken);
    }
}
