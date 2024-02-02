package com.account.yomankum.user.service;

import com.account.yomankum.common.exception.BadRequestException;
import com.account.yomankum.common.exception.Exception;
import com.account.yomankum.common.exception.InternalErrorException;
import com.account.yomankum.user.domain.type.Mail;
import com.account.yomankum.util.RedisUtil;
import com.nimbusds.jose.shaded.gson.Gson;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring6.SpringTemplateEngine;

import java.util.Random;

import static com.account.yomankum.user.dto.MailDto.*;
import static jakarta.mail.Message.RecipientType;

@Slf4j
@Service
@RequiredArgsConstructor
@PropertySource("classpath:application.yml")
public class MailServiceImpl implements MailService {

    private final JavaMailSender mailSender;
    private final SpringTemplateEngine templateEngine;
    private final RedisUtil redisUtil;

    @Value("${mail.id}")
    private String fromEmail;

    @Override
    public String mailSend(Mail mail, String userEmail) {

        if (redisUtil.existData(userEmail)) {
            redisUtil.deleteData(userEmail);
        }

        String result = "";
        String randomCode = "";
        Gson gson = new Gson();

        if (mail.equals(Mail.JOIN)) {
            MailRandomCodeDto mailRandomCodeDto = MailRandomCodeDto.builder()
                    .randomCode(
                            createCode()
                    )
                    .build();
            result = gson.toJson(mailRandomCodeDto);
        }

        MimeMessage template = setTemplate(mail, userEmail, randomCode);
        sendMail(template);

        return result;
    }

    @Override
    public String createCode() {
        String values = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        int codeLength = 5;

        Random random = new Random();
        StringBuilder builder = new StringBuilder();

        for (int i = 0; i < codeLength; i++) {
            int randomIndex = random.nextInt(values.length());
            builder.append(values.charAt(randomIndex));
        }
        return builder.toString();
    }
    @Override
    public MimeMessage setTemplate(Mail type, String userEmail, String randomCode) {

        String key = "";
        String value = "";
        String title = "";
        String template = "";


        if (type.equals(Mail.JOIN)) {
            title = "YOMANKUM * 가입 코드 전송";
            template = "email/joinMailForm";
            key = "code";
            value = randomCode;
        }

        MimeMessage message = setMimeMessage(userEmail, key, value, title, template);

        final long EXPIRE_CODE_TIME = 60 * 15L;
        redisUtil.setDataExpire(userEmail, randomCode, EXPIRE_CODE_TIME);

        return message;
    }

    @Override
    public String getContext(String key, String value, String template) {
        Context context = setContext(key, value);
        return templateEngine.process(template, context);
    }

    @Override
    public void sendMail(MimeMessage message) {
        mailSender.send(message);
    }

    @Override
    public void verifyEmailCode(String userEmail, String randomCode) {
        String randomCodeByEmail = redisUtil.getData(userEmail);

        if (randomCodeByEmail == null) {
            log.error("입력한 이메일이 일치하지 않음 : {}", userEmail);
            throw new BadRequestException(Exception.EMAIL_NOT_FOUND);
        }

        if (!randomCodeByEmail.matches(randomCode)) {
            log.error("입력한 코드가 일치하지 않음 : {}", randomCode);
            throw new BadRequestException(Exception.EMAIL_CODE_UN_MATCHED);
        }
    }

    private MimeMessage setMimeMessage(String userEmail, String key, String value, String title, String template) {
        String charset = "UTF-8";
        String html = "html";

        MimeMessage message = mailSender.createMimeMessage();
        try {
            message.addRecipients(RecipientType.TO, userEmail);
            message.setSubject(title);
            message.setFrom(fromEmail);
            message.setText(getContext(key, value, template), charset, html);
        } catch (MessagingException e) {
            throw new InternalErrorException(Exception.SERVER_ERROR);
        }
        return message;
    }

    private Context setContext(String key, String value) {
        Context context = new Context();
        context.setVariable(key, value);
        return context;
    }
}
