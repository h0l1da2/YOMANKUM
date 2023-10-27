package com.account.yomankum.controller;

import com.account.yomankum.web.Response;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MainController {

    @GetMapping
    public ResponseEntity<Response> main() {
        return Response.ok();
    }

}
