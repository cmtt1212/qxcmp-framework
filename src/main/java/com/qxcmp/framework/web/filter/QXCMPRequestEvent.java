package com.qxcmp.framework.web.filter;

import lombok.Getter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Getter
public class QXCMPRequestEvent {

    private final HttpServletRequest request;

    private final HttpServletResponse response;

    public QXCMPRequestEvent(HttpServletRequest request, HttpServletResponse response) {
        this.request = request;
        this.response = response;
    }
}
