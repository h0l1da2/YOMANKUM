package com.account.yomankum.user.service;

import com.account.yomankum.user.domain.type.Mail;
import com.account.yomankum.common.exception.status4xx.CodeNotFoundException;
import com.account.yomankum.common.exception.status4xx.CodeNotValidException;
import jakarta.mail.Message;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;

public interface MailService {
    String mailSend(Mail mail, String userEmail) throws MessagingException;
    String createCode();
    Message setTemplate(Mail type, String userEmail, String randomCode) throws MessagingException;
    String getContext(String key, String value, String template);
    void sendMail(MimeMessage message);
    void verifyEmailCode(String userEmail, String randomCode) throws CodeNotFoundException, CodeNotValidException;
}
