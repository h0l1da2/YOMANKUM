package com.account.yomankum.common.service;

import com.account.yomankum.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SessionService {

    private final UserRepository userRepository;

    public Long getSessionUserId(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return Long.parseLong(((UserDetails) authentication.getPrincipal()).getUsername());
    }


}
