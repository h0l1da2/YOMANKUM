package com.account.yomankum.web.handler;

import com.account.yomankum.exception.UserNotFoundException;
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
public class UserExceptionHandler {

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(UserNotFoundException.class)
    @ResponseBody
    public ResponseEntity<Response> userNotFoundHandler(UserNotFoundException e) {
        log.error("유저 에러 : {}", e.getResponseCode().getMessage());
        e.printStackTrace();
        return Response.badRequest(e.getResponseCode());
    }

}
