package com.account.yomankum.auth.service;

import com.account.yomankum.auth.dto.request.UserSignUpRequest;
import com.account.yomankum.auth.repository.SignupAuthCodeRepository;
import com.account.yomankum.common.exception.BadRequestException;
import com.account.yomankum.common.exception.Exception;
import com.account.yomankum.mail.SendMailRequest;
import com.account.yomankum.mail.service.MailService;
import com.account.yomankum.mail.service.MailServiceTemp;
import com.account.yomankum.security.service.TokenService;
import com.account.yomankum.user.domain.User;
import com.account.yomankum.user.service.UserFinder;
import com.account.yomankum.user.service.UserService;
import com.account.yomankum.util.RandomCodeGenerator;
import com.account.yomankum.util.RedisUtil;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import lombok.Value;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SignUpService {

    private final UserFinder userFinder;
    private final UserService userService;
    private final BCryptPasswordEncoder passwordEncoder;
    private final SignupAuthCodeRepository signupAuthCodeRepository;
    private final MailService mailService;
    @Value("${mail.template.signup}")
    private final String mailTemplatePath;

    public void sendAuthCode(String email) {
        String randomCode = RandomCodeGenerator.generateFiveDigitsCode();
        signupAuthCodeRepository.saveCodeByEmail(email, randomCode);
        SendMailRequest request = new SendMailRequest(mailTemplatePath, email);
        mailService.sendMail(request);
    }

    public boolean verifyEmailCode(String email, String inputCode) {
        String originalCode = signupAuthCodeRepository.findByEmail(email);
        return inputCode.equals(originalCode);
    }

    public void signUp(UserSignUpRequest userSignUpDto) {
        String email = userSignUpDto.email();
        userFinder.findByEmail(email)
                .ifPresent(user -> {throw new BadRequestException(Exception.DUPLICATED_USER);});

        User user = userSignUpDto.toEntity(passwordEncoder);
        userService.create(user);
    }

}
