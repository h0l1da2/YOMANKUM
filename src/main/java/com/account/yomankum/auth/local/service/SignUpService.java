package com.account.yomankum.auth.local.service;

import com.account.yomankum.auth.local.dto.request.UserSignUpRequest;
import com.account.yomankum.auth.local.repository.SignupAuthCodeRepository;
import com.account.yomankum.common.exception.BadRequestException;
import com.account.yomankum.common.exception.Exception;
import com.account.yomankum.mail.domain.Mail;
import com.account.yomankum.mail.service.MailService;
import com.account.yomankum.user.domain.User;
import com.account.yomankum.user.service.UserFinder;
import com.account.yomankum.user.service.UserService;
import com.account.yomankum.util.RandomCodeGenerator;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SignUpService {

    private final UserFinder userFinder;
    private final UserService userService;
    private final SignupAuthCodeRepository signupAuthCodeRepository;
    private final MailService mailService;

    @Value("${mail.template.signup}")
    private String signupMailTemplate;

    @Value("${mail.title.signup}")
    private String signupMailTitle;

    public void sendAuthCodeMail(String email) {
        String randomCode = RandomCodeGenerator.generateFiveDigitsCode();
        signupAuthCodeRepository.saveCodeByEmail(email, randomCode);
        Mail authCodeMail = createAuthCodeMail(email, randomCode);
        mailService.sendMail(authCodeMail);
    }

    public boolean verifyEmailCode(String email, String inputCode) {
        String originalCode = signupAuthCodeRepository.findByEmail(email);
        if(inputCode.equals(originalCode)){
            signupAuthCodeRepository.deleteCodeByEmail(email);
            return true;
        }
        return false;
    }

    public void signUp(UserSignUpRequest userSignUpDto) {
        String email = userSignUpDto.email();
        userFinder.findByEmail(email)
                .ifPresent(user -> {throw new BadRequestException(Exception.DUPLICATED_USER);});
        User user = userSignUpDto.toEntity();
        userService.create(user);
    }

    private Mail createAuthCodeMail(String email, String randomCode) {
        Mail mail = new Mail(signupMailTitle, signupMailTemplate, email);
        mail.addAttribute("code", randomCode);
        return mail;
    }

}
