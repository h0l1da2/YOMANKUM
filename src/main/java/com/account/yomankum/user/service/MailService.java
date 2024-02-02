package com.account.yomankum.user.service;

import com.account.yomankum.user.domain.type.MailType;
import jakarta.mail.Message;
import jakarta.mail.internet.MimeMessage;

public interface MailService {
    String mailSend(String mailTypeStr, String userEmail);
    String createCode();
    Message setTemplate(MailType mailType, String userEmail, String randomCode);
    String getContext(String key, String value, String template);
    void sendMail(MimeMessage message);
    void verifyEmailCode(String userEmail, String randomCode);
}
