package com.account.yomankum.web.handler;

import com.account.yomankum.web.response.Response;
import com.account.yomankum.web.response.ResponseCode;
import com.nimbusds.jose.shaded.gson.JsonObject;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseBody
    public ResponseEntity<Response> invalidRequestHandler(MethodArgumentNotValidException e) {
        e.printStackTrace();

        JsonObject invalid = new JsonObject();
        for (FieldError error : e.getFieldErrors()) {
            invalid.addProperty(error.getField(), error.getDefaultMessage());
        }

        return Response.badRequest(ResponseCode.NOT_VALID, invalid);
    }}
