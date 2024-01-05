package com.account.yomankum.security;

import com.account.yomankum.domain.User;
import com.account.yomankum.repository.UserRepository;
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
        User findUser = userRepository.findById(id).orElse(null);

        if (findUser == null) {
            throw new UsernameNotFoundException("유저를 찾을 수 없음");
        }

        return new CustomUserDetails(findUser);
    }

}
