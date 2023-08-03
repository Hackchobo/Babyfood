package com.green.babyfood.config.security;

import com.green.babyfood.config.security.model.MyUserDetails;
import com.green.babyfood.config.security.model.UserEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
@RequiredArgsConstructor
public class MyUserDetailsServiceImpl implements UserDetailsService {

    private final UserDetailsMapper mapper;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserEntity entity = mapper.getByUid(username);
        return MyUserDetails.builder()
                .email(entity.getEmail())
                .iuser(entity.getIuser())
                .password(entity.getPassword())
                .roles(Collections.singletonList(entity.getRole()))
                .build();
    }
}
