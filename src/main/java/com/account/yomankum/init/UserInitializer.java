package com.account.yomankum.init;

import com.account.yomankum.user.dto.UserDto.UserSignUpDto;
import com.account.yomankum.user.service.UserService;
import jakarta.annotation.PostConstruct;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserInitializer {

    private final UserService userService;
    private static final String COMMON_PWD = "Admin123!";

    @PostConstruct
    public void init(){
        save("gkdlgkdl2040@naver.com");
        save("hyungzin0309@naver.com");
        save("connect.juhee@gamil.com");
        save("whaleee@naver.com");
    }

    private void save(String id){
        userService.signUp(new UserSignUpDto(id, COMMON_PWD));
    }


}
