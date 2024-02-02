package com.account.yomankum.web.handler;

import com.account.yomankum.web.response.Response;
import com.account.yomankum.web.response.ResponseCode;
import com.nimbusds.jose.shaded.gson.JsonObject;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    // TODO Valid 에러 핸들링 없이 잡는 법 ?
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseBody
    public ResponseEntity<Response> invalidRequestHandler(MethodArgumentNotValidException e) {
        log.error("@Valid 에러 : {}", e.getMessage());
        e.printStackTrace();

        JsonObject invalid = new JsonObject();
        for (FieldError error : e.getFieldErrors()) {
            invalid.addProperty(error.getField(), error.getDefaultMessage());
        }

        return Response.badRequest(ResponseCode.NOT_VALID, invalid);
    }}
