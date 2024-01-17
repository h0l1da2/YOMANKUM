package com.account.yomankum.web.handler;

import com.account.yomankum.exception.SnsException;
import com.account.yomankum.web.response.Response;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class SecurityExceptionHandler {

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(SnsException.class)
    @ResponseBody
    public ResponseEntity<Response> snsExceptionHandler(SnsException e) {
        log.error("SNS 에러 : {}", e.getMessage());
        e.printStackTrace();
        return Response.badRequest(e.getResponseCode());
    }
}
