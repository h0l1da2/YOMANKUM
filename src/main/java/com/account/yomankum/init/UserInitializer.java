package com.account.yomankum.init;

import com.account.yomankum.user.service.UserService;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserInitializer {

    private final UserService userService;

    @PostConstruct
    public void init(){

    }


}
