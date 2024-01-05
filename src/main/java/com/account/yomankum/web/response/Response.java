package com.account.yomankum.web.response;

import lombok.Builder;
import lombok.Getter;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.LocalDateTime;

@Getter
@Builder
public class Response {

    private String message;
    private HttpStatus status;
    private ResponseCode code;
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime timestamp;
    private Object data;

    public static ResponseEntity<Response> ok() {
        return response(HttpStatus.OK);
    }
    public static ResponseEntity<Response> ok(ResponseCode responseCode) {
        return response(HttpStatus.OK, responseCode);
    }
    public static ResponseEntity<Response> ok(ResponseCode responseCode, Object data) {
        return responseWithData(HttpStatus.OK, data, responseCode);
    }

    public static ResponseEntity<Response> badRequest(ResponseCode responseCode) {
        return response(HttpStatus.BAD_REQUEST, responseCode);
    }

    private static ResponseEntity<Response> responseWithData(HttpStatus status, Object data, ResponseCode responseCode) {
        return ResponseEntity.status(status).body(
                Response.builder()
                        .status(status)
                        .message(responseCode.getMessage())
                        .timestamp(LocalDateTime.now())
                        .data(data)
                        .code(responseCode)
                        .build()
        );
    }

    private static ResponseEntity<Response> response(HttpStatus status, ResponseCode responseCode) {
        return ResponseEntity.status(status).body(
                Response.builder()
                        .status(status)
                        .message(responseCode.getMessage())
                        .timestamp(LocalDateTime.now())
                        .code(responseCode)
                        .build()
        );
    }
    private static ResponseEntity<Response> response(HttpStatus status) {
        return ResponseEntity.status(status).body(
                Response.builder()
                        .status(status)
                        .message("200 OK")
                        .timestamp(LocalDateTime.now())
                        .code(ResponseCode.OK)
                        .build()
        );
    }
}
