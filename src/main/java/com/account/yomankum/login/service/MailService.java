package com.account.yomankum.login.service;

import com.account.yomankum.domain.enums.Mail;
import jakarta.mail.Message;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;

public interface MailService {
    String mailSend(Mail mail, String userEmail) throws MessagingException;
    String createCode();
    Message setTemplate(Mail type, String userEmail, String randomCode) throws MessagingException;
    String getContext(String key, String value, String template);
    void sendMail(MimeMessage message);
    boolean verifyEmailCode(String userEmail, String randomCode);
}
