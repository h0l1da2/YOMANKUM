package com.account.yomankum.handler;

import com.account.yomankum.exception.UserDuplicateException;
import com.account.yomankum.web.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class UserHandler {
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(UserDuplicateException.class)
    public ResponseEntity<Response> userDuplicateException(UserDuplicateException e) {
        return Response.badRequest(e.getMessage());
    }
}
