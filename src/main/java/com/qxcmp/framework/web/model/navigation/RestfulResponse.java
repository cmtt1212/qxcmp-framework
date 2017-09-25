package com.qxcmp.framework.web.model.navigation;

import lombok.Data;

/**
 * RESTFUL 响应实体
 *
 * @author Aaric
 */
@Data
public class RestfulResponse {

    private String status;

    private String code;

    private String message;

    private String developerMessage;

    private String moreInfo;

    public RestfulResponse(String status) {
        this.status = status;
    }

    public RestfulResponse(String status, String code) {
        this.status = status;
        this.code = code;
    }

    public RestfulResponse(String status, String code, String message) {
        this.status = status;
        this.code = code;
        this.message = message;
    }

    public RestfulResponse(String status, String code, String message, String developerMessage) {
        this.status = status;
        this.code = code;
        this.message = message;
        this.developerMessage = developerMessage;
    }

    public RestfulResponse(String status, String code, String message, String developerMessage, String moreInfo) {
        this.status = status;
        this.code = code;
        this.message = message;
        this.developerMessage = developerMessage;
        this.moreInfo = moreInfo;
    }
}
