package com.account.yomankum.service.impl;

import com.account.yomankum.domain.dto.UserSignUpDto;
import com.account.yomankum.repository.UserRepository;
import com.account.yomankum.service.SignUpService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SignUpServiceImpl implements SignUpService {

    private final UserRepository userRepository;

    @Override
    public void signUp(UserSignUpDto userSignUpDto) {


    }
}
