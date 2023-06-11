package com.locawork.webapi.service;

import com.locawork.webapi.dao.entity.UserEntity;
import com.locawork.webapi.respository.UserAuthRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Service
public class UserAuthService implements UserDetailsService {

    @Autowired
    UserAuthRepository userAuthRepository;

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {

        UserEntity user = userAuthRepository.existsByName(s);
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        return new UserDetails() {
            @Override
            public Collection<? extends GrantedAuthority> getAuthorities() {
                Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
                return authentication.getAuthorities();
            }

            @Override
            public String getPassword() {
                return user.getPassword();
            }

            @Override
            public String getUsername() {
                return user.getEmail();
            }

            @Override
            public boolean isAccountNonExpired() {
                return user.getExpired();
            }

            @Override
            public boolean isAccountNonLocked() {
                return user.getLocked();
            }

            @Override
            public boolean isCredentialsNonExpired() {
                return user.getCredentialsNonExpired();
            }

            @Override
            public boolean isEnabled() {
                return user.getEnabled();
            }
        };
    }
}
