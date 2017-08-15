package com.qxcmp.framework.view.form;

import com.qxcmp.framework.view.support.FormViewHelper;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.http.HttpMethod;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class FormViewHelperTest {

    private ReloadableResourceBundleMessageSource messageSource;

    private List<String> labels = new ArrayList<>();

    @Before
    public void setUp() throws Exception {
        messageSource = new ReloadableResourceBundleMessageSource();
        messageSource.setBasenames("classpath:useI18n/qxcmp", "classpath:useI18n/ValidationMessages");
        messageSource.setDefaultEncoding("UTF-8");

        labels.add("label1");
        labels.add("label2");
        labels.add("label3");
        labels.add("label4");
        labels.add("label5");
    }

    @Test(expected = NullPointerException.class)
    public void testNoAnnotation() throws Exception {
        new FormViewHelper().next(new TestForm1());
    }

    @Test
    public void testDefaultSettings() throws Exception {
        FormViewEntity formViewEntity = new FormViewHelper().next(new TestForm2(), labels);

        assertEquals("", formViewEntity.getClassName());
        assertEquals("未命名表单", formViewEntity.getCaption());
        assertEquals("暂无描述", formViewEntity.getDescription());
        assertEquals("/test2", formViewEntity.getAction());
        assertEquals("save", formViewEntity.getSubmitIcon());
        assertEquals("提交", formViewEntity.getSubmitTitle());
        assertEquals(HttpMethod.POST, formViewEntity.getMethod());
        assertFalse(formViewEntity.isDisableSubmitIcon());
        assertFalse(formViewEntity.isDisableSubmitTitle());
        assertEquals(11, formViewEntity.getFields().size());

        FormViewFieldEntity idFieldEntity = formViewEntity.getFields().get(0);
        assertTrue(idFieldEntity instanceof HiddenFieldEntity);
        assertEquals("id", idFieldEntity.getField());
        assertEquals("标签", idFieldEntity.getLabel());
        assertEquals("暂无描述", idFieldEntity.getDescription());
        assertEquals("请输入", idFieldEntity.getPlaceholder());
        assertEquals("", idFieldEntity.getAccept());
        assertEquals("标签", idFieldEntity.getAlt());
        assertEquals("", idFieldEntity.getMax());
        assertEquals("", idFieldEntity.getMin());
        assertEquals(0, idFieldEntity.getMaxLength());
        assertEquals("id", idFieldEntity.getCandidateValueIndex());
        assertEquals("id", idFieldEntity.getCandidateTextIndex());
        assertFalse(idFieldEntity.isAutoFocus());
        assertFalse(idFieldEntity.isDisabled());
        assertFalse(idFieldEntity.isMultiple());
        assertFalse(idFieldEntity.isReadOnly());
        assertTrue(idFieldEntity.isRequired());
        assertFalse(idFieldEntity.isCandidateI18n());

        FormViewFieldEntity nicknameFieldEntity = formViewEntity.getFields().get(1);
        assertTrue(nicknameFieldEntity instanceof LabelFieldEntity);
        LabelFieldEntity labelFieldEntity = (LabelFieldEntity) nicknameFieldEntity;
        assertTrue(labelFieldEntity.isReadOnly());

        FormViewFieldEntity usernameFieldEntity = formViewEntity.getFields().get(2);
        assertTrue(usernameFieldEntity instanceof TextFieldEntity);

        FormViewFieldEntity passwordFieldEntity = formViewEntity.getFields().get(3);
        assertTrue(passwordFieldEntity instanceof PasswordFieldEntity);

        FormViewFieldEntity ageFieldEntity = formViewEntity.getFields().get(4);
        assertTrue(ageFieldEntity instanceof NumberFieldEntity);

        FormViewFieldEntity sexFieldEntity = formViewEntity.getFields().get(5);
        assertTrue(sexFieldEntity instanceof SelectFieldEntity);
        SelectFieldEntity sexSelectFieldEntity = (SelectFieldEntity) sexFieldEntity;
        assertEquals(2, sexSelectFieldEntity.getCandidates().size());
        assertEquals("toString()", sexSelectFieldEntity.getCandidateValueIndex());
        assertEquals("toString()", sexSelectFieldEntity.getCandidateTextIndex());
        assertFalse(sexSelectFieldEntity.isMultiple());

        FormViewFieldEntity labelsFieldEntity = formViewEntity.getFields().get(6);
        assertTrue(labelsFieldEntity instanceof SelectFieldEntity);
        SelectFieldEntity labelsSelectFieldEntity = (SelectFieldEntity) labelsFieldEntity;
        assertEquals(5, labelsSelectFieldEntity.getCandidates().size());
        assertEquals("id", labelsSelectFieldEntity.getCandidateValueIndex());
        assertEquals("id", labelsSelectFieldEntity.getCandidateTextIndex());
        assertTrue(labelsSelectFieldEntity.isMultiple());

        FormViewFieldEntity descriptionFieldEntity = formViewEntity.getFields().get(7);
        assertTrue(descriptionFieldEntity instanceof TextAreaEntity);
        TextAreaEntity textAreaEntity = (TextAreaEntity) descriptionFieldEntity;
        assertEquals(0, textAreaEntity.getRows());

        FormViewFieldEntity captchaFieldEntity = formViewEntity.getFields().get(8);
        assertTrue(captchaFieldEntity instanceof CaptchaFieldEntity);
        CaptchaFieldEntity captchaCaptchaFieldEntity = (CaptchaFieldEntity) captchaFieldEntity;
        assertEquals("/api/captcha/image", captchaCaptchaFieldEntity.getUrl());
        assertEquals(CaptchaType.IMAGE, captchaCaptchaFieldEntity.getCaptchaType());

        FormViewFieldEntity portraitFieldEntity = formViewEntity.getFields().get(9);
        assertTrue(portraitFieldEntity instanceof FileFieldEntity);

        FormViewFieldEntity subscribeFieldEntity = formViewEntity.getFields().get(10);
        assertTrue(subscribeFieldEntity instanceof SwitchFieldEntity);
        SwitchFieldEntity subscribeSwitchFieldEntity = (SwitchFieldEntity) subscribeFieldEntity;
        assertEquals("启用", subscribeSwitchFieldEntity.getLabelOn());
        assertEquals("禁用", subscribeSwitchFieldEntity.getLabelOff());
    }
}