package com.account.yomankum.common.exception.handler;

import io.jsonwebtoken.ExpiredJwtException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@RestControllerAdvice
public class TokenExceptionHandler {

    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ExceptionHandler(ExpiredJwtException.class)
    @ResponseBody
    public Map<String, String> invalidRequestHandler(ExpiredJwtException e) {
        log.error("토큰 시간 초과 : {}", e.getMessage());
        Map<String, String> invalid = new HashMap<>();
        invalid.put("code", "401");
        invalid.put("message", "토큰 시간 초과");
        return invalid;
    }
}
