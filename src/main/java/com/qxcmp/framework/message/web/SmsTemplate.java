package com.qxcmp.framework.message.web;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SmsTemplate {

    public SmsTemplate() {
    }

    private String name;

    private String code;
}
