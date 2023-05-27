package com.locawork.webapi.service;

import com.locawork.webapi.dao.entity.SettingsEntity;
import com.locawork.webapi.respository.SettingsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SettingsService implements ISettingsService {

    @Autowired
    SettingsRepository repository;

    @Override
    public SettingsEntity save(SettingsEntity settingsEntity) {
        return repository.save(settingsEntity);
    }

    @Override
    public boolean exists(Integer userId) {
        return repository.exists(userId);
    }

    @Override
    public List<SettingsEntity> findAll() {
        return repository.findAll();
    }

    @Override
    public SettingsEntity getUserSettings(Integer userId) {
        return repository.findUserSettings(userId);
    }

    @Override
    public void updateRadius(Integer userId, Double radius) {
        repository.setRadius(radius, userId);
    }

    @Override
    public void updateViewByDefault(Integer userId, String value) {
        repository.setViewByDefault(value, userId);
    }

    @Override
    public void updateAskPermissionBeforeDeletingAJob(Integer userId, Boolean value) {
        repository.setAskPermissionsBeforeDeletingAJob(value, userId);
    }

    @Override
    public void updateShowInformationOnStartup(Integer userId, Boolean value) {
        repository.setShowInformationOnStartup(value, userId);
    }

    @Override
    public void updateBiometric(Integer userId, Boolean value) {
        repository.updateBiometric(value, userId);
    }

    @Override
    public void removeAllPersonsWhoAreNotMemberAnyMore() {
        repository.removeAllRoles();
    }

    @Override
    public void purchaseMember(String role, Integer userId) {
        repository.purchaseMember(role, userId);
    }

}
