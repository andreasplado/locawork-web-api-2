package com.locawork.webapi.service;

import com.locawork.webapi.dao.entity.UserEntity;
import com.locawork.webapi.respository.UserDataRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserDataService implements IUserService {

    @Autowired
    private UserDataRepository repository;

    @Override
    public List<UserEntity> findAll() {
        return repository.findAll();
    }

    @Override
    public UserEntity save(UserEntity userEntity) {
        return repository.save(userEntity);
    }

    @Override
    public UserEntity update(UserEntity userEntity) {
        if(repository.existsById(userEntity.getId())){
            repository.save(userEntity);
        }
        return userEntity;
    }

    @Override
    public boolean existByEmail(String email){
        return repository.existsByEmail(email);
    }

    @Override
    public UserEntity saveUser(UserEntity userEntity) {
        return null;
    }

    @Override
    public UserEntity findByEmail(String email) {
        return repository.findByEmail(email);
    }

    @Override
    public int findId(String email) {
        return repository.findId(email);
    }

    @Override
    public UserEntity findUserById(Integer id) {
        return repository.findUserById(id);
    }

    @Override
    public String getUserFirebaseToken(Integer id) {
        return repository.getUserFirebaseToken(id);
    }

    @Override
    public void updateUserFirebaseToken(String firebaseToken, Integer id) {
        repository.updateUserFirebaseToken(firebaseToken, id);
    }

    @Override
    public void updateUserRole(String role, Integer id) {
        repository.updateUserRole(role, id);
    }

    @Override
    public String memberRole(Integer id) {
        if(repository.existsById(id)){
            return repository.getMemberRole(id);
        }
        return "";
    }

    @Override
    public boolean tokenExists() {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication()
                .getPrincipal();
        return userDetails.isCredentialsNonExpired();
    }

    @Override
    public void setMemberRole(Integer id, String memberRole) {
        if(repository.existsById(id)){
            repository.setMemberRole(memberRole, id);
        }
    }

    @Override
    public boolean userAuthenticated(String username, String password) {
        return repository.userAuthenticated(username, password);
    }

    @Override
    public void delete(Integer id) {
        repository.deleteById(id);
    }

    @Override
    public Optional<UserEntity> findById(Integer id) {
        return repository.findById(id);
    }

    @Override
    public boolean exists(Integer id) {
        return repository.existsById(id);
    }

    @Override
    public boolean phoneNumberAndEmailMatches(String email, String phoneNumber) {
        if(repository.emailAndPhoneNumberMatches(email, phoneNumber)){
            return true;
        }
        return false;
    }
}
