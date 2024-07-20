package com.account.yomankum.mail.domain;

public interface MailSender {

    void send(Mail mail, String applicationMail);
}
