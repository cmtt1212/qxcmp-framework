package com.qxcmp.framework.view.form;

import com.qxcmp.framework.view.annotation.FormView;
import com.qxcmp.framework.view.annotation.FormViewField;

import java.util.ArrayList;
import java.util.List;

@FormView
public class TestForm2 {

    @FormViewField(type = InputFiledType.HIDDEN)
    private Long id;

    @FormViewField(type = InputFiledType.LABEL)
    private String nickname;

    @FormViewField(type = InputFiledType.TEXT)
    private String username;

    @FormViewField(type = InputFiledType.PASSWORD)
    private String password;

    @FormViewField(type = InputFiledType.NUMBER)
    private int age;

    @FormViewField(enumCandidate = true, enumCandidateClass = Sex.class)
    private Sex sex;

    @FormViewField(consumeCandidate = true)
    private List<String> labels = new ArrayList<>();

    @FormViewField(type = InputFiledType.TEXTAREA)
    private String description;

    @FormViewField(type = InputFiledType.CAPTCHA)
    private String captcha;

    @FormViewField(type = InputFiledType.FILE)
    private String portrait;

    @FormViewField(type = InputFiledType.SWITCH)
    private boolean subscribe;

    public enum Sex {
        MAIL,
        FEMAIL
    }
}
