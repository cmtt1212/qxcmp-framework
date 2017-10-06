package com.qxcmp.framework.web.view.html;


import lombok.Getter;

public class AbstractHTMLElement {

    @Getter
    private String url;

    public AbstractHTMLElement(String url) {
        this.url = url;
    }
}
