package com.account.yomankum.common.service;

import com.account.yomankum.domain.User;
import com.account.yomankum.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SessionService {

    private final UserRepository userRepository;

    public Long getSessionUserId(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Long id = Long.parseLong(((UserDetails) authentication.getPrincipal()).getUsername());

        return userRepository.findById(id)
                .orElseThrow(() -> new UsernameNotFoundException("User not found: " + id)).getId();
    }


}
