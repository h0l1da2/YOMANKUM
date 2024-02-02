package com.account.yomankum.web.handler;

import com.nimbusds.jose.shaded.gson.JsonObject;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseBody
    public JsonObject invalidRequestHandler(MethodArgumentNotValidException e) {
        JsonObject invalid = new JsonObject();
        for (FieldError error : e.getFieldErrors()) {
            invalid.addProperty(
                    error.getField(), error.getDefaultMessage()
            );
        }
        return invalid;
    }}
