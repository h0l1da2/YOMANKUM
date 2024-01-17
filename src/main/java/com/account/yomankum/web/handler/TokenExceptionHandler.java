package com.account.yomankum.web.handler;

import com.account.yomankum.web.response.Response;
import com.account.yomankum.web.response.ResponseCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.security.NoSuchAlgorithmException;
import java.security.SignatureException;
import java.security.spec.InvalidKeySpecException;

@Slf4j
@RestControllerAdvice
public class TokenExceptionHandler {

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(NoSuchAlgorithmException.class)
    @ResponseBody
    public ResponseEntity<Response> noSuchAlgorithmExceptionHandler(NoSuchAlgorithmException e) {
        log.error("Token 알고리즘이 이상함 : {}", e.getMessage());
        e.printStackTrace();
        return Response.badRequest(ResponseCode.TOKEN_NOT_VALID);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(InvalidKeySpecException.class)
    @ResponseBody
    public ResponseEntity<Response> invalidKeySpecExceptionHandler(InvalidKeySpecException e) {
        log.error("Token 키스펙이 이상함 : {}", e.getMessage());
        e.printStackTrace();
        return Response.badRequest(ResponseCode.TOKEN_NOT_VALID);
    }
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(SignatureException.class)
    @ResponseBody
    public ResponseEntity<Response> signatureExceptionExceptionHandler(SignatureException e) {
        log.error("Token 서명이 이상함 : {}", e.getMessage());
        e.printStackTrace();
        return Response.badRequest(ResponseCode.TOKEN_NOT_VALID);
    }
}
