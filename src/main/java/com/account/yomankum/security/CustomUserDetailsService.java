package com.account.yomankum.security;

import com.account.yomankum.domain.User;
import com.account.yomankum.repository.UserRepository;
import com.account.yomankum.web.response.ResponseCode;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        long id = Long.parseLong(username);
        User findUser = userRepository.findById(id).orElseThrow(()->new RuntimeException(ResponseCode.EMAIL000.toString()));
        return new CustomUserDetails(findUser);
    }

}
