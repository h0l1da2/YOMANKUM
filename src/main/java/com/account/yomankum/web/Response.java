package com.account.yomankum.web;

import lombok.Builder;
import lombok.Getter;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.LocalDateTime;

@Getter
@Builder
public class Response {

    String message;
    HttpStatus status;
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    LocalDateTime timestamp;
    Object data;

    public static ResponseEntity<Response> ok() {
        return response(HttpStatus.OK, "SUCCESS");
    }
    public static ResponseEntity<Response> ok(Object data) {
        return responseWithData(HttpStatus.OK, "SUCCESS", data);
    }

    public static ResponseEntity<Response> badRequest(String message) {
        return response(HttpStatus.BAD_REQUEST, message);
    }

    private static ResponseEntity<Response> responseWithData(HttpStatus status, String message, Object data) {
        return ResponseEntity.status(status).body(
                Response.builder()
                        .status(status)
                        .message(message)
                        .timestamp(LocalDateTime.now())
                        .data(data)
                        .build()
        );
    }

    private static ResponseEntity<Response> response(HttpStatus status, String message) {
        return ResponseEntity.status(status).body(
                Response.builder()
                        .status(status)
                        .message(message)
                        .timestamp(LocalDateTime.now())
                        .build()
        );
    }
}
