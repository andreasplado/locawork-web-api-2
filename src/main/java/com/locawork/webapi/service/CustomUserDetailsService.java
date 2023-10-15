package com.locawork.webapi.service;

import com.locawork.webapi.dao.entity.UserEntity;
import com.locawork.webapi.respository.UserDataRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

@Component
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private UserDataRepository userRepository;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // Returning dummy user, use your own logic for example load from
        // database
        List<SimpleGrantedAuthority> authorities = Arrays.asList(new SimpleGrantedAuthority(("ROLE_USER")));
        UserEntity userEntity = userRepository.findByEmail(username);
        if(userEntity != null) {
            User user = new User(userEntity.getEmail(), bCryptPasswordEncoder.encode(userEntity.getPassword()), authorities);
            return user;
        }else{
            return null;
        }
    }
}