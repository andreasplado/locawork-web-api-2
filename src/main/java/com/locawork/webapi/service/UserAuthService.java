package com.locawork.webapi.service;

import com.locawork.webapi.dao.entity.UserEntity;
import com.locawork.webapi.respository.UserAuthRepository;
import com.locawork.webapi.respository.UserDataRepository;
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
public class UserAuthService implements UserDetailsService {

    @Autowired
    private UserDataRepository userDataRepository;

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {

        UserEntity user = userDataRepository.findByEmail(s);
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        System.out.println(user.getIsAccountNonLocked());


        if(!authentication.isAuthenticated()){
            throw new UsernameNotFoundException("Username not found");
        }
        return new UserDetails() {
            @Override
            public Collection<? extends GrantedAuthority> getAuthorities() {
                Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

                List<GrantedAuthority> list = new ArrayList<GrantedAuthority>();

                list.add(new SimpleGrantedAuthority("ROLE_ADMIN"));

                return list;
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
                return user.getIsAccountNonLocked();
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
