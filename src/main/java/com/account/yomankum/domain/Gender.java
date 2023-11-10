package com.account.yomankum.domain;

public enum Gender {

    MALE("남자"), FEMALE("여자");

    private String name;

    Gender(String name) {
        this.name = name;
    }
}
