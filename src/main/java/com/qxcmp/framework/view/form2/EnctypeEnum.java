package com.qxcmp.framework.view.form2;

public enum EnctypeEnum {

    APPLICATION("application/x-www-form-urlencoded"),
    MULTIPART("multipart/form-data"),
    TEXT("text/plain");

    private String name;

    EnctypeEnum(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
