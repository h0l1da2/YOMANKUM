package com.account.yomankum.web.handler;

import com.account.yomankum.exception.UserNotFoundException;
import com.account.yomankum.web.response.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class UserExceptionHandler {

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(UserNotFoundException.class)
    @ResponseBody
    public ResponseEntity<Response> userNotFoundHandler(UserNotFoundException e) {
        e.printStackTrace();
        return Response.badRequest(e.getResponseCode());
    }

}
