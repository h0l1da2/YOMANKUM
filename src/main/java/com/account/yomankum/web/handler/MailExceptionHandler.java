package com.account.yomankum.web.handler;

import com.account.yomankum.web.response.Response;
import com.account.yomankum.web.response.ResponseCode;
import jakarta.mail.MessagingException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class MailExceptionHandler {

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MessagingException.class)
    @ResponseBody
    public ResponseEntity<Response> messagingHandler(MessagingException e) {

        log.error("메일 전송 에러 : {}", e.getMessage());
        log.error("메일 전송 에러 : {}", e.getCause());
        e.printStackTrace();

        return Response.badRequest(ResponseCode.EMAIL_ERROR);
    }
}
