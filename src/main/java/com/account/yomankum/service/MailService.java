package com.account.yomankum.service;

import com.account.yomankum.domain.Mail;
import jakarta.mail.Message;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;

public interface MailService {
    String mailSend(Mail mail, String userEmail) throws MessagingException;
    String createCode();
    Message setTemplate(Mail type, String userEmail, String randomCode) throws MessagingException;
    String getContext(String key, String value, String template);
    void sendMail(MimeMessage message);
}
