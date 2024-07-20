package com.account.yomankum.mail.service;

import com.account.yomankum.mail.domain.Mail;
import com.account.yomankum.mail.domain.MailSender;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class MailService {

    private final MailSender mailSender;

    @Value("${mail.id}")
    private String applicationMail;

    public void sendMail(Mail mail){
        mailSender.send(mail, applicationMail);
    }
}
