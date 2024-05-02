package com.account.yomankum.init;

import com.account.yomankum.user.domain.User;
import com.account.yomankum.user.domain.UserType;
import com.account.yomankum.user.repository.UserRepository;
import jakarta.annotation.PostConstruct;
import java.time.LocalDateTime;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserInitializer {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;
    private static final String COMMON_PWD = "Admin123!";

    @PostConstruct
    public void init(){
        save("gkdlgkdl2040@naver.com");
        save("hyungzin0309@naver.com");
        save("connect.juhee@gamil.com");
        save("whaleee@naver.com");
    }

    private void save(String email){
        userRepository.save(newUser(email));
    }

    private User newUser(String email){
        return User.builder()
                .userType(UserType.USER)
                .email(email)
                .userType(UserType.ADMIN)
                .password(passwordEncoder.encode(COMMON_PWD))
                .pwdChangeDatetime(LocalDateTime.now())
                .build();
    }


}
