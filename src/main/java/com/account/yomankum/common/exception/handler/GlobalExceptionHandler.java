package com.account.yomankum.common.exception.handler;

import com.account.yomankum.auth.oauth.exception.OauthLoginFailException;
import com.account.yomankum.common.exception.BadRequestException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;


@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<String> handleBadRequestException(final BadRequestException e) {
        return ResponseEntity.status(HttpStatus.valueOf(e.getCode()))
                .body(e.getMessage());
    }

    @ExceptionHandler(OauthLoginFailException.class)
    public ResponseEntity<OauthLoginFailException> handleOauthLoginFailException(final OauthLoginFailException e) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e);
    }

}
