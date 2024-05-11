package com.account.yomankum.mail.domain;

import lombok.Getter;

import java.util.HashMap;
import java.util.Map;

@Getter
public class Mail {

    private String title;
    private String mailTemplate;
    private String destination;
    private Map<String, Object> attributes = new HashMap<>();

    public Mail(String title, String mailTemplate, String destination){
        this.title = title;
        this.mailTemplate = mailTemplate;
        this.destination = destination;
    }

    public void addAttribute(String key, Object value){
        attributes.put(key, value);
    }

}
