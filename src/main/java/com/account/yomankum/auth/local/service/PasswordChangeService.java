package com.account.yomankum.auth.local.service;

import com.account.yomankum.auth.local.dto.request.PasswordChangeRequest;
import com.account.yomankum.auth.local.repository.PasswordAuthCodeRepository;
import com.account.yomankum.common.exception.BadRequestException;
import com.account.yomankum.common.exception.Exception;
import com.account.yomankum.mail.SendMailRequest;
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

    public void sendAuthCodeMail(String email) {
        String randomCode = RandomCodeGenerator.generateFiveDigitsCode();
        passwordAuthCodeRepository.saveCodeByEmail(email, randomCode);
        SendMailRequest request = SendMailRequest.passwordMailRequest(changePasswordMailTemplate, email, randomCode);
        mailService.sendMail(request);
    }

    public boolean isValidCode(String email, String inputCode) {
        String originalCode = passwordAuthCodeRepository.findByEmail(email)
                .orElseThrow(()->new BadRequestException(Exception.AUTH_CODE_NOT_EXIST));
        if(inputCode.equals(originalCode)){
            passwordAuthCodeRepository.deleteCodeByEmail(email);
            return true;
        }
        return false;
    }

    public void updatePassword(PasswordChangeRequest request) {
        String originCode = passwordAuthCodeRepository.findByEmail(request.email())
                .orElseThrow(()->new BadRequestException(Exception.AUTH_CODE_NOT_EXIST));

        if(!request.code().equals(originCode)){
            throw new BadRequestException(Exception.AUTH_CODE_UN_MATCHED);
        }
        userService.updatePassword(request.email(), request.password());
    }
}
