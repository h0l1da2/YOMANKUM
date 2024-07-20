package com.account.yomankum.auth.local.service;

import com.account.yomankum.auth.local.dto.request.PasswordChangeRequest;
import com.account.yomankum.auth.local.repository.PasswordAuthCodeRepository;
import com.account.yomankum.common.exception.BadRequestException;
import com.account.yomankum.common.exception.Exception;
import com.account.yomankum.mail.domain.Mail;
import com.account.yomankum.mail.service.MailService;
import com.account.yomankum.user.service.UserService;
import com.account.yomankum.util.RandomCodeGenerator;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PasswordChangeService {

    private final UserService userService;
    private final PasswordAuthCodeRepository passwordAuthCodeRepository;
    private final MailService mailService;

    @Value("${mail.template.password}")
    private String changePasswordMailTemplate;

    @Value("${mail.title.password}")
    private String changePasswordMailTitle;

    public void sendAuthCodeMail(String email) {
        String randomCode = RandomCodeGenerator.generateFiveDigitsCode();
        passwordAuthCodeRepository.saveCodeByEmail(email, randomCode);
        Mail changePasswordMail = createChangePasswordMail(email, randomCode);
        mailService.sendMail(changePasswordMail);
    }

    public boolean isValidCode(String email, String inputCode) {
        String originalCode = passwordAuthCodeRepository.findByEmail(email);
        return inputCode.equals(originalCode);
    }

    public void updatePassword(PasswordChangeRequest request) {
        String originalCode = passwordAuthCodeRepository.findByEmail(request.email());
        if(!request.code().equals(originalCode)){
            throw new BadRequestException(Exception.AUTH_CODE_NOT_VALID);
        }
        userService.updatePasswordByEmail(request.email(), request.password());
    }

    private Mail createChangePasswordMail(String email, String randomCode) {
        Mail mail = new Mail(changePasswordMailTitle, changePasswordMailTemplate, email);
        mail.addAttribute("email", email);
        mail.addAttribute("code", randomCode);
        return mail;
    }

}
