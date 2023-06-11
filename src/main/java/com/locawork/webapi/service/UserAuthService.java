package com.locawork.webapi.service;

import com.locawork.webapi.dao.entity.UserEntity;
import com.locawork.webapi.respository.UserAuthRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Service
public class UserAuthService {

    @Autowired
    UserAuthRepository userAuthRepository;

    public UserEntity loadUserByUsername(String s) throws UsernameNotFoundException {

        UserEntity user = userAuthRepository.existsByName(s);

        if (user == null) {
            throw new UsernameNotFoundException("No user found for "+ user.getEmail() + ".");
        }
        return user;
    }
}
