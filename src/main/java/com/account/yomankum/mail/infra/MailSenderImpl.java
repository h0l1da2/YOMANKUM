package com.account.yomankum.mail.infra;

import com.account.yomankum.common.exception.Exception;
import com.account.yomankum.common.exception.InternalErrorException;
import com.account.yomankum.mail.domain.Mail;
import com.account.yomankum.mail.domain.MailSender;
import jakarta.mail.Message;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring6.SpringTemplateEngine;

import java.util.Map;

@Component
@RequiredArgsConstructor
public class MailSenderImpl implements MailSender {

    private final JavaMailSender mailSender;
    private final SpringTemplateEngine templateEngine;

    @Override
    public void send(Mail mail, String sender) {
        MimeMessage mailParameter = makeMailParameter(mail, sender);
        mailSender.send(mailParameter);
    }

    private MimeMessage makeMailParameter(Mail mail, String sender) {
        MimeMessage message = mailSender.createMimeMessage();
        try {
            message.addRecipients(Message.RecipientType.TO, mail.getDestination());
            message.setSubject(mail.getTitle());
            message.setFrom(sender);
            message.setText(getContext(mail.getAttributes(), mail.getMailTemplate()), "UTF-8", "html");
        } catch (MessagingException e) {
            throw new InternalErrorException(Exception.SERVER_ERROR);
        }
        return message;
    }

    public String getContext(Map<String, Object> attributes, String template) {
        Context context = new Context();
        for (String key : attributes.keySet()) {
            context.setVariable(key, attributes.get(key));
        }
        return templateEngine.process(template, context);
    }

}