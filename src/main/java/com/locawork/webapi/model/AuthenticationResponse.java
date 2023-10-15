package com.locawork.webapi.model;

import com.locawork.webapi.dao.entity.UserEntity;

public class AuthenticationResponse {
    private String token;

    private UserEntity userEntity;

    public AuthenticationResponse(String token, UserEntity userEntity) {
        this.token = token;
        this.userEntity = userEntity;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public UserEntity getUserEntity() {
        return userEntity;
    }

    public void setUserEntity(UserEntity userEntity) {
        this.userEntity = userEntity;
    }
}
